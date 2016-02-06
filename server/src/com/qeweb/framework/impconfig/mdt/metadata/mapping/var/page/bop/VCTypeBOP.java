package com.qeweb.framework.impconfig.mdt.metadata.mapping.var.page.bop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.impconfig.common.VCType;

/**
 * 
 *
 */
public class VCTypeBOP extends BOProperty {
	private static final long serialVersionUID = 3534238373958822593L;

	@Override
	public void init() {
		EnumRange range = new EnumRange();
		Map<String, String> result = new LinkedHashMap<String, String>();
		EnumRange.addPlease(result);
        result.put(VCType.VC_TYPE_FORM + "", "表单");
        result.put(VCType.VC_TYPE_TABLE + "", "表格");
        result.put(VCType.VC_TYPE_TAB + "", "标签页");
        
        result.put(VCType.VC_TYPE_TEXTFEILD + "", "文本域");
        result.put(VCType.VC_TYPE_TEXTAREA + "", "多行文本域");
        result.put(VCType.VC_TYPE_LABEL + "", "只读文本域");
        result.put(VCType.VC_TYPE_PASSWORD + "", "密码框");
        result.put(VCType.VC_TYPE_HIDDEN + "", "隐藏域");
        result.put(VCType.VC_TYPE_SELECT + "", "下拉框");
        result.put(VCType.VC_TYPE_RADIO + "", "单选按钮");
        result.put(VCType.VC_TYPE_CHECKBOX + "", "复选框");
        result.put(VCType.VC_TYPE_OPTIONTRANSERSELECT + "", "双向选择框");
        result.put(VCType.VC_TYPE_ANCHOR + "", "超链接");
        
        result.put(VCType.VC_TYPE_BTN + "", "按钮");
		range.setResult(result);
		getRange().addRange(range);
	}
}
