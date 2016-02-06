package com.qeweb.framework.pl.ext.finegrained.other.expend;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.bc.prop.expend.ExpendRange;
import com.qeweb.framework.bc.prop.expend.ExpendStatus;
import com.qeweb.framework.bc.prop.expend.ExpendValue;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.other.ExtDateField;

/**
 * 分段的日期控件 
 *
 */
public class ExtDateExpend {

	public void paint(ExtDateField dateField) {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", dateField);
		ExtWebHelper.appendAttr(sbr, "id", dateField.getId());
		if(isRequired(dateField)) {
			ExtWebHelper.appendAttr(sbr, "viewlabel", "<FONT color=red>*</FONT>" + dateField.getText());
			ExtWebHelper.appendAttr(sbr, "combineErrors", false);
		}
		else {
			ExtWebHelper.appendAttr(sbr, "viewlabel", dateField.getText());
		}
		ExtWebHelper.appendAttr(sbr, "xtype", "compositefield");
		ExtWebHelper.appendAttr(sbr, "defaultType", "datefield");
		ExtWebHelper.appendAttr(sbr, "expend", true);
		Schema schema = dateField.getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		ExtWebHelper.appendObjectTail(sbr, "items", getItems(dateField));

		dateField.getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}

	private String getItems(ExtDateField dateField){
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendArrayBegin(sbr);

		sbr.append(getRangeDatefield(dateField, ConstantDataIsland.EXPEND_MIN));
		ExtWebHelper.appendObjectSplit(sbr);
		sbr.append("{xtype:'label',text:'-'}");
		ExtWebHelper.appendObjectSplit(sbr);
		sbr.append(getRangeDatefield(dateField, ConstantDataIsland.EXPEND_MAX));
		
		ExtWebHelper.appendArrayAfter(sbr);
		return sbr.toString();
	}
	
	private String getRangeDatefield(ExtDateField dateField, String suffix){
		Value value = getValue(dateField, suffix);
		BCRange range = getRange(dateField, suffix);
		Status status = getStatus(dateField, suffix);
		
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", dateField.getId() + ConstantDataIsland.EXPEND_SPLIT + suffix);
		ExtWebHelper.appendAttr(sbr, "name", dateField.getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "datefield");
		ExtWebHelper.appendAttr(sbr, "format", dateField.getFormat());
		ExtWebHelper.appendAttr(sbr, "editable", false);
		ExtWebHelper.appendAttr(sbr, "emptyText", value.getEmptyValue());
		ExtWebHelper.appendAttr(sbr, "value", dateField.getDateVal(value.getValue()));
		ExtWebHelper.appendStatus(sbr, status);
		ExtWebHelper.appendAttr(sbr, "allowBlank", !range.isRequired());
		ExtWebHelper.appendAttr(sbr, "blankText", AppLocalization.getLocalization(Constant.LOCATION_FORM_REQUIRED));
		
		sbr = new StringBuilder(ExtWebHelper.removeEnd(sbr));
		ExtWebHelper.appendObjectAfter(sbr);
		return sbr.toString();
	}

	private Value getValue(ExtDateField dateField, String suffix) {
		Value value = dateField.getValue();
		if(value instanceof ExpendValue) {
			ExpendValue expendValue = (ExpendValue)value;
			if(StringUtils.isEqual(ConstantDataIsland.EXPEND_MIN, suffix))
				value = expendValue.getStart();
			else
				value = expendValue.getEnd();
		}
		return value;
	}
	
	private BCRange getRange(ExtDateField dateField, String suffix) {
		BCRange range = (BCRange)dateField.getReange();
		if(range instanceof ExpendRange) {
			ExpendRange expendRange = (ExpendRange)range;
			if(StringUtils.isEqual(ConstantDataIsland.EXPEND_MIN, suffix))
				range = expendRange.getStart();
			else
				range = expendRange.getEnd();
		}
		return range;
	}
	
	private Status getStatus(ExtDateField dateField, String suffix) {
		Status status = dateField.getStatus();
		if(status instanceof ExpendStatus) {
			ExpendStatus expendStatus = (ExpendStatus)status;
			if(StringUtils.isEqual(ConstantDataIsland.EXPEND_MIN, suffix))
				status = expendStatus.getStart();
			else
				status = expendStatus.getEnd();
		}
		return status;
	}
	
	private boolean isRequired(ExtDateField dateField) {
		return dateField.isRequired() 
				|| getRange(dateField, ConstantDataIsland.EXPEND_MIN).isRequired() 
				|| getRange(dateField, ConstantDataIsland.EXPEND_MAX).isRequired();
	}
}
