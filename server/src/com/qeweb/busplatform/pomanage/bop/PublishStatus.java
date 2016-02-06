package com.qeweb.busplatform.pomanage.bop;

import com.qeweb.framework.bc.sysbop.StatusBOP;

/**
 * 发布状态
 */
public class PublishStatus extends StatusBOP {

	private static final long serialVersionUID = 458374517844316772L;

	/**
	 * 已发布
	 */
	public final static int YES = 1;
	/**
	 * 未发布
	 */
	public final static int NO = 0;

	@Override
	public String getKey() {
		return "qeweb_publish_status";
	}
}
