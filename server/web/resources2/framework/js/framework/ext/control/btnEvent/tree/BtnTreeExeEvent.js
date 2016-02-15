/**
 * 执行tree按钮的事件, extends BtnExeEvent
 * BtnTreeExeEvent的handleRequest负责处理表单按钮的除JS,query外的其它方法
 *
 */
function BtnTreeExeEvent() {
	BtnTreeExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(this.validate())
			this.execute(operateDom);
	};

	/**
	 * 普通的表单提交仅需要传递按钮所在BO的数据岛
	 * submitDataisland供getDataStr()使用, 默认传递整个数据岛
	 */
	this.submitDataisland = function() {
		return param(GA.dataIsland) + paramValue(xmlToString(this.boDom));
	};
}

extend(BtnTreeExeEvent, BtnExeEvent);


/**
 * 将tree的值同步到数据岛中
 */
BtnTreeExeEvent.prototype.bindData = function(){
	BINDDATA.bindBO(this.boDom, this.method);
	BINDDATA.bindOperate(this.boDom, this.method);
};

