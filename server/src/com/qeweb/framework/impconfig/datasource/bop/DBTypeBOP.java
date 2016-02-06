package com.qeweb.framework.impconfig.datasource.bop;

import java.util.LinkedHashMap;
import java.util.Map;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;


/**
 * 数据库类型BOP
 *
 */
public class DBTypeBOP extends BOProperty {

	private static final long serialVersionUID = -5903679147667227644L;

	public final static String DB_TYPE_MYSQL = "1";
	public final static String DB_TYPE_ORACLE = "2";
	public static final String DB_TYPE_SQLSERVER = "3";


	@Override 
	public void init() {
		Map<String, String> dbTypeMap = new LinkedHashMap<String, String>();
		dbTypeMap.put(DB_TYPE_MYSQL, "MYSQL");
		dbTypeMap.put(DB_TYPE_ORACLE, "ORACLE");
		dbTypeMap.put(DB_TYPE_SQLSERVER, "SQL SERVER");
		
		EnumRange range = new EnumRange();
		range.setResult(dbTypeMap);
		getRange().addRange(range);
	}
}
