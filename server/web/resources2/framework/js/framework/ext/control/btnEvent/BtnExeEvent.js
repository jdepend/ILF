/**
 * 执行按钮的事件, extends ButtonEvent BtnExeEvent的handleRequest负责处理按钮的行为. 这里是职责链的终点。
 */
function BtnExeEvent() {
	BtnExeEvent.superclass.constructor.call(this);

	/**
	 * 处理请求，执行按钮绑定的操作
	 * 
	 * @param operateDom
	 *            按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		var btnEvent;
		if (CommonDom.isForm(this.containerDom)) {
			if (BoFinalMethod.isQuery(this.method))
				btnEvent = new BtnFormQueryEvent();
			else if (BoFinalMethod.isClear(this.method)
					|| BoFinalMethod.isReset(this.method))
				btnEvent = new BtnFormClearEvent();
			else
				btnEvent = new BtnFormExeEvent();
			btnEvent.handleRequest(operateDom);
		} 
		else if (CommonDom.isTable(this.containerDom)) {
			// table中通常不设置query按钮;
			if (BoFinalMethod.isQuery(this.method)) {
				showMSG.showErr(I18N.ERR_TABLE_QUERY);
			} 
			else if (BoFinalMethod.isClear(this.method)) {
				showMSG.showErr(I18N.ERR_TABLE_CLEAR);
			}
			else if (BoFinalMethod.isReset(this.method)) {
				this.containerDom.getStore().rejectChanges();
			}
			// insert/update/view方法需要弹出对话框, 不在此处处理,参见BtnTablePopuEvent.js
			else if (BoFinalMethod.isInsert(this.method)
					|| BoFinalMethod.isUpdate(this.method)
					|| BoFinalMethod.isView(this.method)) {
				;
			} 
			else {
				btnEvent = new BtnTableExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		} 
		else if (CommonDom.isTree(this.containerDom)) {
			// tree中通常不设置query、clear按钮
			if (BoFinalMethod.isQuery(this.method)) {
				showMSG.showErr(I18N.ERR_TREE_QUERY);
			} 
			else if (BoFinalMethod.isClear(this.method)) {
				showMSG.showErr(I18N.ERR_TREE_CLEAR);
			} 
			else {
				btnEvent = new BtnTreeExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		} 
		else if (CommonDom.isTab(this.containerDom)) {
			if (BoFinalMethod.isQuery(this.method)) {
				showMSG.showErr(I18N.ERR_TAB_QUERY);
			} 
			else {
				btnEvent = new BtnTabExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		}
		else {
			// page中通常不设置query、clear按钮
			if (BoFinalMethod.isQuery(this.method)) {
				showMSG.showErr(I18N.ERR_PAGE_QUERY);
			} 
			else if (BoFinalMethod.isClear(this.method)) {
				showMSG.showErr(I18N.ERR_PAGE_CLEAR);
			} 
			else {
				btnEvent = new BtnPageExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		}
	};

	/**
	 * 校验
	 */
	this.validate = function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler(this.boDom);
		return containerHandler.validate(this.containerDom);
	};
}

extend(BtnExeEvent, ButtonEvent);

/**
 * 将页面数据值同步到数据岛中, 各子类需要覆写该方法
 * 
 * @param operateDom
 *            按钮数据岛
 */
BtnExeEvent.prototype.bindData = function(operateDom) {
};

/**
 * 执行按钮的行为，即通过Ga执行BO的方法
 * 
 * @param operateDom	按钮数据岛
 */
BtnExeEvent.prototype.execute = function(operateDom) {
	// "跳转前执行"方法
	var beforeMethodArr = DISLANDTODOM.getBeforeBoMethod(operateDom);
	// 按钮仅绑定了一个或未绑定"跳转前执行"方法
	if (!beforeMethodArr || beforeMethodArr.length <= 1) {
		this._exeMethod(operateDom);
	}
	// 按钮绑定了多个"跳转前执行"方法
	else {
		this._exeBeforeMethod(operateDom, beforeMethodArr, 0);
	}
};

/**
 * 通过ajax执行按钮的行为
 * @param operateDom
 */
BtnExeEvent.prototype._exeMethod = function(operateDom) {
	var result = true;
	this.bindData(operateDom);
	lockSrceen();
	
	Ext.Ajax.request({
		url : appConfig.ctx + actionURL.getGaExe(),
		method : "POST",
		params : this.getDataStr(),
		timeout : appConfig.ajaxTimeout,
		success : function(response) {
			// 如果窜session，跳转到登录后的第一个页面
			validateSession(response.responseText);

			var msg = response.responseText;
			// 后续操作
			var resultMsg = new ResultMsg(msg);
			var btnJSEventAfterExe = new BtnJSEventAfterExe(operateDom);
			var pageFlow = new PageFlow(msg);

			resultMsg.setNextHandler(btnJSEventAfterExe);
			btnJSEventAfterExe.setNextHandler(pageFlow);
			result = resultMsg.handleRequest(operateDom);
		},
		failure : function(response) {
			result = false;
			showMSG.showErr(I18N.MSG_AJAX_FAILURE);
		}
	});

	return result;
};

/**
 * 当按钮绑定了多个"跳转前执行"方法, 按数序执行"跳转前执行"方法后执行其它方法.
 * @parma operateDom
 * @param beforeMethodArr	"跳转前执行"方法集合
 * @param index				beforeMethodArr的下标
 */
BtnExeEvent.prototype._exeBeforeMethod = function(operateDom, beforeMethodArr, index) {
	if(index == beforeMethodArr.length - 1) {
		//执行最后一个"跳转前执行"方法 和 "跳转后执行"方法
		var afterMethodArr = DISLANDTODOM.getAfterBoMethod(operateDom);
		var afterMedhodStr = afterMethodArr.join(",");
		var methodStr = beforeMethodArr[index];
		if(!!afterMedhodStr) 
			methodStr += "," + afterMedhodStr;
			
		var cloneOptDom = DISLAND.getCloneOptDom(operateDom);
		DISLAND.operate_setBOMethod(cloneOptDom, methodStr);
		var btnEvent = this.getNewEvent(cloneOptDom);
		btnEvent._exeMethod(cloneOptDom);

		return;
	}
	
	// 将按钮的行为赋予新的operateDom
	var cloneOptDom = operateDom.clone();
	// 每个方法的saveMod
	var optSaveModArr = DISLANDTODOM.getOptSaveMod(operateDom);
	//按钮级别的saveMod
	var btnSaveMod = operateDom.attr(DISLAND.OPERATE_SAVEMOD);
		
	// 重置cloneOptDom绑定的方法和saveMod, 令其仅绑定 beforeMethodArr[i]和
	// beforeMethodArr[index]对应的saveMod
	DISLAND.operate_setBOMethod(cloneOptDom, beforeMethodArr[index]);
	DISLAND.operate_setSaveMod(cloneOptDom, optSaveModArr[beforeMethodArr[index]] || btnSaveMod);
		
	var btnEvent = this.getNewEvent(cloneOptDom);
	btnEvent.bindData(cloneOptDom);
	var btnExeEvent = this;
	lockSrceen();
	
	// 执行持久化操作, 不进行页面流操作和后续处理 
	Ext.Ajax.request({
		url : appConfig.ctx + actionURL.getGaExe(),
		method : "POST",
		params : btnEvent.getDataStr(),
		timeout : appConfig.ajaxTimeout,
		async : false,
		success : function(response) {
			// 如果窜session，跳转到登录后的第一个页面
			validateSession(response.responseText);

			var msg = response.responseText;
			// 后续操作
			var resultMsg = new ResultMsg(msg);
			var btnJSEventAfterExe = new BtnJSEventAfterExe(cloneOptDom);
			
			resultMsg.setNextHandler(btnJSEventAfterExe);
			//执行成功后递归调用
			if(resultMsg.handleRequest(cloneOptDom)) {
				btnExeEvent._exeBeforeMethod(operateDom, beforeMethodArr, ++index);
			}
		},
		failure : function(response) {
			showMSG.showErr(I18N.MSG_AJAX_FAILURE);
		}
	});
};


/**
 * 该方法重新生成BtnExeEvent实例, 并用this和operateDom初始化BtnExeEvent.
 * 其子类应该覆写该方法.
 */
BtnExeEvent.prototype.getNewEvent = function(operateDom) {
	return null;
};

