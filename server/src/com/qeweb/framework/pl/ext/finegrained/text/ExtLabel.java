package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * label 组件
 *
 */
public class ExtLabel extends Label {
	
	public void paint() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendAttr(sbr, "fieldLabel", getText());
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "defaultType", "label");
		
		if(isTranslate()) {
			ExtWebHelper.appendAttr(sbr, "xtype", "displayfield");
			ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
		}
		else {
			ExtWebHelper.appendAttr(sbr, "xtype", "label");
			ExtWebHelper.appendAttr(sbr, "text", getBc().toText());
		}
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());
		Value value = getValue();
		if(value != null)
			ExtWebHelper.appendAttr(sbr, "realValue", StringUtils.getUnescapedText(value.getValue()));
		
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "style", schema.getStyle());
			ExtWebHelper.appendTail(sbr, "labelStyle", "padding-top:0px;" + schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "style", getStyle());
			ExtWebHelper.appendTail(sbr, "labelStyle", "padding-top:0px;" + getStyle());
		}
		else {
			ExtWebHelper.appendTail(sbr, "labelStyle", "padding-top:0px");
		}
		
		getPageContextInfo().write(sbr.toString());
	}	
}
