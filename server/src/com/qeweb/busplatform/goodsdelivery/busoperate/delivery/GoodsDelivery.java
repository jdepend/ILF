package com.qeweb.busplatform.goodsdelivery.busoperate.delivery;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.common.bop.DeliveryStatus;
import com.qeweb.busplatform.common.bop.ReceiveStatus;
import com.qeweb.busplatform.common.bop.SendStatus;
import com.qeweb.busplatform.common.bop.VerifyStatus;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO;
import com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO;
import com.qeweb.busplatform.goodsdelivery.bop.CancelDeliveryBOP;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 供应商发货操作，包括:
 * <li>按照供货计划发货;
 * <li>按照物料汇总发货
 */
public abstract class GoodsDelivery  implements Serializable{

	private static final long serialVersionUID = -8898263555025253327L;

	/**
	 * 校验能否根据选中的供货计划创建发货单
	 * @param boList
	 * @param bo
	 */
	abstract public boolean validatePlanItem(List<BusinessObject> boList) throws Exception;

	/**
	 * 创建发货单
	 * @param boList	页面信息列表
	 * @param bo		采购订单bo, 用于设置校验失败后的错误信息
	 * @throws Exception
	 */
	abstract public void createDeliveryBill(List<BusinessObject> boList) throws Exception;

	/**
	 * 发货操作
	 * @param boList
	 */
	public void delive(List<BusinessObject> boList) throws Exception {
		BP_VendorGoodsDeliveryBO param = (BP_VendorGoodsDeliveryBO) boList.get(0);
		if(BusSettingConstants.isVerify() && param.getVerifyStatus() != VerifyStatus.YES) {
			throw new BOException("审核尚未通过！");
		}
		else if(param.getLockStatus() == LockStatus.YES) {
			throw new BOException("发货单被锁定，请取消发货单！");
		}

		BP_VendorGoodsDeliveryBO result = (BP_VendorGoodsDeliveryBO)param.getRecord(param.getId());
		Set<BP_VendorGoodsDeliveryItemBO> deliveryItems = result.getVendorGoodsDeliveryItemBOs();
		BP_PurchaseOrderItemBO purchaseItem = null;
		for(BP_VendorGoodsDeliveryItemBO itemBO : deliveryItems) {
			purchaseItem = itemBO.getPurchaseOrderItemBO();
			// 若订单行被锁定给出提示
			if(purchaseItem.getLockStatus() == LockStatus.YES ||
					purchaseItem.getPurchaseOrderBO().getManlockStatus() == LockStatus.YES) {
				throw new BOException("订单:" + purchaseItem.getPurchaseOrderBO().getPurchaseNo() + "被锁定无法发货！请取消发货单");
			}
		}
		result.setDeliveryStatus(DeliveryStatus.YES);
		result.setDeliveryUser(UserContext.getUserBO());
		result.setDeliveryTime(DateUtils.getCurrentTimestamp());
		result.update();
		//发货同步更新订单状态、订单明细状态、供货计划状态
		updateDeliveryStatus(result);
	}

	/**
	 * 取消发货
	 * @param boList
	 */
	public void cancelDelive(List<BusinessObject> boList) throws Exception {
		BP_VendorGoodsDeliveryBO param = (BP_VendorGoodsDeliveryBO) boList.get(0);

		BP_VendorGoodsDeliveryBO result = (BP_VendorGoodsDeliveryBO)param.getRecord(param.getId());
		//发货状态
		Integer deliveryStatus = result.getDeliveryStatus();
		if(result.getReceiveStatus() == ReceiveStatus.YES) {
			throw new BOException("com.qeweb.busplatform.err.goods.err_8");
		}
		//更改发货单状态,并删除该发货单
		result.setDeliveryTime(null);
		result.setDeliveryStatus(DeliveryStatus.NO);
		result.setDeleteFlag(IBaseDao.DELETE_SIGNE);
		result.update();

		Set<BP_VendorGoodsDeliveryItemBO> deliveryItems = result.getVendorGoodsDeliveryItemBOs();
		if(ContainerUtil.isNull(deliveryItems))
			return;

		//删除订单明细
		for(BP_VendorGoodsDeliveryItemBO item : deliveryItems) {
			item.setDeleteFlag(IBaseDao.DELETE_SIGNE);
			item.update();
		}
		//取消已发货的发货单需更新订单、明细、计划发货状态
		if(deliveryStatus == DeliveryStatus.YES)
			updateDeliveryStatus(result);
	}

	/**
	 * 更新发货状态
	 * @param deliveryBO
	 */
	public void updateDeliveryStatus(BP_VendorGoodsDeliveryBO deliveryBO) throws Exception{
		if(deliveryBO == null)
			return;

		//发货单关联订单明细ID
		Set<Long> purchaseItemIds = new HashSet<Long>();
		//发货单关联采购订单ID
		Set<Long> purchaseIds = new HashSet<Long>();

		//更新计划发货状态
		updatePOPlanDeliveryStatus(deliveryBO,purchaseItemIds, purchaseIds);
		//更新订单明细发货状态
		new BP_PurchaseOrderItemBO().updatePOItemsDeliveryStatus(purchaseItemIds);
		//更新订单发货状态
		new BP_PurchaseOrderBO().updatePODeliveryStatus(purchaseIds);
	}

	/**
	 * 更新供货计划发货状态
	 * @param deliveryBO
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	protected void updatePOPlanDeliveryStatus(BP_VendorGoodsDeliveryBO deliveryBO, Set<Long> purchaseItemIds, Set<Long> purchaseIds) throws Exception{
		Set<BP_VendorGoodsDeliveryItemBO> deliveryItems = deliveryBO.getVendorGoodsDeliveryItemBOs();

		for(BP_VendorGoodsDeliveryItemBO item : deliveryItems){
			purchaseIds.add(item.getPurchaseOrderItemBO().getPurchaseOrderBO().getId());
			purchaseItemIds.add(item.getPurchaseItemId());
			//更新发货计划发货状态
			BP_PurchaseGoodsPlanBO purchaseGoodsPlan = item.getPurchaseGoodsPlan();
			Double deliveryQty = 0d;
			//若发货单已发货并且已收货则将实收数量统计至此计划的已发数量
			//若发货单已发未收则将发货数量统计进此计划的已发数量
			//1、已发未收
			String hql = "select sum(item.deliveryQty) from BP_VendorGoodsDeliveryItemBO item where "
				+ " item.vendorGoodsDelivery.deleteFlag=0 and item.deleteFlag=0 "
				+ " and item.vendorGoodsDelivery.deliveryStatus = ? "
				+ " and item.vendorGoodsDelivery.receiveStatus = ? "
				+ " and item.purchaseGoodsPlan.id = ?";
			List<Object> params = new LinkedList<Object>();
			params.add(SendStatus.YES);
			params.add(ReceiveStatus.NO);
			params.add(purchaseGoodsPlan.getId());
			List<Double> result = item.getDao().findBySql(hql.toString(), params.toArray());

			if(ContainerUtil.isNotNull(result))
				deliveryQty = (result.get(0) == null ? 0d : result.get(0));

			//2、已发已收
			hql = "select sum(item.receiveQty) from BP_BuyerGoodsReceiveItemBO item where "
				+ " item.buyerGoodsReceiveBO.deleteFlag=0 and item.deleteFlag=0 "
				+ " and item.vendorGoodsDeliveryItemBO.id in(select ditem.id from BP_VendorGoodsDeliveryItemBO ditem where "
				+ " ditem.vendorGoodsDelivery.deleteFlag=0 and ditem.deleteFlag=0 "
				+ " and ditem.vendorGoodsDelivery.deliveryStatus = ? "
				+ " and ditem.vendorGoodsDelivery.receiveStatus = ? "
				+ " and ditem.purchaseGoodsPlan.id = ? )";
			params.clear();
			params.add(SendStatus.YES);
			params.add(ReceiveStatus.YES);
			params.add(purchaseGoodsPlan.getId());
			result = item.getDao().findBySql(hql.toString(), params.toArray());
			if(ContainerUtil.isNotNull(result))
				deliveryQty += (result.get(0) == null ? 0d : result.get(0));

			//未发货
			if(deliveryQty == 0d)
				purchaseGoodsPlan.setDeliveryStatus(DeliveryStatus.NO);
			//部分发货
			else if(purchaseGoodsPlan.getOrderQty() > deliveryQty)
				purchaseGoodsPlan.setDeliveryStatus(DeliveryStatus.PART);
			//发货完成
			else if (purchaseGoodsPlan.getOrderQty() <= deliveryQty)
				purchaseGoodsPlan.setDeliveryStatus(DeliveryStatus.YES);

			purchaseGoodsPlan.update();
		}
	}



	/**
	 * 获取发货单审核BOP.
	 * <p>
	 * 如果配置了发货单审核功能, 则展现该bop, 否则隐藏.
	 * @return
	 */
	public BOProperty getVerifyBOP() {
		BOProperty bop = new VerifyStatus();
		if(!BusSettingConstants.isVerify())
			bop.getStatus().setHidden(true);

		return bop;
	}

	/**
	 * 获取发货按钮.
	 * <li>如果已经发货或采购商已收货, 则隐藏发货按钮;
	 * <li>如果需要审批, 且审批未通过, 则隐藏发货按钮 .
	 * @param bo BP_VendorGoodsDeliveryBO
	 * @return
	 */
	public OperateBOP getDliveBtn(BP_VendorGoodsDeliveryBO bo) {
		OperateBOP bop = new OperateBOP();
		bop.setHasConfirm(true);
		//如果已经发货或采购商已收货, 则隐藏发货按钮
		if(bo.getDeliveryStatus() == DeliveryStatus.YES || bo.getReceiveStatus() == ReceiveStatus.YES
				|| bo.getLockStatus() == LockStatus.YES)
			bop.getStatus().setHidden(true);
		//如果需要审批, 且审批未通过, 则隐藏发货按钮
		else if(BusSettingConstants.isVerify() && bo.getVerifyStatus() != VerifyStatus.YES)
			bop.getStatus().setHidden(true);

		return bop;
	}

	/**
	 * 获取取消发货按钮
	 * <li>如果采购商已收货, 则隐藏发货按钮;
	 * <li>如果需要审批, 且审批通过, 隐藏取消发货按钮.
	 * @param bo BP_VendorGoodsDeliveryBO
	 * @return
	 */
	public OperateBOP getCancelDliveBtn(BP_VendorGoodsDeliveryBO bo) {
		OperateBOP bop = new CancelDeliveryBOP();

		//如果采购商已收货, 则隐藏发货按钮
		if(bo.getReceiveStatus() == ReceiveStatus.YES)
			bop.getStatus().setHidden(true);

		return bop;
	}
}
