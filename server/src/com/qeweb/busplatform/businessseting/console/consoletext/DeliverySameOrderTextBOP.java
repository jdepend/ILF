package com.qeweb.busplatform.businessseting.console.consoletext;

/**
 * 每张发货单只发同一订单的货
 */
public class DeliverySameOrderTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 778909064736828628L;

	@Override
	protected String getConsoleText() {
		return "否：一张发货单中，可以存在多个订单的内容" +
				"	是：一张发货单中，必须是同一个订单的内容";
	}
}
