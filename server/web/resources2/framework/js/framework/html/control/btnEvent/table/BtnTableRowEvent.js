/**
 * 行级按钮的处理者
 * @see ExtTable.java
 */
var BtnTableRowEvent = {
	boDom : "",				//弹出修改框的数据岛
	formPanel : "",
	recordWin : "",

	/**
	 * 创建行级按钮
	 * createBtn 返回字符串，供rowBtnRender使用
	 * @see ExtTable.paintRwoBtnRender
	 */
	createBtn : function(btnId, btnName, index, btnText) {
		var id = btnId + DISLAND.SPLIT_LINE + index;
		var result = "&nbsp;&nbsp;<a href='#' id='" + id + "' name='" + btnName + "' " + "idx='" + index + "' "
				+ "onclick='BtnTableRowEvent.handle(this)'>" + btnText + "</a>";

		if(btnRender)
			return result;

		var bolist = DISLAND.getDataIsland().find(DISLAND.BOLIST);
		var bo = bolist.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + index + "']");
		bo.children(DISLAND.OPERATE).each(function(){
			if($(this).attr(DISLAND.OPERATE_ID) != btnId)
				return true;

			//按钮隐藏
			if($(this).attr(DISLAND.STATUS_HIDDEN) == 'true') {
				result = "";
			}
			//按钮只读
			else if($(this).attr(DISLAND.STATUS_DISABLE) == 'true') {
				result = "&nbsp;&nbsp;<a href='#' id='" + id + "' name='" + btnName + "' " + "idx='" + index + "' "
					+ "disabled>" + btnText + "</a>";
			}
		});

		return result;
	},

	/**
	 * 单击行级按钮时触发
	 */
	handle : function(rowBtn) {	
		var btn = $(rowBtn);
		var operateDom = DISLAND.getOperate(btn.attr('name'), btn.attr('idx'));
		if(!operateDom)
			return;

		//按钮绑定update或view时触发
		if(BoFinalMethod.isUpdate(operateDom.attr(DISLAND.OPERATE_METHOD))
				|| BoFinalMethod.isView(operateDom.attr(DISLAND.OPERATE_METHOD))) {
			recordWinIdx = DISLANDTODOM.getRecordWinIdx(operateDom);
			this.formPanel = formPanelArr[recordWinIdx];
			this.recordWin = recordWinArr[recordWinIdx];
			this.popuRecordWin(operateDom);
		}
		else {
			var btnJsEvent = new BtnJSEvent();
			var confirmEvent = new ConfirmEvent();
			var btnExeEvent = new BtnExeEvent();
			confirmEvent.setNextHandler(btnExeEvent);
			btnJsEvent.setNextHandler(confirmEvent);
			btnJsEvent.handleRequest(operateDom);
		}
	},

	/**
	 * table弹出框
	 */
	popuRecordWin : function(operateDom) {
		lockSrceen();
		var tableId = DISLANDTODOM.getContainerId(operateDom);
		this.boDom = formDislandlArr[tableId];
		var data = this.createRecordParam(operateDom);
		var btnTableRowEvent = this;

		//search record
		$.ajax({
			type : "POST",
			url : appConfig.ctx + actionURL.getGaRecord(),
			data : data,
			success : function(data){
				btnTableRowEvent.loadData(data);
				unlockScreen();
			}
		});
	},

	/**
	 * 加载弹出框的数据
	 */
	loadData : function(data) {
		var jsonData = StringToJSON(data).data;
		if(!jsonData) {
			showMSG.showErr(I18N.MSG_GETRECORD_FAILURE);
			return;
		}

		this.recordWin.show(Ext.getBody());

		var boDom = this.boDom;
		this.formPanel.items.each(function(item){
			var bopDom = DISLAND.getFormPanelBOPDom(boDom, item.getId());
			var fcHandler = FCHandlerFactory.createFCHandler(bopDom, item);
			var value = jsonData[bopDom.attr(DISLAND.BIND).replaceAll("\\.", "#")];
			//当时间不希望修改用lable显示时 格式一下Date
			if(value && value.time && !(fcHandler instanceof DateHandler)){
				var date ;
				date = new Date(value.time);
				date.setHours(value.hours);
				date.setMinutes(value.minutes);
				date.setSeconds(value.seconds);
				value = date.pattern("yyyy-MM-dd");
			}
			if(value === 0)
				value = '0';
			fcHandler.setValue(value);
			bopDom.find(DISLAND.BOP_VALUE).text(specialCharHandler(fcHandler.getValue()));
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
			+ param(pageFlow.btnName) + btnEvent.btnName
			+ param(pageFlow.sourcePage) + DISLAND.getSourcepage();
	}
}