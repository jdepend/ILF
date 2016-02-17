package com.qeweb.framework.pl.bootstrap.parse;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pl.common.parse.HTMLPagePrinter;

import java.util.List;

/**
 * html解析引擎
 * 根据开发人员 配置的 pl 显示界面xml文件 输出 html
 */
public class BootstrapHTMLPrinter implements HTMLPagePrinter {

    @Override
    public void print(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo) {
        pageContextInfo.write("<html lang=\"zh-CN\">");
        pageContextInfo.write("<head>");
        pageContextInfo.write("<meta charset=\"utf-8\">\n");
        pageContextInfo.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n");
        pageContextInfo.write("<meta name=\"viewport\" content=\"user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height\" />\n");

        this.paintHeader(page, javascriptFiles, pageContextInfo);
        pageContextInfo.write("</head>");
        pageContextInfo.write("<body>");
        page.paint();
//        this.paintFooter(pageContextInfo);
        pageContextInfo.write("</body>");
        pageContextInfo.write("</html>");
    }


    private void paintHeader(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo) {

        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/bootstrap.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/slider_1.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/mlt.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-default.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-style-bar.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/ns-style-growl.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources/css/mobiscroll.custom-2.6.2.min.css\" rel=\"stylesheet\">");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.lazyload.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/bootstrap.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/mobiscroll.custom-2.6.2.min.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/iscroll.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/modernizr.custom.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/jquery.cxselect.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/ilf-metadata.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/ilf-event.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/mlt.js\"></script>");

        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/vsr/common.js\"></script>");
//        List<String> javascriptFiles = this.javascripts.get(file.getName().substring(0, file.getName().indexOf(".")));
        if (javascriptFiles != null && javascriptFiles.size() > 0) {
            for (String javascriptFile : javascriptFiles) {
                pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/business/" + javascriptFile + ".js\"></script>");
            }
        }
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/vsr/onload.js\"></script>");

        pageContextInfo.write("<script>");
        pageContextInfo.write("    var basePath = \"" + Envir.getContextPath() + "\";");
        //pageContextInfo.write("    var loadDataUrl = \"${pageModel.loadDataUrl}\";");
        pageContextInfo.write("</script>");

    }

    private void paintFooter(PageContextInfo pageContextInfo) {

    }


}
