package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatusX;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 按订单行驳回:
 * <li>对订单的每个订单行进行驳回, 修改订单行状态为"驳回", 同时修改订单状态为"驳回".
 * <br><br>
 * 取消驳回:
 * <li> 1.当所有订单明细均为"未确认"时, 将订单状态修改为"未确认";
 * <li> 2.当订单中的部分订单行状态为"已确认"时,且没有一个订单行状态为"驳回"时, 修改订单状态为"部分确认";
 * <li> 3.当其中一个订单行状态为"驳回"时, 不做修改.
 */
public class GoodsPlanReject extends POReject{

	@Override
	public OperateBOP getBtn_WholeReject() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_ItemReject() {
		OperateBOP operateBOP = new OperateBOP();
		operateBOP.setHasConfirm(true);

		return operateBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanReject() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);

		return selectMdBOP;
	}

	@Override
	public OperateBOP getBtn_ItemCancelReject() {
		OperateBOP operateBOP = new OperateBOP();
		operateBOP.setHasConfirm(true);

		return operateBOP;
	}

	@Override
	public OperateBOP getBtn_CancelGoodsPlanReject() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);

		return selectMdBOP;
	}

	@Override
	public void reject(List<BusinessObject> boList) throws Exception {
		if(!validateReject(boList))
			return;

		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseGoodsPlanBO))
				continue;

			Timestamp timestamp = DateUtils.getCurrentTimestamp();
			BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)paramBO.getRecord(paramBO.getId());
			getPORejectCommon().changeConfirmGoodsPlanStatus(goodsPlan, timestamp);

			poToReject(goodsPlan, timestamp);
		}

	}

	@Override
	public void cancelReject(List<BusinessObject> boList) throws Exception {
		if(!validateCancelReject(boList))
			return;

		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseGoodsPlanBO))
				continue;

			Timestamp timestamp = DateUtils.getCurrentTimestamp();
			BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)paramBO.getRecord(paramBO.getId());
			getPoCancelRejectCommon().changeConfirmGoodsPlanStatus(goodsPlan, timestamp);
			//修改订单状态
			poItemToCancelReject(goodsPlan, timestamp);
		}
	}

	/**
	 * 批量取消驳回(订单查询页面)
	 * @param boList
	 * @param bo
	 * @throws Exception
	 */
	@Override
	public void cancelRejectBatch(List<BusinessObject> boList) throws Exception {

		if(!validateCancelRjtBatch(boList))
			return;

		Timestamp timestamp = DateUtils.getCurrentTimestamp();
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				continue;

			poToCancelReject((BP_PurchaseOrderBO) paramBO, timestamp);
		}
	}

	/**
	 * 批量取消驳回的校验
	 * @param boList
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	private boolean validateCancelRjtBatch(List<BusinessObject> boList) throws Exception {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO;
			if(purchaseOrder.getPublishStatus() == PublishStatus.NO)
				throw new BOException("com.qeweb.busplatform.err.po.err_19");
			else if(purchaseOrder.getConfirmStatus() == ConfirmStatusX.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_20");
			else if(purchaseOrder.getCloseStatus() == CloseStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_21");
			else if(purchaseOrder.getConfirmStatus() != ConfirmStatusX.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_22");
		}
		return true;
	}

	/**
	 * 校验是否可以驳回
	 * @param boList
	 * @param bo
	 * @return
	 * @throws BOException
	 */
	private boolean validateReject(List<BusinessObject> boList) throws BOException {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseGoodsPlanBO))
				continue;

			BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)paramBO;
			if(goodsPlan.getConfirmStatus() == ConfirmStatusX.YES || goodsPlan.getConfirmStatus() == ConfirmStatusX.PART)
				throw new BOException("com.qeweb.busplatform.err.po.err_15");
			else if(goodsPlan.getConfirmStatus() == ConfirmStatusX.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_16");
		}

		return true;
	}

	/**
	 * 校验是否可以取消驳回
	 * @param boList
	 * @param bo
	 * @return
	 * @throws BOException
	 */
	private boolean validateCancelReject(List<BusinessObject> boList) throws Exception {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseGoodsPlanBO))
				continue;

			BP_PurchaseGoodsPlanBO goodsPlan = (BP_PurchaseGoodsPlanBO)paramBO.getRecord(paramBO.getId());
			BP_PurchaseOrderItemBO itemBO = goodsPlan.getPurchaseOrderItemBO();
			if(goodsPlan.getConfirmStatus() == ConfirmStatusX.YES || goodsPlan.getConfirmStatus() == ConfirmStatusX.PART)
				throw new BOException("com.qeweb.busplatform.err.po.err_17");
			else if(goodsPlan.getConfirmStatus() != ConfirmStatusX.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_18");
			else if(itemBO.getCloseStatus() != CloseStatus.NO) {
				throw new BOException("com.qeweb.busplatform.err.po.err_21");
			}
		}

		return true;
	}

	/**
	 * 驳回操作: 修改订单行、订单状态 为"驳回"
	 * @param goodsPlan
	 * @param timestamp
	 * @throws Exception
	 */
	private void poToReject(BP_PurchaseGoodsPlanBO goodsPlan, Timestamp timestamp) throws Exception {
		BP_PurchaseOrderItemBO purchaseOrderItem = goodsPlan.getPurchaseOrderItemBO();
		purchaseOrderItem.setConfirmStatus(ConfirmStatusX.REJECT);
		purchaseOrderItem.setConfirmTime(timestamp);

		BP_PurchaseOrderBO purchaseOrder = purchaseOrderItem.getPurchaseOrderBO();
		purchaseOrder.setConfirmStatus(ConfirmStatusX.REJECT);
		purchaseOrder.setConfirmTime(timestamp);
	}

	/**
	 * 取消驳回操作 : 根据订单明细 修改订单状态为"未确认" 或  "部分确认" 或"驳回"
	 * <li> 1.当所有订单明细均为"未确认"时, 将订单状态修改为"未确认";
	 * <li> 2.当订单中的部分订单行状态为"已确认"时,且没有一个订单行状态为"驳回"时, 修改订单状态为"部分确认";
	 * <li> 3.当其中一个订单行状态为"驳回"时, 不做修改.
	 * @param goodsPlan
	 * @param timestamp
	 * @throws Exception
	 */
	private void poItemToCancelReject(BP_PurchaseGoodsPlanBO goodsPlan, Timestamp timestamp) throws Exception {
		BP_PurchaseOrderItemBO orderItem = goodsPlan.getPurchaseOrderItemBO();
		Set<BP_PurchaseGoodsPlanBO> goodsPlans = orderItem.getPurchaseGoodsPlanBOs();

		//部分确认标识
		boolean part = false;
		for(BP_PurchaseGoodsPlanBO paramBO : goodsPlans) {
			//如果有一个供货计划为驳回, 则不修改供货计划状态(此时供货计划状态为"驳回")
			if(paramBO.getConfirmStatus() == ConfirmStatusX.REJECT)
				return;
			//如果有一个订单明细为"已确认", 则设置part为true
			else if(paramBO.getConfirmStatus() == ConfirmStatusX.YES)
				part = true;
		}

		if(part)
			orderItem.setConfirmStatus(ConfirmStatusX.PART);
		else
			orderItem.setConfirmStatus(ConfirmStatusX.NO);

		orderItem.setConfirmTime(timestamp);
		orderItem.update();

		poToCancelReject(orderItem.getPurchaseOrderBO(), timestamp);
	}

	/**
	 * 取消驳回操作: 根据订单,修改订单及其明细的状态.
	 * <li> 如果订单明细为"驳回", 则该明细状态修改为"未确认";
	 * <li> 如果订单中所有明细状态均未"未确认", 则订单状态改为"未确认", 否则改为"部分确认".
	 * @param po
	 * @param timestamp
	 * @throws Exception
	 */
	private void poToCancelReject(BP_PurchaseOrderBO po, Timestamp timestamp) throws Exception {
		Set<BP_PurchaseOrderItemBO> items = ((BP_PurchaseOrderBO)po.getRecord(po.getId())).getPurchaseOrderItemBOs();

		//部分确认标识
		boolean part = false;
		for(BP_PurchaseOrderItemBO item : items) {
			//如果有一个订单明细为驳回, 则不修改订单状态(此时订单状态为"驳回")
			if(item.getConfirmStatus() == ConfirmStatusX.REJECT) {
				return;
			}
			else if(item.getCloseStatus() == CloseStatus.YES || item.getConfirmStatus() == ConfirmStatusX.NO) {
				continue;
			}
			else if(item.getConfirmStatus() == ConfirmStatusX.YES) {
				part = true;
				continue;
			}
		}

		if(part)
			po.setConfirmStatus(ConfirmStatusX.PART);
		else
			po.setConfirmStatus(ConfirmStatusX.NO);

		po.setConfirmTime(timestamp);
		po.update();
	}

}
