package com.qeweb.demo.interaction.validation.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.frameworkbop.StatusBOP;

/**
 * DemoPublishStatusBOP覆盖了查询用范围
 */
public class DemoPublishStatusQueryBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1695676951690096814L;

	/**
	 * 覆盖查询用范围
	 */
	@Override
	public BCRange getQueryRange() {
		//注意:此处需要继续使用getRange()为queryRange重新赋值
		BCRange queryRange = getRange();
		queryRange.clear();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange enumRange = new EnumRange();
		EnumRange.addPlease(map);
		map.put("0", "YES");
		map.put("1", "NO");
		enumRange.setResult(map);
		queryRange.addRange(enumRange);
		
		return queryRange;
	}
}
