package com.qeweb.demo.mobile.bop;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 门店类型
 */
public class ShopTypeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8486750688345608446L;
	
	public void init() {
		EnumRange range = new EnumRange();
		Map<String,String> map = new HashMap<String,String>();
		map.put("A", "A");
		map.put("B+", "B+");
		EnumRange.addPlease(map);
		range.setResult(map);
		getRange().addRange(range);
	}
}
