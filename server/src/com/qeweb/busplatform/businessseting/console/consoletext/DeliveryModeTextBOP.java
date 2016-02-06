package com.qeweb.busplatform.businessseting.console.consoletext;

/**
 * 供应商发货类型
 */
public class DeliveryModeTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7785435364736828628L;

	@Override
	protected String getConsoleText() {
		return "按订单发货：选取订单明细进行发货" +
				"	按物料发货：选取物料进行发货" +
				"   重新登录系统后有效";
	}
}
