/**
 * 执行form的清空（sysClear）按钮事件, extends BtnExeEvent
 *
 */
function BtnFormClearEvent() {
	BtnFormClearEvent.superclass.constructor.call(this);
	
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		this.clear();
	}
}

extend(BtnFormClearEvent, BtnFormExeEvent);

/**
 * 执行清空操作，执行clear方法
 */
BtnFormClearEvent.prototype.clear = function() {
	this.containerDom.getForm().reset();
};