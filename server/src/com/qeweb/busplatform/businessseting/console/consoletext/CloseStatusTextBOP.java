package com.qeweb.busplatform.businessseting.console.consoletext;

/**
 * 订单是否支持部分关闭
 */
public class CloseStatusTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135435364736828627L;

	@Override
	protected String getConsoleText() {
		return "否：订单主信息不支持部分关闭，查询条件中没有“部分关闭”项" +
				"	是：订单主信息支持部分关闭，查询条件中有“部分关闭”项";
	}
}
