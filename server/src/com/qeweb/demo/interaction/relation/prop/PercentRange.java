package com.qeweb.demo.interaction.relation.prop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.prop.EnumRange;

public class PercentRange extends EnumRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097632336203355801L;

	public PercentRange() {
		Map<String, String> percent = new LinkedHashMap<String, String>();
		percent.put("0.1", "10%");
		percent.put("0.2", "20%");
		percent.put("0.5", "50%");
		setResult(percent);
	}
}
