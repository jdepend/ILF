package com.qeweb.framework.frameworkbop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 状态BOP，StatusBOP中有一个枚举型范围，枚举值：1-是， 0-否
 */
public class StatusBOP extends BOProperty {

	private static final long serialVersionUID = -9223066732894803625L;

	public final static String YES = "1";  //是
	public final static String NO = "0";   //否
	
	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String,String> map = new LinkedHashMap<String,String>();
		addPlease(map);
		map.put(YES, getYesText());
		map.put(NO, getNoText());
		range.setResult(map);
		addRange(range);
	}
	
	/**
	 * 添加"请选择"， 子类可覆写该方法
	 * @param map
	 */
	protected void addPlease(Map<String,String> map) {
		EnumRange.addPlease(map);
	}

	protected String getYesText() {
		return "是";
	}
	
	protected String getNoText() {
		return "否";
	}
}
