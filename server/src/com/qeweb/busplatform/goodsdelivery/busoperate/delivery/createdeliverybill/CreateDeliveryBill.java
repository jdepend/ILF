package com.qeweb.busplatform.goodsdelivery.busoperate.delivery.createdeliverybill;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.bop.VerifyStatus;
import com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO;
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
 * 创建发货单
 * <p> 创建发货单有两种配置:
 * <li>1.每张发货单只发同一订单的货;
 * <li>2.每张发货单可以发多个订单的货.
 */
public abstract class CreateDeliveryBill {

	/**
	 * 验证选择生成发货单的供货计划
	 * <li>根据配置验证：发同一订单、多订单
	 * <li>根据配置验证：是否按照要求到货时间先后顺序发货
	 * @param boList
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public boolean validatePlanItem(List<BusinessObject> boList) throws Exception {

		if(!validatePlanData(boList))
			return false;

		return BusSettingConstants.isByOrderTimeASCSign() ? validatePlanOrderTime(boList) : true;
	}

	/**
	 * 校验能否根据选中的供货计划创建发货单
	 * @param boList
	 * @param bo
	 */
	abstract public boolean validatePlanData(List<BusinessObject> boList) throws Exception;

	/**
	 * 根据要求到货时间校验
	 * @param boList
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	private boolean validatePlanOrderTime(List<BusinessObject> boList) throws Exception {
		//已选择的计划ID集
		StringBuilder selectIds = new StringBuilder();
		for(BusinessObject tmpBO : boList)
			selectIds.append(tmpBO.getId()).append(",");
		selectIds.deleteCharAt(selectIds.length() - 1);

		for(int i = 0; i < boList.size(); i++) {
			BP_PendingDeliveryBO planBO = (BP_PendingDeliveryBO) boList.get(i);
			//查询是否有要求到货时间在此计划之前的
			String hql = "from BP_PendingDeliveryBO pending where pending.materialId = ?  "
					+ "and pending.buyerId = ? and pending.vendorId = ? "
					+ "and pending.orderTime < ? and pending.id not in (" + selectIds.toString() + ")";

			List<?> resutl = planBO.getDao().findBySql(hql, new Object[]{planBO.getMaterialId(),
					planBO.getBuyerId(), planBO.getVendorId(), planBO.getOrderTime()});

			if(ContainerUtil.isNotNull(resutl))
				throw new BOException(planBO.getMaterialCode() + AppLocalization.getLocalization("com.qeweb.busplatform.err.goods.err_6"));
		}

		return true;
	}

	/**
	 * 创建发货单
	 * @param boList 选中的发货看板记录
	 */
	public void create(List<BusinessObject> boList) throws Exception {
		if(!validateCreate(boList))
			return;

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
	 * <li>预计交货时间应当不大于订单计划中的最小"要求到货时间";
	 * <li>发货数量大于0, 并根据配置判断是否可以大于未发数量.
	 * @param boList
	 * @param bo
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
			throw new BOException("com.qeweb.busplatform.err.goods.err_3");

		for(int i = 1; i < boList.size(); i++) {
			BP_PendingDeliveryBO pendingBO = (BP_PendingDeliveryBO) boList.get(i);
			if(pendingBO.getWaitQty() == null || pendingBO.getWaitQty() <= 0d)
				throw new BOException("com.qeweb.busplatform.err.goods.err_4");
			else if(!BusSettingConstants.isExcessDelivery() && pendingBO.getWaitQty() > pendingBO.getShuldDlvQty())
				throw new BOException("com.qeweb.busplatform.err.goods.err_5");
		}

		return true;
	}
}
