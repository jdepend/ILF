package com.qeweb.framework.bc.sysbop;

/**
 * 
 * 为按钮指定 NOSubmitBOP后,点击按钮不进行组件前台校验
 */
public class NOSubmitBOP extends OperateBOP {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9016010929792352485L;

	@Override
	public boolean isSubmit() {
		return false;
	}
}
