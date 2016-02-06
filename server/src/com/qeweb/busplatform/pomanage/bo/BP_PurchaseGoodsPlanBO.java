package com.qeweb.busplatform.pomanage.bo;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.GoodsPlanModifyHisOperateBOP;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.POFeedback;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.POReject;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 供货计划.
 * <br>
 * 每个订单明细对应一个或多个供货计划.
 */
public class BP_PurchaseGoodsPlanBO extends BusinessObject {

	private static final long serialVersionUID = -1217367150822714100L;

	private BP_PurchaseOrderItemBO purchaseOrderItemBO;	//订单明细
	private BP_MaterialBO material; //物料
	private UserBO confirmUser;
	private Integer confirmStatus;	//确认状态
	private Timestamp confirmTime;	//确认时间
	private Integer itemNo;			//行号
	private Double orderQty;		//订单数量
	private Integer deliveryStatus; //发货状态
	private String unitName;		//单位名称
	private Date orderTime;			//要求到货时间
	private Integer modifyCount;	//修改次数
	private String feedback;		//反馈
	private String remark;			//备注

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

	private POFeedback OPT_POFeedback;	//反馈
	private POConfirm OPT_POConfirm;	//确认
	private POReject OPT_POReject;		//驳回

	public BP_PurchaseGoodsPlanBO() {
		super();
		addBOP("modifyCount", new NotEmptyBopDec(new GoodsPlanModifyHisOperateBOP(), 1, 10));
		addBOP("feedback", getOPT_POFeedback().getBtn_GoodsPlanFeedback());
		addBOP("confirmStatus", new ConfirmStatus());
		addBOP("orderTime", new DateBOP());
		//确认(供应商)
		addOperateBOP("confirm", getOPT_POConfirm().getBtn_GoodsPlanConfirm());
		//驳回(供应商)
		addOperateBOP("reject", getOPT_POReject().getBtn_GoodsPlanReject());
		//取消驳回(采购商)
		addOperateBOP("cancelGoodsPlanReject", getOPT_POReject().getBtn_CancelGoodsPlanReject());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		if (StringUtils.isEqual("bp_PurchaseOrderItemBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("purchaseOrderItemBO.id", ctxBot.getValue("id"));
			}
			return super.query(thisBOT);
		}
		else {
			thisBOT = bot;
		}

		return super.query(thisBOT, start);
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			BP_PurchaseGoodsPlanBO bo = (BP_PurchaseGoodsPlanBO) page.getItems().get(i);
			BOHelper.initPreferencePage_lazy(bo, this);

			//当供货计划状态为 已确认/关闭 时,隐藏行级 驳回/确认/取消驳回 按钮
			if(bo.getConfirmStatus() == ConfirmStatus.YES) {
				bo.getOperateBOP("reject").getStatus().setHidden(true);
				bo.getOperateBOP("confirm").getStatus().setHidden(true);
				bo.getOperateBOP("cancelGoodsPlanReject").getStatus().setHidden(true);
			}
			//当供货计划状态为 "驳回" 时,隐藏行级 驳回/确认  按钮
			else if(bo.getConfirmStatus() == ConfirmStatus.REJECT) {
				bo.getOperateBOP("reject").getStatus().setHidden(true);
				bo.getOperateBOP("confirm").getStatus().setHidden(true);
			}
			//当供货计划状态为 "未确认" 时,隐藏行级 "取消驳回" 按钮
			else if(bo.getConfirmStatus() == ConfirmStatus.NO) {
				bo.getOperateBOP("cancelGoodsPlanReject").getStatus().setHidden(true);
			}

			boList.add(bo);
		}

		page.setBOList(boList);
	}

	/**
	 * 保存订单明细供货计划
	 * @param goodsPlans 计划明细
	 * @param itemBO 订单明细
	 * @param isUpdate
	 * @param logBuf 日志
	 */
	public void saveGoodsPlans(Set<BP_PurchaseGoodsPlanBO> goodsPlans, BP_PurchaseOrderItemBO itemBO, StringBuffer logBuf) throws Exception {
		if(ContainerUtil.isNull(goodsPlans))
			return ;

		//保存供货计划
		for(BP_PurchaseGoodsPlanBO planBO : goodsPlans) {
			planBO.setPurchaseOrderItemBO(itemBO);
			planBO.insert();
		}
	}

	/**
	 * 更新计划
	 * @param newGoodsPlans
	 * @param oldItemBO
	 * @param logBuf
	 * @throws Exception
	 */
	public void updateGoodsPlans(Set<BP_PurchaseGoodsPlanBO> newGoodsPlans, BP_PurchaseOrderItemBO oldItemBO, StringBuffer logBuf) throws Exception {
		if(ContainerUtil.isNull(newGoodsPlans))
			return ;

		BP_VendorGoodsDeliveryItemBO deliveryItemBO = new BP_VendorGoodsDeliveryItemBO();
		BP_PurchaseGoodsPlanHisBO goodsPlanHisBO = new BP_PurchaseGoodsPlanHisBO();
		Set<BP_PurchaseGoodsPlanBO> oldGoodsPlans = oldItemBO.getPurchaseGoodsPlanBOs();
		// 标识是否有对应的历史记录
		boolean hasOld = false;
		// 标识是否有修改
		boolean hasModify = false;
		// 标识是否存在已确认的供货计划
		boolean hasConfirm = false;
		for(BP_PurchaseGoodsPlanBO newGoodsPlan : newGoodsPlans) {
			newGoodsPlan.setPurchaseOrderItemBO(oldItemBO);
			for(BP_PurchaseGoodsPlanBO oldGoodsPlan : oldGoodsPlans) {
				if(oldGoodsPlan.getItemNo() == newGoodsPlan.getItemNo().intValue() && !isModify(newGoodsPlan, oldGoodsPlan)) {
					//更新供货计划
					updateGoodsPlan(newGoodsPlan, oldGoodsPlan, logBuf);
					hasOld = true;
					hasConfirm = hasConfirm ? hasConfirm : (oldGoodsPlan.getConfirmStatus() == ConfirmStatus.YES);
					break;
				}
				else if (oldGoodsPlan.getItemNo() == newGoodsPlan.getItemNo().intValue() && isModify(newGoodsPlan, oldGoodsPlan)) {
					logBuf.append("修改订单供货计划" + oldGoodsPlan.getPurchaseOrderItemBO().getPurchaseOrderBO().getPurchaseNo() + "供货计划-->"
							+ oldGoodsPlan.getItemNo() + "").append("\n");
					updateGoodsPlan(deliveryItemBO, goodsPlanHisBO, newGoodsPlan, oldGoodsPlan, logBuf);
					hasModify = true;
					hasOld = true;
					break;
				}
			}
			if(!hasOld) {
				//保存新增的供货计划
				logBuf.append("订单：" + newGoodsPlan.getPurchaseOrderItemBO().getPurchaseOrderBO().getPurchaseNo() + ",新增供货计划，行号" + newGoodsPlan.getItemNo()).append("\n");
				newGoodsPlan.insert();
			}
			hasOld = false;
		}
		//包含修改的
		if(hasModify) {
			// 非整单确认并且拆分供货计划
			if(!BusSettingConstants.isWholeSign() && BusSettingConstants.isSplitGoosPlan() && hasConfirm) {
				oldItemBO.setConfirmStatus(ConfirmStatusX.PART);
			}
			else {
				oldItemBO.setConfirmStatus(ConfirmStatus.NO);
			}
			oldItemBO.update();
		}
	}

	/**
	 * 更新单个供货计划不记录历史
	 * @param newGoodsPlan
	 * @param oldGoodsPlan
	 * @param logBuf
	 * @throws Exception
	 */
	private void updateGoodsPlan(BP_PurchaseGoodsPlanBO newGoodsPlan, BP_PurchaseGoodsPlanBO oldGoodsPlan, StringBuffer logBuf) throws Exception {
		long _oldItemId = oldGoodsPlan.getId();
		Integer _confirmStatus = oldGoodsPlan.getConfirmStatus();
		Timestamp _confirmTime = oldGoodsPlan.getConfirmTime();
		PropertyUtils.copyPropertyWithOutNull(oldGoodsPlan, newGoodsPlan);
		oldGoodsPlan.setConfirmStatus(_confirmStatus);
		oldGoodsPlan.setConfirmTime(_confirmTime);
		oldGoodsPlan.setId(_oldItemId);

		oldGoodsPlan.update();
	}

	/**
	 * 更新单个供货计划并保持历史
	 * 1、保持供货计划历史
	 * 2、更新供货计划
	 * @param deliveryItemBO 发货明细获取供货计划已发货数量
	 * @param goodsPlanHis 记录供货计划历史
	 * @param newGoodsPlan
	 * @param oldGoodsPlan
	 * @param logBuf
	 * @throws Exception
	 */
	private void updateGoodsPlan(BP_VendorGoodsDeliveryItemBO deliveryItemBO, BP_PurchaseGoodsPlanHisBO goodsPlanHis,
			BP_PurchaseGoodsPlanBO newGoodsPlan, BP_PurchaseGoodsPlanBO oldGoodsPlan, StringBuffer logBuf) throws Exception {
		//供货计划已发货数量
		double planAlreadyDlvQty = deliveryItemBO.getPOPlanDeliveryQty(oldGoodsPlan.getId());
		//验证是否能被修改(若此条明细已发货则更改的orderQty不能小于已发货数量)
		if(oldGoodsPlan.getDeliveryStatus() != DeliveryStatus.NO && newGoodsPlan.getOrderQty() < planAlreadyDlvQty) {
			logBuf.append("订单:" + oldGoodsPlan.getPurchaseOrderItemBO().getPurchaseOrderBO().getPurchaseNo() + ",订单明细，行号:" +
					oldGoodsPlan.getPurchaseOrderItemBO().getItemNO()).append(",供货计划，行号:").append(newGoodsPlan.getItemNo()).append(" 已发货数量:")
			.append(planAlreadyDlvQty).append("大于修改数量").append(newGoodsPlan.getOrderQty()).append(",忽略此条明细.").append("\n");
			return;
		}
		//供货记录历史
		goodsPlanHis.saveGoodsPlanHis(oldGoodsPlan);

		long oldItemId = oldGoodsPlan.getId();
		PropertyUtils.copyPropertyWithOutNull(oldGoodsPlan, newGoodsPlan);
		oldGoodsPlan.setId(oldItemId);
		oldGoodsPlan.setConfirmStatus(ConfirmStatus.NO);

		//当订购数量 <= 0 时不做处理
		if(newGoodsPlan.getOrderQty() <= 0)
			;
		//当已发货数量 == 订购数量时,将发货状态改为已发货;
		else if(planAlreadyDlvQty == newGoodsPlan.getOrderQty())
			oldGoodsPlan.setDeliveryStatus(DeliveryStatus.YES);
		//当当已发货数量  != 0 && 当已发货数量  < 订购数量时,将发货状态改为部分发货
		else if(planAlreadyDlvQty != 0 && planAlreadyDlvQty < newGoodsPlan.getOrderQty())
			oldGoodsPlan.setDeliveryStatus(DeliveryStatus.PART);
		else
			oldGoodsPlan.setDeliveryStatus(DeliveryStatus.NO);

		oldGoodsPlan.update();
	}

	/**
	 * 比较供货计划，返回供货计划是否有修改
	 * @param newGoodsPlan
	 * @param oldGoodsPlan
	 * @return
	 */
	private boolean isModify(BP_PurchaseGoodsPlanBO newGoodsPlan, BP_PurchaseGoodsPlanBO oldGoodsPlan) {
		//标实是否有修改
		boolean isModify = false;
		Class<?> newClass = newGoodsPlan.getClass();
		Field[] fields = newClass.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();

				if(!StringUtils.isInArray(fieldName, modifyFields()))
					continue;

				Object value = PropertyUtils.getProperty(newGoodsPlan, fieldName);
				Object oldValue = PropertyUtils.getProperty(oldGoodsPlan, fieldName);

				if(!PropertyUtils.isEqual(value, oldValue)) {
					isModify = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isModify;
	}

	/**
	 * 指定哪些属性字段的值修改了，则认为订单有修改（具体项目根据具体情况可覆写此方法）
	 * @return
	 */
	protected String[] modifyFields() {
		return new String[] {"orderQty", "orderTime"};
	}

	/**
	 * 供货计划确认
	 * @param boList
	 * @throws Exception
	 */
	public void confirm(List<BusinessObject> boList) throws Exception {
		getOPT_POConfirm().confirm(boList);
	}

	/**
	 * 供货计划驳回
	 * @param boList
	 * @throws Exception
	 */
	public void reject(List<BusinessObject> boList) throws Exception {
		getOPT_POReject().reject(boList);
	}

	/**
	 * 供货计划取消驳回
	 * @param boList
	 * @throws Exception
	 */
	public void cancelReject(List<BusinessObject> boList) throws Exception {
		getOPT_POReject().cancelReject(boList);
	}

	/**
	 * 显示供货计划历史
	 * @param goodsPlanBO
	 * @return
	 */
	public Object showGoodsPlanHis(BP_PurchaseGoodsPlanBO goodsPlanBO) {
		BOHelper.initPreferencePage_lazy(goodsPlanBO, this);
		return goodsPlanBO;
	}

	/**
	 * 跳转到供货计划反馈
	 * @param itemBO
	 * @return
	 */
	public BP_PurchaseGoodsPlanBO gotoFeedback(BP_PurchaseGoodsPlanBO goodsPlanBO) {
		BOHelper.initPreferencePage_lazy(goodsPlanBO, this);
		return goodsPlanBO;
	}

	public Integer getModifyCount() {
		if(modifyCount != null)
			return this.modifyCount;

		return new BP_PurchaseGoodsPlanHisBO().getModifyCount(this);
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}

	public String getFeedback() {
		if(feedback != null)
			return this.feedback;

		Integer feedbackCount = new BP_FeedBackBO().getfeedbackCount(this.getId(), BP_FeedBackBO.PURCHASE_GOODS_PLAN);
		return AppLocalization.getLocalization("com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.feedback") + "[" + feedbackCount + "]";
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public POFeedback getOPT_POFeedback() {
		if(OPT_POFeedback == null)
			OPT_POFeedback = BusOptManager.getOptGoodsPlanFeedback();

		return OPT_POFeedback;
	}

	public void setOPT_POFeedback(POFeedback oPT_POFeedback) {
		OPT_POFeedback = oPT_POFeedback;
	}

	public Integer getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Double getOrderQty() {
		return this.orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}


	public String getUnitName() {
		if(StringUtils.isEmpty(unitName))
			unitName = getPurchaseOrderItemBO().getUnitName();

		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public BP_PurchaseOrderItemBO getPurchaseOrderItemBO() {
		return purchaseOrderItemBO;
	}

	public void setPurchaseOrderItemBO(BP_PurchaseOrderItemBO purchaseOrderItemBO) {
		this.purchaseOrderItemBO = purchaseOrderItemBO;
	}

	public BP_MaterialBO getMaterial() {
		if(material == null)
			material = getPurchaseOrderItemBO().getMaterial();

		return material;
	}

	public void setMaterial(BP_MaterialBO material) {
		this.material = material;
	}

	public UserBO getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(UserBO confirmUser) {
		this.confirmUser = confirmUser;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public POConfirm getOPT_POConfirm() {
		if(OPT_POConfirm == null)
			OPT_POConfirm = BusOptManager.getOptGoodsPlanConfirm();
		return OPT_POConfirm;
	}

	public void setOPT_POConfirm(POConfirm oPT_POConfirm) {
		OPT_POConfirm = oPT_POConfirm;
	}

	public POReject getOPT_POReject() {
		if(OPT_POReject == null)
			OPT_POReject = BusOptManager.getOptGoodsPlanReject();
		return OPT_POReject;
	}

	public void setOPT_POReject(POReject oPT_POReject) {
		OPT_POReject = oPT_POReject;
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

}