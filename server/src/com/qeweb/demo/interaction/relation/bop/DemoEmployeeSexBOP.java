package com.qeweb.demo.interaction.relation.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 
 * 雇员性别
 */
public class DemoEmployeeSexBOP extends BOProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4306771812058970402L;
	
	public enum SEX {
		ALL, MAN, WOMAN
	}
	
	@Override
	public void init() {
		Map<String, String> sexMap = new LinkedHashMap<String, String>();
		sexMap.put(SEX.ALL + "", "全部");
		sexMap.put(SEX.MAN + "", "男");
		sexMap.put(SEX.WOMAN + "", "女");
		
		EnumRange sexRange = new EnumRange();
		sexRange.setResult(sexMap);
		getRange().addRange(sexRange);
		setValue(SEX.ALL + "");
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DemoEmployeeNameBOP());
		
		return result;
	}
}
