package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.pal.finegrained.text.Hidden;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtHidden extends Hidden {
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "hidden");
		if(getBc() == null)
			ExtWebHelper.appendTail(sbr, "value", "");
		else
			ExtWebHelper.appendTail(sbr, "value", getBc().toText());
		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}
}
