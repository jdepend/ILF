package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.frameworkbop.prop.NumberRange;

/**
 * 必须是数字
 *
 */
public class NumberBop extends BopDecorator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5188150237245186281L;

	/**
	 * 必须是数字
	 * @param bop
	 */
	public NumberBop(BOProperty bop) {
		super(bop);
		addRange(LogicEnum.AND);
	}
	
	/**
	 * 必须是数字
	 * @param bop
	 * @param logicEnum 逻辑校验规则
	 */
	public NumberBop(BOProperty bop, LogicEnum logicEnum) {
		super(bop);
		addRange(logicEnum);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		LogicRange range = new NumberRange();
		BCRange bcRange = this.bop.getRange();
		bcRange.addRange(range, logicRule);
	}
}
