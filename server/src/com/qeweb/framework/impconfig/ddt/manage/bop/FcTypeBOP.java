package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.common.VCType;

/**
 *	细粒度组件类型
 */
public class FcTypeBOP extends BOProperty {

	private static final long serialVersionUID = -7163029970538908150L;
	
	

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		map.put(VCType.VC_TYPE_TEXTFEILD+ "", "textField");
		map.put(VCType.VC_TYPE_TEXTAREA+ "", "textArea");
		map.put(VCType.VC_TYPE_LABEL+ "", "label");		
		map.put(VCType.VC_TYPE_PASSWORD+ "", "password");		
		map.put(VCType.VC_TYPE_HIDDEN+ "", "hidden");		
		map.put(VCType.VC_TYPE_SELECT+ "", "select");		
		map.put(VCType.VC_TYPE_RADIO+ "", "radio");		
		map.put(VCType.VC_TYPE_CHECKBOX+ "", "checkBox");		
		map.put(VCType.VC_TYPE_OPTIONTRANSERSELECT+ "", "optionTranserSelect");		
		map.put(VCType.VC_TYPE_ANCHOR+ "", "anchor");		
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
		getRange().setRequired(true);
	}

}
