
/**
 * table的助手类，在翻页前后使用
 */
var TableHelper = {
	/**
	 * pagebar分页操作之前调用， 增加传至后台的参数
	 * @param boListId
	*/
	beforeTurnPage : function(boListId){
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
	 * store装载后设置表格的配色方案
	 * @param boListDom
	 * @param editParam 	可编辑单元格信息, 在编辑单元格后传入, 包括: 
	 * 		grid - grid本身。This grid 
			record - 正在编辑的record。The record being edited 
			field - 正在编辑的字段名。The field name being edited 
			value - 正在设置的值（value）。The value being set 
			row - grid行索引。The grid row index 
			column - grid列索引。The grid column index 
	 */
	formatCell : function(boListDom, editParam) {
		if(boListDom.children(DISLAND.BOLIST_HEIGHT).length > 0) {
			var curGrid = Ext.getCmp(boListDom.attr(DISLAND.BO_ID));
			boListDom.children(DISLAND.BO).each(function(rowIndex) {
				TableHelper._formatOneRow($(this), curGrid, rowIndex);
			});
		}
	},
	
	/**
	 * 格式化一行展示信息
	 * @param boDom			待格式化行的数据岛信息
	 * @param curGrid
	 * @param rowIndex		待格式化的行号
	 */
	_formatOneRow : function(boDom, curGrid, rowIndex) {
		//高亮显示
		var cm = curGrid.getColumnModel();
		boDom.children(DISLAND.BOP + '[' + DISLAND.BOP_HIGHLIGHT + ']').each(function() {
			var colIndex = cm.findColumnIndex($(this).attr(DISLAND.BIND).replaceAll("\\.", "#"));
			if(colIndex < 0)
				return true;
			
			//设置表格的展示方案, 具体是设置表格中单元格的颜色、字体
			var cell = curGrid.getView().getCell(rowIndex, colIndex);
			$(cell).find('div').attr("style", $(this).attr(DISLAND.BOP_HIGHLIGHT));
		});
	},

	/**
	 * 设置行级按钮的状态
	 * @param tableId
	 */
	setBtnState : function(tableId) {
		var boList = this.getBoListDom(tableId);
		//判断是否有行级按钮
		if(!boList || boList.children(DISLAND.OPERATE + '[' + DISLAND.OPERATE_EXPEND + ']').length == 0)
			return;
		
		//隐藏的行级按钮数量
		var hiddenBtnsCount = 0;
		//带有状态信息的行级按钮
		var statusBtns = boList.children(DISLAND.BO + "[" + DISLAND.BO_ROWOPT_STATUS + "]");
		//设置行级按钮的状态
		statusBtns.each(function() {
			var idx = $(this).attr(DISLAND.BO_INDEX);
			$(this).children(DISLAND.OPERATE + '[' + DISLAND.OPERATE_EXPEND + ']').each(function() {
				var rowBtnId = $(this).attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + idx;
				//按钮隐藏
				if($(this).attr(DISLAND.STATUS_HIDDEN) == 'true') {
					$('#' + rowBtnId).parent().hide();
					hiddenBtnsCount++;
				}
				//按钮只读或不可交互
				else if($(this).attr(DISLAND.STATUS_DISABLE) == 'true' || $(this).attr(DISLAND.STATUS_READONLY) == 'true') {
					$('#' + rowBtnId).attr('disabled', true);
					$('#' + rowBtnId).attr('style', 'color:gray');
					$('#' + rowBtnId).removeAttr('onclick');
				}
			});
		});

		if(appConfig.isTableAutoHideOptCol)
			this.hideOptColumn(tableId, boList, hiddenBtnsCount);
	},

	/**
	 * 隐藏操作列, 如果所有的行级按钮都是隐藏的, 则隐藏操作列.
	 * @param tableId
	 * @param boList
	 * @param hiddenBtnsCount
	 */
	hideOptColumn : function(tableId, boList, hiddenBtnsCount) {
		//全部行级按钮数量
		var btnsCount = 0;
		boList.children(this.BO).each(function() {
			btnsCount += $(this).children(DISLAND.OPERATE).length;
		});
		if(btnsCount == 0)
			return;
		
		//是否隐藏操作列
		var hiddenOptColum = (hiddenBtnsCount == btnsCount);
		var columnModel = Ext.getCmp(tableId).getColumnModel();
		var columns = columnModel.columns;
		for(var i = 0, length = columns.length; i < length; i++) {
			if(columns[i].dataIndex == 'index') {
				columnModel.setHidden(i, hiddenOptColum);
					break;
			}
		}
	},
	
	/**
	 * 计算表格的高度
	 */
	setHeight : function(tableId) {
		var boListDom = this.getBoListDom(tableId);
		if(!boListDom)
			return;
		
		var tableHandler = ContainerHandlerFactory.createContainerHandler(boListDom);
		//页面上的最后一个粗粒度组件
		var lastContainer = itemArray[itemArray.length - 1];
		//静态高度, 表格初始加载时的默认高度
		var staticHeight = tableHandler.container.staticHeight || 0;
		//最大高度, 如果maxHeight不为空, 则自动计算的高度不超过最大maxHeight
		var maxHeight = tableHandler.container.maxHeight || 0;
		
		//如果有静态高度(table标签设定了height), 且taticHeight >= maxHeight, 则不自动计算表格的高度
		if(staticHeight > 0 && staticHeight >= maxHeight) {
			tableHandler.container.setHeight(staticHeight);
			return;
		}
		
		var autoHeight = this.getAutoHeight(boListDom, tableHandler);

		//如果在没有配置粗粒度组件布局的情况下, 最后一个组件是表格, 则表格高度撑满剩余区域
		if(appConfig.isTableHeightFull && !hasConLayout() && lastContainer.getId() == tableId) {
			var tableHeight = Ext.getBody().getHeight() - 12;
			for(var i = 0; i < itemArray.length - 1; i++) {
				//两个组件间的间隔是5
				tableHeight -= (itemArray[i].getHeight() + 5);
			}
			
			//自动计算高度小于staticHeight
			if(staticHeight > 0 && (tableHeight < staticHeight || autoHeight < staticHeight))	
				tableHandler.container.setHeight(staticHeight);
			//自动计算高度超过maxHeight
			else if(maxHeight > 0 && (tableHeight > maxHeight || autoHeight > maxHeight)) 
				tableHandler.container.setHeight(maxHeight);
			else if(tableHeight >= autoHeight) 
				tableHandler.container.setHeight(tableHeight); 
			else if(autoHeight > 0)
				tableHandler.container.setHeight(autoHeight);
		}
		//自动计算高度小于staticHeight
		else if(staticHeight > 0 && autoHeight < staticHeight) {
			tableHandler.container.setHeight(staticHeight);
		}
		//自动计算高度超过最大高度
		else if(maxHeight > 0 && autoHeight > maxHeight) {
			tableHandler.container.setHeight(maxHeight);
		}
		//自动计算高度
		else {
			tableHandler.container.setHeight(autoHeight);
		}
	},
	
	/**
	 * 根据表格行数自动计算表格高度
	 * @param boListDom
	 * @param tableHandler
	 */
	getAutoHeight : function(boListDom, tableHandler) {
		var pageSize = boListDom.children(DISLAND.BO).length;
		return projectStyle.getTableAutoHeight(pageSize, tableHandler.container);
	},
	 
	/**
	 * 翻页时记住选中行
	 * @param tableId
	 */
	rememberCheckedRow : function(tableId) {
		//获取曾经勾选过的全部记录
		var boListDom = this.getBoListDom(tableId);
		var checkedDom = DISLAND.getCheckedDom(boListDom);
		//仅有SelectionModel==checkbox时才会有checkedDom节点
		if(!checkedDom || checkedDom.length == 0)
			return;

		var sm = Ext.getCmp(tableId).getSelectionModel();
		//当前页没有选中项
		if(!sm.getSelections)
			return;
		
		//历史选中项,存储选中的项的ID
		var checkedInHistory = strToArray(checkedDom.attr(DISLAND.BO_ID), ',');
		if(!checkedInHistory)
			checkedInHistory = [];

		var records = sm.getSelections();
		//将当前页选中项并入历史
		this._addCheckedInThisPage(boListDom, checkedDom, records, checkedInHistory);
		//从历史选中项中移除当页未选中项
		this._removeNotCheckedInHistory(boListDom, checkedDom, records, checkedInHistory);

		DISLAND.updateBO(boListDom);
	},

	/**
	 * 将当前页选中项并入历史
	 * @param boListDom
	 * @param checkedDom		历史选中项信息
	 * @param records
	 * @param checkedInHistory	历史选中项数组,存储选中的项的ID
	 */
	_addCheckedInThisPage : function(boListDom, checkedDom, records, checkedInHistory) {
		//本页选中的信息, 这些信息与历史中的数据不重复
		var checkedInThisPageArr = [];
		
		//添加历史记录ID, 已经在历史中的ID不重新添加
		Ext.each(records, function (record) {
			//如果当前页选中项不在选中历史中, 则将其压入历史记录
			if(!!checkedInHistory && !checkedInHistory.exist(record.data.id)) {
				checkedInHistory.push(record.data.id);
				checkedInThisPageArr.push(record.data.id);
			}
		});
		
		if(checkedInHistory.length > 0)
			checkedDom.attr(DISLAND.BO_ID, checkedInHistory.valueOf() + '');
		else
			checkedDom.attr(DISLAND.BO_ID, "");
		
		if(checkedInThisPageArr.length == 0)
			return;
		
		//添加历史记录信息, 已经在历史中的信息不重新添加
		boListDom.children(DISLAND.BO).each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + DISLAND.BOP_ID + "']").slice(0);
			if(checkedInThisPageArr.exist(getXmlNoteText(bop))) {
				//此处需要使用$(this).clone(), 否则, 在append的同时将删除$(this)
				checkedDom.append($(this).clone());
			}
		});
	},

	/**
	 * 从历史选中项中移除当页未选中项
	 * @param boListDom
	 * @param checkedDom		历史选中项信息
	 * @param records
	 * @param checkedInHistory	历史选中项数组,存储选中的项的ID
	 */
	_removeNotCheckedInHistory : function(boListDom, checkedDom, records, checkedInHistory) {
		//本页选中的信息
		var checkedInThisPage = [];
		Ext.each(records, function (record) {
			checkedInThisPage.push(record.data.id);
		});

		//本页取消的信息, 这些信息在历史数据中
		var unCheckedInThisPageArr = [];
		boListDom.children(DISLAND.BO).each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='id']");
			var id = getXmlNoteText(bop);

			//如果当前页的未选中项在历史中, 则从历史中删除该项
			if(!checkedInThisPage.exist(id) && checkedInHistory.exist(id)) {
				checkedInHistory.remove(id);
				unCheckedInThisPageArr.push(id);
			}
		});
		
		if(unCheckedInThisPageArr.length == 0)
			return;
		
		//删除本页取消的信息
		if(checkedInHistory.length > 0) {
			checkedDom.attr(DISLAND.BO_ID, checkedInHistory.valueOf() + '');
			checkedDom.children().each(function() {
				var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + DISLAND.BOP_ID + "']").slice(0);
				if(unCheckedInThisPageArr.exist(bop.text())) {
					$(this).remove();
				}
			});
		}
		//删除全部信息
		else {
			checkedDom.remove();
		}
	},

	/**
	 * 如果有历史选中项, 自动勾选当前页的记录
	 * @param tableId
	 */
	checkedOnThisPage : function(tableId) {
		//获取曾经勾选过的全部记录
		var boListDom = this.getBoListDom(tableId);
		var checkedDom = DISLAND.getCheckedDom(boListDom);
		//仅有SelectionModel==checkbox时才会有checkedDom节点
		if(checkedDom.length == 0)
			return;

		var checkedInHistory = strToArray(checkedDom.attr(DISLAND.BO_ID), ',');
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
		});
	},

	/**
	 * 设置表格的状态及单元格的高亮
	 * @param tableId
	 */
	setTableStatus : function(tableId) {
		var boListDom = this.getBoListDom(tableId);
		if(!!boListDom) {
			if(boListDom.attr(DISLAND.STATUS_HIDDEN) == 'true') {
				var containerHandler = ContainerHandlerFactory.createContainerHandler(boListDom);
				containerHandler.hide();
				return;
			}
			else {
				this.formatCell(boListDom);
			}
		}
	},

	/**
	 * store装载后更新table数据岛
	 * @param tableId
	 */
	updataTableIsland : function(tableId) {
		var boListDom = this.getBoListDom(tableId);
		if(!!boListDom) 
			DISLAND.updateBO(boListDom);
	},

	/**
	 * 获取store中更新过的数据岛
	 */
	getBoListDom : function(tableId) {
		var store = Ext.getCmp(tableId).store;
		var tableIsland = store.reader.jsonData ? store.reader.jsonData.tableIsland : "";

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
		var colModel = Ext.getCmp(tableId).colModel;
		colModel.setColumnWidth(index, projectStyle.getNoWidth(pagebar));
	},


	/**
	 * grid翻页后，将表格头部的复选框置为“不选中”
	 * @param tableId
	 */
	gridCheckboxFix : function(tableId){
		if(!Ext.getCmp(tableId).view || !Ext.getCmp(tableId).view.innerHd)
			return;
		var dom = Ext.fly(Ext.getCmp(tableId).view.innerHd).query("div.x-grid3-hd-checker-on");
		if (dom)
			Ext.get(dom).removeClass("x-grid3-hd-checker-on");
	},

	/**
	 * 单元格编辑前调用，用于：
	 * 1.添加组件关联;
	 * 2.重新加载枚举型范围;
	 * 3.设置单元格的状态
	 * @see ExtTable.java beforeedit 监听中使用
	 * @param {} param
	 * editParam 包含的信息: 
	 * 		grid - grid本身。This grid 
			record - 正在编辑的record。The record being edited 
			field - 正在编辑的字段名。The field name being edited 
			value - 正在设置的值（value）。The value being set 
			row - grid行索引。The grid row index 
			column - grid列索引。The grid column index 
	 * @return {}
	 */
	beforeEdit : function(editParam){
		var bopBind = editParam.field.replaceAll("#", ".");
		var boList = DISLAND.getDom(editParam.grid.id);
		var bo = boList.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + (editParam.row + 1) + "']");
		var bop = bo.children(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bopBind + "']");

		var columnInfo = boList.children(DISLAND.COLUMNINFO);
		var column = columnInfo.children(DISLAND.COLUMNINFO_COLUMN + "[" + DISLAND.BIND + "='" + bopBind + "']");
		var tempBop = DISLAND.createTempBop(columnInfo, column, editParam.row, editParam.grid, bop);

		EditRender.generalRender(boList, tempBop, editParam);
		//展示表格中的附件
		EditRender.fileRender(boList, (editParam.row + 1), bop, column, editParam);
	},

	/**
	 * 可编辑表格编辑后使用.
	 * Grid显示数据的时候有更复杂的情形，与Store不一定对称的话，那么就可以利用afteredit事件来转换数据，达成是一致的数据
	 * @param editParam 包含的信息: 
	 * 		grid - grid本身。This grid 
			record - 正在编辑的record。The record being edited 
			field - 正在编辑的字段名。The field name being edited 
			value - 正在设置的值（value）。The value being set 
			row - grid行索引。The grid row index 
			column - grid列索引。The grid column index 
	 */
	afterEdit : function(editParam) {
		var gridId = editParam.grid.getId();
		//设置行级按钮状态
		TableHelper.setBtnState(gridId);
		
		//将修改后的结果同步导数据
		var boList = DISLAND.getBOListDom(gridId);
		var editBO = boList.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "=" + (editParam.row + 1) + "]");
		var columnInfo = boList.children(DISLAND.COLUMNINFO);
		BINDDATA.bindEditBop(editParam.record.data, editBO, columnInfo);
	},
	
	/**
	 * 格式化修改行的数据(附件控件使用)
	 * @param boListDom
	 * @param editBO		被修改单元格所在行的数据岛
	 * @param editParam
	 */
	formatCellForEditRow : function(boListDom, editBO, editParam) {
		if(boListDom.length == 0 || editBO.length == 0)
			return;

		var curGrid = Ext.getCmp(boListDom.attr(DISLAND.BO_ID));
		//设置单元格的VSR
		this._formatOneRow(editBO, curGrid, editParam.row);
	}
};
