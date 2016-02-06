package com.qeweb.busplatform.goodsreceive.bo;

import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsreceive.dao.ia.IBP_BuyerGoodsReceiveItemDao;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 收货明细
 */
public class BP_BuyerGoodsReceiveItemBO extends BusinessObject {

	private static final long serialVersionUID = -1419156438513011915L;

	private Integer itemNo; 		//行号
	private Double receiveQty; 		//收货(实收)数量
	private Double goodsRejectQty;	//验退数量

	private BP_PurchaseOrderItemBO purchaseOrderItemBO; 			// 采购订单主信息
	private BP_PurchaseGoodsPlanBO purchaseGoodsPlanBO; 			// 供货计划
	private BP_VendorGoodsDeliveryItemBO vendorGoodsDeliveryItemBO; // 发货明细
	private BP_BuyerGoodsReceiveBO buyerGoodsReceiveBO;				// 收货单主信息

	//扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	private String attr_6;
	private String attr_7;
	private String attr_8;
	private String attr_9;
	private String attr_10;
	private String attr_11;
	private String attr_12;
	private String attr_13;
	private String attr_14;
	private String attr_15;
	private String attr_16;
	private String attr_17;
	private String attr_18;
	private String attr_19;
	private String attr_20;

	private IBP_BuyerGoodsReceiveItemDao goodsReceiveItemDao;

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		//根据采购订单查询收货记录
		if (StringUtils.isEqual("bp_PurchaseOrderBO", ctxBot.getBoName())) {
			thisBOT = new BOTemplate();
			thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
			if(bot.getValue("id") != null)
				thisBOT.push("purchaseOrderItemBO.id", new BP_PurchaseOrderItemBO().getPOItemIds(Long.valueOf(bot.getValue("id") + "")));

			thisBOT.setBoName(bot.getBoName());
			return super.query(thisBOT);
		}
		//根据收货单查询收货信息
		else if(StringUtils.isEqual("bp_BuyerGoodsReceiveBO", ctxBot.getBoName())){
			thisBOT = new BOTemplate();
			thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
			if (ctxBot.getValue("id") != null)
				thisBOT.push("buyerGoodsReceiveBO.id", bot.getValue("id"));

			thisBOT.setBoName(bot.getBoName());
			return super.query(thisBOT);
		}

		return super.query(thisBOT, start);
	}

	/**
	 * 保存收货单明细
	 * @param receiveItems
	 * @throws Exception
	 */
	public void saveReceiveItems(Set<BP_BuyerGoodsReceiveItemBO> receiveItems) throws Exception {
		if(ContainerUtil.isNull(receiveItems))
			return;

		for(BP_BuyerGoodsReceiveItemBO recItem : receiveItems) {
			recItem.insert();
		}
	}

	/**
	 * 更新收货单明细
	 * @param receiveBO
	 * @param oldReceiveBO
	 * @param logBuf
	 * @throws Exception
	 */
	public void updateReceiveItemBOs(BP_BuyerGoodsReceiveBO receiveBO,BP_BuyerGoodsReceiveBO oldReceiveBO, StringBuffer logBuf) throws Exception {
		Set<BP_BuyerGoodsReceiveItemBO> newReceiveItems = receiveBO.getBuyerGoodsReceiveItemBOs();
		Set<BP_BuyerGoodsReceiveItemBO> oldReceiveItems = oldReceiveBO.getBuyerGoodsReceiveItemBOs();

		BP_VendorGoodsDeliveryItemBO deliveryItemBO = new BP_VendorGoodsDeliveryItemBO();
		//标实是否有对应历史
		boolean hasOld = false;
		logBuf.append("-->更新收货单：" + receiveBO.getReceiveNo() + "开始...").append("\n");
		for(BP_BuyerGoodsReceiveItemBO newItem : newReceiveItems) {
			for(BP_BuyerGoodsReceiveItemBO oldItem : oldReceiveItems) {
				if(newItem.getItemNo() == oldItem.getItemNo().intValue()) {
					logBuf.append("-->收货单更新收货明细，行号" + newItem.getItemNo()).append("\n");
					//验证是否有修改，涉及到收货数量的更改，不允许超发的情况下需验证是否有超发的情况
					if(newItem.getReceiveQty() > oldItem.getReceiveQty().doubleValue() &&
							!BusSettingConstants.isExcessDelivery())
						deliveryItemBO.checkExcess(oldItem, (newItem.getReceiveQty() - oldItem.getReceiveQty()), logBuf);
					long _recItemId = oldItem.getId();
					PropertyUtils.copyPropertyWithOutNull(oldItem, newItem);
					oldItem.setBuyerGoodsReceiveBO(oldReceiveBO);
					oldItem.setId(_recItemId);
					oldItem.update();
					hasOld = true;
					break;
				}
			}

			if(!hasOld) {
				newItem.setBuyerGoodsReceiveBO(oldReceiveBO);
				newItem.insert();
				logBuf.append("-->收货单新增收货明细，行号" + newItem.getItemNo()).append("\n");
			}
			hasOld = false;
		}
		logBuf.append("-->更新收货单：" + receiveBO.getReceiveNo() + "结束...").append("\n");
	}

	/**
	 * 根据订单号获取收货单IDs
	 * @param purchaseNo
	 * @return
	 */
	public List<Long> getReceiveIds(String purchaseNo) {
		return getDao().getReceiveIds(purchaseNo);
	}

	/**
	 * 根据订单行id获取订单行收货数量
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemReceiveQty(long orderItemId) {
		return getDao().getOrderItemReceiveQty(orderItemId);
	}

	/**
	 * 根据订单行id获取订单行验退数量
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemGoodsRejectQty(long orderItemId) {
		return getDao().getOrderItemGoodsRejectQty(orderItemId);
	}

	public IBP_BuyerGoodsReceiveItemDao getDao() {
		return getGoodsReceiveItemDao();
	}

	public BP_BuyerGoodsReceiveBO getBuyerGoodsReceiveBO() {
		return this.buyerGoodsReceiveBO;
	}

	public void setBuyerGoodsReceiveBO(BP_BuyerGoodsReceiveBO buyerGoodsReceiveBO) {
		this.buyerGoodsReceiveBO = buyerGoodsReceiveBO;
	}

	public BP_VendorGoodsDeliveryItemBO getVendorGoodsDeliveryItemBO() {
		return this.vendorGoodsDeliveryItemBO;
	}

	public void setVendorGoodsDeliveryItemBO(BP_VendorGoodsDeliveryItemBO vendorGoodsDeliveryItemBO) {
		this.vendorGoodsDeliveryItemBO = vendorGoodsDeliveryItemBO;
	}

	public Integer getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getReceiveQty() {
		return this.receiveQty;
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

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

	public BP_PurchaseGoodsPlanBO getPurchaseGoodsPlanBO() {
		return purchaseGoodsPlanBO;
	}

	public void setPurchaseGoodsPlanBO(BP_PurchaseGoodsPlanBO purchaseGoodsPlanBO) {
		this.purchaseGoodsPlanBO = purchaseGoodsPlanBO;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}

	public String getAttr_6() {
		return attr_6;
	}

	public void setAttr_6(String attr_6) {
		this.attr_6 = attr_6;
	}

	public String getAttr_7() {
		return attr_7;
	}

	public void setAttr_7(String attr_7) {
		this.attr_7 = attr_7;
	}

	public String getAttr_8() {
		return attr_8;
	}

	public void setAttr_8(String attr_8) {
		this.attr_8 = attr_8;
	}

	public String getAttr_9() {
		return attr_9;
	}

	public void setAttr_9(String attr_9) {
		this.attr_9 = attr_9;
	}

	public String getAttr_10() {
		return attr_10;
	}

	public void setAttr_10(String attr_10) {
		this.attr_10 = attr_10;
	}

	public String getAttr_11() {
		return attr_11;
	}

	public void setAttr_11(String attr_11) {
		this.attr_11 = attr_11;
	}

	public String getAttr_12() {
		return attr_12;
	}

	public void setAttr_12(String attr_12) {
		this.attr_12 = attr_12;
	}

	public String getAttr_13() {
		return attr_13;
	}

	public void setAttr_13(String attr_13) {
		this.attr_13 = attr_13;
	}

	public String getAttr_14() {
		return attr_14;
	}

	public void setAttr_14(String attr_14) {
		this.attr_14 = attr_14;
	}

	public String getAttr_15() {
		return attr_15;
	}

	public void setAttr_15(String attr_15) {
		this.attr_15 = attr_15;
	}

	public String getAttr_16() {
		return attr_16;
	}

	public void setAttr_16(String attr_16) {
		this.attr_16 = attr_16;
	}

	public String getAttr_17() {
		return attr_17;
	}

	public void setAttr_17(String attr_17) {
		this.attr_17 = attr_17;
	}

	public String getAttr_18() {
		return attr_18;
	}

	public void setAttr_18(String attr_18) {
		this.attr_18 = attr_18;
	}

	public String getAttr_19() {
		return attr_19;
	}

	public void setAttr_19(String attr_19) {
		this.attr_19 = attr_19;
	}

	public String getAttr_20() {
		return attr_20;
	}

	public void setAttr_20(String attr_20) {
		this.attr_20 = attr_20;
	}

	public IBP_BuyerGoodsReceiveItemDao getGoodsReceiveItemDao() {
		if(goodsReceiveItemDao == null)
			goodsReceiveItemDao = (IBP_BuyerGoodsReceiveItemDao)SpringConstant.getCTX().getBean("IBP_BuyerGoodsReceiveItemDao");
		return goodsReceiveItemDao;
	}

	public void setGoodsReceiveItemDao(IBP_BuyerGoodsReceiveItemDao goodsReceiveItemDao) {
		this.goodsReceiveItemDao = goodsReceiveItemDao;
	}

}