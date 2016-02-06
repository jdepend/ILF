package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;

/**
 * 是否允许超量发货
 */
public class ExcessDeliveryTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135490364736828627L;

	@Override
	protected String getConsoleText() {
		return "否：不允许超出订单的订购数量进行发货" +
				"	是：允许超出订单的订购数量进行发货";
	}
}
