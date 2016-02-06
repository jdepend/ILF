package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;

/**
 * 确认状态
 */
public class ConfirmStatus extends StatusBOP {

	private static final long serialVersionUID = 4158374517844316772L;

	/**
	 * 已确认
	 */
	public final static int YES = 1;
	/**
	 * 未确认
	 */
	public final static int NO = 0;
	/**
	 * 驳回
	 */
	public static final int REJECT = -1;

	@Override
	public String getKey() {
		return "qeweb_confirm_status";
	}
}
