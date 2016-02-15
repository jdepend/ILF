/**
 * 表格中可编辑单元格在编辑状态下的render
 */
var EditRender = {
    /**
	 * 初始化表格中的可编辑组件
	 * @param {} bolist
	 * @param {} bop
	 */
	generalRender : function(bolist, bop, editParam){
		var fcId = DISLANDTODOM.editFcId(bolist, bop);
		bop.parent().attr(DISLAND.BO_ID, bolist.attr(DISLAND.BO_ID));
		bop.parent().attr(DISLAND.BIND, bolist.attr(DISLAND.BIND));
		
		var type = bop.attr(DISLAND.COLUMN_TYPE);
		if(type == "file")
			fcId = DISLANDTODOM.getFileUploadFCId(fcId);
		
		var fc = Ext.getCmp(fcId);
		if(fc) {
			fc.tblIndex = bop.parent().attr(DISLAND.BO_INDEX);
			var fcHandler = FCHandlerFactory.createFCHandler(bop, fc);
			fcHandler.init();
			fcHandler.update();
			
			//日期控件第一次点击时需要特殊处理
			if(fcHandler instanceof DateHandler) {
				var value = decodeToDisplay(editParam.value);
				if(/\d+\s00:00:00$/.test(value)) {
					value = value.replace(/\s00:00:00$/, "");
					//设置可表格中编辑组件的展示值
					editParam.record.data[bop.attr(DISLAND.BIND)] = Date.parseDate(value, 'Y-m-d'); 
				}
			}
		}
	},
	
	/**
	 * 展示表格中的附件
	 * @param bolist
	 * @param boIndex
	 * @param bop
	 * @param column
	 * @param editParam
	 * @return {Boolean}
	 */
	fileRender : function(bolist, boIndex, bop, column, editParam){
		if(column.attr(DISLAND.COLUMN_TYPE) != "file")
			return;
		
		//附件控件
		var fileFieldId = DISLANDTODOM.editFcId(bolist, bop);
		var cellFc = Ext.getCmp(DISLANDTODOM.getFileUploadFCId(fileFieldId));
		cellFc.boIndex = boIndex;

		var winId = DISLANDTODOM.getFileCellWin(fileFieldId);
		var formId = cellFc.getId() + "-filwWinForm";
		
		var winForm = Ext.getCmp(formId) || new Ext.FormPanel({
			id : formId,
			bodyStyle : {padding : '20'},
			border : false,
			labelAlign : 'right',
			labelWidth : 120,
			layout : 'fit',
			items : [cellFc]
		});
		
		var win = Ext.getCmp(winId) || new Ext.Window({
			id : winId,
			title : cellFc.fieldLabel,
			width : '400',
			height : '150',
			modal : true,
			layout : 'fit',
			closeAction : 'hide',
			maximizable : true,
			collapsible : true,
			constrain : true,
			items : [winForm],
			buttons : [ {
				text : I18N.DIALOG_YES,
				iconCls : 'save',
				height : 25,
				handler : function() {
					observableArr[winId].notify();
				}
			}, {
				text : I18N.DIALOG_CLOSE,
				iconCls : 'noIcon',
				height : 25,
				handler : function() {
					win.hide();
				}
			} ]
		});
		
		winForm.insert(0, cellFc);
		win.show(Ext.getBody(), function() {
			var file_ID = DISLANDTODOM.getFileID(fileFieldId);
			var $fileArray = jQuery("#" + winId).find("#" + file_ID + ":gt(0)");
			if ($fileArray.length > 0) {
				$fileArray.each(function(i, n) {
					$(n).parent()[0].removeChild(n);
				});
			}
		});
		
		var anchor = Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));
		if(!anchor)
			return true;
		if(bop.attr(DISLAND.BOP_DATA_URL)) {
			var fileName = getXmlNoteText(bop);
			var anchorStr = "<a href='" + bop.attr(DISLAND.BOP_DATA_URL) + "' target='_blank' title='" + fileName + "'>" + fileName + "</a>";
			anchor.setText(anchorStr, false);
			Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(bop.text());
		}
		else {
			anchor.setText("", false);
			Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue("");
		}
		
		observableArr[winId] = new CellEditObservable(win);
		var observer = new CellEditObserver();
		observer.tableId = bolist.attr(DISLAND.BOLIST_ID);
		observer.boIndex = boIndex;
		observer.bopBind = column.attr(DISLAND.BIND);
		observer.editParam = editParam;
		observableArr[winId].addObserver(observer);
	}
};
