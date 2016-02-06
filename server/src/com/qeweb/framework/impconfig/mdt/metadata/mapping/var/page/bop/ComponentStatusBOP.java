package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 
 *
 */
public class ComponentStatusBOP extends BOProperty {
	private static final long serialVersionUID = 3534238373958822593L;

	final static public String STATUS_TRUE = "true";
	final static public String STATUS_FALSE = "false";
	
	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> result = new LinkedHashMap<String, String>();
		EnumRange.addPlease(result);
		result.put(STATUS_TRUE, "是");
		result.put(STATUS_FALSE, "否");
		range.setResult(result);
		getRange().addRange(range);
	}
}
