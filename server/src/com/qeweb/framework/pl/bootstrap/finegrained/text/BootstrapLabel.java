package com.qeweb.framework.pl.bootstrap.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class BootstrapLabel extends Label{
	@Override
	public void paint(){
		try {
			StringBuilder sbr = new StringBuilder();
			BOProperty bop = getBc();

            sbr.append("<span>" + bop.getValue().getValue()+ "</span>");

//			HTMLWebHelper.appendHead(sbr, this);
//			HTMLWebHelper.appendStartTag(sbr, "span");
//			HTMLWebHelper.appendAttr(sbr, "name", getName());
//			HTMLWebHelper.appendStatus(sbr, bop.getStatus());
//			HTMLWebHelper.appendEndTag(sbr);
//			HTMLWebHelper.appendContent(sbr, bop.toText());
//			HTMLWebHelper.appendEndTag(sbr, "span");
//			HTMLWebHelper.appendTail(sbr);

			getPageContextInfo().write(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
