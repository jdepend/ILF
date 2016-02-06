package com.qeweb.framework.impconfig.exeprocessmag.pageflow.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.mdt.MDTContext;

/**
 * 变量加载策略, 点击按钮后如何使用变量
 * 变量加载有如下策略:
 * 1:方法执行前设置变量, 2:方法执行后设置变量
 */
public class VarLoadStrategyBOP extends BOProperty {

	private static final long serialVersionUID = 2166608110361249838L;
	
	public void init() {
		Map<String, String> rangeMap = new LinkedHashMap<String, String>();
		rangeMap.put(MDTContext.STRATEGY_BEFORE + "", "方法执行前");
		rangeMap.put(MDTContext.STRATEGY_AFTER + "", "方法执行后");
		EnumRange range = new EnumRange();
		range.setResult(rangeMap);
		addRange(range);
		getRange().setRequired(true);
		setValue(MDTContext.STRATEGY_BEFORE + "");
	}
}
