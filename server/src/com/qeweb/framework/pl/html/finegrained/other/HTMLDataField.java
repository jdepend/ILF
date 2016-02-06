package com.qeweb.framework.pl.html.finegrained.other;

import com.qeweb.framework.pal.finegrained.other.DateField;
import com.qeweb.framework.pl.html.HTMLWebHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.appconfig.AppCookie;
import com.qeweb.framework.common.utils.StringUtils;

public class HTMLDataField extends DateField{

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = getBc();

		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendAttr(sbr, "type" , "text");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "style",
				"width:" + HTMLWebHelper.getFcDefaultWidth());
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendAttr(sbr, "value", bop.toText(), StringUtils.isNotEmpty(bop.toText()));
		if (StringUtils.isEqual(AppCookie.getLanguageType(), AppCookie.LANGUAGE_CN))
			HTMLWebHelper.appendAttr(sbr, "onFocus", "WdatePicker({lang:\"zh-cn\"})");
		else
			HTMLWebHelper.appendAttr(sbr, "onFocus", "WdatePicker({lang:\"en\"})");
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendTail(sbr);

		getPageContextInfo().write(sbr.toString());
	}

}
