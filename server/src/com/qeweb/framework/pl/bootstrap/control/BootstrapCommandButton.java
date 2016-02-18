package com.qeweb.framework.pl.bootstrap.control;


import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class BootstrapCommandButton extends CommandButton {
	
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
        sbr.append("<div class=\"form-group\">\n");
        sbr.append("<div class=\"col-xs-12\" style=\"text-align: center;\">\n");

        sbr.append("<button class=\"btn btn-primary\" style=\"width:20%\"");
        HTMLWebHelper.appendAttr(sbr, "id", getId());
        HTMLWebHelper.appendAttr(sbr, "name", getName());
        HTMLWebHelper.appendAttr(sbr, "type", "button");
        HTMLWebHelper.appendEndTag(sbr);
        sbr.append(getText()+"</button>\n");

        sbr.append("</div>\n");
        sbr.append("</div>\n");
		getPageContextInfo().write(sbr.toString());
	}
	
}
