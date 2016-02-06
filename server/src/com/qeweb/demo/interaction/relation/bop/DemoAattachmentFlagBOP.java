package com.qeweb.demo.interaction.relation.bop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.EnumRange;

/**
 * 是否上传附件
 *
 */
public class DemoAattachmentFlagBOP extends BOProperty {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9072939043305756045L;

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("1", "是");
		map.put("0", "否");
		EnumRange empRange = new EnumRange();
		empRange.setResult(map);
		getRange().addRange(empRange);
		setValue("1");
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		ArrayList<BusinessComponent> result = new ArrayList<BusinessComponent>();
		result.add(new DemoAttachmentBOP());
		result.add(new DemoMultiAttachmentBOP());
		return result;
	}
}
