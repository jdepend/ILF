package com.qeweb.framework.frameworkbop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BopDecorator;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;
import com.qeweb.framework.frameworkbop.prop.EmailRange;

/**
 * 
 * 为bop增加email校验
 */
public class EmailBopDec extends BopDecorator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877177215095116056L;

	/**
	 * 为bop增加email校验,默认使用 AND 连接 
	 * @param bop
	 */
	public EmailBopDec(BOProperty bop) {
		super(bop);
		addRange(LogicEnum.AND);
	}
	
	/**
	 * 为bop增加email校验
	 * @param bop
	 * @param logicEnum
	 */
	public EmailBopDec(BOProperty bop, LogicEnum logicEnum) {
		super(bop);
		addRange(logicEnum);
	}
	
	@Override
	public void addRange(LogicEnum logicRule) {
		this.bop.getRange().addRange(new EmailRange(), logicRule);
	}
}
