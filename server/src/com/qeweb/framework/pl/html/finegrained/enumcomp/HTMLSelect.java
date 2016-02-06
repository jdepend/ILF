package com.qeweb.framework.pl.html.finegrained.enumcomp;

import java.util.Map;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLSelect extends Select {

	@Override
	public void paint() {
		BOProperty bop = getBc();
		StringBuilder sbr = new StringBuilder();

		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "select");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "style",
				"width:" + HTMLWebHelper.getFcDefaultWidth());
		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		HTMLWebHelper.appendEndTag(sbr);

		Map<String, String> store = bop.toMap();
		for (String field : store.keySet()) {
			HTMLWebHelper.appendStartTag(sbr, "option");
			HTMLWebHelper.appendAttr(sbr, "value", field);
			HTMLWebHelper.appendEndTag(sbr);
			HTMLWebHelper.appendContent(sbr, store.get(field));
			HTMLWebHelper.appendEndTag(sbr, "option");
		}

		HTMLWebHelper.appendEndTag(sbr, "select");
		HTMLWebHelper.appendTail(sbr);
		getPageContextInfo().write(sbr.toString());
	}

	@Override
	public void paintDeptTree() {
		
	}
}
