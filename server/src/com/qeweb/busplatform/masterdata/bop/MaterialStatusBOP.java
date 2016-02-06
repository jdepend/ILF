package com.qeweb.busplatform.masterdata.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 物料状态BOP
 */
public class MaterialStatusBOP extends BOProperty {

	private static final long serialVersionUID = 9151543123448205605L;

	public final static int YES = 1;	//启用
	public final static int NO = 0;		//禁用

	public void init() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		EnumRange.addPlease(result);
		result.put(YES + "", "启用");
		result.put(NO + "", "禁用");

		EnumRange range = new EnumRange();
		range.setResult(result);
		addRange(range);
	}
}
