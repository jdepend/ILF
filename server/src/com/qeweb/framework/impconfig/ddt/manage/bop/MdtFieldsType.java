package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 *	细粒度组件管理-列类型BOP
 */
public class MdtFieldsType extends BOProperty {

	private static final long serialVersionUID = 401563819259718299L;
	/**
	 * 列类型-固定列
	 */
	public static final Integer MDT_FIELDS_TYPE_FIXED = 1;
	/**
	 * 列类型-动态列
	 */
	public static final Integer MDT_FIELDS_TYPE_DYNAMIC = 2;
	

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		map.put(MDT_FIELDS_TYPE_FIXED.toString(), "固定列");
		map.put(MDT_FIELDS_TYPE_DYNAMIC.toString(), "动态列");
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
	}

}
