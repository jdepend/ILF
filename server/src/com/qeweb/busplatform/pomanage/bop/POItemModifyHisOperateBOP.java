package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.OperateBOP;

/**
 * po明细修改历史BOP
 * @author alex
 *
 */
public class POItemModifyHisOperateBOP extends OperateBOP {

	private static final long serialVersionUID = -3791930335548766062L;

	public String getOperate() {
		return "bp_PurchaseOrderItemBO.showItemInfo";
	}
}
