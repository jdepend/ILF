package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * 供货计划修改历史
 *
 */
public class GoodsPlanModifyHisOperateBOP extends OperateBOP {


	private static final long serialVersionUID = 3755953794960770824L;

	public String getOperate() {
		return "bp_PurchaseGoodsPlanBO.showGoodsPlanHis";
	}
}
