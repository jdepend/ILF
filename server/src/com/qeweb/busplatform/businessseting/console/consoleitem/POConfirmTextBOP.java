package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;

/**
 * 订单是否支持部分关闭
 */
public class POConfirmTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135435364736828627L;

	@Override
	protected String getConsoleText() {
		return "否：按采购订单主信息确认订单" +
		"	是：按采购订单明细信息确认订单";
	}
}
