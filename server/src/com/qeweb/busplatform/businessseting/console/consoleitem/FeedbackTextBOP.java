package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;

/**
 * 反馈方式
 */
public class FeedbackTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7135435364736828628L;

	@Override
	protected String getConsoleText() {
		return "是：针对采购订单主信息进行反馈操作" +
				"	否：针对采购订单明细信息进行反馈操作";
	}
}
