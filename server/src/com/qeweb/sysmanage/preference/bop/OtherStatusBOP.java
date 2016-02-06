package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

public class OtherStatusBOP extends BOProperty {
	private static final long serialVersionUID = 7937696368181802739L;
	
	public final static String YES = "true";  //是
	public final static String NO = "false";  //否
	
	@Override
	public void init() {
		getRange().clear();
		EnumRange range = new EnumRange();
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put(YES, "是");
		map.put(NO, "否");
		range.setResult(map);
		getRange().addRange(range);
	}
}
