package com.qeweb.busplatform.common.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 发货状态
 */
public class DeliveryStatus extends StatusBOP {

	private static final long serialVersionUID = -6192479801503704629L;

	/**
	 * 已发货
	 */
	public final static int YES = 1;
	/**
	 * 未发货
	 */
	public final static int NO = 0;
	/**
	 * 部分发货
	 */
	public static final int PART = -1;

	@Override
	public String getKey() {
		return "qeweb_delivery_status";
	}
}
