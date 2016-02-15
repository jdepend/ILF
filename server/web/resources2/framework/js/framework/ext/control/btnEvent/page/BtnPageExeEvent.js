/**
 * 执行page按钮的事件, extends BtnExeEvent
 * BtnPageExeEvent的handleRequest负责处理Page按钮的除JS,query(通常page中的按钮不能绑定query方法)外的其它方法
 *
 */
function BtnPageExeEvent() {
	BtnPageExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(operateDom.attr(DISLAND.OPERATE_NOTSUBMIT) == 'true') 
			this.execute(operateDom);
		else if(this.validate())
			this.execute(operateDom);
	}
}

extend(BtnPageExeEvent, BtnExeEvent);

/**
 * 将页面的所有数据同步到数据岛
 */
BtnPageExeEvent.prototype.bindData = function(operateDom){
	BINDDATA.bindPage(operateDom);
};

