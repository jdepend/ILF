package com.qeweb.demo.interaction.relation.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 选择图片
 */
public class DemoCheckLogoBOP extends BOProperty {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8709205103501709476L;

	public enum LOGO {
		BAIDU, GOOGLE
	}
	
	@Override
	public void init() {
		Map<String, String> logos = new LinkedHashMap<String, String>();
		logos.put(LOGO.BAIDU + "", "百度");
		logos.put(LOGO.GOOGLE + "", "谷歌");
	
		EnumRange empRange = new EnumRange();
		empRange.setResult(logos);
		getRange().addRange(empRange);
		setValue(LOGO.BAIDU + "");
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DemoLogoBOP());
		return result;
	}
}
