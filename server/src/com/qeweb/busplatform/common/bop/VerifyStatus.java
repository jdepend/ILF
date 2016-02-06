package com.qeweb.busplatform.common.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;


/**
 * 审批状态
 */
public class VerifyStatus extends StatusBOP {

	private static final long serialVersionUID = 5672443889854799439L;

	/**
	 * 审批通过
	 */
	public final static int YES = 1;
	/**
	 * 未审批
	 */
	public final static int NO = 0;
	/**
	 * 审批驳回
	 */
	public final static int REJECT = -1;

	@Override
	public String getKey() {
		return "qeweb_verify_status";
	}
}
