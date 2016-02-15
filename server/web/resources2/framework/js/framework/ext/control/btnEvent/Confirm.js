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
	var records = container.getSelectionModel().getSelections();
	if (records.length == 0) {
		alertMsg.choseRecord(container.title);
		return false;
	}
	
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
//	var records = container.getStore().getModifiedRecords();
//	if (records.length == 0) {
//		alertMsg.modify(container.title);
//		return false;
//	}
	return true;
};

/**
 * 弹出confirm提示信息
 */
ConfirmEvent.prototype.confirm = function(operateDom) {
	var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
	var confirmDisplay = pageDom.attr(DISLAND.CONFIRM_DISPLAY);
	var hasConfirm = operateDom.attr(DISLAND.OPERATE_HASCONFIRM);
	var confirmMsg = operateDom.attr(DISLAND.OPERATE_CONFIRMMSG) || I18N.CONFIRM_OPERATE;
	
	if(!this.nextHandler)
		return;
	
	if (DISLAND.CONFIRM_DISPLAY_NO == confirmDisplay || (!hasConfirm && !BoFinalMethod.isDelete(operateDom.attr(DISLAND.OPERATE_METHOD)))) {
		this.nextHandler.handleRequest(operateDom);
	} 
	else {
		Ext.MessageBox.minWidth = showMSG.getMsgWidth(confirmMsg, 280);

		Ext.Msg.confirm(I18N.CONFIRM_TIPS, showMSG.getMsg(confirmMsg),
			function(btn) {
				displayStatus.changeConfirmStatus();
				if (btn == 'yes') {
					this.nextHandler.handleRequest(operateDom);
				}
			}, this);
	}
};

/**
 * 全局按钮的confirm处理
 */
ConfirmEvent.prototype.pageOperate = function(operateDom) {
	if (DISLANDTODOM.getSaveMod(operateDom)) {
		//选中模式
		if (DISLANDTODOM.pageBtn_isSelectMod(operateDom)) {
			var containerIds = DISLANDTODOM.getSelectContainerId(operateDom);
			for (var i = 0; i < containerIds.length; i++) {
				if(this._isCheckedInHistory(null, containerIds[i]))
					continue;
				else if(!this._isCheckedOnThisPage(Ext.getCmp(containerIds[i])))
					return;
			}
		}
		// 修改
		else if (DISLANDTODOM.pageBtn_isModifyMod(operateDom)) {
			var containerIds = DISLANDTODOM.getModifyContainerId(operateDom);
			for (var i = 0; i < containerIds.length; i++) {
				if(!this._isModifyOnThisPage(Ext.getCmp(containerIds[i])))
					return;
			}
		}		
		//自适应 || 全选模式
		else if (DISLANDTODOM.pageBtn_isAdaptiveMod(operateDom)) {
			this.confirm(operateDom);
			return;
		}
		//全选(all)模式
		else if (DISLANDTODOM.btn_isAllMod(operateDom) || DISLANDTODOM.pageBtn_isAllMod(operateDom)) {
			this.confirm(operateDom);
			return;
		}
		//empty模式
		else if (DISLANDTODOM.btn_isEmptyMod(operateDom) || DISLANDTODOM.pageBtn_isEmptyMod(operateDom)) {
			this.confirm(operateDom);
			return;
		}
		//校验全局按钮的saveMod是否正确
		//正确的格式: containerId.saveMod   例: table1.modify,table2.select,table3.empty
		else if(DISLANDTODOM.btn_isSelectMod(operateDom) 
				|| DISLANDTODOM.btn_isAdaptiveMod(operateDom)
				|| DISLANDTODOM.btn_isModifyMod(operateDom)
				|| DISLANDTODOM.btn_isEmptyMod(operateDom)) {
			Ext.Msg.alert(I18N.CONFIRM_TIPS, 
				"全局按钮的saveMod格式错误！<br> 正确的格式：tableId.saveMod   例: table1.modify,table2.select,table3.empty");
			return;
		}
		else {
			return;
		}
	}

	this.confirm(operateDom);
};

/**
 * 表格按钮的confirm处理
 */
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
			showMSG.showErr(I18N.ERR_TABLE_CLEAR);
			return;
		}
		else if(BoFinalMethod.isReset(this.method)) {
			this.containerDom.getStore().rejectChanges();
			return;
		}
	}
	this.confirm(operateDom);
};

/**
 * 表单按钮的confirm处理
 */
ConfirmEvent.prototype.formOperate = function(operateDom) {
	// 特殊情况（查询或非持久化）
	if (BoFinalMethod.isQuery(this.method))
		this.nextHandler.handleRequest(operateDom);
	else
		this.confirm(operateDom);
};
