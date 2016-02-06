package com.qeweb.framework.pl.html.finegrained.other;

import com.qeweb.framework.pal.finegrained.other.FileField;
import com.qeweb.framework.pl.html.HTMLWebHelper;

/**
 * HTMLFileField
 *
 */
public class HTMLFileField extends FileField {
	
	@Override
	public void paintSingle() {
		StringBuilder sbr = new StringBuilder();
		
		HTMLWebHelper.appendHead(sbr, this);
		HTMLWebHelper.appendStartTag(sbr, "input");
		HTMLWebHelper.appendAttr(sbr, "name", getName());
		HTMLWebHelper.appendAttr(sbr, "id", getId());
		HTMLWebHelper.appendAttr(sbr, "type", "file");
		HTMLWebHelper.appendStatus(sbr, getBc().getStatus());
		HTMLWebHelper.appendEndTag(sbr);
		HTMLWebHelper.appendTail(sbr);
		
		getPageContextInfo().write(sbr.toString());
		
	}

	@Override
	public void paintMultiple() {
		
	}
}
