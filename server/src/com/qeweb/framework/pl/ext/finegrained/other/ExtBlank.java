/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qeweb.framework.pl.ext.finegrained.other;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.other.Blank;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 *
 * @author qe-st-7
 */
public class ExtBlank extends Blank {

    @Override
    public void paint() {
        StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendAttr(sbr, "fieldLabel", "");
		ExtWebHelper.appendAttr(sbr, "viewlabel", "");
		ExtWebHelper.appendAttr(sbr, "id", this.getId());
		ExtWebHelper.appendAttr(sbr, "name", this.getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
		ExtWebHelper.appendAttr(sbr, "cls", "label");
		ExtWebHelper.appendAttr(sbr, "value", getText());
		ExtWebHelper.appendTail(sbr, "readOnly", "true");
			
		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
    }
    
    @Override
	public String getText() {
		return StringUtils.getUnescapedText(AppLocalization.getLocalization(text));
	}
}
