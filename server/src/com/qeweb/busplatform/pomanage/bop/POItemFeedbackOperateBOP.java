package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * POItem反馈BOP
 */
public class POItemFeedbackOperateBOP extends OperateBOP {

	private static final long serialVersionUID = -7248362506496231734L;

	public String getOperate() {
		return "bp_PurchaseOrderItemBO.gotoFeedback";
	}
}
