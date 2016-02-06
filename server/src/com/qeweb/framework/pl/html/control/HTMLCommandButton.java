package com.qeweb.framework.pl.html.control;


import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class HTMLCommandButton extends CommandButton {
	
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendAttr(sbr, "id", getId());
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "type", "button");
		HTMLWebHelper.appendAttr(sbr, "value", getText());
		HTMLWebHelper.appendSingleEndTag(sbr);
		getPageContextInfo().write(sbr.toString());
	}
	
}
