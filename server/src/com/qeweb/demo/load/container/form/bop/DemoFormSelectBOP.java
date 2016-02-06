package com.qeweb.demo.load.container.form.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 下拉列表BOP
 */
public class DemoFormSelectBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7373347375478446388L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> map = new LinkedHashMap<String, String>();
		//添加"请选择"字样
		EnumRange.addPlease(map);
		map.put("0", "男");
		map.put("1", "女");
		range.setResult(map);
		addRange(range);
	}
}
