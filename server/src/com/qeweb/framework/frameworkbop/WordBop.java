package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.bc.prop.LogicRange;
import com.qeweb.framework.frameworkbop.prop.WordRange;

/**
 * 必须是数字、字母或下划线
 *
 */
public class WordBop extends BopDecorator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5703879605249036313L;

	/**
	 * 必须是数字、字母或下划线
	 * @param bop
	 */
	public WordBop(BOProperty bop) {
		super(bop);
		addRange(LogicEnum.AND);
	}
	
	/**
	 * 必须是数字、字母或下划线
	 * @param bop
	 * @param logicEnum 逻辑校验规则
	 */
	public WordBop(BOProperty bop, LogicEnum logicEnum) {
		super(bop);
		addRange(logicEnum);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		LogicRange range = new WordRange();
		this.bop.getRange().addRange(range, logicRule);
	}
}
