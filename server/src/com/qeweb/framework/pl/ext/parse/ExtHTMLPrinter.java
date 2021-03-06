package com.qeweb.framework.pl.ext.parse;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.XMLPageUtil;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pl.common.parse.FileEngineContextInfo;
import com.qeweb.framework.pl.common.parse.HTMLPagePrinter;
import com.qeweb.framework.pl.common.parse.WriterEngineContextInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

/**
 * html解析引擎
 * 根据开发人员 配置的 pl 显示界面xml文件 输出 html
 */
public class ExtHTMLPrinter implements HTMLPagePrinter {

    @Override
    public void print(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo) {
        pageContextInfo.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        pageContextInfo.write("<head>");
        pageContextInfo.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\" />");
        this.paintHeader(page, javascriptFiles, pageContextInfo);
        page.setStyle();
        page.paintTitle();
        pageContextInfo.write("</head>");
        page.paint();
//        this.paintFooter(pageContextInfo);
        pageContextInfo.write("</html>");
    }

    private void paintHeader(Page page, List<String> javascriptFiles, PageContextInfo pageContextInfo) {

        String ctx = Envir.getContextPath();
        String lang = "-" + DisplayType.getLanguageType();
        String ajaxTimeout = AppConfig.getPropValue(AppConfig.AJAX_TIMEOUT);
        String optRemember = AppConfig.getPropValue(AppConfig.OPT_OPTREMEMBER);
        boolean isAutoSearch = AppConfig.isAutoSearch();
        String themeType = DisplayType.getThemeType();

        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/resources/css/ext-all.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/history.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link id=\"theme\" href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/resources/css/xtheme-blue.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/MultiSelect.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/LockingGridView.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/uploadDialog/css/Ext.ux.UploadDialog.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/showTips.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/fileuploadfield.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/Icon.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/css/ColumnHeaderGroup.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/htmleditor/resource/css/htmleditorplugins.css\" type=\"text/css\" rel=\"stylesheet\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/css/ext/grid-enableCopy.css\" rel=\"stylesheet\" type=\"text/css\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/css/ext/loading.css\" rel=\"stylesheet\" type=\"text/css\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/css/Spinner.css\" rel=\"stylesheet\" type=\"text/css\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/css/projectIcon.css\" rel=\"stylesheet\" type=\"text/css\">\n");
        pageContextInfo.write("<link href=\"" + Envir.getContextPath() + "/resources2/css/projectStyleDefault.css\" type=\"text/css\" rel=\"stylesheet\">\n");
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
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/jquery/jquery.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/jquery/jquery.tipsy.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/jquery/jquery.xml2json.pack.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/jquery/ajaxfileupload.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/mobile/AndroidEngine.js\"></script>\n");
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
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/adapter/jquery/ext-jquery-adapter.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/adapter/ext/ext-base.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ext-all.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ext-grid-enableCopy.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ext-lang-zh_CN.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/dataTime/Spinner.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/dataTime/SpinnerField.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/dataTime/DateTimeField.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/TreeNodeCheck.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/MultiSelect.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/ItemSelector.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/Image.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/uploadDialog/Ext.ux.UploadDialog.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/uploadDialog/locale/zh_CN.utf-8.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/showTips.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/ExtExpend.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/fileUpload/FileUploadField.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/form/form.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/grid/ProgressBarPager.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/grid/SlidingPager.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/grid/ColumnHeaderGroup.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/tab/TabCloseMenu.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/htmleditor/js/Ext.ux.form.HtmlEditor.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/ext/ext3.2/ux/tipwindow/tipWindow.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/ux/extlayout.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/ux/ExtValidate.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/ux/ExtUpload.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/ux/EnumValidate.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/ux/ContainerResize.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/extCommonUtils.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/tree/extTreeInteract.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/showMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/dialog.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/commonDom.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/dataIslandToDOM.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/dialogFillBack.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/returnMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/saveCase.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/common/tableSetting.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/ContainerHandlerFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/ContainerHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/PageHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/FormHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/TableHelper.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/TableHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/TabHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/handler/SysAddRowHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/ContainerHandlerFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/render/CellRender.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/coarsegrained/render/EditRender.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/BOPSubmit.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/fcHandlerFactory.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/FCHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/SelectHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/RadioHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/CheckboxHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/CheckboxHelper.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/OptionTranserSelectHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/TextHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/HiddenHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/ImgHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/DateHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/FileHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/LabelHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/AnchorHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/finegrained/handler/EditorHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/ButtonEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/BtnJSEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/BtnJSEventAfterExe.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/Confirm.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/PageFlow.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/ResultMsg.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/BtnExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormQueryEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormExportEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormClearEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTableExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTablePopuEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTableRowEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/table/BtnSysAddRowEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/tree/BtnTreeExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/tab/BtnTabExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/ButtonHandler.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/btnEvent/page/BtnPageExeEvent.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/load.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/observable/CellEditObservable.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/observer/CellEditObserver.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/observable/ContainerObservable.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/framework/js/framework/ext/control/observer/ContainerObserver.js\"></script>\n");
        pageContextInfo.write("<script type=\"text/javascript\" src=\"" + Envir.getContextPath() + "/resources2/js/projectStyle.js\"></script>");


//        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/vsr/common.js\"></script>");
        if (javascriptFiles != null && javascriptFiles.size() > 0) {
            for (String javascriptFile : javascriptFiles) {
                pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/business/" + javascriptFile + ".js\"></script>");
            }
        }
        pageContextInfo.write("<script src=\"" + Envir.getContextPath() + "/resources2/vsr/onload.js\"></script>");
    }

    private void paintFooter(PageContextInfo pageContextInfo) {
        pageContextInfo.write("<script type=\"text/javascript\">\n");

        pageContextInfo.write("$(document).ready(function(){\n");
        pageContextInfo.write("setTimeout(function() {\n");
        pageContextInfo.write("\tExt.get('loading').remove();\n");
        pageContextInfo.write("\tExt.get('loading-mask').fadeOut({remove:true});\n");
        pageContextInfo.write("}, 100); \n");
        pageContextInfo.write("});");

        pageContextInfo.write("</script>\n");
    }


}
