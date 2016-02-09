package com.qeweb.framework.pl.html.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLTextArea extends TextArea {

	@Override
	public void paint() {
		StringBuilder styleStr = new StringBuilder();
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = getBc();

		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "textarea");

        //增加ID属性
        HTMLWebHelper.appendAttr(sbr, "id", getId());

		HTMLWebHelper.appendAttr(sbr, "name", getName());

		HTMLWebHelper.appendStatus(sbr, bop.getStatus());
		styleStr.append("width:" + HTMLWebHelper.getFcDefaultWidth());
		if (getHeight() > 0F)
			styleStr.append(";height:" + getHeight() + "px");
		HTMLWebHelper.appendAttr(sbr, "style", styleStr.toString());
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendContent(sbr, bop.toText());
		HTMLWebHelper.appendEndTag(sbr, "textarea");
		HTMLWebHelper.appendTail(sbr);

		getPageContextInfo().write(sbr.toString());
	}

}
