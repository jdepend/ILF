package com.qeweb.busplatform.pomanage.bo;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.constants.ConstantFtpFiles;
import com.qeweb.busplatform.common.imp.excel.ImpExl;
import com.qeweb.busplatform.common.operate.BusOptManager;
import com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO;
import com.qeweb.busplatform.pomanage.POStatusFactory;
import com.qeweb.busplatform.pomanage.bop.ChangeStatus;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.busplatform.pomanage.bop.POImpExcelOperateBOP;
import com.qeweb.busplatform.pomanage.bop.POImpIAOperateBOP;
import com.qeweb.busplatform.pomanage.bop.POReceiveStatus;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.lock.POManlock;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm.POConfirm;
import com.qeweb.busplatform.pomanage.busoperate.postatuschange.popublish.POPublish;
import com.qeweb.busplatform.pomanage.dao.ia.IPODownLoadFilterDao;
import com.qeweb.busplatform.pomanage.filter.IPODownLoadFileter;
import com.qeweb.busplatform.pomanage.imp.BP_ImpPurchaseOrderBO;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.SelectMdBOP;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bo.UserBO;

/**
 * 采购订单
 */
public class BP_PurchaseOrderBO extends BusinessObject {

	private static final long serialVersionUID = 647125677115603428L;

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

	private UserBO buyerUser;			//采购员
	private OrganizationBO buyer;		//采购商
	private OrganizationBO vendor;		//供应商
	private UserBO publishUser;			//发布人
	private UserBO confirmUser;			//确认人
	private UserBO closeUser;			//关闭人
	private UserBO manlockUser;			//订单锁定人
	private Timestamp modifyTime;		//最后修改时间（业务使用）

	private Set<BP_PurchaseOrderItemBO> purchaseOrderItemBOs = new HashSet<BP_PurchaseOrderItemBO>(); // 采购明细

	//查询
	private String materialCode;	//物料编码
	private String materialName;	//物料名称

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

	//采购单下载过滤器
	private IPODownLoadFileter poDownLoadFilter;
	private IPODownLoadFilterDao downLoadFilterDao;

	//订单行为
	private POPublish OPT_POPublish;	//订单发布
	private POConfirm OPT_POConfirm;	//订单确认


	public BP_PurchaseOrderBO() {
		super();
		addBOP("publishStatus", new PublishStatus());
		addBOP("confirmStatus", new POStatusFactory().getConfirmStatus());
		addBOP("receiveStatus", new POReceiveStatus());
		addBOP("deliveryStatus", new DeliveryStatus());
		addBOP("changeStatus", new ChangeStatus());
		addBOP("closeStatus",  new POStatusFactory().getCloseStatus());
		addBOP("purchaseTime", new DateBOP());
		addBOP("publishTime", new DateBOP());
		addBOP("confirmTime", new DateBOP());
		addBOP("closeTime", new DateBOP());
		addBOP("lockStatus", new LockStatus());
		//锁定
		LockStatus manlockStatus = new LockStatus();
		manlockStatus.getStatus().setHidden(!BusSettingConstants.isManualLock());
		addBOP("manlockStatus", manlockStatus);
		BOProperty manlockReasonBOP = new BOProperty();
		manlockReasonBOP.getStatus().setHidden(manlockStatus.getStatus().isHidden());
		addBOP("manlockReason", manlockReasonBOP);
		BOProperty manlockTimeBOP = new BOProperty();
		manlockTimeBOP.getStatus().setHidden(manlockStatus.getStatus().isHidden());
		addBOP("manlockTime", manlockTimeBOP);
		//反馈
		addBOP("feedback", BusOptManager.getOptPOFeedback().getBtn_WholeFeedback());

		//导入按钮
		//从excel导入
		addOperateBOP("importPOFromExcel", new POImpExcelOperateBOP());
		//从接口导入
		addOperateBOP("importPOFromIA", new POImpIAOperateBOP());
		//整单确认按钮
		addOperateBOP("wholeConfirm", getOPT_POConfirm().getBtn_WholeConfirm());
		//整单驳回按钮
		addOperateBOP("wholeReject", BusOptManager.getOptPOReject().getBtn_WholeReject());
		//整单关闭按钮
		addOperateBOP("wholeClose", BusOptManager.getOptPOClose().getBtn_WholeClose());
		//订单手动锁定
		addOperateBOP("poManlock", new POManlock().getManlockBtn());
		//订单手动解锁
		addOperateBOP("poManunlock", new POManlock().getManunlockBtn());

		//发布按钮(订单列表页面)
		SelectMdBOP publishBOP = new SelectMdBOP();
		publishBOP.setHasConfirm(true);
		addOperateBOP("publish", publishBOP);
		//取消发布按钮(订单列表页面)
		SelectMdBOP cancelPublishBOP = new SelectMdBOP();
		cancelPublishBOP.setHasConfirm(true);
		addOperateBOP("cancelPublish", cancelPublishBOP);
		//取消驳回按钮
		SelectMdBOP cancelRejectBOP = new SelectMdBOP();
		cancelRejectBOP.setHasConfirm(true);
		addOperateBOP("cancelReject", cancelRejectBOP);
	}

	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		//供应商仅能看见发布状态为"已发布"的订单
		if(UserContext.isVendor()) {
			bot.push("publishStatus", PublishStatus.YES);
			bot.push("vendor.id", UserContext.getOrgId());
		}

		//根据物料查询
		List<Long> ids = new BP_PurchaseOrderItemBO().getPOIds(bot.getValue("materialCode") + "", bot.getValue("materialName") + "");
		bot.push("id", ids);
		bot.getBotMap().remove("materialCode");
		bot.getBotMap().remove("materialName");
		bot.getBotMap().remove("feedback");

		//数据级权限
		//bot.push("vendor.id", UserContext.getVenderIds());
		return super.query(bot, start);
	}

	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("purchaseNo", IBaseDao.ORDER_BY_DESC);
		return super.queryOrderBy();
	}

	/**
	 * 设置订单明细页面的按钮状态,
	 * 包括全局按钮 : "确认", "驳回","取消驳回", "发布", "取消发布", "反馈"
	 */
	public BP_PurchaseOrderBO setBtnStatus(BP_PurchaseOrderBO bo) {
		//将下列按钮设置为page级别按钮
		//发布按钮
		OperateBOP poPublishBOP = new OperateBOP();
		poPublishBOP.setHasConfirm(true);
		addOperateBOP("poPublish", poPublishBOP);
		//取消发布按钮
		OperateBOP poCancelPublishBOP = new OperateBOP();
		poCancelPublishBOP.setHasConfirm(true);
		addOperateBOP("poCancelPublish", poCancelPublishBOP);
		//订单关闭按钮
		OperateBOP poCloseBOP = new OperateBOP();
		poCloseBOP.setHasConfirm(true);
		addOperateBOP("poClose", poCloseBOP);
		//订单确认
		OperateBOP poConfirmBOP = new OperateBOP();
		poConfirmBOP.setHasConfirm(true);
		addOperateBOP("poConfirm", poConfirmBOP);
		//订单驳回
		OperateBOP poRejectBOP = new OperateBOP();
		poRejectBOP.setHasConfirm(true);
		addOperateBOP("poReject", new OperateBOP());
		//取消驳回
		OperateBOP poCancelRejectBOP = new OperateBOP();
		poCancelRejectBOP.setHasConfirm(true);
		addOperateBOP("poCancelReject", poCancelRejectBOP);

		//如果订单关闭, 下列按钮隐藏
		if(bo.getCloseStatus() == CloseStatus.YES) {
			getOperateBOP("poConfirm").getStatus().setHidden(true);
			getOperateBOP("poReject").getStatus().setHidden(true);
			getOperateBOP("poCancelReject").getStatus().setHidden(true);
			getOperateBOP("poPublish").getStatus().setHidden(true);
			getOperateBOP("poCancelPublish").getStatus().setHidden(true);
			getOperateBOP("poClose").getStatus().setHidden(true);
		}
		//如果订单已驳回, 下列按钮隐藏
		else if(bo.getConfirmStatus() == ConfirmStatus.REJECT) {
			getOperateBOP("poPublish").getStatus().setHidden(true);
			getOperateBOP("poConfirm").getStatus().setHidden(true);
			getOperateBOP("poReject").getStatus().setHidden(true);
		}
		//如果订单已确认或部分确认, 下列按钮隐藏
		else if(bo.getConfirmStatus() == ConfirmStatus.YES || bo.getConfirmStatus() == ConfirmStatusX.PART) {
			getOperateBOP("poConfirm").getStatus().setHidden(true);
			getOperateBOP("poPublish").getStatus().setHidden(true);
			getOperateBOP("poReject").getStatus().setHidden(true);
			getOperateBOP("poCancelReject").getStatus().setHidden(true);
			getOperateBOP("poCancelPublish").getStatus().setHidden(true);
		}
		//如果订单已发布, 下列按钮隐藏
		else if(bo.getPublishStatus() == PublishStatus.YES) {
			getOperateBOP("poPublish").getStatus().setHidden(true);
		}
		//如果订单未发布, 下列按钮隐藏
		else if(bo.getPublishStatus() == PublishStatus.NO) {
			getOperateBOP("poCancelPublish").getStatus().setHidden(true);
			getOperateBOP("poConfirm").getStatus().setHidden(true);
			getOperateBOP("poReject").getStatus().setHidden(true);
			getOperateBOP("poCancelReject").getStatus().setHidden(true);
		}

		//如果订单未确认, 下列按钮隐藏
		if(bo.getConfirmStatus() == ConfirmStatus.NO) {
			getOperateBOP("poCancelReject").getStatus().setHidden(true);
		}
		//如果非手动关闭则关闭按钮隐藏
		if(!BusSettingConstants.isManualClose()) {
			getOperateBOP("poClose").getStatus().setHidden(true);
		}
		//手动锁定采购订单
		if(BusSettingConstants.isManualLock() && bo.getManlockStatus() == LockStatus.YES)
			getOperateBOP("poManlock").getStatus().setHidden(true);

		if(BusSettingConstants.isManualLock() && bo.getManlockStatus() == LockStatus.NO)
			getOperateBOP("poManunlock").getStatus().setHidden(true);

		return this;
	}

	/**
	 * 查看订单明细
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public BP_PurchaseOrderBO viewDetial(BusinessObject bo) throws Exception {
		long boId = bo.getId();
		if(bo instanceof BP_PurchaseGoodsPlanBO)
			boId = ((BP_PurchaseGoodsPlanBO) bo).getPurchaseOrderItemBO().getPurchaseOrderBO().getId();
		if(bo instanceof BP_PurchaseOrderItemBO)
			boId = ((BP_PurchaseOrderItemBO) bo).getPurchaseOrderBO().getId();
		BP_PurchaseOrderBO result = (BP_PurchaseOrderBO)getRecord(boId);
		if(result.getManlockUser() != null) {
			BOProperty userCodeBOP = result.getManlockUser().getBOP("userCode");
			userCodeBOP.getStatus().setHidden(!BusSettingConstants.isManualLock());
		}
		BOHelper.initPreferencePage_lazy(result, this);

		return result;
	}

	/**
	 * 填写锁定原因页面显示订单信息
	 * @param bos
	 * @return
	 * @throws Exception
	 */
	public BP_PurchaseOrderBO viewOrderBO(List<BusinessObject> bos) throws Exception {
		if(ContainerUtil.isNull(bos))
			return null;

		BP_PurchaseOrderBO result = null;
		for(BusinessObject bo : bos) {
			if(bo instanceof BP_PurchaseOrderBO) {
				result = (BP_PurchaseOrderBO)getRecord(bo.getId());
				break;
			}
		}
		BOHelper.initPreferencePage_lazy(result, this);
		return result;
	}

	/**
	 * 订单批量发布
	 * @param boList
	 * @throws Exception
	 */
	public void publishBatch(List<BusinessObject> boList) throws Exception {
		getOPT_POPublish().publishBatch(boList);
	}

	/**
	 * 订单发布
	 * @param boList
	 * @throws Exception
	 */
	public void publish(List<BusinessObject> boList) throws Exception {
		getOPT_POPublish().publish(boList);
	}

	/**
	 * 批量取消发布
	 * @param boList
	 * @throws Exception
	 */
	public void cancelPublishBatch(List<BusinessObject> boList) throws Exception {
		getOPT_POPublish().cancelPublishBatch(boList);
	}

	/**
	 * 取消发布
	 * @param boList
	 * @throws Exception
	 */
	public void cancelPublish(List<BusinessObject> boList) throws Exception {
		getOPT_POPublish().cancelPublish(boList);
	}

	/**
	 * 供应商确认采购订单
	 * @param purchaseOrders
	 * @throws Exception
	 */
	public void confirm(List<BusinessObject> boList) throws Exception {
		getOPT_POConfirm().confirm(boList);
	}

	/**
	 * 供应商驳回采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void reject(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptPOReject().reject(boList);
	}

	/**
	 * 采购商取消驳回采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void cancelReject(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptPOReject().cancelReject(boList);
	}

	/**
	 * 采购商批量取消驳回采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void cancelRejectBatch(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptPOReject().cancelRejectBatch(boList);
	}

	/**
	 * 采购订单关闭
	 * @param boList
	 * @throws Exception
	 */
	public void close(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptPOClose().close(boList);
	}

	/**
	 * 采购订单批量关闭
	 * @param boList
	 * @throws Exception
	 */
	public void closeBatch(List<BusinessObject> boList) throws Exception {
		BusOptManager.getOptPOClose().closeBatch(boList);
	}

	/**
	 * 手动锁定采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void manlock(List<BusinessObject> boList) throws Exception {
		new POManlock().manlock(boList);
	}

	/**
	 * 手动解除锁定
	 * @param boList
	 * @throws Exception
	 */
	public void manunlock(List<BusinessObject> boList) throws Exception {
		new POManlock().manunlock(boList);
	}

	/**
	 * 保存或更新采购订单
	 * @param boList
	 * @throws Exception
	 */
	public void saveOrUpdateOrders(List<BP_PurchaseOrderBO> boList) throws Exception {
		Map<String, List<BP_PurchaseOrderBO>> result = getPoDownLoadFilter().filterPOs(boList);
		if(ContainerUtil.isNull(result))
			return;

		BP_PurchaseOrderItemBO itemBO = new BP_PurchaseOrderItemBO();
		//新增订单
		for(BP_PurchaseOrderBO orderBO : result.get(IPODownLoadFileter.INSERT)) {
			orderBO.setLockStatus(LockStatus.NO);
			orderBO.insert();
			itemBO.saveOrderItems(orderBO.getPurchaseOrderItemBOs(), orderBO, new StringBuffer());
		}

		//修改订单
		for(BP_PurchaseOrderBO orderBO : result.get(IPODownLoadFileter.UPDATE)) {
			BP_PurchaseOrderBO oldOrderBO = getDownLoadFilterDao().getTheSameOrder(orderBO);
			itemBO.updateOrderItems(orderBO.getPurchaseOrderItemBOs(), oldOrderBO, new StringBuffer());
			updatePurchaseOrder(orderBO, oldOrderBO);
		}
	}

	/**
	 * 更新订单主信息
	 * @throws Exception
	 */
	protected void updatePurchaseOrder(BP_PurchaseOrderBO newOrderBO, BP_PurchaseOrderBO oldOrderBO) throws Exception {
		long _id = oldOrderBO.getId();
		Integer _publishStatus = oldOrderBO.getPublishStatus();
		Timestamp _publishTime = oldOrderBO.getPublishTime();
		Integer _confirmStatus = oldOrderBO.getConfirmStatus();
		Timestamp _confirmTime = oldOrderBO.getConfirmTime();
		Integer _deliveryStatus = oldOrderBO.getDeliveryStatus();
		Integer _receiveStatus = oldOrderBO.getReceiveStatus();
		Integer _modifyCount = oldOrderBO.getModifyCount();
		Integer _closeStatus = oldOrderBO.getCloseStatus();
		PropertyUtils.copyPropertyWithOutNull(oldOrderBO, newOrderBO);
		oldOrderBO.setId(_id);
		oldOrderBO.setPublishStatus(_publishStatus);
		oldOrderBO.setPublishTime(_publishTime);
		oldOrderBO.setConfirmStatus(_confirmStatus);
		oldOrderBO.setConfirmTime(_confirmTime);
		oldOrderBO.setDeliveryStatus(_deliveryStatus);
		oldOrderBO.setReceiveStatus(_receiveStatus);
		oldOrderBO.setModifyCount(_modifyCount);
		if(oldOrderBO.getLockStatus() != null && oldOrderBO.getLockStatus() == LockStatus.YES)
			oldOrderBO.setCloseStatus(_closeStatus);

		oldOrderBO.update();
	}

	/**
	 * 更新订单确认状态
	 * @param orderBO
	 * @throws Exception
	 */
	public void updatePOConfirmStatus(BP_PurchaseOrderBO orderBO) throws Exception {
		Set<BP_PurchaseOrderItemBO> purchaseOrderItemBOs = orderBO.getPurchaseOrderItemBOs();
		if(ContainerUtil.isNull(purchaseOrderItemBOs))
			return;

		boolean hasPart = false;
		boolean hasConfirm = false;
		boolean hasNoConfirm = false;
		for(BP_PurchaseOrderItemBO itemBO : purchaseOrderItemBOs) {
			if(itemBO.getConfirmStatus() == ConfirmStatusX.PART) {
				hasPart = true;
				break;
			}
			else if(itemBO.getConfirmStatus() == ConfirmStatus.YES) {
				hasConfirm = true;
			}
			else if(itemBO.getConfirmStatus() == ConfirmStatus.NO) {
				hasNoConfirm = true;
			}
		}
		if(hasPart || (hasConfirm && hasNoConfirm)) {
			orderBO.setConfirmStatus(ConfirmStatusX.PART);
		}
		else if(hasNoConfirm && !hasConfirm && !hasPart) {
			orderBO.setConfirmStatus(ConfirmStatus.NO);
		}
		else
			orderBO.setConfirmStatus(ConfirmStatus.YES);

		orderBO.update();
	}

	/**
	 * 根据订单ID更新订单发货状态
	 * @param purchaseIds
	 * @throws Exception
	 */
	public void updatePODeliveryStatus(Collection<Long> purchaseIds) throws Exception {
		if(ContainerUtil.isNull(purchaseIds))
			return;

		for(Long purchaseId : purchaseIds){
			BP_PurchaseOrderBO order = (BP_PurchaseOrderBO)getDao().getById(BP_PurchaseOrderBO.class, purchaseId);
			Set<BP_PurchaseOrderItemBO> purchaseOrderItemBOs = order.getPurchaseOrderItemBOs();
			//是否有已发货明细
			boolean isDelivery = false;
			//是否有未发货明细
			boolean isNoDelivery = false;
			//是否有部分发货明细
			boolean isPart = false;
			for(BP_PurchaseOrderItemBO item : purchaseOrderItemBOs){
				if(item.getDeliveryStatus() == DeliveryStatus.YES)
					isDelivery = true;
				if(item.getDeliveryStatus() == DeliveryStatus.PART)
					isPart = true;
				if(item.getDeliveryStatus() == DeliveryStatus.NO)
					isNoDelivery = true;
			}
			if(isPart || (isDelivery && isNoDelivery))
				order.setDeliveryStatus(DeliveryStatus.PART);
			else if(isDelivery && !isNoDelivery && !isPart)
				order.setDeliveryStatus(DeliveryStatus.YES);
			else if(isNoDelivery && !isDelivery && !isPart)
				order.setDeliveryStatus(DeliveryStatus.NO);

			order.update();
		}
	}

	/**
	 * 根据订单ID更新订单收货状态
	 * @param purchaseIds
	 * @throws Exception
	 */
	public void updatePOReceiveStatus(Collection<Long> purchaseIds) throws Exception {
		if(ContainerUtil.isNull(purchaseIds))
			return;

		// 更新订单的收货状态
		BP_PurchaseOrderBO orderBO = null;
		BP_BuyerGoodsReceiveItemBO receiveItemBO = new BP_BuyerGoodsReceiveItemBO();
		for (Long orderId : purchaseIds) {
			orderBO = (BP_PurchaseOrderBO)getRecord(orderId);
			if (orderBO == null)
				continue;

			// 查询订单下面的明细是否都收货完成
			Set<BP_PurchaseOrderItemBO> orderItemBOs = orderBO.getPurchaseOrderItemBOs();
			// 是否收货完成
			boolean isReceive = false;
			// 是否存在未收货
			boolean isNoReceive = false;
			// 是否部分收货
			boolean isPart = false;
			double itemReceiveQty = 0d;
			for (BP_PurchaseOrderItemBO itemBO : orderItemBOs) {
				itemReceiveQty = receiveItemBO.getOrderItemReceiveQty(itemBO.getId());
				if(itemBO.getOrderQty() != 0 && itemBO.getOrderQty() == itemReceiveQty)
					isReceive = true;
				else if(itemReceiveQty != 0 && itemBO.getOrderQty() > itemReceiveQty)
					isPart = true;
				else
					isNoReceive = true;
			}
			// 采购订单整单收货完成
			if (isReceive && !isNoReceive && !isPart) {
				orderBO.setReceiveStatus(POReceiveStatus.YES);
				orderBO.update();
			}
			else if (isReceive || isPart) {
				orderBO.setReceiveStatus(POReceiveStatus.PART);
				orderBO.update();
			}
			else if (isNoReceive && !isReceive && !isPart) {
				orderBO.setReceiveStatus(POReceiveStatus.NO);
				orderBO.update();
			}

		}
	}

	/**
	 * 跳转到反馈订单页面
	 * @param orderBO
	 * @return
	 * @throws Exception
	 */
	public BP_PurchaseOrderBO gotoFeedback(BP_PurchaseOrderBO orderBO) throws Exception {
		BOHelper.initPreferencePage_lazy(orderBO, this);
		return orderBO;
	}

	/**
	 * 从接口中导入订单
	 * @throws BOException
	 */
	public void impPO() throws BOException {
		ImpExl impExl = new BP_ImpPurchaseOrderBO();
		impExl.setExlFile(ConstantFtpFiles.getPurchaseOrderFile());
		impExl.imp();
	}

	public String getFeedback() {
		if(feedback != null)
			return feedback;

		Integer feedbackCount = new BP_FeedBackBO().getfeedbackCount(this.getId(), BP_FeedBackBO.PURCHASE_ORDER);
		return AppLocalization.getLocalization("com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.feedback") + "[" + feedbackCount + "]";
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Integer getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(Integer changeStatus) {
		this.changeStatus = changeStatus;
	}

	public Integer getPublishStatus() {
		if(publishStatus == null)
			publishStatus = PublishStatus.NO;
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
		if(deliveryStatus == null)
			deliveryStatus = DeliveryStatus.NO;
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

	public UserBO getBuyerUser() {
		return buyerUser;
	}

	public void setBuyerUser(UserBO buyerUser) {
		this.buyerUser = buyerUser;
	}

	public OrganizationBO getBuyer() {
		return buyer;
	}

	public void setBuyer(OrganizationBO buyer) {
		this.buyer = buyer;
	}

	public OrganizationBO getVendor() {
		return vendor;
	}

	public void setVendor(OrganizationBO vendor) {
		this.vendor = vendor;
	}

	public UserBO getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(UserBO publishUser) {
		this.publishUser = publishUser;
	}

	public UserBO getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(UserBO confirmUser) {
		this.confirmUser = confirmUser;
	}

	public UserBO getCloseUser() {
		return closeUser;
	}

	public void setCloseUser(UserBO closeUser) {
		this.closeUser = closeUser;
	}

	public Set<BP_PurchaseOrderItemBO> getPurchaseOrderItemBOs() {
		return purchaseOrderItemBOs;
	}

	public void setPurchaseOrderItemBOs(
			Set<BP_PurchaseOrderItemBO> purchaseOrderItemBOs) {
		this.purchaseOrderItemBOs = purchaseOrderItemBOs;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
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

	public Integer getModifyCount() {
		return modifyCount;
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

	public IPODownLoadFileter getPoDownLoadFilter() {
		return poDownLoadFilter;
	}

	public void setPoDownLoadFilter(IPODownLoadFileter poDownLoadFilter) {
		this.poDownLoadFilter = poDownLoadFilter;
	}

	public POPublish getOPT_POPublish() {
		if(OPT_POPublish == null)
			OPT_POPublish = BusOptManager.getOptPOPublish();

		return OPT_POPublish;
	}

	public void setOPT_POPublish(POPublish oPT_POPublish) {
		OPT_POPublish = oPT_POPublish;
	}

	public POConfirm getOPT_POConfirm() {
		if(OPT_POConfirm == null)
			OPT_POConfirm = BusOptManager.getOptPOConfirm();

		return OPT_POConfirm;
	}

	public void setOPT_POConfirm(POConfirm oPT_POConfirm) {
		OPT_POConfirm = oPT_POConfirm;
	}

	public IPODownLoadFilterDao getDownLoadFilterDao() {
		if(downLoadFilterDao == null)
			downLoadFilterDao = (IPODownLoadFilterDao)SpringConstant.getCTX().getBean("IPODownLoadFilterDao");

		return downLoadFilterDao;
	}

	public void setDownLoadFilterDao(IPODownLoadFilterDao downLoadFilterDao) {
		this.downLoadFilterDao = downLoadFilterDao;
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

	public UserBO getManlockUser() {
		return manlockUser;
	}

	public void setManlockUser(UserBO manlockUser) {
		this.manlockUser = manlockUser;
	}

}