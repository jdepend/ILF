package com.qeweb.demo.load.container.form.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * checobox BOP
 */
public class DemoFormCheckBoxBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7073906871131667166L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "男");
		map.put("1", "女");
		range.setResult(map);
		addRange(range);
	}
}
