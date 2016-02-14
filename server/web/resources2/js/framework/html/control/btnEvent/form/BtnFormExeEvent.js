/**
 * 执行form按钮的事件, extends BtnExeEvent
 * BtnFormExeEvent的handleRequest负责处理表单按钮的除JS,query外的其它方法
 *
 */
function BtnFormExeEvent() {
	BtnFormExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(operateDom.attr(DISLAND.OPERATE_NOTSUBMIT) == 'true')
			this.execute(operateDom);
		else if(this.validate())
			this.execute(operateDom);
	};
}

extend(BtnFormExeEvent, BtnExeEvent);


/**
 * 将Form的值同步到数据岛中
 */
BtnFormExeEvent.prototype.bindData = function(){
	BINDDATA.bindBO(this.boDom, this.method);
	BINDDATA.bindOperate(this.boDom, this.method);
}


