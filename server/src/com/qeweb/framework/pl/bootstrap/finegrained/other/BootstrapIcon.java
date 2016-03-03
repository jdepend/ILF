package com.qeweb.framework.pl.bootstrap.finegrained.other;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.other.Icon;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class BootstrapIcon extends Icon{
	@Override
	public void paint(){
		try {
			StringBuilder sbr = new StringBuilder();
			BOProperty bop = getBc();
            sbr.append("<span class=\"glyphicon " + bop.getValue().getValue() + "\" aria-hidden=\"true\"></span>");
            getPageContextInfo().write(sbr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
