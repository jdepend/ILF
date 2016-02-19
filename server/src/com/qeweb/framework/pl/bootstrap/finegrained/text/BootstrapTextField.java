package com.qeweb.framework.pl.bootstrap.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.text.TextField;
import com.qeweb.framework.pl.html.HTMLWebHelper;

/**
 * BootstrapTextField
 */
public class BootstrapTextField extends TextField {

    @Override
    public void paint() {
        StringBuilder sbr = new StringBuilder();
        BOProperty bop = getBc();

        String id = this.getParent().getBcId() + "." + getId();
        String name = getName();

        sbr.append("<input class=\"form-control\"");
        HTMLWebHelper.appendAttr(sbr, "name", getName());
        HTMLWebHelper.appendAttr(sbr, "id", id);
        HTMLWebHelper.appendAttr(sbr, "value", bop.toText());
        if(bop.getRange().isRequired()){
            sbr.append(" required");
        }
        HTMLWebHelper.appendEndTag(sbr);

        getPageContextInfo().write(sbr.toString());
    }
}
