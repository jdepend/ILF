package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 是否必填bop
 */
public class RequiredBOP extends BOProperty {

	private static final long serialVersionUID = 7973174283952231434L;
	/**
	 * 必填
	 */
	private static final boolean REQUIRED = true;
	/**
	 * 非必填
	 */
	private static final boolean UNREQUIRED = false;
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(UNREQUIRED + "", "非必填");
		map.put(REQUIRED + "", "必填");
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		setValue(UNREQUIRED + "");
	}
}
