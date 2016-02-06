package com.qeweb.demo.common.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

public class OrgTypeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8138602252573123970L;

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		map.put("1", "采购商");
		map.put("2", "供应商");
	
		EnumRange empRange = new EnumRange();
		empRange.setResult(map);
		getRange().addRange(empRange);
	}
}
