/**
 * 执行table按钮的事件, extends BtnExeEvent
 * BtnSysAddRowEvent的handleRequest负责处理表格的动态新增行
 *
 */
function BtnSysAddRowEvent() {
	BtnSysAddRowEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(BoFinalMethod.isAddRow(this.method) || BoFinalMethod.isDelRow(this.method))
			this.execute();
		else 
			this.nextHandler.handleRequest(operateDom);
	}
}

extend(BtnSysAddRowEvent, BtnExeEvent);


/**
 * 将table的值同步到数据岛中
 */
BtnSysAddRowEvent.prototype.bindData = function(operateDom){
	BINDDATA.bindBOList(this.boDom, operateDom);
}

/**
 * 执行按钮的行为，新增行和删除行
 */
BtnSysAddRowEvent.prototype.execute = function(){
	var handler = new SysAddRowHandler(this.boDom);
	if(BoFinalMethod.isAddRow(this.method)) {
		handler.addRow();
	} 
	else {
		handler.delRow();
	}
}
