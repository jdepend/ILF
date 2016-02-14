/**
 * 执行ResultMsg后，职责链将流至PageFlow，
 * PageFlow用于页面流跳转。
 */
function PageFlow(msg) {
	PageFlow.superclass.constructor.call(this);
	this.path = StringToJSON(msg)[ConstantJSON.MSG_PATH];

	/**
	 * 处理页面流跳转
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		if(!!this.path && this.path != 'null'){
			this.doRedirect();
		}
		else {
			this.unDisable();
		}
	}
}

extend(PageFlow, ButtonEvent);

/**
 * 构造一个form，以表单提交的方式提交数据到指定的跳转页面
 * 用于页面流，处理点击按钮后页面跳转
 */
PageFlow.prototype.doRedirect = function() {
	this.redirectAdapter("_self");
};

/**
 * 构造一个form，以表单提交的方式弹出页面
 * 用于页面流，处理点击按钮后页面跳转
 */
PageFlow.prototype.doOpen = function() {
	this.redirectAdapter("_blank");
};

PageFlow.prototype.redirectAdapter = function(targetType) {
		var form = null;
	form = $("<form target= '" + targetType + "'></form>");
	form.attr('action', appConfig.ctx + actionURL.getRedirect(this.path));
	form.attr('method','post');
	var sourceNameField = $("<input type='hidden' name='sourceName' value='"
			+ this.boDom.attr(DISLAND.BIND) + "'/>");
	var btnNameField = $("<input type='hidden' name='btnName' value='"
			+ this.btnName + "'/>");
	var sourcePageField = $("<input type='hidden' name='sourcePage' value='"
			+ DISLAND.getSourcepage() + "'/>");

	if (this.method) {
		var operationField = $("<input type='hidden' name='contextOperate' value='"
				+ this.method + "'/>");
		form.append(operationField);
	}

	var dataIslandField = $("<input type='hidden' name='dataIsland' value='"
			+ xmlToString(this.boDom) + "'/>");
	form.append(sourceNameField);
	form.append(btnNameField);
	form.append(sourcePageField);
	form.append(dataIslandField);
	form.appendTo("body");
	form.css('display', 'none');
	form.submit();
}
