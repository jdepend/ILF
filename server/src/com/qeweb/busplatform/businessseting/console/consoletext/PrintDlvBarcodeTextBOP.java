package com.qeweb.busplatform.businessseting.console.consoletext;


/**
 * 是否打印发货单条码
 */
public class PrintDlvBarcodeTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135444364766998627L;

	@Override
	protected String getConsoleText() {
		return "否：发货单打印时，不需要提供发货单号的条码" +
			"	是：发货单打印时，需要提供发货单号的条码";
	}
}
