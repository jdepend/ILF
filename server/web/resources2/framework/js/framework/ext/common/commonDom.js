
/**
 * 通用Ext DOM操作
 * @returns
 */
var CommonDom = function() {};

/**
 * fc 是否是label
 * @param {} fc 细粒度组件Ext对象
 * @return {} 是label返回true
 */
CommonDom.isLabelDom = function(fc) {
	return fc ? (fc.getXType() == 'label' && fc.defaultType && fc.defaultType == 'label') : false;
};

/**
 * fc 是否是textField/textArea/password/compositefield(dataField/filefield/textField)/
 * @param fc 细粒度组件Ext对象
 * @returns boolean
 */
CommonDom.isTextDom = function(fc) {
	return fc ? (fc.getXType() == 'textfield' 
				|| fc.getXType() == 'textarea'
				|| fc.getXType() == 'password' 
				|| (fc.getXType() == 'compositefield' && fc.defaultType && fc.defaultType  == 'textfield')
				|| fc.getXType() == 'spinnerfield') : false;
};

/**
 * fc 是否是htmleditor
 * @param fc 细粒度组件Ext对象
 * @returns boolean
 */
CommonDom.isEditorDom = function(fc) {
	if(!fc)
		return false;
	
	return fc.getXType() == 'htmleditor' || fc.getXType() == 'displayfield';
};

/**
 * fc 是否是hidden
 * @param fc Ext组件对象
 * @return 是compositefield 返回true
 */
CommonDom.isHidden = function(fc) {
	return fc ? (fc.getXType() == 'hidden') : false;
};

/**
 * fc 是否是radio
 * @param fc 细粒度组件Ext对象
 * @returns 是radio 返回true
 */
CommonDom.isRadio = function(fc) {
	return fc ? ((fc.getXType() == 'fieldset' && fc.defaultType && fc.defaultType == 'radio') || fc.getXType() == 'radiogroup') : false;
};

/**
 * fc 是否是checkbox
 * @param fc 细粒度组件Ext对象
 * @returns 是checkbox 返回true
 */
CommonDom.isCheckbox = function(fc) {	
	return fc ? ((fc.getXType() == 'fieldset' && fc.defaultType && fc.defaultType == 'checkbox') || fc.getXType() == 'checkboxgroup') : false;
};

/**
 * fc 是否是select
 * @param fc 细粒度组件Ext对象
 * @returns 是select 返回true
 */
CommonDom.isSelect = function(fc) {
	return fc ? fc.getXType() == 'combo' : false;
};

/**
 * fc 是否是Image
 * @param fc 细粒度组件Ext对象
 * @returns 是image 返回true
 */
CommonDom.isImg = function(fc) {
	return fc ? fc.defaultType == 'image' : false;
};

/**
 * fc 是否是双向选择
 * @param fc 细粒度组件Ext对象
 * @returns 是双向选择 返回true
 */
CommonDom.isOptTrans = function(fc) {
	return fc ? fc.getXType() == 'itemselector' : false;
};

/**
 * fc 是否是日期控件
 * @param fc 细粒度组件Ext对象
 * @returns 是日期控件 返回true
 */
CommonDom.isDate = function(fc) {
	return fc ? (fc.getXType() == 'datefield' || (fc.defaultType && fc.defaultType  == 'datefield')) : false;
};

/**
 * fc 是否是文件上传控件
 * @param fc 细粒度组件Ext对象
 * @returns 是文件上传控件 返回true
 */
CommonDom.isFile = function(fc) {
	return fc ? (fc.getXType() == 'fileuploadfield' || (fc.defaultType && fc.defaultType  == 'filefield')) : false;
};

/**
 * fc 是否是超链接控件
 * @param fc 细粒度组件Ext对象
 * @returns 是超链接控件 返回true
 */
CommonDom.isAnchor = function(fc) {
	return fc ? (fc.type && fc.type  == 'anchor') : false;
};

/**
 * vc 是否是按钮
 * @returns 是按钮 返回true
 */
CommonDom.isButton = function(vc){
	return vc ? vc.getXType() == "button" : false;
};

/**
 * container 是否是form
 * @param container 粗粒度组件Ext对象
 * @returns 是form 返回true
 */
CommonDom.isForm = function(container) {
	return container ? container.getXType() == 'form' : false;
};

/**
 * container 是否是table
 * @param containerDom 粗粒度组件Ext对象
 * @returns 是form 返回true
 */
CommonDom.isTable = function(container) {
	return container ? container.getXType() == 'editorgrid' : false;
};

/**
 * container 是否是tree
 * @param container 粗粒度组件dom
 * @returns 是tree 返回true
 */
CommonDom.isTree = function(container) {
	return container ? container.getXType() == "treepanel" : false;
};

/**
 * xmlDom 是否是page组件生成的dom
 */
CommonDom.isPage = function(xmlDom) {
	return xmlDom ? xmlDom.is(DISLAND.PAGE) : false;
};

/**
 * xmlDom 是否是tab组件生成的dom
 */
CommonDom.isTab = function(container) {
	return container ? container.getXType() == "tabpanel" : false;
};

/**
 * 获取Ext组合控件真实的组件ID
 * @param {} fcCmp
 * @return {}
 */
CommonDom.getFcId = function(cmp){
	var fcId = cmp.getId();
	if(cmp.getXType() == "compositefield")
		fcId = cmp.items.items[0].getId();
	return fcId;
};