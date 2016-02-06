package com.qeweb.framework.pl.ext.finegrained.other;

import java.text.ParseException;
import java.util.Date;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.sysbop.DateBOP;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.other.DateField;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.finegrained.other.expend.ExtDateExpend;

public class ExtDateField extends DateField  {

	@Override
	public void paint() {
		if(!isHasExpend()) {
			StringBuilder sbr = new StringBuilder();

			ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
			ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
			ExtWebHelper.appendAttr(sbr, "id", getId());
			ExtWebHelper.appendAttr(sbr, "name", getName());
			ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
			ExtWebHelper.appendAttr(sbr, "xtype", "datefield");
			ExtWebHelper.appendAttr(sbr, "format", getFormat());
			ExtWebHelper.appendObjectAttr(sbr, "disabledDays", getDisabledDays());
			ExtWebHelper.appendAttr(sbr, "value",  getDateVal());
			if(getHistoryBC() != null)
				ExtWebHelper.appendAttr(sbr, "historyText", getDateVal(getHistoryBC().toText()));
			if(isValidateable())
				ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
			ExtWebHelper.appendStatus(sbr, getStatus());
			Schema schema = getSchema();
			if(schema != null) {
				ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
			}
			else if(getStyle() != null) {
				ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
			}
			ExtWebHelper.appendTail(sbr, "editable", false);

			getPageContextInfo().write(sbr.toString());
		}
		else {
			new ExtDateExpend().paint(this);
		}
	}
	
	public String getFormat() {
		BOProperty bop = BoOperateUtil.getRealBop(getBc());
		return (bop instanceof DateBOP) ? ((DateBOP) bop).getFormat() : DateBOP.YYYY_MM_DD;
	}

	public String getDateVal() {
		String valText = getBc().toText();
		return getDateVal(valText);
	}

	public String getDateVal(String valText) {
		if(StringUtils.isEmpty(valText))
			return valText;
		
		String format = getFormat();
		String dateFormat;
		if(StringUtils.isEqual(DateBOP.YYYY_MM_DD, format))
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD;
		else if(StringUtils.isEqual(DateBOP.YYYY_MM_DD_HH, format))
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD_HH;
		else if(StringUtils.isEqual(DateBOP.YYYY_MM_DD_HH_MM, format))
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM;
		else 
			dateFormat = DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS;
		
		try {
			Date date = DateUtils.parseUtilDate(valText, new String[]{dateFormat});
			return DateUtils.dateToString(date, dateFormat);
		} catch (ParseException e) {
			e.printStackTrace();
			return valText.substring(0, 10);
		}
	}
}
