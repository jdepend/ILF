/**
 * 执行table按钮的事件, extends BtnExeEvent
 * BtnTableExeEvent的handleRequest负责处理表单按钮的除JS,query外的其它方法
 *
 */
function BtnTableExeEvent() {
	BtnTableExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		this.execute(operateDom);
	}
}

extend(BtnTableExeEvent, BtnExeEvent);


/**
 * 将table的值同步到数据岛中
 */
BtnTableExeEvent.prototype.bindData = function(operateDom){
	BINDDATA.bindBOList(this.boDom, operateDom);
}


