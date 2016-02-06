package com.qeweb.framework.pl.html.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.text.Password;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLPassword extends Password {
	
	@Override
	public void paint(){
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = (BOProperty) getBc();
		
		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendAttr(sbr, "type" , "password");
		HTMLWebHelper.appendAttr(sbr, "style",
				"width:" + HTMLWebHelper.getFcDefaultWidth());
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendAttr(sbr, "value", bop.toText(), StringUtils.isNotEmpty(bop.toText()));
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendTail(sbr);
		
		getPageContextInfo().write(sbr.toString());
	}

}
