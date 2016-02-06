package com.qeweb.busplatform.pomanage.bop;



/**
 * 关闭状态(关闭、未关闭、部分关闭状态)
 */
public class CloseStatusX extends CloseStatus {

	private static final long serialVersionUID = -5679415697017003618L;

	/**
	 * 部分关闭
	 */
	public static final int PART = -1;

	@Override
	public String getKey() {
		return "qeweb_close_statusX";
	}
}
