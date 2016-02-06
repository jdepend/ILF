package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * PO反馈BOP
 */
public class POFeedbackOperateBOP extends OperateBOP {

	private static final long serialVersionUID = -3791930335548766062L;

	public String getOperate() {
		return "bp_PurchaseOrderBO.gotoFeedback";
	}
}
