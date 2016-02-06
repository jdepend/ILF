package com.qeweb.framework.bc.prop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.common.internation.AppLocalization;

/**
 * 枚举型范围
 * 
 * @author Ale
 *
 */
public class EnumRange extends Range {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2611459887712258269L;
	//枚举型 <value, text>
	protected Map<String, String> result = new LinkedHashMap<String, String>();
	
	@Override
	public boolean isIN(Value value) {
		return getResult().containsKey(value);
	}
	
	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	/**
	 * 添加“请选择”选项, “请选择”对应的枚举值是""
	 * @param result
	 */
	final static public void addPlease(Map<String, String> result) {
		result.put("", AppLocalization.getLocalization("fc.select.emptyText"));
	}
	
	/**
	 * 添加“全部”选项, “全部”对应的枚举值是""
	 * @param result
	 */
	final static public void addAll(Map<String, String> result) {
		result.put("", AppLocalization.getLocalization("fc.radio.allText"));
	}
}
