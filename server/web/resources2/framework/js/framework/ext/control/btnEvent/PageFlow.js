/**
 * 执行ResultMsg后，职责链将流至PageFlow，
 * PageFlow用于页面流跳转。
 */
function PageFlow(msg) {
	PageFlow.superclass.constructor.call(this);
	var jsonMst = StringToJSON(msg) || new Array();
	this.path = jsonMst[ConstantJSON.MSG_PATH];
	this.isPopUp = jsonMst[ConstantJSON.IS_POPUP];
	this.hasCloseBtn = jsonMst[ConstantJSON.HAS_CLOSEBTN];
	this.dialogWidth = jsonMst[ConstantJSON.DIALOG_WIDTH];
	this.dialogHeight = jsonMst[ConstantJSON.DIALOG_HEIGHT];
	this.dialogTitle = jsonMst[ConstantJSON.DIALOG_TITLE];
	this.dialogIcon = jsonMst[ConstantJSON.DIALOG_ICON];
	this.msg = jsonMst[ConstantJSON.MSG];
	this.msgFlag = jsonMst[ConstantJSON.MSG_FLAG];
	this.closePage = jsonMst[ConstantJSON.CLOSE_PAGE];
	this.targetVC = jsonMst[ConstantJSON.TARGET_VC];
	this.closeTargetVC = jsonMst[ConstantJSON.CLOSE_TARGET_VC];
	this.parentContainerId = jsonMst[ConstantJSON.PAR_CON_ID];

	/**
	 * 处理页面流跳转
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		//页面跳转
		if(!!this.path && this.path != 'null'){
			this.redirect(operateDom);
		}
		//刷新目标组件或关闭弹出窗口
		else if(this.targetVC || this.closePage) {
			this.unDisable();
			this.refreshParentContainer();
		}
		//执行js操作
		else if(operateDom.attr(DISLAND.OPERATE_JS_AFTER) || operateDom.attr(DISLAND.OPERATE_JS_BEFORE)) {
			this.unDisable();
			unlockScreen();
		}
		//不绑定任何操作(摆设型按钮)
		else if(!operateDom.attr(DISLAND.OPERATE_METHOD)){
			this.unDisable();
			unlockScreen();
		}
	};
}

extend(PageFlow, ButtonEvent);

/**
 * 页面跳转
 */
PageFlow.prototype.redirect = function(operateDom) {
	var path = this.path;
	var closePage = this.closePage;
	var paramData = this.getDataStr();
	
	if(this.msgFlag){
		if(!this.msg || this.msg == 'null')
			this.msg = I18N.MSG_OPERATION_SUCCESS;
		paramData = paramData + param(pageFlow.returnMsg) + encodeURIComponent(this.msg);
	}
	
	if(this.isPopUp) {
		unlockScreen();
		dialog.openMDialog(this, paramData);
		//若为行级按钮，将bo operationstatus 还原为init
		if(operateDom.attr(DISLAND.OPERATE_EXPEND) == "true")
			operateDom.parent().attr(DISLAND.BO_OPERATIONSTATUS,DISLAND.BO_OPERATIONSTATUS_INIT);
	}
	else {
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveParam(),
			method : "POST",
			params : paramData,
			timeout : appConfig.ajaxTimeout,
			success : function(response) {
				// 关闭模态框，并设置父页面的页面流
				if(closePage && path){
					parent.window.location.href = appConfig.ctx + actionURL.getRedirect(path);
					parent.Ext.getCmp("qeweb-dialog").close();
					unlockScreen();
				}
				else {
					window.location.href = appConfig.ctx + actionURL.getRedirect(path);
				}
			}
		});
	}
};

/**
 * 刷新指定组件（当前页面或父页面）
 * @param closeTargetVC 对应页面流的closeTargetVC属性, 在点击弹出页面的关闭按钮时会传递该参数.
 * @see dialog.js  openMDialog
 */
PageFlow.prototype.refreshParentContainer = function(closeTargetVC) {
	var targetVC = closeTargetVC || this.targetVC || this.parentContainerId;
	if(!!closeTargetVC) {
		BINDDATA.bindPage();
	}
	
	var param = {};
	param.paramData = this.getDataStr();
	param.closePage = this.closePage;
	param.scope = (param.closePage ? parent.window : window);
	param.scopeDataIsland = xmlToString(param.scope.DISLAND.getDataIsland());
	param.sourceContainerName = DISLANDTODOM.getBoName(this.boDom);
	
	if(targetVC == "empty") {
		//如果在弹出页的页面流中设置targetVC="empty", 则点击按钮关闭弹出页面后, 不刷新父页面组件
		if(param.closePage) {
			closeDialog(param.scope);
		}
		//如果在源面流中设置targetVC="empty", 则不执行任何操作
		else {
			unlockScreen();
			return;
		}
	}
	
	//指定方法刷新指定组件
	var arr = [];
	if(isNotEmpty(targetVC))
		arr = targetVC.split(",");

	freshTargetVC(arr, 0, param);
};

/**
 * 弹出页面关闭时刷新父页面指定组件
 * @param arr
 * @param idx
 * @param param
 */
function freshTargetVC(arr, idx, param) {
	if(!arr || arr.length < 0) {
		return;
	}
	
	//指定方法刷新指定组件
	if(idx < arr.length) { 
		var arr2 = arr[idx].replace('.', '#').split("#");
		var targetVCId = arr2[0];
		var targetVCmethod = (arr2.length > 1 ? arr2[1] : "");

		Ext.Ajax.request({
			//保存上下文信息
			url : appConfig.ctx + actionURL.saveParam(),
			method : "POST",
			//同步操作, 组件需要按顺序刷新
			async : false,
			targetVCId : targetVCId,
			targetVCmethod : targetVCmethod,
			params : param.paramData,
			timeout : appConfig.ajaxTimeout,
			success : function(response, options) {
				//options中包含targetVCId和targetVCmethod, options将存储本次循环的变量
				//如果targetVCId=='page', 表示要刷新父页面的全局按钮
				if(options.targetVCId == 'page') {
					var pageHandler = new param.scope.PageHandler(param.scope.DISLAND.getDataIsland().find(DISLAND.PAGE));
					pageHandler.reload(options.targetVCmethod, param.sourceContainerName, param.scopeDataIsland);
				}
				else {
					var boDom = param.scope.DISLAND.getDom(options.targetVCId);
					var containerHandler = param.scope.ContainerHandlerFactory.createContainerHandler(boDom);
					containerHandler.reload(options.targetVCmethod, param.sourceContainerName, param.scopeDataIsland);
				}
				
				freshTargetVC(arr, idx + 1, param);
			}
		});
	}
	else {
		closeDialog(param.scope);
	}
}

/**
 * 关闭弹出页面
 * @param scope	作用域
 */
function closeDialog(scope) {
	var dialog = scope.Ext.getCmp("qeweb-dialog");
	if(dialog)
		dialog.close();
}

