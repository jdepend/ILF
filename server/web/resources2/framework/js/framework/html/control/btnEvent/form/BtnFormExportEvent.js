/**
 * 执行form按钮的事件, extends BtnExeEvent BtnFormExportEvent的handleRequest负责处理表单的导出方法
 * 
 */
function BtnFormExportEvent() {
	BtnFormExportEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if (BoFinalMethod.isExport(this.method) && this.validate()) 
			this.execute(operateDom);
		else 
			this.nextHandler.handleRequest(operateDom);
	};

	/**
	 * 普通的表单提交仅需要传递按钮所在BO的数据岛 submitDataisland供getDataStr()使用, 默认传递整个数据岛
	 */
	this.submitDataisland = function() {
		return param(GA.dataIsland) + paramValue(xmlToString(this.boDom));
	};
}

extend(BtnFormExportEvent, BtnFormExeEvent);

/**
 * 执行按钮的行为，即通过Ga执行BO的方法
 * 
 * @param operateDom
 *            按钮数据岛
 */
BtnFormExportEvent.prototype.execute = function(operateDom) {
	this.bindData();
	var sourceName = this.containerDom[0] ? this.containerDom[0].name
			: this.boDom.attr(DISLAND.BIND);
	var url = appConfig.ctx + actionURL.doExport() + param(pageFlow.sourceName)
			+ sourceName + param(GA.operation) + this.method
			+ param(pageFlow.btnName) + this.btnName
			+ param(pageFlow.sourcePage) + DISLAND.getSourcepage()
			+ param(pageFlow.contextOperate) + this.method
			+ param(GA.relations)
			+ arrayToStr(DISLAND.getRelationBo(this.boDom));

	var formA = document.getElementById("exportForm");
	var dataIsland = document.getElementById('dataIsland');
	var paramVal = paramValue(xmlToString(getDataIslandByJQ()));
	if (formA) {
		document.body.removeChild(formA);
	}
	formA = document.createElement('form');
	dataIsland = document.createElement('input');
	formA.setAttribute("id", "exportForm");
	formA.setAttribute("method", "post");
	formA.setAttribute("action", url);
	dataIsland.setAttribute("value", paramVal);
	dataIsland.setAttribute("id", "dataIsland");
	dataIsland.setAttribute("name", "dataIsland");
	dataIsland.setAttribute("type", "hidden");
	formA.appendChild(dataIsland);
	document.body.appendChild(formA);

	formA.submit();
};