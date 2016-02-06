package com.qeweb.demo.mobile.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 门店bop
 */
public class StoreBOP extends BOProperty {
	

	private static final long serialVersionUID = 7721658932346764750L;

	@Override
	public void init() {
		Map<String,String> armyMap = new LinkedHashMap<String, String>();
		EnumRange.addPlease(armyMap);
		armyMap.put("HD6653", "联想百脑汇专卖");
		armyMap.put("HD6631", "联想太平洋旗舰店");
		armyMap.put("HD6626", "联想西单苏宁专卖");
		EnumRange range = new EnumRange();
		range.setResult(armyMap);
		getRange().addRange(range);
	}
}
