package com.qeweb.busplatform.businessseting.console.consoletext;

/**
 * 关闭方式
 */
public class CloseModeTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135435364736828626L;

	@Override
	protected String getConsoleText() {
		return "手动关闭：人工在系统中执行订单关闭" +
			"     自动关闭：满足关闭条件时，系统自动执行订单关闭" +
			"     ERP同步关闭：关闭状态作为订单的属性，从ERP下载，当“采购订单来源=ERP”时，该项有效";
	}
}
