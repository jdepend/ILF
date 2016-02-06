package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.text.composite.ExtTextAreaComposite;

/**
 * 细粒度组件textArea的ext封装实现
 *
 */
public class ExtTextArea extends TextArea {
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
			ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
			if(getHeight() > 0F)
				ExtWebHelper.appendAttr(sbr, "height", getHeight());
			ExtWebHelper.appendAttr(sbr, "xtype", "textarea");
			if(isValidateable())
				ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
			ExtWebHelper.appendStatus(sbr, getBc().getStatus());
			
			if(getHistoryBC() != null)
				ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());
			ExtWebHelper.appendTail(sbr, "value", getBc().toText());
			
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
		}
		else {
			new ExtTextAreaComposite().paint(this);
		}
	}
}
