package com.qeweb.demo.load.container.form.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * radio BOP
 */
public class DemoFormRadioBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997378615375477952L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "男");
		map.put("1", "女");
		range.setResult(map);
		addRange(range);
		
		setValue("0");
	}
}
