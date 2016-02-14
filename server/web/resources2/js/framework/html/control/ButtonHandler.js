/**
 * 按钮处理类, 主要负责为按钮添加事件
 * @param operateDom 按钮数据岛
 */
function ButtonHandler(operateDom) {

	this.init = function() {
		this.addEvent();
	};

	/**
	 * 添加按钮的事件
	 * 职责链流经的顺序：
	 * js方法-->是否弹出选择框-->持久化按钮或查询按钮;
	 * 		持久化操作--(回调函数)-->操作结果对话框-->页面流;
	 * 		查询按钮-->页面流-->(查询按钮的回调函)BO关联;
	 *
	 */
	this.addEvent = function() {
		var btnJsEvent = new BtnJSEvent();
		var expEvent = new BtnFormExportEvent();
		var confirmEvent = new ConfirmEvent();
		var btnRowEditEvent = new BtnSysAddRowEvent();
		var btnExeEvent = new BtnExeEvent();

		btnJsEvent.setNextHandler(expEvent);
		expEvent.setNextHandler(confirmEvent);
		confirmEvent.setNextHandler(btnRowEditEvent);
		btnRowEditEvent.setNextHandler(btnExeEvent);

		var buttonDom = DISLANDTODOM.getButton(operateDom);
		if(buttonDom) {
			buttonDom.bind('click', function(){
				btnJsEvent.handleRequest(operateDom);
			});
		}
	};
};
