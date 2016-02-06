package com.qeweb.framework.impconfig.mdt.phymag.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 物理列数据类型
 */
public class PhyColDataTypeBOP extends BOProperty {

	private static final long serialVersionUID = 5473835254965725475L;

	public static final String DATATYPE_STRING = "string";
	public static final String DATATYPE_INT = "int";
	public static final String DATATYPE_DATE = "date";
	
	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(DATATYPE_STRING, DATATYPE_STRING);
		map.put(DATATYPE_INT, DATATYPE_INT);
		map.put(DATATYPE_DATE, DATATYPE_DATE);
		
		EnumRange dataTypeRante = new EnumRange();
		dataTypeRante.setResult(map);
		getRange().addRange(dataTypeRante);
		setValue(DATATYPE_STRING);
	}
	
	public static final boolean isInt(String dataType) {
		return StringUtils.isEqual(dataType, DATATYPE_INT);
	}
	
	public static final boolean isString(String dataType) {
		return StringUtils.isEqual(dataType, DATATYPE_STRING);
	}
	
	public static final boolean isDate(String dataType) {
		return StringUtils.isEqual(dataType, DATATYPE_DATE);
	}
}
