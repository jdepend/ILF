package com.qeweb.framework.common.constant;

/**
 * 平台参数
 * <br>
 * 一些功能通过URL传递参数，ConstantParam 是平台的所有待传递参数
 */
public interface ConstantParam {

	//以下是翻页时传递的参数
	//如果有tableName，表示执行翻页操作
	final public static String GA_TABLENAME = "tableName";
	//翻页时的页码
	final public static String GA_START_ROW = "start";
	
	//菜单URL中添加的参数, 如果path中有pathType=menu,则清空上下文参数
	final public static String MENU_PATH_PARAM = "pathType=menu";
	
	//页面流 使用参数 ,可以通过以下三个参数获知应跳转到哪个目标页面
	final public static String GA_SOURCE_PAGE = "sourcePage";
	final public static String GA_SOURCE_VCID = "sourceVcId";
	final public static String GA_BTNNAME = "btnName";
	
	final public static String GA_SOURCENAME = "sourceName";
	final public static String GA_OPERATION = "operation";
	
	final public static String TIMESTEMP = "timeStemp";
	//蹿session的ticket
	final public static String SESSION_TICKET = ConstantDataIsland.SESSION_TICKET;
	//防止重复刷新的令牌
	final static public String TOKEN_TICKET = ConstantDataIsland.TOKEN_TICKET;
	
	/*
	 * PARAM_OPERATE是上下文跳转使用的参数,如果按钮绑定的是另一个bo的方法,则需要使用该参数.
	 * 如: 
	 * <qeweb:table id='bo1' bind='bo1'>
	 * 		<qeweb:commandButton bind='bo2.method'/>
	 * </qeweb:table>
	 * 此时PARAM_OPERATE 是 bo2.method
	 */
	final public static String CONTEXT_OPERATE = "contextOperate";
	
	//目标页面的粗粒度组件接收参数的标识
	//<qeweb:form id='id' bind='bo' param="required'>
	final public static String CONTEXT_PARAM_REQURIED = "required";
	
	//页面加载时调用的bo方法
	//如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod&param=a,b,c,d的形式，
	//表示页面加载时执行onLoadMethod方法，onLoadMethod需要为粗粒度组件设置值
	final public static String CONTEXT_ONLOAD = "load";
	//页面加载时调用的bo方法的参数, 该参数通过URL传递
	//如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod&param=a,b,c,d的形式，
	//表示页面加载时执行onLoadMethod方法，onLoadMethod需要为粗粒度组件设置值
	final public static String CONTEXT_ONLOAD_PARAM = "param";
	
	//断点续传使用,文件唯一标识
	final public static String BROKENDOWNLOAD_FIELDID = "fileid";
	
	//操作前弹出提示框
	final public static String SHOW_DIALOG = "showDialog";
	//操作后弹出提示框
	final public static String AFTER_OPERATIONBOX = "afterOperationBox";
	
	//弹出选择使用的参数
	final public static String SOURCEBTN_SM = "sm";	//模态对话框中的table所展示的选择按钮的selectionModel
	
	//返回提示信息
	final public static String RETURN_MESSAGE = "returnMsg";

	//弹出框父组件ID集合
	final public static String PARENT_CONTAINER_MAP = "parentContainerMap";
	//排序字段名
	final public static String GA_TABLE_FILENAME = "sort";
	//排序方向
	final public static String GA_TABLE_ORDER = "dir";
	
	final public static String GA_TIP_MSG = "tipMsg";
}
