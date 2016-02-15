/**
 * 按钮处理类, 主要负责为按钮添加事件
 * @param operateDom 按钮数据岛
 */
function ButtonHandler(operateDom) {
	
	this.btn = DISLANDTODOM.getButton(operateDom);

	this.init = function() {
		this.addEvent();
	};

	/**
	 * 添加按钮的事件
	 * 职责链流经的顺序：
	 * js方法->表单的导出按钮->弹出confirm提示->表格的动态新行->按钮的operate操作
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

		//对于table来说，仅表格级按钮在此处理，
		//行级按钮在grid.store加载时直接写入click事件,通过(BtnTableExeEvent.js)BtnTableRowEvent.handle注册
		if(this.btn) {
			var handle = function() {btnJsEvent.handleRequest(operateDom);};
			if(this.btn.type == "button")
				this.btn.on('click', handle);
			//行级按钮
			else
				this.btn.onclick = handle;
		}
	};
	
	/**
	 * 改变值/状态/范围
	 */
	this.update = function() {
		this.updateStatus();
	};
	
	
	/**
	 * 改变status
	 */
	this.updateStatus = function(){
		this._setDisabled(operateDom.attr(DISLAND.STATUS_DISABLE));
		this._setHidden(operateDom.attr(DISLAND.STATUS_HIDDEN));
	};

	//_setDisabled
	this._setDisabled = function(isDisabled) {
		if(!!!this.btn)
			return;
		
		if(this.btn.type == "button"){
			if (isDisabled === "true")
				this.btn.setDisabled(true);
			else
				this.btn.setDisabled(false);
		}
	};

	//_setHidden
	this._setHidden = function(isHidden) {
		if(!!!this.btn)
			return;
		if(this.btn.type == "button"){
			if(isHidden === "true")
				this.btn.hide();
			else
				this.btn.show();
		}
	};
};
