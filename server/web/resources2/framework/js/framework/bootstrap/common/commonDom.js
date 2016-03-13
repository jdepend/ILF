
/**
 * 通用HTML DOM操作
 * @returns
 */
var CommonDom = function() {}

/**
 * fcDom 是否是textField或textArea
 * @param fcDom 细粒度组件dom
 * @returns 是textField或textArea 返回true
 */
CommonDom.isTextDom = function(fcDom) {
	return fcDom.is('input[type="text"]') || fcDom.is('textArea') || fcDom.is('input[type="password"]') || fcDom.is('input[type="hidden"]');
}

/**
 * fcDom 是否是LABLE
 * @param fcDom 细粒度组件DOM
 * @returns 是LABLE 返回true
 */
CommonDom.isLableDom = function(fcDom) {
	return fcDom.is('span');
}

/**
 * container 是否是tree
 * @param container 粗粒度组件dom
 * @returns 是tree 返回true
 */
CommonDom.isTree = function(container) {
	return false;
	//return container ? container.getXType() == "treepanel" : false;
};

/**
 * fcDom 是否是radio
 * @param fcDom 细粒度组件dom
 * @returns 是radio 返回true
 */
CommonDom.isRadio = function(fcDom) {
	if(typeof(fcDom.is) == 'function')
		return fcDom.is('input[type="radio"]');
	else
		return (fcDom.type == 'radio');
}

/**
 * fcDom 是否是checkbox
 * @param fcDom 细粒度组件dom
 * @returns 是checkbox 返回true
 */
CommonDom.isCheckbox = function(fcDom) {
	if(typeof(fcDom.is) == 'function')
		return fcDom.is('input[type="checkbox"]');
	else
		return (fcDom.type == 'checkbox');
}

/**
 * fcDom 是否是select
 * @param fcDom 细粒度组件dom
 * @returns 是select 返回true
 */
CommonDom.isSelect = function(fcDom) {
	return fcDom.is('select');
}

/**
 * fcDom 是否是Image
 * @param fcDom 细粒度组件dom
 * @returns 是image 返回true
 */
CommonDom.isImg = function(fcDom) {
	return fcDom.is('img');
}

/**
 * fcDom 是否是双向选择
 * @param fcDom 细粒度组件dom
 * @returns 是双向选择 返回true
 */
CommonDom.isOptTrans = function(fcDom) {
	//细粒度组件中仅有双向选择控件在table中
	return fcDom.is('table');
}

/**
 * xmlDom 是否是page组件生成的dom
 */
CommonDom.isPage = function(xmlDom) {
	return xmlDom ? xmlDom.is(DISLAND.PAGE) : false;
}

/**
 * containerDom 是否是form
 * @param containerDom 粗粒度组件dom
 * @returns 是form 返回true
 */
CommonDom.isForm = function(containerDom) {
	return containerDom.is('form');
}

/**
 * containerDom 是否是form
 * @param containerDom 粗粒度组件dom
 * @returns 是form 返回true
 */
CommonDom.isTable = function(containerDom) {
	//html表格绘制时，包含在一个div中，扩展了xtype属性，值为table
	return containerDom.is('table') || containerDom.attr("xtype") == "table";
}

/**
 * buttonDom 是否是button
 * @param buttonDom 控制组件dom
 * @returns 是button 返回true
 */
CommonDom.isButton = function(buttonDom) {
	return buttonDom.is('input[type="button"]') || buttonDom.is('button');
}

/**
 * 是否是单控件
 * 例如：
 * 		<input type='text'>  单控件
 * 		<select name='test'> 单控件
 * 
 *		<input type='checkbox'/> 组合控件
 *		<input type='radio'/> 组合控件
 *		 		
 */
CommonDom.isSingleInput = function(fcDom){
	return this.isTextDom(fcDom) || this.isSelect(fcDom) || this.isImg(fcDom);
}

/**
 * 屏蔽特殊键
 */
document.onkeydown = function(e) {
	/*
	var docType = document.activeElement.type;
	var key = window.event ? event.keyCode : event.which;
	//屏蔽Backspace, F5, ctrl+R 键
	if (key == 8 || key == 116 || key == 82) {
		if(docType == "text" || docType == "textarea" || docType == "password") {
			;
		}
		//兼容IE浏览器
		else if($.browser.msie) {
			event.keyCode = 0;
			event.returnValue = false;
		}
		else {
			event.preventDefault();
		}
	}
	*/
}