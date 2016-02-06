package com.qeweb.framework.pl.ext.finegrained.text.composite;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextField;

/**
 * 带有选择按钮的文本域控件:
 * 当文本框右侧有选择按钮时,需要生成一个compositefield控件
 */
public class ExtTextFieldComposite {
	
	public void paint(ExtTextField textField) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendAttrName(sbr, "fieldLabel", textField);
		ExtWebHelper.appendAttr(sbr, "xtype", "compositefield");
		ExtWebHelper.appendAttr(sbr, "defaultType", "textfield");
		if(textField.isRequired()) {
			ExtWebHelper.appendAttr(sbr, "combineErrors", false);
		}
		Schema schema = textField.getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		sbr.append("items:[{");
		sbr.append(getTextField(textField)).append("}");
		textField.getPageContextInfo().write(sbr.toString());
		if(!textField.getStatus().isDisable()
				&& (!textField.getStatus().isReadonly() || AppConfig.getSourceBtnDisplayOnReadonly())
				&& !textField.getStatus().isHidden()){
			textField.getPageContextInfo().write(",");
			textField.getSourceBtn().paint();
		}
		textField.getPageContextInfo().write("]");
	}
	
	private String getTextField(ExtTextField textField) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendBlank(sbr, textField);
		ExtWebHelper.appendAttr(sbr, "flex", ExtWebHelper.getFlex(textField.getCollSpan()));
		ExtWebHelper.appendAttr(sbr, "id", textField.getId());
		ExtWebHelper.appendAttr(sbr, "name", textField.getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
		ExtWebHelper.appendAttr(sbr, "emptyText", textField.getBc().getValue().getEmptyValue());
		
		if(textField.getBc() == null)
			return ExtWebHelper.removeEnd(sbr);
		
		ExtWebHelper.appendAttr(sbr, "value", textField.getBc().toText());
		if(textField.getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", textField.getHistoryBC().toText());
		if(textField.isValidateable())
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		ExtWebHelper.appendStatus(sbr, textField.getStatus(), textField.getSourceBtn() != null);
		
		return ExtWebHelper.removeEnd(sbr);
	}
}
