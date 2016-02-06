package com.qeweb.framework.common.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

public class ClassUtil {

	/**
	 * 
	 * @param fieldType
	 * @param value
	 * @return
	 */
	final static public Object getRealValue(Type fieldType, String value) {
		if(StringUtils.isEmpty(value))
			return null;
		
		if (fieldType == String.class) {
			return value;
		}
		else if (fieldType == Short.class || fieldType == short.class) {
			return Short.parseShort(value);
		}
		else if (fieldType == Integer.class || fieldType == int.class) {
			return Integer.parseInt(value);
		}
		else if (fieldType == Long.class ||  fieldType == long.class) {
			return Long.parseLong(value);
		}
		else if (fieldType == Double.class || fieldType == double.class) {
			return Double.parseDouble(value);
		}
		else if (fieldType == Date.class) {
			return DateUtils.parseUtilDate(value);
		}
		else if (fieldType == Timestamp.class) {
			return DateUtils.getTimestamp(value);
		}
		else if (fieldType == Boolean.class || fieldType == boolean.class) {
			return Boolean.parseBoolean(value);
		}
		else {
			return null;
		}
	}
}
