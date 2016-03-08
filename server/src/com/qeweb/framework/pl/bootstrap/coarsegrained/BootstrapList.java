package com.qeweb.framework.pl.bootstrap.coarsegrained;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.List;
import com.qeweb.framework.pal.coarsegrained.Title;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

import java.util.Map;

/**
 * Created by wangdg on 2016/2/18.
 */
public class BootstrapList extends List {
    @Override
    public void paint() {
        PageContextInfo out = getPageContextInfo();

        out.write("<div style=\"background-color: beige;\">\n");
        out.write("<ul id=\"list-group\" class=\"list-group mlt-record-background-color\">\n");
        out.write("<div style=\"height: 3px;\"></div>\n");

        paintFCList();

        out.write("<div style=\"height: 4px;\"></div>\n");
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
