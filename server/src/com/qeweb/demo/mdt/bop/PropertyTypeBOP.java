package com.qeweb.demo.mdt.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 资产类型BOP
 */
public class PropertyTypeBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1261846814892054167L;
	public final static int PRO_TYPE_BUILDING = 10;		//房屋
	public final static int PRO_TYPE_AMOUNT = 20;		//总资产
	
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(PRO_TYPE_BUILDING + "", "房屋");
		map.put(PRO_TYPE_AMOUNT + "", "设备");
		range.setResult(map);
		addRange(range);
		
		setValue(PRO_TYPE_BUILDING + "");
	}
}
