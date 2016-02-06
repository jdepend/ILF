package com.qeweb.demo.interaction.relation.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.demo.interaction.relation.bop.DemoEmployeeSexBOP.SEX;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 
 * 雇员姓名
 */
public class DemoEmployeeNameBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2911791501616009082L;
	
	private Map<String, String> manMap = new LinkedHashMap<String, String>();
	private Map<String, String> womanMap = new LinkedHashMap<String, String>();
	
	@Override
	public void init() {
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		manMap.put("0", "孙博");
		manMap.put("1", "岳麓");
		manMap.put("2", "徐扬");
		womanMap.put("3", "咸丽华");
		womanMap.put("4", "薛玲军");
		womanMap.put("5", "宋威威");
		empMap.putAll(manMap);
		empMap.putAll(womanMap);
		
		EnumRange empRange = new EnumRange();
		empRange.setResult(empMap);
		getRange().addRange(empRange);
	}
	
	@Override
	public BusinessComponent query(BOProperty sourceBop) {
		if(!(sourceBop instanceof DemoEmployeeSexBOP)) 
			return null;
		
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		String value = sourceBop.getValue().getValue();
		if(StringUtils.isEqual(SEX.ALL + "", value)) {
			empMap.putAll(manMap);
			empMap.putAll(womanMap);
		}
		else if (StringUtils.isEqual(SEX.MAN + "", value)) {
			empMap.putAll(manMap);
		}
		else {
			empMap.putAll(womanMap);
		}
		
		EnumRange empRange = new EnumRange();
		empRange.setResult(empMap);
		getRange().addRange(empRange);
		
		return this;
	}
}
