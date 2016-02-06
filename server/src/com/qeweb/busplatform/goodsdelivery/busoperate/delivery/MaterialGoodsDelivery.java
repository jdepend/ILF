package com.qeweb.busplatform.goodsdelivery.busoperate.delivery;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.bop.VerifyStatus;
import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryMaterialBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;

/**
 * 按照物料创建发货单
 * <li>选择汇总的物料,输入发货单信息;
 * <li>验证发货单填写数据正确性;
 * <li>系统自动匹配采购订单明细、供货计划先进先出原则.
 */
public class MaterialGoodsDelivery extends GoodsDelivery {

	private static final long serialVersionUID = 6554645038854463011L;

	/**
	 * 选择发货物料验证
	 * @throws BOException
	 */
	@Override
	public boolean validatePlanItem(List<BusinessObject> boList) throws BOException {
		if(ContainerUtil.isNull(boList))
			throw new BOException("com.qeweb.busplatform.err.goods.err_1");

		//验证是否选择同一采购组织
		BP_PendingDeliveryMaterialBO planBO = (BP_PendingDeliveryMaterialBO) boList.get(0);
		long buyerId = planBO.getBuyerId();

		String errorMsg = "";
		BP_PendingDeliveryMaterialBO pendingDlvMaterial = null;
		for(int i = 0; i < boList.size(); i++) {
			pendingDlvMaterial = (BP_PendingDeliveryMaterialBO)boList.get(i);
			if(buyerId != pendingDlvMaterial.getBuyerId())
				throw new BOException("com.qeweb.busplatform.err.goods.err_7");
			if (pendingDlvMaterial.getWaitQty() == null || pendingDlvMaterial.getWaitQty() <= 0d) {
				errorMsg = "[" + pendingDlvMaterial.getMaterial().getMaterialName() + "]" + AppLocalization.getLocalization("com.qeweb.busplatform.err.goods.err_4");
				throw new BOException(errorMsg);
			}
			else if (!BusSettingConstants.isExcessDelivery() && pendingDlvMaterial.getWaitQty() > pendingDlvMaterial.getUnDlvQty()) {
				// 发货数量不能大于未发数量
				errorMsg = "[" + pendingDlvMaterial.getMaterial().getMaterialName() + "]" + AppLocalization.getLocalization("com.qeweb.busplatform.err.goods.err_5");
				throw new BOException(errorMsg);
			}
		}

		return true;
	}

	/**
	 * 保存发货单
	 */
	@Override
	public void createDeliveryBill(List<BusinessObject> boList) throws Exception {
		if(!validateCreate(boList))
			return ;

		//创建发货单
		//保存发货单主信息
		BP_VendorGoodsDeliveryBO deliveryBO = (BP_VendorGoodsDeliveryBO) boList.get(0);
		//供应商
		deliveryBO.setVendor(UserContext.getOrgBO());
		//采购组织
		BP_PendingDeliveryBO _pendingBO = (BP_PendingDeliveryBO) boList.get(1);
		deliveryBO.setBuyer((OrganizationBO)deliveryBO.getDao().getById(OrganizationBO.class, _pendingBO.getBuyerId()));
		deliveryBO.setDeliveryStatus(DeliveryStatus.NO);
		deliveryBO.setVerifyStatus(VerifyStatus.NO);
		deliveryBO.setReceiveStatus(ReceiveStatus.NO);
		deliveryBO.insert();

		//保存发货单明细
		for(int i = 1; i < boList.size(); i++) {
			BP_PendingDeliveryBO pendingBO = (BP_PendingDeliveryBO) boList.get(i);
			BP_VendorGoodsDeliveryItemBO deliveryItemBO = new BP_VendorGoodsDeliveryItemBO();
			deliveryItemBO.setPurchaseItemId(pendingBO.getItemId());
			deliveryItemBO.setPurchaseGoodsPlanId(pendingBO.getId());
			deliveryItemBO.setDeliveryId(deliveryBO.getId());
			//行号
			deliveryItemBO.setItemNo(i);
			//设置已发货数量为"发货数量"
			deliveryItemBO.setDeliveryQty(pendingBO.getWaitQty());
			deliveryItemBO.insert();
		}
	}

	/**
	 * 创建发货单前的校验:
	 * <li>预计交货时间应当大于当前日期;
	 * <li>发货数量大于0, 并根据配置判断是否可以大于未发数量.
	 * @param boList
	 * @return
	 */
	private boolean validateCreate(List<BusinessObject> boList) throws Exception {

		if(boList.size() == 1)
			throw new BOException("com.qeweb.busplatform.err.goods.err_1");

		BP_VendorGoodsDeliveryBO deliveryBO = (BP_VendorGoodsDeliveryBO)boList.get(0);
		Timestamp estimatedDlvTime = deliveryBO.getEstimatedDlvTime() == null ? null :
			(DateUtils.stringToTimestamp(DateUtils.dateToString(deliveryBO.getEstimatedDlvTime()) + " 23:59:59",
			DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));

		if(estimatedDlvTime != null && estimatedDlvTime.before(new Date()))
			throw new BOException("com.qeweb.busplatform.err.goods.err_2");

		return true;
	}
}
