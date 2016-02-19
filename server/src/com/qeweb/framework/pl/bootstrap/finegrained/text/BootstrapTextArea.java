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

        sbr.append("<textarea class=\"form-control\" name=\"" + getName() + "\" id=\"" + getId() + "\" value=\"" + bop.toText() + "\" rows=\"3\"></textarea>\n");

		getPageContextInfo().write(sbr.toString());
	}

}
