/**
 * 行级按钮的处理者
 * @see CellRender.js
 */
var BtnTableRowEvent = {
	boDom : "",				//弹出修改框的数据岛
	formPanel : "",
	recordWin : "",

	/**
	 * 单击行级按钮时触发
	 * @param rowBtn 行级按钮所在的组件 
	 * @see CellRender.rowBtnRender
	 */
	handle : function(rowBtn) {
		var btn = $(rowBtn);

		var boListId = DISLANDTODOM.getContainerIdByBtnId(btn.attr('name'));
		var bo = DISLAND.getBOListDom(boListId).children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "=" + btn.attr('idx') + "]");
		var operateDom = DISLAND.getRowOperate(bo, btn.attr('name'));

		if(operateDom.length < 0)
			return;

		//按钮绑定update或view时触发
		var isUpdate = BoFinalMethod.isUpdate(operateDom.attr(DISLAND.OPERATE_METHOD));
		if(isUpdate || BoFinalMethod.isView(operateDom.attr(DISLAND.OPERATE_METHOD))) {
			recordWinIdx = DISLANDTODOM.getRecordWinIdx(operateDom);
			this.formPanel = formPanelArr[recordWinIdx];
			this.recordWin = recordWinArr[recordWinIdx];
			this.popuRecordWin(operateDom, isUpdate);
		}
		else {
			var btnJsEvent = new BtnJSEvent();
			var expEvent = new BtnFormExportEvent();
			var confirmEvent = new ConfirmEvent();
			var btnRowEditEvent = new BtnSysAddRowEvent();
			var btnExeEvent = new BtnExeEvent();
			
			btnJsEvent.setNextHandler(expEvent);
			expEvent.setNextHandler(confirmEvent);
			confirmEvent.setNextHandler(btnRowEditEvent);
			btnRowEditEvent.setNextHandler(btnExeEvent);
			
			btnJsEvent.handleRequest(operateDom);
		}
	},

	/**
	 * table弹出框
	 */
	popuRecordWin : function(operateDom, isUpdate) {
		lockSrceen();
		this.boDom = formDislandlArr[DISLANDTODOM.getRecordWinIdx(operateDom)];
		var data = this.createRecordParam(operateDom);
		var btnTableRowEvent = this;

		//search record
		$.ajax({
			type : "POST",
			url : appConfig.ctx + actionURL.getGaRecord(),
			data : data,
			success : function(data){
				btnTableRowEvent.loadData(data, isUpdate);
				unlockScreen();
			}
		});
	},

	/**
	 * 加载弹出框的数据
	 */
	loadData : function(data, isUpdate) {
		if(!data) {
			showMSG.showWaring("该数据对应的ID在数据库中不存在");
			return;
		}
			
		var jsonData = StringToJSON(data).data;
		if(!jsonData) {
			showMSG.showErr(I18N.MSG_GETRECORD_FAILURE);
			return;
		}

		try {
			this.recordWin.show(Ext.getBody());
		}
		catch(e) {
			showMSG.showErr('table标签没有配置弹出框，请检查display属性，其格式是：field1=table:edit,insert,update,view;field2=insert');
			return;
		}

		var boDom = this.boDom;
		this.formPanel.items.each(function(item){
			var bopDom = DISLAND.getFormPanelBOPDom(boDom, CommonDom.getFcId(item));
			if(bopDom.length == 0)
				return true;
			var fcHandler = FCHandlerFactory.createFCHandler(bopDom, item);
			var bopBind = bopDom.attr(DISLAND.BIND).replaceAll("\\.", "#");
			var value = jsonData[bopBind];
			var disable = jsonData[bopBind + '-disable'];
			var hidden = jsonData[bopBind + '-hidden'];
			var readonly = jsonData[bopBind + '-readonly'];
			//当时间用lable显示时 格式一下Date
			if(value && value.time && (fcHandler instanceof DateHandler)){
				var date ;
				date = new Date(value.time);
				date.setHours(value.hours);
				date.setMinutes(value.minutes);
				date.setSeconds(value.seconds);
				value = date.pattern("yyyy-MM-dd");
			}
			else if(value === 0) {
				value = '0';
			}

			fcHandler.setValue(value);
			if(isUpdate)
				fcHandler.updateStatus(disable, hidden, readonly);
			bopDom.find(DISLAND.BOP_VALUE).text(specialCharHandler(value));			
		});
	},

	/**
	 * Ga.getRecord的参数
	 */
	createRecordParam : function(operateDom) {
		var btnEvent = new ButtonEvent();
		btnEvent.init(operateDom);
		return pageFlow.sourceName + btnEvent.boDom.attr(DISLAND.BIND)
			+ param(GA.recordId) + DISLANDTODOM.getRecordId(operateDom)
			+ param(GA.operation) + BoFinalMethod.RECORD
			+ param(GA.winType) + btnEvent.method
			+ param(pageFlow.btnName) + btnEvent.btnName
			+ param(pageFlow.sourcePage) + DISLAND.getSourcepage();
	}
};