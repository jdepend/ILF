package com.qeweb.busplatform.pomanage.bop;


/**
 * 确认状态包含部分确认
 */
public class ConfirmStatusX extends ConfirmStatus {

	private static final long serialVersionUID = -7238732953303459876L;

	/**
	 * 部分确认
	 */
	public static final int PART = 2;

	@Override
	public String getKey() {
		return "qeweb_confirm_statusX";
	}
}
