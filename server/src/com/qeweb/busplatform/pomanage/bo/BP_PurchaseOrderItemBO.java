package com.qeweb.busplatform.pomanage.bo;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO;
import com.qeweb.busplatform.pomanage.bop.ChangeStatus;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.CloseStatusX;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.busplatform.pomanage.bop.POItemModifyHisOperateBOP;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pofeedback.POFeedback;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject.POReject;
import com.qeweb.busplatform.pomanage.dao.ia.IBP_PurchaseOrderItemDao;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 采购订单明细
 */
public class BP_PurchaseOrderItemBO extends BusinessObject {

	private static final long serialVersionUID = -1217367150822714100L;

	//订单
	private BP_PurchaseOrderBO purchaseOrderBO;
	//供货计划
	private Set<BP_PurchaseGoodsPlanBO> purchaseGoodsPlanBOs = new HashSet<BP_PurchaseGoodsPlanBO>(0);
	//发货明细
	private Set<BP_VendorGoodsDeliveryItemBO> vendorGoodsDeliveryItemBOs = new HashSet<BP_VendorGoodsDeliveryItemBO>(0);
	//订单统计
	private Set<BP_PurchaseOrderStatisticsBO> orderStatisticsBOs;
	// 物料
	private BP_MaterialBO material;
	//确认人
	private UserBO confirmUser;

	private Integer itemNO;				//行号
	private Integer deliveryStatus;		//发货状态
	private Timestamp deliveryStatusChangeTime;	//发货状态修改时间
	private Integer confirmStatus;		//确认状态
	private Timestamp confirmTime;		//确认时间
	private Integer closeStatus;		//关闭状态
	private Timestamp closeTime;		//关闭时间
	private Double orderQty;			//订购数量
	private String unitName;			//单位
	private Timestamp orderTime;		//要求到货时间
	private Integer modifyCount;		//修改次数
	private String feedback;			//反馈
	private Double amount;				//总额(钱)
	private String taxesCategories;		//税种
	private Double taxesRate;			//税率
	private Double taxIncludePrice;		//含税单价
	private String remark;				//备注
	private Integer lockStatus;			//锁定状态，更新订单行异常时锁定此订单行 0：正常 1：异常
	private String lockMsg;				//锁定日志，显示锁定原因

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

	private POFeedback OPT_POFeedback;			//反馈
	private POReject OPT_POReject;				//驳回
	private POConfirm OPT_POConfirm;			//确认

	private IBP_PurchaseOrderItemDao purchaseOrderItemDao;

	public BP_PurchaseOrderItemBO() {
		super();
		addBOP("modifyCount", new NotEmptyBopDec(new POItemModifyHisOperateBOP(), 1, 10));
		addBOP("orderTime", new DateBOP());
		//反馈
		addBOP("feedback", getOPT_POFeedback().getBtn_ItemFeedback());
		addBOP("confirmStatus", new ConfirmStatusX());
		addBOP("closeStatus", new CloseStatus());
		addBOP("lockStatus", new LockStatus());

		//确认(供应商)
		addOperateBOP("confirm", getOPT_POConfirm().getBtn_ItemConfirm());
		//驳回(供应商)
		addOperateBOP("reject", getOPT_POReject().getBtn_ItemReject());
		//取消驳回(采购商)
		addOperateBOP("itemCancelReject", getOPT_POReject().getBtn_ItemCancelReject());
		//供货计划
		addOperateBOP("viewGoodsPlan", BusOptManager.getGoodsPlanBtn().getViewGoodsPlanBtn());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate ctxBot = PropertyUtils.createCtxBot(bot);
		BOTemplate thisBOT = null;

		if (StringUtils.isEqual("bp_PurchaseOrderBO", ctxBot.getBoName())) {
			if (ctxBot.getValue("id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("purchaseOrderBO.id", ctxBot.getValue("id"));
			}
			return super.query(thisBOT);
		}
		else if(StringUtils.isEqual("BP_PurchaseGoodsPlanBO", ctxBot.getBoName())){
			if (ctxBot.getValue("purchaseOrderItemBO.purchaseOrderBO.id") != null) {
				thisBOT = new BOTemplate();
				thisBOT.setColumnHeaderOrderMap(ctxBot.getColumnHeaderOrderMap());
				thisBOT.push("purchaseOrderBO.id", ctxBot.getValue("purchaseOrderItemBO.purchaseOrderBO.id"));
			}
		}

		return super.query(thisBOT, start);
	}

	/**
	 * 订单明细按行号升序排列
	 */
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("itemNO", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}

	@Override
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		for (int i = 0; i < page.getItems().size(); i++) {
			BP_PurchaseOrderItemBO bo = (BP_PurchaseOrderItemBO) page.getItems().get(i);
			BOHelper.initPreferencePage_lazy(bo, this);

			//当订单行状态为 已确认/关闭 时,隐藏行级 驳回/确认/取消驳回 按钮
			if(bo.getConfirmStatus() == ConfirmStatus.YES || bo.getCloseStatus() == CloseStatus.YES) {
				bo.getOperateBOP("reject").getStatus().setHidden(true);
				bo.getOperateBOP("confirm").getStatus().setHidden(true);
				bo.getOperateBOP("itemCancelReject").getStatus().setHidden(true);
			}
			//当订单行状态为 "驳回" 时,隐藏行级 驳回/确认  按钮
			else if(bo.getConfirmStatus() == ConfirmStatus.REJECT) {
				bo.getOperateBOP("reject").getStatus().setHidden(true);
				bo.getOperateBOP("confirm").getStatus().setHidden(true);
			}
			//当订单行状态为 "未确认" 时,隐藏行级 "取消驳回" 按钮
			else if(bo.getConfirmStatus() == ConfirmStatus.NO) {
				bo.getOperateBOP("itemCancelReject").getStatus().setHidden(true);
			}

			boList.add(bo);
		}

		page.setBOList(boList);
	}

	/**
	 * 根据物料编码和物料名称查询采购订单ID
	 * @param materialCode
	 * @param materialName
	 * @return
	 */
	public List<Long> getPOIds(String materialCode, String materialName) {
		return getDao().getPOIds(materialCode, materialName);
	}

	/**
	 * 根据订单ID查询订单对应的明细ID
	 * @param orderId
	 * @return
	 */
	public List<Long> getPOItemIds(long orderId) {
		return getDao().getPOItemIds(orderId);
	}

	/**
	 * 订单明细确认
	 * @param boList
	 * @throws Exception
	 */
	public void confirm(List<BusinessObject> boList) throws Exception {
		getOPT_POConfirm().confirm(boList);
	}

	/**
	 * 订单明细驳回
	 * @param boList
	 * @throws Exception
	 */
	public void reject(List<BusinessObject> boList) throws Exception {
		getOPT_POReject().reject(boList);
	}

	/**
	 * 订单明细取消驳回
	 * @param boList
	 * @throws Exception
	 */
	public void cancelReject(List<BusinessObject> boList) throws Exception {
		getOPT_POReject().cancelReject(boList);
	}

	/**
	 * 显示订单明细
	 * @return
	 */
	public Object showItemInfo(BP_PurchaseOrderItemBO itemBO){
		BOHelper.initPreferencePage_lazy(itemBO, this);
		return itemBO;
	}

	/**
	 * 保存采购订单明细
	 * 1、保存订单明细
	 * 2、保存订单明细供货计划
	 * @param orderItems
	 * @param orderBO
	 * @param logBuf 记录日志
	 * @throws Exception
	 */
	public void saveOrderItems(Set<BP_PurchaseOrderItemBO> orderItems, BP_PurchaseOrderBO orderBO, StringBuffer logBuf) throws Exception {
		if(ContainerUtil.isNull(orderItems))
			return;

		BP_PurchaseGoodsPlanBO goodsPlan = new BP_PurchaseGoodsPlanBO();
		for(BP_PurchaseOrderItemBO itemBO : orderItems) {
			itemBO.setPurchaseOrderBO(orderBO);
			itemBO.setLockStatus(LockStatus.NO);
			itemBO.insert();
			//同步新增供货计划
			goodsPlan.saveGoodsPlans(getItemPlans(itemBO), itemBO, logBuf);
		}
	}

	/**
	 * 返回订单明细对应供货计划
	 * @param itemBO
	 * @return
	 */
	private Set<BP_PurchaseGoodsPlanBO> getItemPlans(BP_PurchaseOrderItemBO itemBO) {
		//无明细对应计划，拷贝明细作为计划
		if(ContainerUtil.isNull(itemBO.getPurchaseGoodsPlanBOs())) {
			Set<BP_PurchaseGoodsPlanBO>  planSet = new HashSet<BP_PurchaseGoodsPlanBO>();
			BP_PurchaseGoodsPlanBO plan = new BP_PurchaseGoodsPlanBO();
			plan.setPurchaseOrderItemBO(itemBO);
			plan.setItemNo(itemBO.getItemNO());			//行号
			plan.setOrderQty(itemBO.getOrderQty());		//订单数量
			plan.setDeliveryStatus(DeliveryStatus.NO);	//发货状态
			plan.setUnitName(itemBO.getUnitName());		//单位
			plan.setConfirmStatus(itemBO.getConfirmStatus());
			plan.setOrderTime(itemBO.getOrderTime());	//要求到货时间
			planSet.add(plan);
			return planSet;
		}
		else {
			return itemBO.getPurchaseGoodsPlanBOs();
		}
	}

	/**
	 * 采购订单明细修改
	 * @param orderItems
	 * @param orderBO
	 * @param logBuf 日志
	 * @throws Exception
	 */
	public void updateOrderItems(Set<BP_PurchaseOrderItemBO> orderItems, BP_PurchaseOrderBO oldOrderBO, StringBuffer logBuf) throws Exception {
		BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)SpringConstant.getCTX().getBean("bp_PurchaseGoodsPlanBO");
		BP_PurchaseOrderItemHisBO itemHis = new BP_PurchaseOrderItemHisBO();
		// 明细比较，是否修改，有修改更新明细并记录历史
		Set<BP_PurchaseOrderItemBO> oldOrderItems = oldOrderBO.getPurchaseOrderItemBOs();
		// 标识是否有对应的历史记录
		boolean hasOld = false;
		// 标识是否有修改
		boolean hasModify = false;
		// 标识是否需锁定订单主信息
		boolean isLock = false;
		// 标识是否存在已确认的订单明细行
		boolean hasConfirm = false;
		// 标识是否存在已关闭
		boolean hasClose = false;
		for(BP_PurchaseOrderItemBO newItem : orderItems) {
			for(BP_PurchaseOrderItemBO oldItem : oldOrderItems) {
				//相同明细、未修改跳出循环
				if(newItem.getItemNO() == oldItem.getItemNO().intValue() && !isModify(newItem, oldItem)) {
					//更新明细不记录历史
					updateOrderItem(goodsPlan, newItem, oldItem, logBuf);
					hasOld = true;
					hasClose = hasClose ? hasClose : (newItem.getCloseStatus() != null && newItem.getCloseStatus() == CloseStatus.YES);
					hasConfirm = hasConfirm ? hasConfirm : (oldItem.getConfirmStatus() == ConfirmStatus.YES);
					break;
				}
				//相同明细、有修改则可更新
				else if(newItem.getItemNO() == oldItem.getItemNO().intValue() && isModify(newItem, oldItem)) {
					logBuf.append("修改订单" + oldOrderBO.getPurchaseNo() + "明细-->" + oldItem.getItemNO() + "").append("\n");
					hasOld = true;
					//已关闭订单明细行不做更新
					if(oldItem.getCloseStatus() == CloseStatus.YES) {
						logBuf.append("订单：" + oldItem.getPurchaseOrderBO().getPurchaseNo()).append("订单明细，行号" + oldItem.getItemNO() + ",已关闭,忽略此条明细.").append("\n");
						hasClose = true;
						break;
					}
					if(!updateOrderItem(goodsPlan, itemHis, newItem, oldItem, logBuf) && !isLock) {
						isLock = true;
						break;
					}
					hasClose = hasClose ? hasClose : (newItem.getCloseStatus() != null && newItem.getCloseStatus() == CloseStatus.YES);
					hasModify = true;
					break;
				}
			}

			if(!hasOld) {
				logBuf.append("订单：" + oldOrderBO.getPurchaseNo() + ",新增订单明细，行号" + newItem.getItemNO()).append("\n");
				newItem.setPurchaseOrderBO(oldOrderBO);
				newItem.insert();
				//保存计划
				goodsPlan.saveGoodsPlans(getItemPlans(newItem), newItem, logBuf);
			}
			hasOld = false;
		}
		//更新订单锁定状态
		oldOrderBO.setLockStatus((isLock ? LockStatus.YES : LockStatus.NO));
		//订单主信息关闭状态
		if(isLock && hasClose && BusSettingConstants.isCloseForPart())
			oldOrderBO.setCloseStatus(CloseStatusX.PART);

		if(hasModify){
			//如果是按明细行确认并且包含已确认，则订单主信息为部分确认,否则为未确认
			if(!BusSettingConstants.isWholeSign() && hasConfirm) {
				oldOrderBO.setConfirmStatus(ConfirmStatusX.PART);
			}
			else {
				oldOrderBO.setConfirmStatus(ConfirmStatus.NO);
				//有修改更新订单主信息修改次数
				oldOrderBO.setModifyCount(oldOrderBO.getModifyCount() == null ? 1 : (oldOrderBO.getModifyCount() + 1));
				oldOrderBO.setChangeStatus(ChangeStatus.YES);
			}
			oldOrderBO.update();
		}
		updatePOStatus(oldOrderBO);
	}

	/**
	 * 更新订单收发货状态
	 * @throws Exception
	 */
	private void updatePOStatus(BP_PurchaseOrderBO oldOrderBO) throws Exception{
		Set<Long> purchaseIds = new HashSet<Long>();
		purchaseIds.add(oldOrderBO.getId());
		//更新订单确认状态
		oldOrderBO.updatePOConfirmStatus(oldOrderBO);
		//更新订单发货状态
		oldOrderBO.updatePODeliveryStatus(purchaseIds);
		//更新订单收货状态
		oldOrderBO.updatePOReceiveStatus(purchaseIds);
	}

	/**
	 * 更新订单明细操作(不记录历史，更新明细的状态、供货计划的调整等等)
	 * @param newItem
	 * @param oldItem
	 * @param logBuf
	 * @throws Exception
	 */
	private void updateOrderItem(BP_PurchaseGoodsPlanBO goodsPlan,BP_PurchaseOrderItemBO newItem,
			BP_PurchaseOrderItemBO oldItem, StringBuffer logBuf) throws Exception {
		long _oldItemId = oldItem.getId();
		Integer _confirmStatus = oldItem.getConfirmStatus();
		Timestamp _confirmTime = oldItem.getConfirmTime();
		Integer _deliveryStatus = oldItem.getDeliveryStatus();
		PropertyUtils.copyPropertyWithOutNull(oldItem, newItem);
		oldItem.setConfirmStatus(_confirmStatus);
		oldItem.setConfirmTime(_confirmTime);
		oldItem.setDeliveryStatus(_deliveryStatus);
		oldItem.setId(_oldItemId);
		oldItem.setLockStatus(LockStatus.NO);
		oldItem.setLockMsg("");
		oldItem.update();
		//更新供货计划
		goodsPlan.updateGoodsPlans(getItemPlans(newItem), oldItem, logBuf);
	}
	/**
	 * 更新订单明细操作
	 * 1、保存明细历史
	 * 3、更新明细
	 * 4、更新供货计划
	 * @param bo
	 * @param goodsPlan
	 * @param itemHis
	 * @param newItem
	 * @param oldItem
	 * @throws Exception
	 * @return 订单行更新成功与否状态
	 */
	private boolean updateOrderItem(BP_PurchaseGoodsPlanBO goodsPlan, BP_PurchaseOrderItemHisBO itemHis,
			BP_PurchaseOrderItemBO newItem,BP_PurchaseOrderItemBO oldItem, StringBuffer logBuf) throws Exception {
		BP_VendorGoodsDeliveryItemBO deliveryItemBO = new BP_VendorGoodsDeliveryItemBO();
		BP_BuyerGoodsReceiveItemBO receiveItemBO = new BP_BuyerGoodsReceiveItemBO();
		//(1)总验退数量
		double rejectQty = receiveItemBO.getOrderItemGoodsRejectQty(oldItem.getId());
		//(2)差异数量 = 总已收货的发货数量 - 总实收 - 总验退
		double varianceQty = deliveryItemBO.getOrderItemHaveRecDlvQty(oldItem.getId())
							- (receiveItemBO.getOrderItemReceiveQty(oldItem.getId()) + rejectQty);
		//(3)已发货数量= 总已发货 - 验退
		double alreadyDlvQty = deliveryItemBO.getOrderItemTotalDlvQty(oldItem.getId()) - rejectQty;

		//修改数量 >= (3)已发货数量  - (2)差异数量 ，则可修改
		double checkQty = alreadyDlvQty - varianceQty;
		//验证是否能被修改(若此条明细已发货则更改的orderQty不能小于已发货数量 - 差异数量)
		if(oldItem.getDeliveryStatus() != DeliveryStatus.NO && newItem.getOrderQty() < checkQty) {
			StringBuffer msg = new StringBuffer("已发货数量:").append(alreadyDlvQty).append(",差异数量:").append(varianceQty).append(";大于修改数量")
			.append(newItem.getOrderQty()).append(",忽略此条明细.");
			logBuf.append("订单：" + oldItem.getPurchaseOrderBO().getPurchaseNo()).append("订单明细，行号" + oldItem.getItemNO() + ",").append(msg).append("\n");

			oldItem.setLockStatus(LockStatus.YES);
			oldItem.setLockMsg(msg.toString());
			oldItem.update();
			return false;
		}
		//记录历史
		itemHis.saveItemHis(oldItem);
		//更新明细
		long oldItemId = oldItem.getId();
		PropertyUtils.copyPropertyWithOutNull(oldItem, newItem);
		oldItem.setId(oldItemId);
		oldItem.setConfirmStatus(ConfirmStatus.NO);
		//明细发货状态
		if(newItem.getOrderQty() <= 0)
			;
		else if(newItem.getOrderQty() <= checkQty)
			oldItem.setDeliveryStatus(DeliveryStatus.YES);
		else if(checkQty != 0d && checkQty < newItem.getOrderQty())
			oldItem.setDeliveryStatus(DeliveryStatus.PART);
		else
			oldItem.setDeliveryStatus(DeliveryStatus.NO);

		//重置锁定状态 锁定MSG
		oldItem.setLockStatus(LockStatus.NO);
		oldItem.setLockMsg("");
		BOHelper.setBOPublicFields_update(oldItem);
		getDao().update(oldItem);
		//更新计划
		goodsPlan.updateGoodsPlans(getItemPlans(newItem), oldItem, logBuf);
		return true;
	}

	/**
	 * 根据订单明细ID更新订单明细发货状态
	 * @throws Exception
	 */
	public void updatePOItemsDeliveryStatus(Collection<Long> purchaseItemIds) throws Exception {
		//更新订单明细状态
		for(Long purchaseItemId : purchaseItemIds){
			//查询明细下所有计划状态，根据计划状态更新订单明细状态
			BP_PurchaseOrderItemBO item = (BP_PurchaseOrderItemBO)getDao().getById(BP_PurchaseOrderItemBO.class, purchaseItemId);
			Set<BP_PurchaseGoodsPlanBO> purchaseGoodsPlanBOs = item.getPurchaseGoodsPlanBOs();
			boolean isDelivery = false;	//是否有已发货计划
			boolean isNoDelivery = false;	//是否有未发货计划
			boolean isPart = false; //是否有部分发货的
			for(BP_PurchaseGoodsPlanBO plan : purchaseGoodsPlanBOs){
				if(plan.getDeliveryStatus() == DeliveryStatus.YES)
					isDelivery = true;
				if(plan.getDeliveryStatus() == DeliveryStatus.PART)
					isPart = true;
				if(plan.getDeliveryStatus() == DeliveryStatus.NO)
					isNoDelivery = true;
			}
			if(isPart || (isDelivery && isNoDelivery))
				item.setDeliveryStatus(DeliveryStatus.PART);
			else if(isDelivery && !isNoDelivery && !isPart)
				item.setDeliveryStatus(DeliveryStatus.YES);
			else if(isNoDelivery && !isDelivery && !isPart)
				item.setDeliveryStatus(DeliveryStatus.NO);

			item.setDeliveryStatusChangeTime(DateUtils.getCurrentTimestamp());
			item.update();
		}
	}

	/**
	 * 比较订单明细，返回明细是否有修改
	 * @param orderItem
	 * @param oldOrderItem
	 * @return
	 */
	private boolean isModify(BP_PurchaseOrderItemBO orderItem, BP_PurchaseOrderItemBO oldOrderItem) {
		//标实是否有修改
		boolean isModify = false;
		Class<?> newClass = orderItem.getClass();
		Field[] fields = newClass.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				//单据状态不作为是否变更的依据
				if("serialVersionUID".equals(fieldName) || !StringUtils.isInArray(fieldName, modifyFields()))
					continue;

				Object value = PropertyUtils.getProperty(orderItem, fieldName);
				Object oldValue = PropertyUtils.getProperty(oldOrderItem, fieldName);

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
	 * 跳转到订单明细反馈
	 * @param itemBO
	 * @return
	 */
	public BP_PurchaseOrderItemBO gotoFeedback(BP_PurchaseOrderItemBO itemBO) {
		BOHelper.initPreferencePage_lazy(itemBO, this);
		return itemBO;
	}

	public String getFeedback() {
		if(feedback != null)
			return this.feedback;

		Integer feedbackCount = new BP_FeedBackBO().getfeedbackCount(this.getId(), BP_FeedBackBO.PURCHASE_ORDER_ITEM);
		return AppLocalization.getLocalization("com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.feedback") + "[" + feedbackCount + "]";
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * 获取修改次数
	 * @return
	 */
	public Integer getModifyCount() {
		if(modifyCount != null)
			return this.modifyCount;

		return new BP_PurchaseOrderItemHisBO().getModifyCount(this);
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}

	public BP_PurchaseOrderBO getPurchaseOrderBO() {
		return this.purchaseOrderBO;
	}

	public void setPurchaseOrderBO(BP_PurchaseOrderBO purchaseOrderBO) {
		this.purchaseOrderBO = purchaseOrderBO;
	}

	public Double getOrderQty() {
		return this.orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Timestamp getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public BP_MaterialBO getMaterial() {
		return material;
	}

	public void setMaterial(BP_MaterialBO material) {
		this.material = material;
	}

	public Set<BP_VendorGoodsDeliveryItemBO> getVendorGoodsDeliveryItemBOs() {
		return vendorGoodsDeliveryItemBOs;
	}

	public void setVendorGoodsDeliveryItemBOs(Set<BP_VendorGoodsDeliveryItemBO> vendorGoodsDeliveryItemBOs) {
		this.vendorGoodsDeliveryItemBOs = vendorGoodsDeliveryItemBOs;
	}

	public Set<BP_PurchaseGoodsPlanBO> getPurchaseGoodsPlanBOs() {
		return purchaseGoodsPlanBOs;
	}

	public void setPurchaseGoodsPlanBOs(
			Set<BP_PurchaseGoodsPlanBO> purchaseGoodsPlanBOs) {
		this.purchaseGoodsPlanBOs = purchaseGoodsPlanBOs;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getConfirmStatus() {
		if(confirmStatus == null)
			confirmStatus = 0;

		return confirmStatus;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Integer getItemNO() {
		return itemNO;
	}

	public void setItemNO(Integer itemNO) {
		this.itemNO = itemNO;
	}

	public UserBO getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(UserBO confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Timestamp getDeliveryStatusChangeTime() {
		return deliveryStatusChangeTime;
	}

	public void setDeliveryStatusChangeTime(Timestamp deliveryStatusChangeTime) {
		this.deliveryStatusChangeTime = deliveryStatusChangeTime;
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

	public Integer getCloseStatus() {
		if(closeStatus == null)
			closeStatus = CloseStatus.NO;
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

	public POFeedback getOPT_POFeedback() {
		if(OPT_POFeedback == null)
			OPT_POFeedback = BusOptManager.getOptPOFeedback();
		return OPT_POFeedback;
	}

	public void setOPT_POFeedback(POFeedback oPT_POFeedback) {
		OPT_POFeedback = oPT_POFeedback;
	}

	public POReject getOPT_POReject() {
		if(OPT_POReject == null)
			OPT_POReject = BusOptManager.getOptPOReject();
		return OPT_POReject;
	}

	public void setOPT_POReject(POReject oPT_POReject) {
		OPT_POReject = oPT_POReject;
	}

	public POConfirm getOPT_POConfirm() {
		if(OPT_POConfirm == null)
			OPT_POConfirm = BusOptManager.getOptPOConfirm();
		return OPT_POConfirm;
	}

	public void setOPT_POConfirm(POConfirm oPT_POConfirm) {
		OPT_POConfirm = oPT_POConfirm;
	}

	public IBP_PurchaseOrderItemDao getDao() {
		return getPurchaseOrderItemDao();
	}

	public IBP_PurchaseOrderItemDao getPurchaseOrderItemDao() {
		if(purchaseOrderItemDao == null)
			purchaseOrderItemDao = (IBP_PurchaseOrderItemDao)SpringConstant.getCTX().getBean("IBP_PurchaseOrderItemDao");
		return purchaseOrderItemDao;
	}

	public void setPurchaseOrderItemDao(IBP_PurchaseOrderItemDao purchaseOrderItemDao) {
		this.purchaseOrderItemDao = purchaseOrderItemDao;
	}

	public Set<BP_PurchaseOrderStatisticsBO> getOrderStatisticsBOs() {
		return orderStatisticsBOs;
	}

	public void setOrderStatisticsBOs(
			Set<BP_PurchaseOrderStatisticsBO> orderStatisticsBOs) {
		this.orderStatisticsBOs = orderStatisticsBOs;
	}


}