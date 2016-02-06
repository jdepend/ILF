package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;

/**
 *	锁定状态，当单据处于异常时的锁定标实
 *
 */
public class LockStatus extends StatusBOP {

	private static final long serialVersionUID = 2738803682408807210L;

	/**
	 * 锁定
	 */
	public final static int YES = 1;

	/**
	 * 正常
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_lock_status";
	}
}
