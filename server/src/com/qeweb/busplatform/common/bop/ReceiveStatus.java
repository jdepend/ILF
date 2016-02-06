package com.qeweb.busplatform.common.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 收货状态(发货单)
 */
public class ReceiveStatus extends StatusBOP {

	private static final long serialVersionUID = -480074160157718213L;

	/**
	 * 已收货
	 */
	public final static int YES = 1;
	/**
	 * 未收货
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_receive_status";
	}
}
