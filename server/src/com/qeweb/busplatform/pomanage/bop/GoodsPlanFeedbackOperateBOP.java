package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 供货计划反馈BOP
 */
public class GoodsPlanFeedbackOperateBOP extends OperateBOP {


	private static final long serialVersionUID = 1892558062217542015L;

	public String getOperate() {
		return "bp_PurchaseGoodsPlanBO.gotoFeedback";
	}
}
