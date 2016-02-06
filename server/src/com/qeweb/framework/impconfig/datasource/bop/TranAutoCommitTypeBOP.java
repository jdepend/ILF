package com.qeweb.framework.impconfig.datasource.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 事物自动提交
 */
public class TranAutoCommitTypeBOP extends BOProperty {

	private static final long serialVersionUID = -5239020837296421719L;

	public final static String AUTO_COMMIT_YES = "true";
	public final static String AUTO_COMMIT_NO = "false";

	@Override 
	public void init() {
		Map<String, String> dbTypeMap = new LinkedHashMap<String, String>();
		dbTypeMap.put(AUTO_COMMIT_YES, "是");
		dbTypeMap.put(AUTO_COMMIT_NO, "否");
		
		EnumRange range = new EnumRange();
		range.setResult(dbTypeMap);
		getRange().addRange(range);
	}
}
