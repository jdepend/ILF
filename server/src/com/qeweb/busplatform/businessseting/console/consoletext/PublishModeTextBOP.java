package com.qeweb.busplatform.businessseting.console.consoletext;


/**
 * 订单确认方式
 */
public class PublishModeTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135435364736828625L;

	@Override
	protected String getConsoleText() {
		return "手动发布：订单进入系统后，手动发布给供应商" +
				"	自动发布：订单进入系统后，自动发布给供应商";
	}
}
