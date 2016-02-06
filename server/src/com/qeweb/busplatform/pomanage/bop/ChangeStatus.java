package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;

/**
 * 变更状态
 */
public class ChangeStatus extends StatusBOP {

	private static final long serialVersionUID = 4627574014191756443L;

	/**
	 * 已变更
	 */
	public final static int YES = 1;
	/**
	 * 未变更
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_change_status";
	}
}
