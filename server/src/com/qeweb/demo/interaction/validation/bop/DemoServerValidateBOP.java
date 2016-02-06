package com.qeweb.demo.interaction.validation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.Range;


/**
 * Demo:
 * BOP 后台校验
 *
 */
public class DemoServerValidateBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3037733875775713590L;

	@Override
	public void init() {
		//自定义的LogicRange
		Range logicRange = new ServerRangeExample();
		getRange().addRange(logicRange);
	}
}
