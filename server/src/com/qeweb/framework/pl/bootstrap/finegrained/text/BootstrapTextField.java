package com.qeweb.framework.pl.bootstrap.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.text.TextField;
import com.qeweb.framework.pl.html.HTMLWebHelper;

/**
 * HTMLTextField
 */
public class BootstrapTextField extends TextField {

    @Override
    public void paint() {
        StringBuilder sbr = new StringBuilder();
        BOProperty bop = getBc();

        String id = this.getParent().getBcId() + "." + getId();
        String name = getName();
        String text = getText();

        sbr.append("<div class=\"form-group\" id=\"" + id + "_group\">\n");
        sbr.append("<label for=\"" + name + "\" class=\"col-xs-4 control-label\" style=\"text-align: right;\">" + text + "：</label>\n");
        sbr.append("<div class=\"col-xs-8\">\n");
        sbr.append("<input class=\"form-control\" name=\"" + name + "\" id=\"" + id + "\" type=\"TEXT\" value=\"" + bop.toText() + "\" required=\"\">\n");
        sbr.append("</div>\n");
        sbr.append("</div>\n");

        getPageContextInfo().write(sbr.toString());
    }
}
