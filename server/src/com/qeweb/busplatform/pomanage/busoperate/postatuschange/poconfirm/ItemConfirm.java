package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.businessseting.BusSettingConstants;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;

/**
 * 按订单行确认.
 * <li>对订单的每个订单行进行确认, 修改订单行状态为"已确认";
 * <li>当所有订单行都为"已确认"时, 修改订单状态为"已确认";
 * <li>当订单中的部分订单行状态为"已确认"时,且没有一个订单行状态为"驳回"时, 修改订单状态为"部分确认".
 */
public class ItemConfirm extends POConfirm {

	@Override
	public OperateBOP getBtn_WholeConfirm() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemConfirm() {
		OperateBOP operateBOP = new OperateBOP();
		if(BusSettingConstants.isSplitGoosPlan() && !BusSettingConstants.isPOConfirmGoosPlan())
			operateBOP.getStatus().setHidden(true);

		operateBOP.setHasConfirm(true);
		return operateBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanConfirm() {
		OperateBOP operateBOP = new OperateBOP();
		operateBOP.setHasConfirm(true);
		return operateBOP;
	}

	protected boolean validate(List<BusinessObject> boList) throws Exception {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderItemBO))
				continue;

			BP_PurchaseOrderItemBO purchaseItem = (BP_PurchaseOrderItemBO)paramBO.getRecord(paramBO.getId());
			if(purchaseItem.getConfirmStatus() == ConfirmStatusX.REJECT) {
				throw new BOException("com.qeweb.busplatform.err.po.err_2");
			}
			else if(purchaseItem.getConfirmStatus() == ConfirmStatusX.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_3");
			}
			else if(purchaseItem.getPurchaseOrderBO().getManlockStatus() == LockStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_27");
			}
		}

		return true;
	}

	/**
	 * 订单确认, 可确认单笔订单的所有或其中某一订单行
	 */
	@Override
	protected void confirmBatch(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList))
			return;

		if(boList.get(0) instanceof BP_PurchaseOrderBO) {
			getPoCommon().changeConfirmStatus(boList.get(0));
		}
		else {
			Timestamp timestamp = DateUtils.getCurrentTimestamp();
			BusinessObject bo = boList.get(0);
			BP_PurchaseOrderItemBO item = (BP_PurchaseOrderItemBO)bo.getRecord(bo.getId());

			getPoCommon().changeConfirmItemStatus(item, timestamp);
			//修改订单状态
			changePOConfirmStatus(item, timestamp);
		}
	}

	/**
	 * 根据订单明细修改订单状态:
	 * <li> 1.当订单中的部分订单行状态为"已确认"时,且没有一个订单行状态为"驳回"时, 修改订单状态为"部分确认".
	 * <li> 2.当其中一个订单行状态为"驳回"时, 不做修改.
	 * @param poItem
	 * @param timestamp
	 * @throws Exception
	 */
	private void changePOConfirmStatus(BP_PurchaseOrderItemBO poItem, Timestamp timestamp) throws Exception {
		BP_PurchaseOrderBO purchaseOrder = poItem.getPurchaseOrderBO();
		Set<BP_PurchaseOrderItemBO> items = purchaseOrder.getPurchaseOrderItemBOs();

		//部分确认标识
		boolean part = false;
		for(BP_PurchaseOrderItemBO item : items) {
			//如果有一个订单明细为驳回, 则不修改订单状态(此时订单状态为"驳回")
			if(item.getConfirmStatus() == ConfirmStatusX.REJECT)
				return;
			//如果有一个订单明细为"未确认", 则设置part为true
			else if(item.getConfirmStatus() == ConfirmStatusX.NO)
				part = true;
		}

		if(part)
			purchaseOrder.setConfirmStatus(ConfirmStatusX.PART);
		else
			purchaseOrder.setConfirmStatus(ConfirmStatusX.YES);

		purchaseOrder.setConfirmTime(timestamp);
		purchaseOrder.setConfirmUser(UserContext.getUserBO());
	}

}
