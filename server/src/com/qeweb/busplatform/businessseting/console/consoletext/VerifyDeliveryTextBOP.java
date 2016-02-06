package com.qeweb.busplatform.businessseting.console.consoletext;


/**
 * 发货单是否需要审核
 */
public class VerifyDeliveryTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135490364766998627L;

	@Override
	protected String getConsoleText() {
		return "否：发货前，不需要采购商对发货单进行审核" +
				"	是：发货前，需要采购商对发货单进行审核";
	}
}
