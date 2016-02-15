/**
 * 执行tab按钮的事件, extends BtnExeEvent
 * BtnTabExeEvent的handleRequest负责处理tab按钮的除JS,query外的其它方法
 *
 */
function BtnTabExeEvent() {
	BtnTabExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(this.validate())
			this.execute(operateDom);
	};
}

extend(BtnTabExeEvent, BtnExeEvent);


/**
 * 将tab的值同步到数据岛中
 */
BtnTabExeEvent.prototype.bindData = function(){
	BINDDATA.bindBO(this.boDom, this.method);
	BINDDATA.bindOperate(this.boDom, this.method);
};


