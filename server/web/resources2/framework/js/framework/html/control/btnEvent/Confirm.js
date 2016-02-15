/**
 * 点击持久化按钮时职责链将流经ConfirmEvent， ConfirmEvent用于判断是否需要在执行前弹出提示，即是否弹出confirm框
 */
function ConfirmEvent() {
	ConfirmEvent.superclass.constructor.call(this);

	/**
	 * 处理弹出confirm框
	 * 
	 * @param operateDom
	 *            按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		
		// 全局按钮
		if (!this.containerDom) 
			this.pageOperate(operateDom);
		// 表格级按钮
		else if (CommonDom.isTable(this.containerDom)) 
			this.tableOperate(operateDom);
		// 表单级按钮
		else if (CommonDom.isForm(this.containerDom)) 
			this.formOperate(operateDom);
		else 
			this.confirm(operateDom);
	};
}

extend(ConfirmEvent, ButtonEvent);

/**
 * 本页是否有勾选的记录
 */
ConfirmEvent.prototype._isCheckedOnThisPage = function(container) {
	return true;
};

/**
 * 是否有历史勾选记录
 */
ConfirmEvent.prototype._isCheckedInHistory = function(container, containerId) {
	var boListId = "";
	if(container)
		boListId = container.getId();
	else 
		boListId = containerId;
	
	var checkedDom = DISLAND.getCheckedDom(DISLAND.getBOListDom(boListId));
	if(!checkedDom)
		return true;
	
	return !!checkedDom.text();
};

/**
 * 本页是否有修改的记录
 */
ConfirmEvent.prototype._isModifyOnThisPage = function(container) {
	return false;
};

/**
 * 默认不弹出
 */
ConfirmEvent.prototype.confirm = function(operateDom) {
	var nextHandler = this.nextHandler;
	var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
	var confirmDisplay = pageDom.attr(DISLAND.CONFIRM_DISPLAY);
	var hasConfirm = operateDom.attr(DISLAND.OPERATE_HASCONFIRM);
	var confirmMsg = operateDom.attr(DISLAND.OPERATE_CONFIRMMSG) || I18N.CONFIRM_OPERATE;
	if (DISLAND.CONFIRM_DISPLAY_NO == confirmDisplay || (!hasConfirm && !BoFinalMethod.isDelete(operateDom.attr(DISLAND.OPERATE_METHOD)))) {
		nextHandler.handleRequest(operateDom);
	}
	else{
		showMSG.confirm(confirmMsg,function(){
			displayStatus.submitBtn(true);
			nextHandler.handleRequest(operateDom);
		});
	}
};

ConfirmEvent.prototype.pageOperate = function(operateDom) {
	if (DISLANDTODOM.getSaveMod(operateDom)) {
		//选中模式
		var isSelectMod = DISLANDTODOM.pageBtn_isSelectMod(operateDom);
		//修改模式
		var isModifyMod = DISLANDTODOM.pageBtn_isModifyMod(operateDom);
		//自适应模式
		var isAdaptiveMod = DISLANDTODOM.pageBtn_isAdaptiveMod(operateDom);
		
		//选择
		if (isSelectMod) {
			var containerIds = DISLANDTODOM.getSelectContainerId(operateDom);
			for (var i = 0; i < containerIds.length; i++) {
				if(this._isCheckedInHistory(null, containerIds[i]))
					continue;
				else if(!this._isCheckedOnThisPage(Ext.getCmp(containerIds[i])))
					return;
			}
		}
		// 修改
		else if (isModifyMod) {
			var containerIds = DISLANDTODOM.getModifyContainerId(operateDom);
			for (var i = 0; i < containerIds.length; i++) {
				if(!this._isModifyOnThisPage(Ext.getCmp(containerIds[i])))
					return;
			}
		}		
		//自适应
		else if (isAdaptiveMod) {
			this.confirm(operateDom);
		}
		else {
			return;
		}
	}
	
	this.confirm(operateDom);
};

ConfirmEvent.prototype.tableOperate = function(operateDom) {
	//表格级别按钮
	if (DISLANDTODOM.isTableButton(operateDom)) {
		//选中模式
		var isSelectMod = DISLANDTODOM.btn_isSelectMod(operateDom);
		//修改模式
		var isModifyMod = DISLANDTODOM.btn_isModifyMod(operateDom);
		
		//如果开启了选择记忆模式, 需要判断所有翻页中是否有选中
		if(isSelectMod) {
			if(!this._isCheckedInHistory(this.containerDom) && !this._isCheckedOnThisPage(this.containerDom)) {
				alertMsg.choseRecord(this.containerDom.title);
				return;
			}
		}
		else if (isSelectMod || BoFinalMethod.isDelete(this.method)) {
			if(!this._isCheckedOnThisPage(this.containerDom))
				return;
		} 
		// 新增
		else if (BoFinalMethod.isInsert(this.method)) {
			return;
		} 
		// 修改
		else if (isModifyMod) {
			if(!this._isModifyOnThisPage(this.containerDom))
				return;
		} 
		// 特殊情况（动态新增行添加行或删除行）
		else if (BoFinalMethod.isAddRow(this.method) || BoFinalMethod.isDelRow(this.method)) {
			this.nextHandler.handleRequest(operateDom);
			return;
		}
		else if(BoFinalMethod.isClear(this.method)) {
			return;
		}
		else if(BoFinalMethod.isReset(this.method)) {
			return;
		}
	}
	this.confirm(operateDom);
};

ConfirmEvent.prototype.formOperate = function(operateDom) {
	// 特殊情况（查询或非持久化）
	if (BoFinalMethod.isQuery(this.method))
		this.nextHandler.handleRequest(operateDom);
	else
		this.confirm(operateDom);
};
