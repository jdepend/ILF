package com.qeweb.busplatform.goodsdelivery.bo;

import java.util.List;

import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.goodsdelivery.dao.ia.IBP_VendorGoodsDeliveryItemDao;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.EmptyBop;

/**
 * 发货单明细
 */
public class BP_VendorGoodsDeliveryItemBO extends BusinessObject {

	private static final long serialVersionUID = 2353368952109018755L;

	private Long deliveryId;			//发货单ID
	private Long purchaseGoodsPlanId;	//供货计划ID
	private Long purchaseItemId;		//采购明细ID
	private Integer itemNo;				//行号
	private String unitName;			//单位
	private Double deliveryQty; 		//已发货数量
	private String remark;				//备注
	private Integer lockStatus = LockStatus.NO;	//锁定状态
	private String lockMsg;						//锁定日志，显示锁定原因

	private BP_PurchaseGoodsPlanBO purchaseGoodsPlan;		//供货计划
	private BP_PurchaseOrderItemBO purchaseOrderItemBO; 	//采购订单明细
	private BP_VendorGoodsDeliveryBO vendorGoodsDelivery;	//发货单主信息

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

	private IBP_VendorGoodsDeliveryItemDao goodsDeliveryItemDao;

	public BP_VendorGoodsDeliveryItemBO() {
		super();
		addBOP("remark", new EmptyBop(200));
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		//根据采购订单查询发货信息
		if (StringUtils.isEqual("bp_PurchaseOrderBO", ctxBot.getBoName())) {
			BOTemplate thisBOT = new BOTemplate();
			thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
			if(ctxBot.getValue("id") != null) {
				List<Long> poItemIds = new BP_PurchaseOrderItemBO().getPOItemIds(Long.valueOf(ctxBot.getValue("id") + ""));
				if(ContainerUtil.isNull(poItemIds))
					return null;
				thisBOT.push("purchaseOrderItemBO.id", poItemIds);
			}
			//已发货
			thisBOT.push("vendorGoodsDelivery.deliveryStatus", DeliveryStatus.YES);
			thisBOT.setBoName(bot.getBoName());
			return super.query(thisBOT);
		}
		//根据发货单查询发货信息
		else if (StringUtils.isEqual("bp_VendorGoodsDeliveryBO", ctxBot.getBoName())
				|| StringUtils.isEqual("bp_PendingReceiveBO", ctxBot.getBoName())) {
			return getDeliveryItems(ctxBot);
		}
		else {
			return super.query(ctxBot, start);
		}
	}

	/**
	 * 根据发货单主信息获取发货单明细
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public Object getDeliveryItems(BOTemplate bot) throws Exception {
		BOTemplate thisBOT = new BOTemplate();
		thisBOT.setColumnHeaderOrderMap(bot.getColumnHeaderOrderMap());
		thisBOT.push("vendorGoodsDelivery.id", bot.getValue("id"));
		return super.query(thisBOT);
	}

	/**
	 * 根据采购订单号获取发货单IDs
	 * @param purchaseNo
	 * @return
	 */
	public List<Long> getDeliveryIds(String purchaseNo) {
		return getDao().getDeliveryIds(purchaseNo);
	}

	/**
	 * 获取对应订单明细行的已发数量
	 * @return
	 */
	public double getOrderItemDeliveryQty(long orderItemId) {
		return getDao().getOrderItemDeliveryQty(orderItemId);
	}

	/**
	 * 获取订单明细行（发货单占用数量，已创建未发货数量）
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemOccupyQty(long orderItemId) {
		return getDao().getOrderItemOccupyQty(orderItemId);
	}

	/**
	 * 获取订单行已收货的总发货数量
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemHaveRecDlvQty(long orderItemId) {
		return getDao().getOrderItemHaveRecDlvQty(orderItemId);
	}

	/**
	 * 获取订单行总发货数量（包括创建发货单占用及已发货的）
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemTotalDlvQty(long orderItemId) {
		return getDao().getOrderItemTotalDlvQty(orderItemId);
	}

	/**
	 * 获取对应供货计划行的已发数量
	 * @param orderPlanId
	 * @return
	 */
	public double getPOPlanDeliveryQty(long orderPlanId) {
		return getDao().getPOPlanDeliveryQty(orderPlanId);
	}

	/**
	 * 根据订单明细行获取在途数量
	 * @param orderItemId
	 * @return
	 */
	public double getOrderItemOnWayQty(long orderItemId) {
		return getDao().getOrderItemOnWayQty(orderItemId);
	}

	/**
	 * 验证收货单对应的订单明细是否超发，超发则锁定与之关联的未发货发货单
	 * @param receiveItemBO
	 * @param excessQty 更改差额数量（新数量数量 - 原收货数量）
	 * @throws Exception
	 */
	public void checkExcess(BP_BuyerGoodsReceiveItemBO receiveItemBO, double excessQty, StringBuffer logBuf) throws Exception {
		//1、获取收货单对应的订单明细行
		BP_PurchaseOrderItemBO orderItemBO = receiveItemBO.getPurchaseOrderItemBO();
		//2、计算订单明细的未发数量，(未发数量-excessQty) > 0 return...
		double orderQty = orderItemBO.getOrderQty();

		double alreadyRecQty = receiveItemBO.getOrderItemReceiveQty(orderItemBO.getId());
		double onwayQty = getOrderItemOnWayQty(orderItemBO.getId());
		double occupyQty = getOrderItemOccupyQty(orderItemBO.getId());
		//未发数量 = 订单数量-(在途+实收+创建未发)
		double unDlvQty = orderQty - (onwayQty + alreadyRecQty + occupyQty);
		if(unDlvQty > excessQty)
			return;

		//3、否则获取创建未发的发货单， excessQty - 发货数量 直到 <=0 为止 ，锁定对应的发货单
		List<BP_VendorGoodsDeliveryItemBO> deliveryItemBOs = getUnRecItemByPOItemId(orderItemBO.getId());
		if(ContainerUtil.isNull(deliveryItemBOs)) {
			logBuf.append("-->订单：").append(orderItemBO.getPurchaseOrderBO().getPurchaseNo()).append(",行：").append(orderItemBO.getItemNO()).append("存在超量发货异常...").append("\n");
		}
		else {
			BP_VendorGoodsDeliveryBO vendorGoodsDeliveryBO = null;
			for(BP_VendorGoodsDeliveryItemBO dlvItemBO : deliveryItemBOs) {
				excessQty = excessQty - dlvItemBO.getDeliveryQty();
				dlvItemBO.setLockStatus(LockStatus.YES);
				dlvItemBO.setLockMsg("此发货明细关联订单行存在超量发货，请取消改发货单！");
				dlvItemBO.update();
				vendorGoodsDeliveryBO = dlvItemBO.getVendorGoodsDelivery();
				vendorGoodsDeliveryBO.setLockStatus(LockStatus.YES);
				vendorGoodsDeliveryBO.update();
				if(excessQty <= 0)
					break;
			}
		}
	}

	public List<BP_VendorGoodsDeliveryItemBO> getUnRecItemByPOItemId(long orderItemId) throws Exception {
		return getDao().getUnRecItemByPOItemId(orderItemId);
	}

	public IBP_VendorGoodsDeliveryItemDao getDao() {
		return getGoodsDeliveryItemDao();
	}

	public BP_VendorGoodsDeliveryBO getVendorGoodsDelivery() {
		return vendorGoodsDelivery;
	}

	public void setVendorGoodsDelivery(BP_VendorGoodsDeliveryBO vendorGoodsDelivery) {
		this.vendorGoodsDelivery = vendorGoodsDelivery;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getDeliveryQty() {
		return this.deliveryQty;
	}

	public void setDeliveryQty(Double deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

	public Long getPurchaseItemId() {
		return purchaseItemId;
	}

	public void setPurchaseItemId(Long purchaseItemId) {
		this.purchaseItemId = purchaseItemId;
	}


	public BP_PurchaseGoodsPlanBO getPurchaseGoodsPlan() {
		return purchaseGoodsPlan;
	}

	public void setPurchaseGoodsPlan(BP_PurchaseGoodsPlanBO purchaseGoodsPlan) {
		this.purchaseGoodsPlan = purchaseGoodsPlan;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getPurchaseGoodsPlanId() {
		return purchaseGoodsPlanId;
	}

	public void setPurchaseGoodsPlanId(Long purchaseGoodsPlanId) {
		this.purchaseGoodsPlanId = purchaseGoodsPlanId;
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

	public IBP_VendorGoodsDeliveryItemDao getGoodsDeliveryItemDao() {
		if(goodsDeliveryItemDao == null)
			goodsDeliveryItemDao = (IBP_VendorGoodsDeliveryItemDao)SpringConstant.getCTX().getBean("IBP_VendorGoodsDeliveryItemDao");
		return goodsDeliveryItemDao;
	}

	public void setGoodsDeliveryItemDao(
			IBP_VendorGoodsDeliveryItemDao goodsDeliveryItemDao) {
		this.goodsDeliveryItemDao = goodsDeliveryItemDao;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getLockMsg() {
		return lockMsg;
	}

	public void setLockMsg(String lockMsg) {
		this.lockMsg = lockMsg;
	}

}