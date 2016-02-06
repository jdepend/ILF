package com.qeweb.busplatform.businessseting.console.consoletext;


/**
 * 供应商管理方式
 */
public class VendorMagTypeTextBOP extends ConsoleTextBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6135435364736828627L;

	@Override
	protected String getConsoleText() {
		return "否：供应商和采购商 在两个菜单分别管理"  +
				"	是：供应商和采购商 在同一菜单管理" +
				"   重新登录系统后有效";
	}
}
