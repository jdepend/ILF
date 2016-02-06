package com.qeweb.demo.interaction.validation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.BCRange;

/**
 * 重写了采购订单号的查询用范围
 */
public class DemoPoNoQueryBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7491310055681285701L;

	@Override
	public BCRange getQueryRange() {
		return getRange();
	}
}
