package com.qeweb.busplatform.pomanage.busoperate.postatuschange.pororeject;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.PublishStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 整单驳回:
 * <li>对整个订单进行驳回, 修改订单状态为"驳回";
 * <li>同时修改订单中的每个订单行的状态为"驳回";
 * <li>整单驳回可同时对多个订单进行驳回.
 * <br><br>
 * 取消驳回:
 * <li>订单和订单中的每个订单行的状态均改为"未确认".
 */
public class WholeReject extends POReject{

	@Override
	public OperateBOP getBtn_WholeReject() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);
		return selectMdBOP;
	}

	@Override
	public OperateBOP getBtn_ItemReject() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanReject() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);
		return selectMdBOP;
	}

	@Override
	public OperateBOP getBtn_ItemCancelReject() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_CancelGoodsPlanReject() {
		OperateBOP operateBOP = new OperateBOP();
		operateBOP.getStatus().setHidden(true);

		return operateBOP;
	}

	@Override
	public void reject(List<BusinessObject> boList) throws Exception {
		if(validateReject(boList))
			getPORejectCommon().changeConfirmStatus(boList);
	}

	@Override
	public void cancelReject(List<BusinessObject> boList) throws Exception {
		cancelRejectBatch(boList);
	}

	/**
	 * 批量取消驳回(订单查询页面)
	 * @param boList
	 * @param bo
	 * @throws Exception
	 */
	@Override
	public void cancelRejectBatch(List<BusinessObject> boList) throws Exception {
		if(validateCancelRjtBatch(boList))
			getPoCancelRejectCommon().changeConfirmStatus(boList);
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
			else if(purchaseOrder.getConfirmStatus() == ConfirmStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_20");
			else if(purchaseOrder.getCloseStatus() == CloseStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_21");
			else if(purchaseOrder.getConfirmStatus() != ConfirmStatus.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_22");
		}
		return true;
	}

	/**
	 * 校验是否可以驳回
	 * @param boList
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	private boolean validateReject(List<BusinessObject> boList) throws Exception {
		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO;
			if(purchaseOrder.getConfirmStatus() == ConfirmStatus.YES)
				throw new BOException("com.qeweb.busplatform.err.po.err_23");
			else if(purchaseOrder.getConfirmStatus() == ConfirmStatus.REJECT)
				throw new BOException("com.qeweb.busplatform.err.po.err_16");
		}

		return true;
	}

}
