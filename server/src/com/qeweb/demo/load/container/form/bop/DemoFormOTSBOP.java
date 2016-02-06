package com.qeweb.demo.load.container.form.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.MutiValue;
import com.qeweb.framework.bc.prop.Value;

/**
 * 双向选择
 */
public class DemoFormOTSBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4764644819988769170L;

	@Override 
	public void init() {
		Map<String, String> classMap = new LinkedHashMap<String, String>();
		classMap.put("1", "一班");
		classMap.put("2", "二班");
		classMap.put("3", "三班");		
		classMap.put("4", "四班");
		
		MutiValue value = new MutiValue();
		List<Value> valueList = new LinkedList<Value>();
		Value v1 = new Value();
		v1.setValue("1");
		Value v2 = new Value();
		v2.setValue("2");
		valueList.add(v1);
		valueList.add(v2);
		value.setMutiValue(valueList);
		setValue(value);
		
		EnumRange range = new EnumRange();
		range.setResult(classMap);
		getRange().addRange(range);
	}
}
