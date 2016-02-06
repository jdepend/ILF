package com.qeweb.busplatform.goodsdelivery.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.busplatform.common.bo.BP_BillNoBO;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.bop.SendStatus;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsdelivery.busoperate.delivery.GoodsDelivery;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.EmptyBopDec;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 发货单主信息
 */
public class BP_VendorGoodsDeliveryBO extends BusinessObject {

	private static final long serialVersionUID = -2120200876761360235L;

	private String deliveryNo; 			//发货单号
	private Integer deliveryStatus; 	//发货状态
	private Date deliveryTime; 			//发货时间
	private Integer verifyStatus;		//审核状态
	private Integer receiveStatus; 		//收货状态
	private Timestamp verifyTime; 		//审核时间
	private Timestamp estimatedDlvTime;	//预计交付日期
	private String remark;				//备注
	private Integer lockStatus = LockStatus.NO;	//锁定状态，更新收货单时存在多发则锁定多余的发货单 0：正常 1：异常

	private String purchaseNo;			//采购订单号
	private OrganizationBO vendor; 		//供应商
	private OrganizationBO buyer; 		//采购商
	private UserBO deliveryUser;		//发货人
	private UserBO verifyUser;			//审核人
	private UserBO createUser; 			//创建人
	private String receiveFactory;		//收货方
	private String receiveAddress;		//收货地址
	// 发货明细
	private Set<BP_VendorGoodsDeliveryItemBO> vendorGoodsDeliveryItemBOs = new HashSet<BP_VendorGoodsDeliveryItemBO>(0);
	// 收货单主信息
	private Set<BP_BuyerGoodsReceiveBO> goodsReceiveBO = new HashSet<BP_BuyerGoodsReceiveBO>(0);

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

	//获取发货单操作
	private GoodsDelivery OPT_GoodsDelivery;

	public BP_VendorGoodsDeliveryBO() {
		super();
		addBOP("verifyStatus", getOPT_GoodsDelivery().getVerifyBOP());
		addBOP("deliveryTime", new DateBOP());
		addBOP("estimatedDlvTime", new DateBOP());
		addBOP("deliveryStatus", new SendStatus());
		addBOP("receiveStatus", new ReceiveStatus());
		addBOP("lockStatus", new LockStatus());
		addBOP("remark", new EmptyBopDec(new BOProperty(), 200));
		//返回按钮
		addOperateBOP("back", new NOSubmitBOP());
		//创建发货单
		OperateBOP createDeliveryBOP = new OperateBOP();
		createDeliveryBOP.setHasConfirm(true);
		addOperateBOP("createDelivery", createDeliveryBOP);

	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		if(UserContext.isVendor()) {
			bot.push("vendor.id", UserContext.getOrgId());
		}

		//订单号条件
		List<Long> dlvIds = new ArrayList<Long>();
		if(!StringUtils.isEmptyStr(bot.getValue("purchaseNo") + "")) {
			List<Long> ids = new BP_VendorGoodsDeliveryItemBO().getDeliveryIds(bot.getValue("purchaseNo") + "");
			if(ContainerUtil.isNotNull(ids))
				dlvIds.addAll(ids);
			else
				dlvIds.add(0l);
		}

		bot.push("id", dlvIds);
		bot.getBotMap().remove("purchaseNo");

		return super.query(bot, start);
	}

	/**
	 * 生成发货单主信息, 主要包括创建人及系统自动生成的发货单号
	 * @return
	 */
	public BP_VendorGoodsDeliveryBO gainDeliveryInfo(){
		deliveryNo = new BP_BillNoBO().gainNextSequenceNo();
		createUser = UserContext.getUserBO();
		BOHelper.initPreferencePage_lazy(this, this);

		return this;
	}

	/**
	 * 创建发货单
	 */
	public void createDelivery(List<BusinessObject> boList) throws Exception {
		getOPT_GoodsDelivery().createDeliveryBill(boList);
	}

	/**
	 * 设置按钮状态，包括发货/取消发货/审核通过/审核驳回
	 * @param bo
	 * @return
	 */
	public BP_VendorGoodsDeliveryBO setBtnStatus(BP_VendorGoodsDeliveryBO bo) {
		//发货
		addOperateBOP("delive", getOPT_GoodsDelivery().getDliveBtn(bo));
		//取消发货
		addOperateBOP("cancelDelive", getOPT_GoodsDelivery().getCancelDliveBtn(bo));

		return this;
	}

	/**
	 * 获取发货单主信息
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public BP_VendorGoodsDeliveryBO getDeliveryMaint(BP_VendorGoodsDeliveryBO bo) throws Exception {
		BP_VendorGoodsDeliveryBO delBO = (BP_VendorGoodsDeliveryBO)getRecord(bo.getId());
		BOHelper.initPreferencePage_lazy(delBO, this);
		return delBO;
	}

	/**
	 * 根据发货单号获取发货单信息
	 * @param deliveryNo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public BP_VendorGoodsDeliveryBO getDelivery(String deliveryNo) throws Exception {
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("deliveryNo", deliveryNo));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<BP_VendorGoodsDeliveryBO> result = getDao().findByCriteria(dc);

		return ContainerUtil.isNull(result) ? null : result.get(0);
	}

	/**
	 * 发货操作
	 * @param boList
	 * @throws Exception
	 */
	public void delive(List<BusinessObject> boList) throws Exception{
		getOPT_GoodsDelivery().delive(boList);
	}

	/**
	 * 取消发货
	 * @param deliveryBills
	 * @throws Exception
	 */
	public void cancelDelive(List<BusinessObject> boList) throws Exception{
		getOPT_GoodsDelivery().cancelDelive(boList);
	}

	/**
	 * 更新发货单收货状态
	 * @param receiveBO
	 * @param purchaseIds 发货单关联的订单IDs
	 * @throws Exception
	 */
	public void updateDeliveryBillReceiveStatus(BP_BuyerGoodsReceiveBO receiveBO, Set<Long> purchaseIds) throws Exception {
		Set<Long> deliveryIds = new HashSet<Long>();
		// 发货单明细
		Map<String, BP_VendorGoodsDeliveryItemBO> deliveryItemMap = new HashMap<String, BP_VendorGoodsDeliveryItemBO>();
		BP_VendorGoodsDeliveryItemBO deliveryItemBO = null;
		BP_PurchaseOrderItemBO orderItemBO = null;
		for (BP_BuyerGoodsReceiveItemBO itemBO : receiveBO.getBuyerGoodsReceiveItemBOs()) {
			deliveryItemMap.put(itemBO.getVendorGoodsDeliveryItemBO().getId() + "",itemBO.getVendorGoodsDeliveryItemBO());
			deliveryItemBO = (BP_VendorGoodsDeliveryItemBO)getDao().getById(BP_VendorGoodsDeliveryItemBO.class, itemBO.getVendorGoodsDeliveryItemBO().getId());
			orderItemBO = (BP_PurchaseOrderItemBO)getDao().getById(BP_PurchaseOrderItemBO.class, itemBO.getPurchaseOrderItemBO().getId());
			deliveryIds.add(deliveryItemBO.getVendorGoodsDelivery().getId());
			purchaseIds.add(orderItemBO.getPurchaseOrderBO().getId());
		}
		BP_VendorGoodsDeliveryBO deliveryBO = null;
		Set<BP_VendorGoodsDeliveryItemBO> deliveryItemBOs = null;
		boolean reveiveFinsh = true;
		// 更新发货单收货状态
		for (Long deliveryId : deliveryIds) {
			deliveryBO = (BP_VendorGoodsDeliveryBO) getDao().getById(
					BP_VendorGoodsDeliveryBO.class, deliveryId);
			deliveryItemBOs = deliveryBO.getVendorGoodsDeliveryItemBOs();
			if (ContainerUtil.isNull(deliveryItemBOs))
				continue;
			// 查看发货单明细是否都已收货
			for (BP_VendorGoodsDeliveryItemBO deliveryItem : deliveryItemBOs) {
				if (!deliveryItemMap.containsKey(deliveryItem.getId() + "")) {
					reveiveFinsh = false;
				}
			}
			// 收货完成
			if (reveiveFinsh) {
				deliveryBO.setReceiveStatus(ReceiveStatus.YES);
				deliveryBO.update();
			}
		}
	}

	/**
	 * 获取采购订单号, 当一张发货单只发同一订单时页面显示
	 * @return
	 */
	public String getPurchaseNo() {
		if(StringUtils.isEmpty(purchaseNo)) {
			for(BP_VendorGoodsDeliveryItemBO item : getVendorGoodsDeliveryItemBOs()){
				purchaseNo = item.getPurchaseOrderItemBO().getPurchaseOrderBO().getPurchaseNo();
				break;
			}
		}
		return purchaseNo;
	}

	public Integer getReceiveStatus() {
		return receiveStatus;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Timestamp getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Timestamp verifyTime) {
		this.verifyTime = verifyTime;
	}

	public void setReceiveStatus(Integer receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public Timestamp getEstimatedDlvTime() {
		return estimatedDlvTime;
	}

	public void setEstimatedDlvTime(Timestamp estimatedDlvTime) {
		this.estimatedDlvTime = estimatedDlvTime;
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

	public OrganizationBO getVendor() {
		return vendor;
	}

	public void setVendor(OrganizationBO vendor) {
		this.vendor = vendor;
	}

	public OrganizationBO getBuyer() {
		return buyer;
	}

	public void setBuyer(OrganizationBO buyer) {
		this.buyer = buyer;
	}

	public UserBO getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(UserBO deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public UserBO getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(UserBO verifyUser) {
		this.verifyUser = verifyUser;
	}

	public UserBO getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserBO createUser) {
		this.createUser = createUser;
	}

	public Set<BP_VendorGoodsDeliveryItemBO> getVendorGoodsDeliveryItemBOs() {
		return vendorGoodsDeliveryItemBOs;
	}

	public void setVendorGoodsDeliveryItemBOs(
			Set<BP_VendorGoodsDeliveryItemBO> vendorGoodsDeliveryItemBOs) {
		this.vendorGoodsDeliveryItemBOs = vendorGoodsDeliveryItemBOs;
	}

	public Set<BP_BuyerGoodsReceiveBO> getGoodsReceiveBO() {
		return goodsReceiveBO;
	}

	public void setGoodsReceiveBO(Set<BP_BuyerGoodsReceiveBO> goodsReceiveBO) {
		this.goodsReceiveBO = goodsReceiveBO;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getReceiveFactory() {
		return receiveFactory;
	}

	public void setReceiveFactory(String receiveFactory) {
		this.receiveFactory = receiveFactory;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
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

	public GoodsDelivery getOPT_GoodsDelivery() {
		if(OPT_GoodsDelivery == null)
			OPT_GoodsDelivery = BusOptManager.getOptDeliveryGoods();
		return OPT_GoodsDelivery;
	}

	public void setOPT_GoodsDelivery(GoodsDelivery oPT_GoodsDelivery) {
		OPT_GoodsDelivery = oPT_GoodsDelivery;
	}

}