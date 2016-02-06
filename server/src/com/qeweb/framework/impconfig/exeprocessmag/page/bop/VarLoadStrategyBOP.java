package com.qeweb.framework.impconfig.exeprocessmag.page.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.mdt.MDTContext;

/**
 * 变量加载策略BOP.
 * 变量加载有如下策略:
 * 1:页面加载前恢复初始值;2:页面加载前保持原值;3:页面加载前使用默认值
 */
public class VarLoadStrategyBOP extends BOProperty {

	private static final long serialVersionUID = 2166608110361249838L;
	
	public void init() {
		Map<String, String> rangeMap = new LinkedHashMap<String, String>();
		rangeMap.put(MDTContext.STRATEGY_RESET + "", "恢复初始值");
		rangeMap.put(MDTContext.STRATEGY_HOLD + "", "保持原值");
		rangeMap.put(MDTContext.STRATEGY_DEFAULT + "", "使用默认值");
		EnumRange range = new EnumRange();
		range.setResult(rangeMap);
		addRange(range);
		getRange().setRequired(true);
		setValue(MDTContext.STRATEGY_RESET + "");
	}
}
