package com.qeweb.demo.common.bo;

import java.sql.Timestamp;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * demo: 订单明细
 */
public class DemoPOItemBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6268906457251725330L;
	// 订单
	private DemoPurchaseOrderBO purchaseOrderBO;
	// 物料
	private DemoMaterial material;

	private Integer itemNO; 					// 行号
	private Integer deliveryStatus; 			// 发货状态
	private Timestamp deliveryStatusChangeTime;
	private Integer confirmStatus; 				// 确认状态
	private Timestamp confirmTime; 				// 确认时间
	private Integer closeStatus; 				// 关闭状态
	private Timestamp closeTime; 				// 关闭时间
	private Double orderQty; 					// 订购数量
	private String unitName; 					// 单位
	private Timestamp orderTime; 				// 要求到货时间
	private Integer modifyCount; 				// 修改次数
	private String feedback; 					// 反馈
	private Double amount; 						// 总额(钱)
	private String taxesCategories; 			// 税种
	private Double taxesRate; 					// 税率
	private Double taxIncludePrice; 			// 含税单价
	private String remark; 						// 备注
	private Integer lockStatus; 				// 锁定状态，更新订单行异常时锁定此订单行 0：正常 1：异常
	private String lockMsg; 					// 锁定日志，显示锁定原因

	// 扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	
	public DemoPOItemBO() {
		addBOP("deliveryStatus", new StatusBOP());
		addBOP("confirmStatus", new StatusBOP());
		addBOP("closeStatus", new StatusBOP());
		addBOP("lockStatus", new StatusBOP());
	}
	
	public DemoPurchaseOrderBO getPurchaseOrderBO() {
		return purchaseOrderBO;
	}
	public void setPurchaseOrderBO(DemoPurchaseOrderBO purchaseOrderBO) {
		this.purchaseOrderBO = purchaseOrderBO;
	}
	public DemoMaterial getMaterial() {
		return material;
	}
	public void setMaterial(DemoMaterial material) {
		this.material = material;
	}
	public Integer getItemNO() {
		return itemNO;
	}
	public void setItemNO(Integer itemNO) {
		this.itemNO = itemNO;
	}
	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Timestamp getDeliveryStatusChangeTime() {
		return deliveryStatusChangeTime;
	}
	public void setDeliveryStatusChangeTime(Timestamp deliveryStatusChangeTime) {
		this.deliveryStatusChangeTime = deliveryStatusChangeTime;
	}
	public Integer getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public Timestamp getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Integer getCloseStatus() {
		return closeStatus;
	}
	public void setCloseStatus(Integer closeStatus) {
		this.closeStatus = closeStatus;
	}
	public Timestamp getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}
	public Double getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getModifyCount() {
		return modifyCount;
	}
	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTaxesCategories() {
		return taxesCategories;
	}
	public void setTaxesCategories(String taxesCategories) {
		this.taxesCategories = taxesCategories;
	}
	public Double getTaxesRate() {
		return taxesRate;
	}
	public void setTaxesRate(Double taxesRate) {
		this.taxesRate = taxesRate;
	}
	public Double getTaxIncludePrice() {
		return taxIncludePrice;
	}
	public void setTaxIncludePrice(Double taxIncludePrice) {
		this.taxIncludePrice = taxIncludePrice;
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

}
