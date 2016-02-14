
//TODO 临时解决方法: 此处定义了全局变量, BtnTableRowEvent.createBtn和TableHelper.renderCell方法使用, 以便解决行级按钮的状态问题
btnRender = false;

/**
 * table的助手类，在翻页前后使用
 */
var TableHelper = {
	/**
	 * pagebar分页操作之前调用， 增加传至后台的参数
	 * @param boListId
	*/
	beforeTurnPage : function(boListId){
		btnRender = true;
		var sorceBOID = DISLAND.getSourceBOID(boListId);
		if(sorceBOID)
			BINDDATA.bindBO(DISLAND.getDom(sorceBOID));

		var tableStore = Ext.getCmp(boListId).getStore();
		var dataParam = xmlToString(DISLAND.getDataIsland());
		tableStore.baseParams.sourceName = sorceBOID;
		tableStore.baseParams.dataIsland = dataParam;
		if(tableStore.lastOptions && tableStore.lastOptions.params)
			tableStore.lastOptions.params.dataIsland = dataParam;
	},

	/**
	 * store装载后设置表格的展示信息，包括枚举型展示值、配色方案、状态的图片代替文字展示
	 */
	formatCell : function(boDom) {
		var boId = boDom.attr(DISLAND.BO_ID) ;
		var columnInfo = boDom.children(DISLAND.COLUMNINFO);

		boDom.children(DISLAND.BO).each(function(rowIndex){
			var curGrid = Ext.getCmp(boId);
			$(this).children(DISLAND.BOP).each(function(){
				var colIndex = curGrid.colModel.findColumnIndex($(this).attr(DISLAND.BIND).replaceAll("\\.", "#"));
				if(colIndex < 0)
					return true;
				var cell = curGrid.getView().getCell(rowIndex, colIndex);
				if(typeof(cell) == 'undefined')
					return true;

				var value = DISLAND.getColumnVSR($(this), columnInfo).value
				if(Ext.isDate(value)){
					var format = DISLAND.getColumnVSR(bop, columnInfo).format;
					value = value.format(format || 'Y-m-d');
				}

				//设置展示值
				$(cell).find('div').text(value);
				//设置表格的展示方案, 具体是设置表格中单元格的颜色、字体
				if($(this).attr(DISLAND.BOP_HIGHLIGHT)) {
					$(cell).find('div').attr("style", $(this).attr(DISLAND.BOP_HIGHLIGHT));
				}
				else{
					$(cell).find('div').attr("style", '');
				}
			});
		});
	},


	/**
	 * 翻页时记住选中行
	 */
	rememberCheckedRow : function(tableId) {
		//获取曾经勾选过的全部记录
	/*	var boListDom = this._getBoListDom(tableId);
		var checkedDom = DISLAND.getCheckedDom(boListDom);
		//仅有SelectionModel==checkbox时才会有checkedDom节点
		if(!checkedDom)
			return;

		//历史选中项
		var checkedInHistory = checkedDom.text().split(',');
		if(!checkedInHistory[0])
			checkedInHistory = [];

		var sm = Ext.getCmp(tableId).getSelectionModel();
		if(!sm.getSelections)
			return;
		var records = sm.getSelections();

		//将当前页选中项并入历史
		this._addCheckedInThisPage(records, checkedInHistory);
		//从历史选中项中移除当页未选中项
		this._removeNotCheckedInHistory(boListDom, records, checkedInHistory);

		if(!!checkedInHistory)
			checkedDom.text(checkedInHistory.valueOf() + '');
		else
			checkedDom.text("");

		DISLAND.updateBO(boListDom);*/
	},

	/**
	 * 将当前页选中项并入历史
	 */
	_addCheckedInThisPage : function(records, checkedInHistory) {
		Ext.each(records, function (record) {
			//如果当前页选中项不在选中历史中, 则将其压入历史记录
			if(!!checkedInHistory && !checkedInHistory.exist(record.data.id))
				checkedInHistory.push(record.data.id);
		});
	},

	/**
	 * 从历史选中项中移除当页未选中项
	 */
	_removeNotCheckedInHistory : function(boListDom, records, checkedInHistory) {
		var checkedInThisPage = [];
		Ext.each(records, function (record) {
			checkedInThisPage.push(record.data.id);
		});

		boListDom.children(DISLAND.BO).each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='id']");
			var id = bop.text();

			//如果当前页的未选中项在历史中, 则从历史中删除该项
			if(!!checkedInThisPage && !checkedInThisPage.exist(id))
				checkedInHistory.remove(id);
			else if(!checkedInThisPage)
				checkedInHistory.remove(id);
		});
	},

	/**
	 * 如果有历史选中项, 自动勾选当前页的记录
	 */
	checkedOnThisPage : function(tableId) {
		//获取曾经勾选过的全部记录
		var boListDom = this._getBoListDom(tableId);
		var checkedDom = DISLAND.getCheckedDom(boListDom);
		//仅有SelectionModel==checkbox时才会有checkedDom节点
		if(!checkedDom)
			return;

		var checkedInHistory = checkedDom.text().split(',');
		if(!checkedInHistory)
			return;

		var sm = Ext.getCmp(tableId).getSelectionModel();
		boListDom.children(DISLAND.BO).each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='id']");
			//如果设置默认选中，则必须在table中设置id属性
			if(bop.length == 0) {
				showMSG.showWaring(I18N.ERR_TABLE_CHECKED);
				return false;
			}

			//自动选中
			if(checkedInHistory.exist(bop.text())) {
				sm.selectRow($(this).attr(DISLAND.BO_INDEX) - 1, true);
			}
		})
	},

	/**
	 * 翻页后设置格式化表格的展示信息
	 */
	formatCellAfterPbar : function(tableId) {
		var boListDom = this._getBoListDom(tableId);
		if(!!boListDom)
			this.formatCell(boListDom);
	},

	/**
	 * 页面跳转后设置格式化表格的展示信息
	 * TODO 临时解决方法，将会引起性能问题
	 */
	renderCell : function(value, meta, record) {
		var index = record.data.index;
		var bindName = this.dataIndex.replace("#", ".");
		var img;
		var display;

		var bolist = DISLAND.getDataIsland().find(DISLAND.BOLIST);
		var columnInfo = bolist.children(DISLAND.COLUMNINFO);
		var bo = bolist.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + index + "']");

		bo.children(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bindName + "']").each(function() {
			var bop = $(this).clone();
			if(Ext.isDate(value)){
				var format = DISLAND.getColumnVSR(bop, columnInfo).format;
				value = value.format(format || 'Y-m-d');
			}
			bop.text(value);

			// 高亮设置
			if ($(this).attr(DISLAND.BOP_HIGHLIGHT) && $(this).text() == value)
				meta.attr = 'style=\"'	+ $(this).attr(DISLAND.BOP_HIGHLIGHT) + '\"';

			display = DISLAND.getColumnVSR(bop, columnInfo).value;
		});

		return img || display || value;
	},

	/**
	 * 设置行级按钮的状态
	 */
	setBtnState : function(tableId, boListDom) {
		var boListDom = boListDom || this._getBoListDom(tableId);
		if(!boListDom)
			return;

		//设置行级按钮的状态
		boListDom.children(DISLAND.BO).each(function() {
			var idx = $(this).attr(DISLAND.BO_INDEX);
			$(this).children(DISLAND.OPERATE).each(function() {
				var rowBtnId = $(this).attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + idx;
				//按钮隐藏
				if($(this).attr(DISLAND.STATUS_HIDDEN) == 'true') {
					$('#' + rowBtnId).hide();
				}
				//按钮只读
				else if($(this).attr(DISLAND.STATUS_DISABLE) == 'true') {
					$('#' + rowBtnId).attr('disabled', true);
					$('#' + rowBtnId).removeAttr('onclick');
				}
			});
		});
	},

	/**
	 * store装载后更新table数据岛
	 */
	updataTableIsland : function(tableId) {
		var boListDom = this._getBoListDom(tableId);
		if(!!boListDom)
			DISLAND.updateBO(boListDom);
	},

	/**
	 * 获取store中更新过的数据岛
	 */
	_getBoListDom : function(tableId) {
		var store = Ext.getCmp(tableId).store;
		var tableIsland = store.reader.jsonData.tableIsland;

		if(!tableIsland)
			return DISLAND.getBOListDom(tableId);
		else
			return XMLDomFactory.getInstance(tableIsland).find(DISLAND.BOLIST);
	},

	/**
	 * 改变序号列宽度
	 * @param pagebar 翻页控件
	 * @param index 序号列所在索引
	 * @param tableId grid的id
	 */
	changeNoWidth : function(pagebar, index, tableId){
		var width = 23; // 默认23px
		var maxNo = pagebar.cursor + pagebar.pageSize + 1;
		var changeWidth = 7 * maxNo.toString().length;
		if(changeWidth < width)
			changeWidth = width;
		var colModel = Ext.getCmp(tableId).colModel;
		colModel.setColumnWidth(index, changeWidth);
	},


	/**
	 * grid翻页后，将表格头部的复选框置为“不选中”
	 * @param tableId  grid的id
	 */
	gridCheckboxFix : function(tableId){
		var dom = Ext.fly(Ext.getCmp(tableId).view.innerHd).query("div.x-grid3-hd-checker-on");
		if (dom)
			Ext.get(dom).removeClass("x-grid3-hd-checker-on");
	}
}