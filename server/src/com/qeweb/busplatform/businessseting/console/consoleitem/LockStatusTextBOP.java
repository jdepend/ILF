package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;

/**
 * 订单是否支持手动锁定
 */
public class LockStatusTextBOP extends ConsoleTextBOP {

	private static final long serialVersionUID = 8805798303377172275L;

	@Override
	protected String getConsoleText() {
		return "否：订单主信息不支持手动锁定" +
				"	是：订单主信息支持手动锁定";
	}
}
