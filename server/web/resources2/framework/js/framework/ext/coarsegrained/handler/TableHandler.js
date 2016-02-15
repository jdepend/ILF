function TableHandler(boDom) {
	TableHandler.superclass.constructor.call(this, boDom);
	//table行点击
	this._ROW_EVENT = "rowdblclick";

	/**
	 * 根据boDom刷新table的数据.
	 */
	this.freshMyself = function() {
		this.showTableFields();
		this._clearHistory();
		this._clearThisPage();
		
		//此处供排序使用, 列排序时需要将store中的参数dataIsland传递至后台
		if(this.container) {
			var tableStore = this.container.getStore();
			tableStore.baseParams.dataIsland = xmlToString(DISLAND.getDataIsland());
		}
	};

	/**
	 * 粗粒度组件校验
	 */
	this.validate = function() {
		var vRecords = this.container.getStore().data.items; // 获取需要校验的数据数据
		return this.container.isValidate(vRecords);
	};
		
	/**
	 * 当item是bo.bop时，需要将.替换成#,以便ext正确识别
	 */
	this.changeTOExtItemName = function(itemName) {
		return itemName.replaceAll("\\.", "#");
	};

	/**
	 * 装载组件的数据
	 * @param method				补齐父类reload方法中的参数, 未使用
	 * @param sourceContainerName	补齐父类reload方法中的参数, 未使用
	 * @param scopeDataIsland		补齐父类reload方法中的参数, 未使用
	 * @param freshTargetVC  @see PageFlow.js  freshTargetVC
	 */
	this.reload = function(method, sourceContainerName, scopeDataIsland, freshTargetVC) {
		TableHelper.beforeTurnPage(this.container.getId());
		lockSrceen();
		this.container.getStore().reload();
		
		//递归调用, 所有组件按顺序刷新, 刷新后关闭页面
		if(typeof freshTargetVC === 'function')
			freshTargetVC();
	};

	/**
	 * 添加table粗粒度关联
	 */
	this._addRelation = function() {
		if(this.relations && this.relations.length > 0){
			var tableHandler = this;
			this.container.addListener(this._ROW_EVENT, function(grid, rowIndex, e) {
				tableHandler.addBOT(rowIndex + 1);
				observableArr[tableHandler.boDom.attr(DISLAND.BO_ID)].notify();
			});
		}
	};

	/**
	 * 善后工作, 在点击按钮执行行为成功后触发. 刷新表单, 并清除选中项.
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
		DISLAND.clearCheckedDom(boListDom);
	},

	/**
	 * 清空当前页选中项
	 */
	this._clearThisPage = function() {
		//清空当前页选中项
		var sm = this.container.getSelectionModel();
		if(!sm || (sm.getSelections && sm.getSelections().length == 0))
			return;
		sm.clearSelections();
	},

	/**
	 * 获取选中行
	 */
	this.getSelections = function() {
		var arr = [];
		var records = this.container.getSelectionModel().getSelections();
		Ext.each(records, function (record) {
			arr.push(record.get(DISLAND.BO_INDEX) + '');
		});
		return arr;
	},

	/**
	 * 获取修改行数据
	 */
	this.getModifiedRecords = function() {
		return this.container.getStore().getModifiedRecords();
	},

	/**
	 * 提交修改数据
	 */
	this.commitChanges = function() {
		this.container.getStore().commitChanges();
	};
	
	/**
	 * 添加bot, 作为其关联粗粒度组件的查询条件
	 * @param {} rowNum
	 */
	this.addBOT = function(rowNum){
		
		var boList = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));
		var bot = boList.find(DISLAND.BOLIST_BOT);
		bot.remove();
		
		//在触发关联时重新创建bot
		var newBot = XMLDomFactory.getInstance('<' + DISLAND.BOLIST_BOT + '></' + DISLAND.BOLIST_BOT + '>').find(DISLAND.BOLIST_BOT);
		var bo = boList.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + rowNum + "']").clone();
		newBot.append(bo);
		boList.append(newBot);
	};
}

extend(TableHandler, ContainerHandler);

/**
 * 展示tableFields指定的列
 */
TableHandler.prototype.showTableFields = function() {
	//替换数据
	var tableStore = "{" + this.showPageBar() + ",'";
	var changeTOExtItemName = this.changeTOExtItemName;

	var newRow = '';
	this.boDom.children(DISLAND.BO).each(function(){
		newRow += "{'" + DISLAND.BO_INDEX + "':" + $(this).attr(DISLAND.BO_INDEX) + ",";

		$(this).children(DISLAND.BOP).each(function(){
			var text = getXmlNoteText($(this));
			if(isEmpty(text))
				text = '';
			else
				text = decodeToValue(getUnescapedText(text));
			
			var enumRange = $(this).attr(DISLAND.BOP_RANGE_ENUM);
			if(enumRange && text) {
				enumRange = decodeToValue(getUnescapedText(enumRange));
				var items =  eval('(' + enumRange + ')');
				for(var i = 0; i < items.length; i++){
					if(items[i].k == text) {
						text = items[i].v; 
						break;
					}
				}
			}

			newRow += "'" + changeTOExtItemName($(this).attr(DISLAND.BIND)) + "':'" + text + "',";
		});
		newRow = removeEnd(newRow);
		newRow += "},";
	});

	tableStore += ConstantJSON.PAGEBAR_ROOT + "':[" + removeEnd(newRow) + "]}";
	this.container.getStore().loadData(StringToJSON(tableStore), false);
};

TableHandler.prototype.showPageBar = function(){
	var pageInation = DISLAND.getPagination(this.boDom);
	return "'limit':" + (pageInation.limit || 0)
		+ ",'total':" + (pageInation.total || 0)
		+ ",'start':" + (pageInation.start || 0);
};

/**
 * 处理粗粒度组件的关联，负责更新被关联的粗粒度组件
 * @param {} data
 */
TableHandler.prototype.relationHandle = function(data) {
	if(!data)
		return;

	//查询结果数据岛（后台将把查询结果生成数据岛）
	var newDataIsland = XMLDomFactory.getInstance(data.responseText).find(DISLAND.dataIsland);

	//重置table选中行bo operationstatus状态
	var boList = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));
	boList.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS, DISLAND.BO_OPERATIONSTATUS_INIT);

	//与containerDom关联的bo
	var relations = this.relations;
	//此处遍历所有粗粒度组件,找出与table关联的table，并更新其数据
	for(var i in relations) {
		var targetTable = newDataIsland.find(DISLAND.BOLIST + "[" + DISLAND.BOLIST_ID + "='" + i + "']" ).slice(0);
		targetContainer = ContainerHandlerFactory.createContainerHandler(targetTable);
		targetContainer.fresh();
	}
};

/**
 * 表格中是否有单元格被修改过
 * @see ExtTable.java
 * @return 是否放弃翻页, false: 继续翻页, true: 放弃翻页.
 */
TableHandler.prototype.isEdit = function(boListId){
	if(!this.container)
		this.container = Ext.getCmp(boListId);
	
	//没有修改任何数据, 直接返回false
	if(this.getModifiedRecords() == 0)
		return false;
	
	var result = false;
	unlockScreen();
	
	if(confirm(I18N.CONFIRM_TABLE_EDIT)) {
		//放弃翻页
		result = true;
	}
	else {
		lockSrceen();
		this.commitChanges();
	}
	
	return result;
};


/**
 * 回滚所有editTable的修改
 */
TableHandler.prototype.clearEdit = function(boListId){
	if(!this.container)
		this.container = Ext.getCmp(boListId);
	this.container.getStore().rejectChanges();			
};
