function TableHandler(boDom) {
	TableHandler.superclass.constructor.call(this, boDom);

	/**
	 * 根据boDom刷新table的数据.
	 */
	this.freshMyself = function() {

		var table = this.container;
        //console.log(table);
		//移除标题行之外的数据
		var titleRow = table.find('tr:first');
		table.empty();
		table.append(titleRow);

		var columnInfo = this.boDom.children(DISLAND.COLUMNINFO);
		//table的展示列，如果tableFields为空，展示除hidden组件外的所有<qeweb:table>标签中的细粒度组件
		var tableFields = strToArray(columnInfo.attr(DISLAND.COLUMNINFO_TABLEFIELDS), DISLAND.SPLIT_COMMA);
		
		if(!!tableFields)
			this.showTableFields(table, columnInfo, tableFields);
		else
			this.showAllTableFields(table, columnInfo);

		this.showPagination();
	},

	/**
	 * 获取选中行
	 */
	this.getSelections = function() {
		var arr = [];
		var containerId = this.containerDom.attr('id');
		$("input[name='" + this.getSelectModelName(containerId) + "']").each(function(){
			if($(this).is(':checked'))
				arr.push($(this).attr("value"));
		});
		return arr;
	},

	/**
	 * 获取修改行数据
	 */
	this.getModifiedRecords = function() {
		var arr = [];
		return arr;
	},

	/**
	 * 提交修改数据
	 */
	this.commitChanges = function() {

	};

	/**
	 * 装载组件的数据
	 */
	this.reload = function() {
		var pageBar = new pagebar(this);
		pageBar.turn(0);
	};

	/**
	 * 善后工作, 在点击按钮执行行为成功后触发. 表格组件刷新表格, 并清除选中项.
	 */
	this.aftermath = function() {
		this._clearHistory();
		this.reload();
		this._clearThisPage();
	};

	/**
	 * 清空历史选中项
	 */
	this._clearHistory = function() {
		var boListDom = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BOLIST_ID));
		var checkedDom = DISLAND.getCheckedDom(boListDom);
		if(!!checkedDom)
			checkedDom.text("");
	};

	/**
	 * 清空当前页选中项
	 */
	this._clearThisPage = function() {
		//清空当前页选中项
//		var sm = this.container.getSelectionModel();
//		if(!sm)
//			return;
//		sm.clearSelections();
	};
}

extend(TableHandler, ContainerHandler);

/**
 * 展示tableFields指定的列
 */
TableHandler.prototype.showTableFields = function(table, columnInfo, tableFields) {
	var tableHandler = this;

	var containerId = this.container.attr('id');
	//替换数据
	this.boDom.children(DISLAND.BO).each(function(){
//        console.log($(this));
		var newRow = '<tr>';
		newRow += tableHandler.addSM(containerId, columnInfo, $(this).attr(DISLAND.BO_INDEX));
		newRow += tableHandler.addExpendBtn($(this));

		//添加其它行
		$(this).children(DISLAND.BOP).each(function(){
			if(tableFields && !isInArray(tableFields, $(this).attr(DISLAND.BIND)))
				return true;

			var columnVSR = DISLAND.getColumnVSR($(this), columnInfo);
			if(!!columnVSR.status && columnVSR.status.attr(DISLAND.STATUS_HIDDEN) === 'true')
				return true;

			if($(this).attr(DISLAND.BOP_HIGHLIGHT))
				newRow += '<td style=\"' + $(this).attr(DISLAND.BOP_HIGHLIGHT) + '\">';
			else
				newRow += '<td>';

			newRow += columnVSR.value || "&nbsp;";
			newRow += '</td>';
		});
		newRow += '</tr>';
		
		table.append(newRow);

		tableHandler.addListenerOnBtn($(this));
	});
}

/**
 * 添加选择模式
 */
TableHandler.prototype.addSM = function(tableId, columnInfo, value) {
	var result = "";
	if(DISLAND.sm_checkbox(columnInfo)){
		result += '<th>';
		result += '<input type="' + columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL) + '"'
				+ 'name="' + this.getSelectModelName(tableId)
 				+ '" value="' + value + '">'
				+ '</th>';
	}

	return result;
}

/**
 * table选择框名称
 */
TableHandler.prototype.getSelectModelName = function(tableId) {
	return tableId + "_sm";
}

/**
 * 添加行级按钮
 */
TableHandler.prototype.addExpendBtn = function(rowBoDom) {
	var result = "";
	rowBoDom.children(DISLAND.OPERATE).each(function() {
		//行级按钮才添加
		if(!!$(this).attr(DISLAND.OPERATE_IDX))
			result += '<input type="button"'
			 + ' name="' + $(this).attr(DISLAND.OPERATE_ID) + '"'
			 + ' id="' + $(this).attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + $(this).attr(DISLAND.OPERATE_IDX) + '"'
			 + ' value="' + $(this).attr(DISLAND.OPERATE_TEXT) + '">';
	});

	if(!!result)
		result = '<td align="center">' + result + '</td>';

	return result;
}

/**
 * 注册行级按钮
 */
TableHandler.prototype.addListenerOnBtn = function(rowBoDom) {
	rowBoDom.children(DISLAND.OPERATE).each(function() {
		//行级
		if(!!$(this).attr(DISLAND.OPERATE_IDX)){
			var btnHandler = new ButtonHandler($(this));
			btnHandler.init();
		}
	});
}

/**
 * 展示除hidden组件外的所有<qeweb:table>标签中的细粒度组件
 */
TableHandler.prototype.showAllTableFields = function(table, columnInfo) {
	this.showTableFields(table, columnInfo);
}

/**
 * 展示分页信息
 */
TableHandler.prototype.showPagination = function() {
	var pageInation = DISLAND.getPagination(this.boDom);
//	var pageBarDom = this.container.parent().next();
    var pageBarDom = $("#"+this.container.attr("id")+"_pagebar");
	var pageBar = new pagebar(this);
	pageBar.paint(pageBarDom,
			pageInation.total,
			pageInation.start,
			pageInation.limit,
			parseInt(pageInation.start) + this.boDom.children(DISLAND.BO).length);
}

