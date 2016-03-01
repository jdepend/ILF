<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/taglib/qeweb" prefix="qeweb"%>
<%@page import="com.qeweb.framework.common.appconfig.AppCookie"%>
<%@page import="com.qeweb.framework.common.appconfig.AppConfig"%>
<%@page import="com.qeweb.framework.common.utils.StringUtils"%>
<%@page import="com.qeweb.framework.common.Envir"%>
<%@page import="com.qeweb.framework.manager.DisplayType"%>
<%if(DisplayType.isHtml()){%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%} %>
<html xmlns="http://www.w3.org/1999/xhtml">

<%
	String ctx = Envir.getContextPath();
	String lang = "-" + DisplayType.getLanguageType();
	String ajaxTimeout = AppConfig.getPropValue(AppConfig.AJAX_TIMEOUT);
	String optRemember = AppConfig.getPropValue(AppConfig.OPT_OPTREMEMBER);
	boolean isAutoSearch = AppConfig.isAutoSearch();
	String themeType = DisplayType.getThemeType();
%>
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8" />
	<META HTTP-EQUIV="expires" CONTENT="-1">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Window-target" CONTENT="_top">
	<%if(DisplayType.isTerminal()) {%>
		<link href="<%=ctx %>/resources2/framework/css/idTabs/main.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/android/theme-<%=themeType %>.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/tipsy.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/html/layer.css" type="text/css"  rel="stylesheet" />
	<%} else if(DisplayType.isHtml()){%>
		<link href="<%=ctx %>/resources2/framework/css/tipsy.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/html/dtree/dtree.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/idTabs/main.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/html/active.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/css/html/layer.css" type="text/css"  rel="stylesheet" />
	<%} else {%>
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/history.css" type="text/css"  rel="stylesheet" />
		<link id="theme" href="<%=ctx %>/resources2/framework/js/ext/ext3.2/resources/css/xtheme-<%=themeType %>.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/MultiSelect.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/LockingGridView.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/uploadDialog/css/Ext.ux.UploadDialog.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/showTips.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/fileuploadfield.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/Icon.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/css/ColumnHeaderGroup.css" type="text/css"  rel="stylesheet" />
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/htmleditor/resource/css/htmleditorplugins.css" type="text/css"  rel="stylesheet" />
		
		<link href="<%=ctx %>/resources2/framework/css/ext/grid-enableCopy.css"  rel="stylesheet"  type="text/css"/>
		<link href="<%=ctx %>/resources2/framework/css/ext/loading.css"  rel="stylesheet"  type="text/css"/>
		<link href="<%=ctx %>/resources2/framework/js/ext/ext3.2/css/Spinner.css" rel="stylesheet" type="text/css" />
		<link href="<%=ctx %>/resources2/css/projectIcon.css" rel="stylesheet" type="text/css" />
		<%if(DisplayType.isCellStyleNewline()) { %>
			<link href="<%=ctx %>/resources2/css/projectStyle.css" type="text/css"  rel="stylesheet" />
		<%} else { %>
			<link href="<%=ctx %>/resources2/css/projectStyleDefault.css" type="text/css"  rel="stylesheet" />
		<%}%>
	<%}%>
	
	<script type="text/javascript">
		var appConfig = {
			ctx : '<%=ctx %>',
			ajaxTimeout : '<%=ajaxTimeout %>',
			//记忆操作(弹出确认信息和提示信息)
			optRemember : '<%=optRemember %>',
			//使用查询条件时是否自动触发查询
			isAutoSearch : <%=isAutoSearch%>,
			//终端使用百度地图
			isBaiduMap : <%=AppConfig.isBaiduMap() %>,
			//弹出框是否默认带有关闭按钮
			hasCloseBtn : <%=AppConfig.hasCloseBtn()%>,
			//如果在没有配置粗粒度组件布局的情况下, 最后一个组件是表格, 则表格高度撑满剩余区域
			isTableHeightFull : <%=AppConfig.isTableHeightFull()%>,
			//当行级按钮全部隐藏时, 操作列是否自动隐藏 
			isTableAutoHideOptCol : <%=AppConfig.isTableAutoHideOptCol()%>
		}; 
	</script>

	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/qewebi18n<%=lang %>.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.tipsy.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.xml2json.pack.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/ajaxfileupload.js"></script>
	
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/mobile/AndroidEngine.js"></script>
	
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/extends.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/stringUtils.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/param.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/actionURL.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/xmlDataIsland.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/XMLDomFactory.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/CommonUtils.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/TransToDataIslandDom.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/BoFinalMethod.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/bindData.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/constantJSON.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/NumberUtil.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/Observable.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/Observer.js"></script>
	<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/baseExpand.js"></script>
	<%if(DisplayType.isMobile()){%>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/compatibleEvent.js"></script>
	<%} %>
	<%if(DisplayType.isTerminal()){%>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.idTabs.min.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/dataIslandToDOM.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/commonDom.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/htmlPageBar.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/html/dtree/dtree.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/html/dtree/mobileTree.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/htmlPageBar.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/load/htmlTreeLoad.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/ContainerHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/ContainerHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/PageHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/FormHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/TableHelper.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/TableHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/SysAddRowHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/BOPSubmit.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/fcHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/FCHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/LabelHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/TextHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/SelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/RadioHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/CheckboxHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/ImgHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/OptionTranserSelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/TransToDataIslandDom.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/BoFinalMethod.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/ButtonEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/BtnJSEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/Confirm.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/PageFlow.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/ResultMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/BtnExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormQueryEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormExportEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormClearEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/table/BtnTableExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/table/BtnSysAddRowEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/page/BtnPageExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/ButtonHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/load.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/html/date/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/commonDomUtil.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/returnMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.layer.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/showMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/showTips.js"></script>
	<%}else if(DisplayType.isExt()){%>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/adapter/jquery/ext-jquery-adapter.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ext-all.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ext-grid-enableCopy.js"></script>
		
		<%if("-zh_CN".equals(lang)){%>
			<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ext-lang<%=lang %>.js"></script>
		<%}%>

		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/dataTime/Spinner.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/dataTime/SpinnerField.js"></script>
        <script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/dataTime/DateTimeField.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/TreeNodeCheck.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/MultiSelect.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/ItemSelector.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/Image.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/uploadDialog/Ext.ux.UploadDialog.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/uploadDialog/locale/zh_CN.utf-8.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/showTips.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/ExtExpend.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/fileUpload/FileUploadField.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/form/form.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/grid/ProgressBarPager.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/grid/SlidingPager.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/grid/ColumnHeaderGroup.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/tab/TabCloseMenu.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/htmleditor/js/Ext.ux.form.HtmlEditor.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/ext/ext3.2/ux/tipwindow/tipWindow.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/ux/extlayout.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/ux/ExtValidate.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/ux/ExtUpload.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/ux/EnumValidate.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/ux/ContainerResize.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/extCommonUtils.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/tree/extTreeInteract.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/showMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/dialog.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/commonDom.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/dataIslandToDOM.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/dialogFillBack.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/returnMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/saveCase.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/common/tableSetting.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/ContainerHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/ContainerHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/PageHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/FormHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/TableHelper.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/TableHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/TabHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/handler/SysAddRowHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/ContainerHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/render/CellRender.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/coarsegrained/render/EditRender.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/BOPSubmit.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/fcHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/FCHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/SelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/RadioHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/CheckboxHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/CheckboxHelper.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/OptionTranserSelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/TextHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/HiddenHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/ImgHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/DateHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/FileHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/LabelHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/AnchorHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/finegrained/handler/EditorHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/ButtonEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/BtnJSEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/BtnJSEventAfterExe.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/Confirm.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/PageFlow.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/ResultMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/BtnExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormQueryEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormExportEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/form/BtnFormClearEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTableExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTablePopuEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/table/BtnTableRowEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/table/BtnSysAddRowEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/tree/BtnTreeExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/tab/BtnTabExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/ButtonHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/btnEvent/page/BtnPageExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/load.js"></script>

		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/observable/CellEditObservable.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/observer/CellEditObserver.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/observable/ContainerObservable.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/ext/control/observer/ContainerObserver.js"></script>

		<script type="text/javascript" src="<%=ctx %>/resources2/js/projectStyle.js"></script>
	<%}else if(DisplayType.isHtml()){%>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.idTabs.min.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/dataIslandToDOM.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/commonDom.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/html/dtree/dtree.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/htmlPageBar.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/load/htmlTreeLoad.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/ContainerHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/ContainerHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/PageHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/FormHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/TableHelper.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/TableHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/coarsegrained/handler/SysAddRowHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/BOPSubmit.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/fcHandlerFactory.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/FCHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/LabelHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/TextHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/SelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/RadioHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/CheckboxHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/ImgHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/finegrained/handler/OptionTranserSelectHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/dataisland/TransToDataIslandDom.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/common/constant/BoFinalMethod.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/ButtonEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/BtnJSEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/Confirm.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/PageFlow.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/ResultMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/BtnExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormQueryEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormExportEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/form/BtnFormClearEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/table/BtnTableExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/table/BtnSysAddRowEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/btnEvent/page/BtnPageExeEvent.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/ButtonHandler.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/load.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/html/date/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/commonDomUtil.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/returnMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/jquery/jquery.layer.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/showMsg.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/common/showTips.js"></script>
		
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/observable/ContainerObservable.js"></script>
		<script type="text/javascript" src="<%=ctx %>/resources2/framework/js/framework/html/control/observer/ContainerObserver.js"></script>
	<%}%>
</head>
</html>

<%if(DisplayType.isExt()) {%>
	<script type="text/javascript">
		$(document).ready(function(){
			setTimeout(function() {
				Ext.get('loading').remove();
				Ext.get('loading-mask').fadeOut({remove:true});
			}, 100); 
		});
	</script>
	
	<!--显示loding区域-->
	<div id='loading-mask'></div>
	<div id='loading'>
		<div class='loading-indicator'>
			<IMG align='absMiddle' src="<%=ctx %>/resources2/framework/images/common/ajax1.gif">
				<script type="text/javascript">
					document.write(I18N.COMMON_INIT);
				</script>
		</div>
	</div>
<%}%>