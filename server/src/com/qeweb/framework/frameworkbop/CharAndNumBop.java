package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.frameworkbop.prop.CharAndNumRange;

/**
 * 必须是数字或字母
 *
 */
public class CharAndNumBop extends BopDecorator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3912242852227135372L;

	/**
	 * 必须是数字或字母
	 * @param bop
	 */
	public CharAndNumBop(BOProperty bop) {
		super(bop);
		addRange(LogicEnum.AND);
	}
	
	/**
	 * 必须是数字或字母
	 * @param bop
	 * @param logicEnum 逻辑校验规则
	 */
	public CharAndNumBop(BOProperty bop, LogicEnum logicEnum) {
		super(bop);
		addRange(logicEnum);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		LogicRange range = new CharAndNumRange();
		this.bop.getRange().addRange(range, logicRule);
	}
}
