package com.qeweb.framework.pl.bootstrap.coarsegrained;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Navbar;

/**
 * Created by wangdg on 2016/2/18.
 */
public class BootstrapNavbar extends Navbar {
    @Override
    public void paint() {
        PageContextInfo out = getPageContextInfo();
        StringBuilder sbr = new StringBuilder();

        String enterMode = Envir.getRequest().getParameter("enterMode");

        sbr.append("<div id=\"topMenu\">\n");
        sbr.append("<script>\n");
        sbr.append("    var enterMode = \"" + enterMode + "\";\n");
        sbr.append("</script>\n");
        sbr.append("<div class=\"navbar-default navbar-fixed-top mlt-frame-border\" style=\"position: absolute;\">\n");
        sbr.append("    <!-- Brand and toggle get grouped for better mobile display -->\n");
        sbr.append("    <div class=\"col-xs-1\" style=\"position: absolute; z-index: 1;\">\n");
        sbr.append("        <a class=\"navbar-brand\" href=\"javascript:pageBack(enterMode);\">\n");
        sbr.append("            <span class=\"glyphicon glyphicon-menu-left mlt-color\" aria-hidden=\"true\"></span>\n");
        sbr.append("        </a>\n");
        sbr.append("    </div>\n");
        sbr.append("    <div class=\"col-xs-12\">\n");
        sbr.append("        <a class=\"navbar-brand\" style=\"width:100%;text-align:center;\">" + this.getText() + "</a>\n");
        sbr.append("    </div>\n");
        sbr.append("</div>\n");
        sbr.append("</div>");
        sbr.append("<div style=\"height: 50px;\"></div>");
        out.write(sbr.toString());
    }
}
