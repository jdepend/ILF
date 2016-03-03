package com.qeweb.framework.pl.bootstrap.coarsegrained;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Navbar;
import com.qeweb.framework.pal.coarsegrained.Title;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

import java.util.Map;

/**
 * Created by wangdg on 2016/2/18.
 */
public class BootstrapTitle extends Title {
    @Override
    public void paint() {
        PageContextInfo out = getPageContextInfo();

        out.write("<div class=\"row\" id=\"lt_title\">\n");
        out.write("<div class=\"col-xs-12\" style=\"height:30px; padding-top: 5px; padding-left: 5px;\">\n");

        paintFCList();

        out.write("</div>\n");
        out.write("</div>");
    }

    private void paintFCList() {
        if (ContainerUtil.isNull(getFcList()))
            return;

        PageContextInfo out = getPageContextInfo();
        // 细粒度组件列表<细粒度组件bind, 细粒度组件>
        Map<String, FinegrainedComponent> fcList = getFcList();

        for (String bopBind : fcList.keySet()) {
            FinegrainedComponent fc = fcList.get(bopBind);
            fc.paint();
        }
    }
}
