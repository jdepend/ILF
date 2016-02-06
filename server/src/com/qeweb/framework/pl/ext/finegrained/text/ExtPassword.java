package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtPassword extends Password {
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
		ExtWebHelper.appendAttr(sbr, "inputType", "password");
		ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
		if(isValidateable())
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
		}
		ExtWebHelper.appendStatus(sbr, getStatus());

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}
}
