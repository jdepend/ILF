package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 细粒度组件状态bop
 */
public class FcStatusBOP extends BOProperty {
	private static final long serialVersionUID = -1254925072596327688L;
	private static final Integer EDIT = 1;
	private static final Integer READONLY = 2;
	private static final Integer UNEDIT = 3;
	private static final Integer HIDDEN = 4;

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		map.put(EDIT.toString(), "可编辑");
		map.put(READONLY.toString(), "只读");
		map.put(UNEDIT.toString(), "不可交互");
		map.put(HIDDEN.toString(), "隐藏");

		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
	}
}
