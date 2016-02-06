package com.qeweb.busplatform.common.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 发货状态
 */
public class SendStatus extends StatusBOP {

	private static final long serialVersionUID = -2699716465329605870L;

	/**
	 * 已发货
	 */
	public final static int YES = 1;
	/**
	 * 未发货
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_send_status";
	}
}
