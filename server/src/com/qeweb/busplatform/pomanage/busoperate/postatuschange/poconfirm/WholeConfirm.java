package com.qeweb.busplatform.pomanage.busoperate.postatuschange.poconfirm;

import java.util.List;

import com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO;
import com.qeweb.busplatform.pomanage.bop.CloseStatus;
import com.qeweb.busplatform.pomanage.bop.ConfirmStatus;
import com.qeweb.busplatform.pomanage.bop.LockStatus;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 整单确认
 * <li>对整个订单进行确认, 修改订单状态为"已确认";
 * <li>同时修改订单中的每个订单行的状态为"已确认";
 * <li>整单确认可同时对多个订单进行确认.
 */
public class WholeConfirm extends POConfirm {

	@Override
	public OperateBOP getBtn_WholeConfirm() {
		SelectMdBOP selectMdBOP = new SelectMdBOP();
		selectMdBOP.setHasConfirm(true);
		return selectMdBOP;
	}

	@Override
	public OperateBOP getBtn_ItemConfirm() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	public OperateBOP getBtn_GoodsPlanConfirm() {
		OperateBOP optBOP = new OperateBOP();
		optBOP.getStatus().setHidden(true);

		return optBOP;
	}

	@Override
	protected boolean validate(List<BusinessObject> boList) throws Exception {
		if(ContainerUtil.isNull(boList)) {
			throw new BOException("com.qeweb.busplatform.err.po.err_1");
		}

		for(BusinessObject paramBO : boList) {
			if(!(paramBO instanceof BP_PurchaseOrderBO))
				break;

			BP_PurchaseOrderBO purchaseOrder = (BP_PurchaseOrderBO)paramBO.getRecord(paramBO.getId());
			if(purchaseOrder.getConfirmStatus() == ConfirmStatus.REJECT) {
				throw new BOException("com.qeweb.busplatform.err.po.err_2");
			}
			else if(purchaseOrder.getConfirmStatus() == ConfirmStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_3");
			}
			else if(purchaseOrder.getCloseStatus() == CloseStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_4");
			}
			else if(purchaseOrder.getManlockStatus() == LockStatus.YES) {
				throw new BOException("com.qeweb.busplatform.err.po.err_27");
			}
		}

		return true;
	}

	@Override
	protected void confirmBatch(List<BusinessObject> boList) throws Exception {
		getPoCommon().changeConfirmStatus(boList);
	}
}
