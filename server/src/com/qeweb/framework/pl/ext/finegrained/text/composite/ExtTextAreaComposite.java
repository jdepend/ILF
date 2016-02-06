package com.qeweb.framework.pl.ext.finegrained.text.composite;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.text.ExtTextArea;

/**
 * 带有选择按钮的多行文本域控件:
 * 当多行文本框右侧有选择按钮时,需要生成一个compositefield控件
 */
public class ExtTextAreaComposite {

	public void paint(ExtTextArea textArea) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendAttrName(sbr, "fieldLabel", textArea);
		ExtWebHelper.appendAttr(sbr, "xtype", "compositefield");
		ExtWebHelper.appendAttr(sbr, "defaultType", "textarea");
		Schema schema = textArea.getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		sbr.append("items:[{");
		sbr.append(getTextArea(textArea)).append("}");
		textArea.getPageContextInfo().write(sbr.toString());
		if(!textArea.getStatus().isDisable()
				&& (!textArea.getStatus().isReadonly() || AppConfig.getSourceBtnDisplayOnReadonly())
				&& !textArea.getStatus().isHidden()){
			textArea.getPageContextInfo().write(",");
			textArea.getSourceBtn().paint();
		}
		textArea.getPageContextInfo().write("]");
	}

	private String getTextArea(ExtTextArea textArea) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendBlank(sbr, textArea);
		ExtWebHelper.appendAttr(sbr, "flex", ExtWebHelper.getFlex(textArea.getCollSpan()));
		
		ExtWebHelper.appendAttr(sbr, "id", textArea.getId());
		ExtWebHelper.appendAttr(sbr, "name", textArea.getName());
		ExtWebHelper.appendAttr(sbr, "emptyText", textArea.getBc().getValue().getEmptyValue());
		if(textArea.getHeight() > 0F)
			ExtWebHelper.appendAttr(sbr, "height", textArea.getHeight());
		ExtWebHelper.appendAttr(sbr, "xtype", "textarea");
		if(textArea.isValidateable())
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		ExtWebHelper.appendStatus(sbr, textArea.getBc().getStatus());
		
		if(textArea.getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", textArea.getHistoryBC().toText());
		ExtWebHelper.appendTail(sbr, "value", textArea.getBc().toText());
		
		return ExtWebHelper.removeEnd(sbr);
	}
}
