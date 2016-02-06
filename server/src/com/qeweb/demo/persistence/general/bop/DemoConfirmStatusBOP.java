package com.qeweb.demo.persistence.general.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.frameworkbop.StatusBOP;

public class DemoConfirmStatusBOP extends StatusBOP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6569324129151314332L;

	@Override
	public void init() {
		Map<String,String> map = new LinkedHashMap<String,String>();
		map.put(YES, getYesText());
		map.put(NO, getNoText());
		EnumRange range = new EnumRange();
		range.setResult(map);
		addRange(range);
		setValue(NO);
	}
}
