package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 收货状态
 */
public class POReceiveStatus extends StatusBOP {

	private static final long serialVersionUID = -2716806933232719892L;

	/**
	 * 已收货
	 */
	public final static int YES = 1;
	/**
	 * 未收货
	 */
	public final static int NO = 0;
	/**
	 * 部分收货
	 */
	public final static int PART = -1;

	@Override
	public String getKey() {
		return "qeweb_poreceive_status";
	}
}
