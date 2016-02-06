package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.text.TextField;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.text.composite.ExtTextFieldComposite;

/**
 * 细粒度组件textField的ext封装实现
 *
 */
public class ExtTextField extends TextField {
	
	@Override
	public void paint() {
		if(getSourceBtn() == null) {
			StringBuilder sbr = new StringBuilder();
			ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
			ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
			Schema schema = getSchema();
			if(schema != null) {
				ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
			}
			else if(getStyle() != null) {
				ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
			}
			ExtWebHelper.appendAttr(sbr, "id", getId());
			ExtWebHelper.appendAttr(sbr, "name", getName());
			ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
			
			if(getBc() == null) {
				getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
				return ;
			}
			
			ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
			ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
			if(getHistoryBC() != null)
				ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());
			if(isValidateable())
				ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
			ExtWebHelper.appendStatus(sbr, getStatus(), getSourceBtn() != null);
			
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
		}
		else {
			new ExtTextFieldComposite().paint(this);
		}
	}
}
