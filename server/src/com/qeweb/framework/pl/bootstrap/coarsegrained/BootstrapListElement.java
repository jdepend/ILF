package com.qeweb.framework.pl.bootstrap.coarsegrained;

import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.List;
import com.qeweb.framework.pal.coarsegrained.ListElement;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

import java.util.Map;

/**
 * Created by wangdg on 2016/2/18.
 */
public class BootstrapListElement extends ListElement {
    @Override
    public void paint() {
        PageContextInfo out = getPageContextInfo();

        BusinessObject bo = getBc();

        out.write("<li class=\"list-group-item mlt-record-background-color\"  style=\"height: 80px; padding-top: 4px; padding-bottom: 4px;\">\n");
        out.write("<div class=\"row rowcontent mlt-record-middle-background-color\"  style=\"height:72px; padding-top: 8px;padding-bottom: 8px; \">\n");

        paintFCList();

        out.write("</div>");
        out.write("</li>\n");
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
