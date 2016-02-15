
function param(param) {
	return "&" + param;
}

/**
 * 页面流参数<br>
 * 一些功能通过URL传递参数，pageFlow中是页面流所需要的参数
 */
var pageFlow = {
	//页面流 使用参数 ,可以通过以下三个参数获知应跳转到哪个目标页面 
	sourceName : "sourceName=",
	btnName : "btnName=",
	sourcePage : "sourcePage=",
	//contextOperate是上下文跳转使用的参数, 如果在目标页面需要执行按钮绑定的方法，则需要设置该参数
	contextOperate : "contextOperate=",
	returnMsg : "returnMsg="
};

/**
 * 保存查询条件功能所用的参数
 */
var saveCase = {
	dataIsland : "dataIsland=",
	sourcePage: "sourcePage=",				//页面路径
	caseName : "caseName=",					//查询条件名称
	containerId : "containerId=", 			//"保存条件"按钮所在的组件ID
	saveForNewCase : "saveForNewCase=",		//是否保存为新条件的标识
	oldId : "oldId="	
};

/**
 * 保存表格设置功能所用的参数
 */
var tableSettingParam = {
	sourcePage : "sourcePage=",			//页面路径
	tableId : "tableId=",				//表格组件id
	hiddenBop : "hiddenBop=",			//本次隐藏或反隐藏的bop
	isHidden : "hidden=",				//隐藏或反隐藏
	position : "position=",				//表格展示列的位置
	changeWidthBop : "changeWidthBop=",	//本次修改宽度的bop
	newWidth : "newWidth="				//修改的宽度
};

/**
 * 选择按钮传递的参数
 */
var sourceBtn = {
	sm : "sm="
};

/**
 * GA的参数
 */
var GA = {
	//group标签标识的粗粒度组件关联关系
	relations : "relations=",
	//BO应当执行的方法
	operation : "operation=",
	//数据岛
	dataIsland : "dataIsland=",
	vcId : "vcId=",
	
	//记录的Id, 通过recordId可从数据库获得唯一的bo
	recordId : "recordId=",
	//系统弹出框类型
	winType : "winType=",
	//被关联粗粒度组件名称
	targetName : "targetName=",
	//指定组件的数据数据岛
	targetXML : "targetXML="
};

/**
 * AJAX POST数据中有特殊符号导致数据丢失的解决方法
 * 1. "+"号：JavaScript解析为字符串连接符，所以服务器端接收数据时"+"会丢失变空格。
 * 2. "&"号：JavaScript解析为变量连接符，所以服务器端接收数据时&符号以后的数据都会丢失变空格。
 * 3. "%"号：会导致数据全部丢失。
 */
function paramValue(value) {
	value = value.replace(/%/g, "%25");  //该行必须在最前，应该先转百分号。
	value = value.replace(/\+/g, "%2B"); 
	value = value.replace(/\&/g, "%26");  
	return encodeURIComponent(value);
}