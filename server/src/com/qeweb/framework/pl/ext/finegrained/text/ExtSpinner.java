package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.SequenceRange;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.text.Spinner;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * 
 * 数字型微调控件，提供自动键击过滤和数字校验.
 * Spinner绑定的bop仅能使用一个连续型范围
 * 
 */
public class ExtSpinner extends Spinner {

	@Override
	public void paint()  {
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
		ExtWebHelper.appendAttr(sbr, "xtype", "spinnerfield");
		ExtWebHelper.appendAttr(sbr, "allowDecimals", true);
		ExtWebHelper.appendAttr(sbr, "accelerate", true);
		
		if(getBc() == null) {
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
			return ;
		}
		
		ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
		ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());
		
		ExtWebHelper.appendStatus(sbr, getStatus());
		BCRange bcRange = getBc().getRange();
		SequenceRange sequenceRange = bcRange.getFirstSequence();
		if(sequenceRange != null) {
			ExtWebHelper.appendAttr(sbr, "minValue", sequenceRange.getMin());
			ExtWebHelper.appendAttr(sbr, "maxValue", sequenceRange.getMax());
			ExtWebHelper.appendAttr(sbr, "incrementValue", sequenceRange.getStep());
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		}
		
		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}
}
