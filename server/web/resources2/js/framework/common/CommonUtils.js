/**
 * 得到隐藏域中的数据岛
 * @return {}
 */
function getDataIslandByJQ(){
	return getDataIsland().find(DISLAND.dataIsland).slice(0);
}


$(document).ready(function(){
	//获得数据岛
	getDataIsland = initDataisland();
});

/**
 * 是否配置了粗粒度组件布局
 */
function hasConLayout() {
	return $('#dataIsland').attr('layout') == 'true';
}

function initDataisland(){
	var _island = XMLDomFactory.getInstance();
	function manageDataIsland(dataIslandStr){
		if(!!dataIslandStr)
			_island = XMLDomFactory.getInstance(dataIslandStr);
		return _island;
    }

    return manageDataIsland;
}

/**
 * 替换所有匹配的字符串
 */
String.prototype.replaceAll  = function(s1, s2){
    return this.replace(new RegExp(s1, "g"), s2);
}

/**
 * 数据岛特殊字符处理
 * 把字符中的&，%转义，并包含在<![CDATA[]]>中
 * @param str
 * @returns {String}
 */
function specialCharHandler(str){
	if(str instanceof Array)
		str = str.toString();
	return "<![CDATA[" + replaceAll(str, "&,%", ":amp;,%25") + "]]>";
}

function getCDATAContent(str) {
	return "<![CDATA[" + str + "]]>";
}

/**
 * 数据岛特殊字符处理。从数据岛还原到控件时使用。
 * 把字符中的:amp;,%25还原为& %
 * @param str
 * @returns {String}
 */
function specialCharEncode(str){
	return replaceAll(str, ":amp;,%25", "&,%");
}

/**
 * 禁止右键弹出菜单
 */
//function document.oncontextmenu() {     
//	return false;     
//} 

/**
 * 监听全页面中的按键事件
 */
KeyDown = {
	on : function(evt) {
		if (evt.keyCode == 13) {
	        KeyDown.submit();
	    }
	},
	
	/**
	 * 当获得焦点的控件非文本域输入控件时，执行页面的第一个按钮
	 */
	submit : function(){
		var ae = document.activeElement;		
		if(ae.type && ae.type === 'textarea')
			return;
		var operateList = DISLAND.getOperateList();
		if(!operateList)
			return;
		//执行页面第一个非隐藏且有效的按钮对应的方法
		var btn = DISLANDTODOM.getFisrstShowBtn();
		if(!!btn)
			btn.fireEvent("click");
	}
};

function addKeyDownListener(){
	if (document.addEventListener) {
		document.addEventListener("keypress", KeyDown.on, true);
	} 
	else {
		//如果是IE
		document.attachEvent("onkeypress", KeyDown.on);
	}
}
addKeyDownListener();

/**
 * 是否使用Ext
 * @returns {Boolean}
 */
function isUseExt() {
	return typeof(Ext) == "object";
}

/**
 * 登录后的首页是否使用了border布局(使用<qeweb:mainMenu>标签)
 * @returns {Boolean}
 */
function isUseBorderLayout() {
	return typeof addTab == 'function';
}

/**
 * 阻塞函数
 * @param milliSeconds 阻塞时间,单位:毫秒
 */
function sleep(milliSeconds) {
	var startTime = new Date().getTime();
	while (new Date().getTime() < startTime + milliSeconds);
}

/**
 * 处理窜session问题
 * @param actionView
 */
function validateSession(actionView) {
	//如果窜session，跳转到登录后的第一个页面
	if (actionView == ConstantJSON.RELOGIN) {
		window.parent.location.href = appConfig.ctx + actionURL.getRelogin();
	}
}

/**
 * 获取按钮所属bo中bop的值. 该方法仅对表单中的按钮或表格的行级按钮有效.
 * @param btnEvent	ButtonEvent
 * @param bopBind
 * @returns
 */
function getFCValue(btnEvent, bopBind) {
	var bo = btnEvent.operateDom.parent();
	var bop = bo.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bopBind + "']");
	return getXmlNoteText(bop);
}

/**
 * 根据分辨率获取自适应size 
 */
function getAdaptiveSize(size) {
	return screen.width > 1280 ? size : size / 1280 * screen.width
}

