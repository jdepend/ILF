package com.qeweb.busplatform.businessseting.console.consoleitem;

import com.qeweb.busplatform.businessseting.console.consoletext.ConsoleTextBOP;

/**
 * 订单供货计划拆分
 */
public class POGoodsPlanTextBOP extends ConsoleTextBOP {

	/**
	 *
	 */
	private static final long serialVersionUID = 4208146465995761339L;

	@Override
	protected String getConsoleText() {
		return "否：订单明细的供货计划不拆分" +
		"	是：订单明细供货计划拆分为多条";
	}
}
