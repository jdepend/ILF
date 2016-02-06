package com.qeweb.framework.bc.sysbop;

/**
 * 为按钮指定 NOFreshBOP后,点击按钮后不刷新组件
 */
public class NOFreshBOP extends OperateBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1490460934887886092L;

	public boolean isFresh() {
		return false;
	}
}
