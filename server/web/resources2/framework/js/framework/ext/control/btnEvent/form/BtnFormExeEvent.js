/**
 * 执行form按钮的事件, extends BtnExeEvent
 * BtnFormExeEvent的handleRequest负责处理表单按钮的除JS,query外的其它方法
 *
 */
function BtnFormExeEvent() {
	BtnFormExeEvent.superclass.constructor.call(this);

	//table弹出框的类型, 其值的范围是 insert/update/view
	this.panelType = "";

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(operateDom.attr(DISLAND.OPERATE_NOTSUBMIT) == 'true')
			this.execute(operateDom);
		else if(this.validate())
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

extend(BtnFormExeEvent, BtnExeEvent);

/**
 * 设置table弹出框的类型, 在BtnTablePopuEvent中使用
 */
BtnFormExeEvent.prototype.setPanelType = function(panelType) {
	this.panelType = panelType;
};

/**
 * 将Form的值同步到数据岛中
 */
BtnFormExeEvent.prototype.bindData = function(){
	BINDDATA.bindBO(this.boDom, this.method, this.panelType);
	BINDDATA.bindOperate(this.boDom, this.method);
};

