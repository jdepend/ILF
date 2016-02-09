package com.qeweb.framework.pl.html.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLHidden extends Hidden {

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = getBc();

		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendAttr(sbr, "type", "hidden");
        //增加ID属性-2016
        HTMLWebHelper.appendAttr(sbr, "id", getId());

		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "value", bop.toText(), StringUtils.isNotEmpty(bop.toText()));
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendTail(sbr);
		
		getPageContextInfo().write(sbr.toString());
	}
}
