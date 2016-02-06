package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 关闭状态（关闭、未关闭状态）
 */
public class CloseStatus extends StatusBOP {

	private static final long serialVersionUID = -2716806933232719800L;

	/**
	 * 已关闭
	 */
	public final static int YES = 1;
	/**
	 * 未关闭
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_close_status";
	}
}
