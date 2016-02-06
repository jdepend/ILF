package com.qeweb.demo.common.bo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.frameworkbop.CityBOP;
import com.qeweb.framework.frameworkbop.CountryBOP;
import com.qeweb.framework.frameworkbop.ProvinceBOP;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * demo: 采购订单BO
 *
 */
public class DemoPurchaseOrderBO extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8827296536225081425L;
	
	private String purchaseNo; 			// 采购订单号
	private Timestamp purchaseTime;		// 采购日期
	private Integer changeStatus; 		// 修改状态
	private Integer publishStatus; 		// 发布状态
	private Timestamp publishTime; 		// 发布时间
	private Integer confirmStatus; 		// 确认状态
	private Timestamp confirmTime; 		// 确认时间
	private Integer deliveryStatus; 	// 发货状态
	private Integer receiveStatus; 		// 收货状态
	private Integer closeStatus; 		// 关闭状态
	private Timestamp closeTime; 		// 关闭时间
	private String receiveFactory;		// 收货方
	private String deliveryAddress;		// 送货地址
	private String creditTermsCode;		// 付款条件编码
	private String taxesCategories;		// 税种
	private String currencyCategories;	// 币种
	private Double exchangeRate;		// 汇率
	private Double taxesRate;			// 税率
	private String remark;				// 备注
	private Integer lockStatus;			// 异常锁定状态，更新订单异常时锁定此订单 0：正常 1：异常
	private String feedback;			// 反馈
	private Integer modifyCount;		// 修改次数
	private Integer manlockStatus = LockStatus.NO;		// 人工锁定状态
	private Timestamp manlockTime; 						// 人工锁定时间
	private String manlockReason;						// 人工锁定原因
	private Timestamp modifyTime;
	private Integer country;
	private Integer province;
	private Integer city;
	
	private DemoOrgBO buyer= new DemoOrgBO();			//采购商
	private DemoOrgBO vendor = new DemoOrgBO();			//供应商

	private Set<DemoPOItemBO> poItems = new HashSet<DemoPOItemBO>(); // 采购明细

	//扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	
	public DemoPurchaseOrderBO() {
		super();
		addBOP("publishStatus", new StatusBOP());
		addBOP("confirmStatus", new StatusBOP());
		addBOP("deliveryStatus", new StatusBOP());
		addBOP("receiveStatus", new StatusBOP());
		addBOP("closeStatus", new StatusBOP());
		addBOP("lockStatus", new StatusBOP());
		addBOP("purchaseTime", new DateBOP(DateBOP.YYYY_MM_DD));
		addBOP("confirmTime", new DateBOP(DateBOP.YYYY_MM_DD_HH_MM));
		addBOP("publishTime", new DateBOP(DateBOP.YYYY_MM_DD_HH_MM_SS));
		addBOP("country", new CountryBOP());
		addBOP("province", new ProvinceBOP());
		addBOP("city", new CityBOP());
	}
	
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	public Integer getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(Integer changeStatus) {
		this.changeStatus = changeStatus;
	}
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
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
	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Integer getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
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
	public String getReceiveFactory() {
		return receiveFactory;
	}
	public void setReceiveFactory(String receiveFactory) {
		this.receiveFactory = receiveFactory;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getCreditTermsCode() {
		return creditTermsCode;
	}
	public void setCreditTermsCode(String creditTermsCode) {
		this.creditTermsCode = creditTermsCode;
	}
	public String getTaxesCategories() {
		return taxesCategories;
	}
	public void setTaxesCategories(String taxesCategories) {
		this.taxesCategories = taxesCategories;
	}
	public String getCurrencyCategories() {
		return currencyCategories;
	}
	public void setCurrencyCategories(String currencyCategories) {
		this.currencyCategories = currencyCategories;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Double getTaxesRate() {
		return taxesRate;
	}
	public void setTaxesRate(Double taxesRate) {
		this.taxesRate = taxesRate;
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
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Integer getModifyCount() {
		return modifyCount;
	}
	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}
	public Integer getManlockStatus() {
		return manlockStatus;
	}
	public void setManlockStatus(Integer manlockStatus) {
		this.manlockStatus = manlockStatus;
	}
	public Timestamp getManlockTime() {
		return manlockTime;
	}
	public void setManlockTime(Timestamp manlockTime) {
		this.manlockTime = manlockTime;
	}
	public String getManlockReason() {
		return manlockReason;
	}
	public void setManlockReason(String manlockReason) {
		this.manlockReason = manlockReason;
	}
	public DemoOrgBO getBuyer() {
		return buyer;
	}
	public void setBuyer(DemoOrgBO buyer) {
		this.buyer = buyer;
	}
	public DemoOrgBO getVendor() {
		return vendor;
	}
	public void setVendor(DemoOrgBO vendor) {
		this.vendor = vendor;
	}
	public Set<DemoPOItemBO> getPoItems() {
		return poItems;
	}
	public void setPoItems(Set<DemoPOItemBO> poItems) {
		this.poItems = poItems;
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
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
}
