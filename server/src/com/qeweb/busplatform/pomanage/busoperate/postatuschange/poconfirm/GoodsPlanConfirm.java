package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
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
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 按供货计划确认确认，一条订单行对应多条供货计划.
 * <li>对订单的每个订单行的供货计划进行确认, 修改订单行状态为"已确认","部分确认";
 * <li>当所有订单行的供货计划都为"已确认"时, 修改订单行状态为"已确认";
 * <li>当订单行中的部分供货计划状态为"已确认"时,且没有一个供货计划状态为"驳回"时, 修改订单行状态为"部分确认".
 * <li>当所有订单行都为"已确认"时,修改订单状态为"已确认";
 * <li>当订单中的部分订单行状态为"已确认"时,且没有一个订单行状态为"驳回"时, 修改订单状态为"部分确认".
 */
public class GoodsPlanConfirm extends POConfirm {

	@Override
	public OperateBOP getBtn_WholeConfirm() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemConfirm() {
		OperateBOP operateBOP = new OperateBOP();
		operateBOP.getStatus().setHidden(true);
		return operateBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanConfirm() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);
		return selectMdBOP;
	}

	@Override
	protected boolean validate(List<BusinessObject> boList) throws Exception {
		for (BusinessObject paramBO : boList) {
			if (!(paramBO instanceof BP_PurchaseGoodsPlanBO))
				continue;

			BP_PurchaseGoodsPlanBO goodsPlanBO = (BP_PurchaseGoodsPlanBO) paramBO.getRecord(paramBO.getId());
			if (goodsPlanBO.getConfirmStatus() == ConfirmStatusX.REJECT) {
				throw new BOException("com.qeweb.busplatform.err.po.err_2");
			}
			else if (goodsPlanBO.getConfirmStatus() == ConfirmStatusX.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_3");
			}
			else if(goodsPlanBO.getPurchaseOrderItemBO().getPurchaseOrderBO().getManlockStatus()
					== LockStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_27");
			}
		}

		return true;
	}

	@Override
	protected void confirmBatch(List<BusinessObject> boList) throws Exception {
		if (ContainerUtil.isNull(boList))
			return;

		for(BusinessObject bo : boList) {
			if(bo instanceof BP_PurchaseGoodsPlanBO) {
				Timestamp timestamp = DateUtils.getCurrentTimestamp();
				BP_PurchaseGoodsPlanBO item = (BP_PurchaseGoodsPlanBO) bo.getRecord(bo.getId());
				getPoCommon().changeConfirmGoodsPlanStatus(item, timestamp);

				changePOItemConfirmStatus(item, timestamp);
			}
		}
	}

	/**
	 * 根据供货计划修改订单明细确认状态:
	 * <li> 1.当供货计划确认状态为"已确认"时,且没有一个供货计划状态态为"驳回"时, 修改订单明细行状态为"部分确认".
	 * <li> 2.当其中一个供货计划状态为"驳回"时, 不做修改.
	 * @param goodsPlan
	 * @param timestamp
	 * @throws Exception
	 */
	private void changePOItemConfirmStatus(BP_PurchaseGoodsPlanBO goodsPlan, Timestamp timestamp) throws Exception {
		BP_PurchaseOrderItemBO purchaseOrderItem = goodsPlan.getPurchaseOrderItemBO();
		Set<BP_PurchaseGoodsPlanBO> items = purchaseOrderItem.getPurchaseGoodsPlanBOs();

		//部分确认标识
		boolean part = false;
		for(BP_PurchaseGoodsPlanBO item : items) {
			//如果有一个供货计划为驳回, 则不修改订单明细状态(此时订单明细行状态为"驳回")
			if(item.getConfirmStatus() == ConfirmStatusX.REJECT)
				return;
			//如果有一个供货计划为"未确认", 则设置part为true
			else if(item.getConfirmStatus() == ConfirmStatusX.NO)
				part = true;
		}

		if(part)
			purchaseOrderItem.setConfirmStatus(ConfirmStatusX.PART);
		else
			purchaseOrderItem.setConfirmStatus(ConfirmStatusX.YES);

		purchaseOrderItem.setConfirmTime(timestamp);
		purchaseOrderItem.setConfirmUser(UserContext.getUserBO());

		changePOConfirmStatus(purchaseOrderItem, timestamp);
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
