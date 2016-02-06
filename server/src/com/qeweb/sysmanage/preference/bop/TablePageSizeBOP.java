package com.qeweb.sysmanage.preference.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 表格每页最大显示行数
 */
public class TablePageSizeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8990093094534168395L;

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("5", "5");
		map.put("10", "10");
		map.put("15", "15");
		map.put("20", "20");
		map.put("25", "25");
		map.put("30", "30");
		map.put("35", "35");
		map.put("40", "40");
		map.put("45", "45");
		map.put("50", "50");
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
	}
}
