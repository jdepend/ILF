package com.qeweb.busplatform.pomanage.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.dao.ia.IBP_PurchaseOrderExecutionDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;

/**
 * 采购订单执行情况
 */
public class BP_PurchaseOrderExecutionBO extends BusinessObject {

	private static final long serialVersionUID = 9028174439316419733L;

	private Double receiveQty;			// 实收数量
	private Double goodsRejectQty;		// 验退数量
	private Double onwayQty;			// 在途数量
	private Double unsendQty;			// 未发数量

	private BP_PurchaseOrderBO purchaseOrderBO;				//采购订单
	private BP_PurchaseOrderItemBO purchaseOrderItemBO;		//采购订单明细

	//查询条件
	private String purchaseNo; 			// 采购订单号
	private Timestamp orderTime;		// 要求到货时间
	private String vendorCode;			// 供应商编码
	private String vendorName;			// 供应商编码
	private String buyerUserCode;		// 采购员编码
	private String buyerUserName;		// 采购员编码

	private IBP_PurchaseOrderExecutionDao orderExecutionDao;

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(bot == null)
			bot = new BOTemplate();

		Map<String, Object> botMap = bot.getBotMap();
		botMap.put(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE + "");
		bot.setBotMap(botMap);
		bot.setOrderMap(queryOrderBy());

		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, BP_PurchaseOrderItemBO.class);

		Page page = getDao().findPageByCriteria(dc, getPageSize(), start);
		setPageRealValues(page);
		return page;
	}

	/**
	 * 设置返回页面Page统计值
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	private void setPageRealValues(Page page) {
		BP_PurchaseOrderExecutionBO orderExecBO = null;
		List<BP_PurchaseOrderExecutionBO> orderExecList = new LinkedList<BP_PurchaseOrderExecutionBO>();
		if(ContainerUtil.isNotNull(page.getItems())) {
			BP_PurchaseOrderStatisticsBO orderStatisticsBO = null;
			for(BP_PurchaseOrderItemBO item : (List<BP_PurchaseOrderItemBO>)page.getItems()) {
				orderExecBO = new BP_PurchaseOrderExecutionBO();
				orderExecBO.setId(item.getId());
				orderExecBO.setPurchaseOrderBO(item.getPurchaseOrderBO());
				orderExecBO.setPurchaseOrderItemBO(item);
				// 统计
				orderStatisticsBO = ContainerUtil.isNull(item.getOrderStatisticsBOs()) ? null : item.getOrderStatisticsBOs().iterator().next();
				// 实收数量
				orderExecBO.setReceiveQty(orderStatisticsBO == null ? 0d : orderStatisticsBO.getReceiveQty());
				// 验退数量
				orderExecBO.setGoodsRejectQty(orderStatisticsBO == null ? 0d : orderStatisticsBO.getGoodsRejectQty());
				// 在途数量
				orderExecBO.setOnwayQty(orderStatisticsBO == null ? 0d : orderStatisticsBO.getOnwayQty());
				// 未发数量
				orderExecBO.setUnsendQty(orderStatisticsBO == null ? item.getOrderQty() : orderStatisticsBO.getUnsendQty());
				// 最后统计时间
				orderExecBO.setLastModifyTime(orderStatisticsBO == null ? null : orderStatisticsBO.getLastModifyTime());

				orderExecList.add(orderExecBO);
			}
			page.setItems(orderExecList);
		}
		initPreferencePage(page);
	}

	/**
	 * 统计订单执行
	 * @throws Exception
	 */
	public void statistics() throws Exception {
		//1、获取需统计的订单明细 [订单行ID, 订单数量, 确认状态, 关闭状态]
		List<Object[]> result = getDao().getPendingStatisticsOrderItems();
		if(ContainerUtil.isNull(result))
			return;
		//2、按明细进行统计
		BP_VendorGoodsDeliveryItemBO deliveryItemBO = new BP_VendorGoodsDeliveryItemBO();
		BP_BuyerGoodsReceiveItemBO receiveItemBO = new BP_BuyerGoodsReceiveItemBO();

		Long itemId = null;				//订单明细ID
		double orderQty = 0d;			//订单数量
		Integer closeStatus = null;		//关闭状态
		double receiveQty = 0d;			//实收数量
		double goodsRejectQty = 0d;		//验退数量
		double onwayQty = 0d;			//在途数量
		double unsendQty = 0d;			//未发数量
		BP_PurchaseOrderStatisticsBO orderStatistics = null;
		//处理数据记录数
		int index = 0;
		for(Object[] itemInfo : result) {
			index ++;
			itemId = ((Number)itemInfo[0]).longValue();
			orderQty = ((Number)itemInfo[1]).doubleValue();
			closeStatus = ((Number)itemInfo[3]).intValue();

			orderStatistics = getDao().getOrderStatisticsByItmeId(itemId);

			receiveQty = receiveItemBO.getOrderItemReceiveQty(itemId);

			goodsRejectQty = receiveItemBO.getOrderItemGoodsRejectQty(itemId);

			onwayQty = deliveryItemBO.getOrderItemOnWayQty(itemId);

			//对于订单执行情况    未发= 订单 - 在途 - 实收
			unsendQty = orderQty - (onwayQty + receiveQty);

			if(orderStatistics == null) {
				orderStatistics = new BP_PurchaseOrderStatisticsBO();
				orderStatistics.setPurchaseOrderItemId(itemId);
				orderStatistics.setReceiveQty(receiveQty);
				orderStatistics.setGoodsRejectQty(goodsRejectQty);
				orderStatistics.setOnwayQty(onwayQty);
				orderStatistics.setUnsendQty(unsendQty);
				//标示是否需要再次统计
				if(closeStatus != null && closeStatus == CloseStatus.YES)
					orderStatistics.setIsStatistics(BP_PurchaseOrderStatisticsBO.STATISTICS_NO);
				else
					orderStatistics.setIsStatistics(BP_PurchaseOrderStatisticsBO.STATISTICS_YES);

				orderStatistics.insert();
			}
			else {
				orderStatistics.setReceiveQty(receiveQty);
				orderStatistics.setGoodsRejectQty(goodsRejectQty);
				orderStatistics.setOnwayQty(onwayQty);
				orderStatistics.setUnsendQty(unsendQty);

				if(closeStatus != null && closeStatus == CloseStatus.YES)
					orderStatistics.setIsStatistics(BP_PurchaseOrderStatisticsBO.STATISTICS_NO);

				orderStatistics.update();
			}

			if(index % 50 == 0)
				getDao().flush();
		}
	}

	public IBP_PurchaseOrderExecutionDao getDao() {
		return getOrderExecutionDao();
	}

	public Double getReceiveQty() {
		return receiveQty;
	}

	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public Double getGoodsRejectQty() {
		return goodsRejectQty;
	}

	public void setGoodsRejectQty(Double goodsRejectQty) {
		this.goodsRejectQty = goodsRejectQty;
	}

	public Double getOnwayQty() {
		return onwayQty;
	}

	public void setOnwayQty(Double onwayQty) {
		this.onwayQty = onwayQty;
	}

	public Double getUnsendQty() {
		return unsendQty;
	}

	public void setUnsendQty(Double unsendQty) {
		this.unsendQty = unsendQty;
	}

	public BP_PurchaseOrderBO getPurchaseOrderBO() {
		return purchaseOrderBO;
	}

	public void setPurchaseOrderBO(BP_PurchaseOrderBO purchaseOrderBO) {
		this.purchaseOrderBO = purchaseOrderBO;
	}

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBuyerUserCode() {
		return buyerUserCode;
	}

	public void setBuyerUserCode(String buyerUserCode) {
		this.buyerUserCode = buyerUserCode;
	}

	public String getBuyerUserName() {
		return buyerUserName;
	}

	public void setBuyerUserName(String buyerUserName) {
		this.buyerUserName = buyerUserName;
	}

	public IBP_PurchaseOrderExecutionDao getOrderExecutionDao() {
		if(orderExecutionDao == null)
			orderExecutionDao = (IBP_PurchaseOrderExecutionDao)SpringConstant.getCTX().getBean("IBP_PurchaseOrderExecutionDao");
		return orderExecutionDao;
	}

	public void setOrderExecutionDao(IBP_PurchaseOrderExecutionDao orderExecutionDao) {
		this.orderExecutionDao = orderExecutionDao;
	}

}