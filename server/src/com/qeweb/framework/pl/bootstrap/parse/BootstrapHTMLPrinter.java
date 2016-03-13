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
        page.setStyle();
        page.paintTitle();
        pageContextInfo.write("</head>");
        page.paint();
//        this.paintFooter(pageContextInfo);
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
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/ilf-form.js\"></script>");
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources/js/mlt.js\"></script>");

        printViewComportResource(pageContextInfo);

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


    private void printViewComportResource(PageContextInfo pageContextInfo) {

        String ctx = Envir.getContextPath();
        String ajaxTimeout = AppConfig.getPropValue(AppConfig.AJAX_TIMEOUT);
        String optRemember = AppConfig.getPropValue(AppConfig.OPT_OPTREMEMBER);
        boolean isAutoSearch = AppConfig.isAutoSearch();

        pageContextInfo.write("<script type=\"text/javascript\">\n");

        pageContextInfo.write("var appConfig = {\n");
        pageContextInfo.write("ctx : '" + ctx + "',\n");
        pageContextInfo.write("ajaxTimeout : '" + ajaxTimeout + "',\n");
        pageContextInfo.write("//记忆操作(弹出确认信息和提示信息)\n");
        pageContextInfo.write("optRemember : '" + optRemember + "',\n");
        pageContextInfo.write("//使用查询条件时是否自动触发查询\n");
        pageContextInfo.write("isAutoSearch : " + isAutoSearch + ",\n");
        pageContextInfo.write("//终端使用百度地图\n");
        pageContextInfo.write("isBaiduMap : " + AppConfig.isBaiduMap() + ",\n");
        pageContextInfo.write("//弹出框是否默认带有关闭按钮\n");
        pageContextInfo.write("hasCloseBtn : " + AppConfig.hasCloseBtn() + ",\n");
        pageContextInfo.write("//如果在没有配置粗粒度组件布局的情况下, 最后一个组件是表格, 则表格高度撑满剩余区域\n");
        pageContextInfo.write("isTableHeightFull : " + AppConfig.isTableHeightFull() + ",\n");
        pageContextInfo.write("//当行级按钮全部隐藏时, 操作列是否自动隐藏 \n");
        pageContextInfo.write("isTableAutoHideOptCol : " + AppConfig.isTableAutoHideOptCol() + "\n");
        pageContextInfo.write("}; ");

        pageContextInfo.write("</script>\n");

        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/qewebi18n-zh_CN.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/jquery/jquery.xml2json.pack.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/extends.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/stringUtils.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/constant/param.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/constant/actionURL.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/dataisland/xmlDataIsland.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/dataisland/XMLDomFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/CommonUtils.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/dataisland/TransToDataIslandDom.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/constant/BoFinalMethod.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/dataisland/bindData.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/constant/constantJSON.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/NumberUtil.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/Observable.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/Observer.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/baseExpand.js\"></script>\n");

        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/dataIslandToDOM.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/commonDom.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/htmlPageBar.js\"></script>\n");
        //pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/framework/js/framework/html/load/htmlTreeLoad.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/ContainerHandlerFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/ContainerHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/PageHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/FormHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/TableHelper.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/TableHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/coarsegrained/handler/SysAddRowHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/BOPSubmit.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/fcHandlerFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/FCHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/LabelHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/TextHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/SelectHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/RadioHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/CheckboxHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/ImgHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/finegrained/handler/OptionTranserSelectHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/dataisland/TransToDataIslandDom.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/common/constant/BoFinalMethod.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/ButtonEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/BtnJSEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/Confirm.js\"></script>\n");

        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/PageFlow.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/ResultMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/BtnExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/form/BtnFormExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/form/BtnFormQueryEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/form/BtnFormExportEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/form/BtnFormClearEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/table/BtnTableExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/table/BtnSysAddRowEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/btnEvent/page/BtnPageExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/ButtonHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/load.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/html/date/WdatePicker.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/commonDomUtil.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/returnMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/showMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/common/showTips.js\"></script>\n");

        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/observable/ContainerObservable.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/bootstrap/control/observer/ContainerObserver.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/js/projectStyle.js\"></script>\n");


        pageContextInfo.write("<script type=\"text/javascript\">\n");
        pageContextInfo.write("var observableArr = new Array();");
        pageContextInfo.write("</script>\n");
        pageContextInfo.write("\n");

    }


}
