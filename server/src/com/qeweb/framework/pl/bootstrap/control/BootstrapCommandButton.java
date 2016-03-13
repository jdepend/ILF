package com.qeweb.framework.pl.bootstrap.control;


import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class BootstrapCommandButton extends CommandButton {
	
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();


        sbr.append("<a class=\"btn btn-primary\" style=\"width:100\"");
        HTMLWebHelper.appendAttr(sbr, "id", getId());
        HTMLWebHelper.appendAttr(sbr, "name", getName());
        HTMLWebHelper.appendAttr(sbr, "type", "button");
        HTMLWebHelper.appendAttr(sbr, "operate", getOperate());
        HTMLWebHelper.appendEndTag(sbr);
        sbr.append(getText()+"</a>\n");

    	getPageContextInfo().write(sbr.toString());
	}
	
}
