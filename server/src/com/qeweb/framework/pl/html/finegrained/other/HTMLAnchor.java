package com.qeweb.framework.pl.html.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.other.Anchor;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLAnchor extends Anchor {

	@Override
	public void paintInOther() {
		try {
			StringBuilder sbr = new StringBuilder();
			BOProperty bop = getBc();

			HTMLWebHelper.appendHead(sbr, this);
			HTMLWebHelper.appendStartTag(sbr, "a");
			HTMLWebHelper.appendAttr(sbr, "name", getName());
			HTMLWebHelper.appendStatus(sbr, bop.getStatus());
			HTMLWebHelper.appendAttr(sbr, "href", bop.toLink());
			HTMLWebHelper.appendAttr(sbr, "target", "_blank");
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, bop.toText());
			HTMLWebHelper.appendEndTag(sbr, "a");
		
			getPageContextInfo().write(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paintInTable() {
		
	}
}
