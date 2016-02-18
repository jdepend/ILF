package com.qeweb.framework.pl.bootstrap.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.text.TextArea;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class BootstrapTextArea extends TextArea {

	@Override
	public void paint() {
		//StringBuilder styleStr = new StringBuilder();
		StringBuilder sbr = new StringBuilder();
		BOProperty bop = getBc();

        sbr.append("<div class=\"form-group\" >\n");
        sbr.append("<label for=\"" + getId() + "\" class=\"col-xs-4 control-label\" style=\"text-align: right;\">" + getText() + "：</label>\n");
        sbr.append("<div class=\"col-xs-8\">\n");
        sbr.append("<textarea class=\"form-control\" name=\"" + getName() + "\" id=\"" + getId() + "\" value=\"" + bop.toText() + "\" rows=\"3\"></textarea>\n");
        sbr.append("</div>\n");
        sbr.append("</div>\n");

		getPageContextInfo().write(sbr.toString());
	}

}
