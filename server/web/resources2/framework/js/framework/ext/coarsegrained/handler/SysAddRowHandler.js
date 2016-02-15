/**
 * talbe动态新增行
 */
function SysAddRowHandler(boDom) {
	SysAddRowHandler.superclass.constructor.call(this, boDom);
}

extend(SysAddRowHandler, TableHandler);


/**
 * 动态新增
 */
SysAddRowHandler.prototype.addRow = function() {
	var store = this.container.store;
	var boList = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));
	//表格中数据的最大序号(bo的index属性)
	var maxIdx = store.getCount() + 1;
	var dataFiels = store.recordType.prototype.fields.keys;
	var boList = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));
	var columnInfo = boList.children(DISLAND.COLUMNINFO);
	var temp = "<" + DISLAND.BO + " " + DISLAND.BO_INDEX + "=\""
			+ maxIdx + "\" " + DISLAND.BO_OPERATIONSTATUS + "=\"" + DISLAND.BO_OPERATIONSTATUS_INIT + "\">";

	var u = new store.recordType();
	u.data = {};
	for (var i = 0; i < dataFiels.length; i++) {
		var value = "";
		var column = columnInfo.find(DISLAND.COLUMNINFO_COLUMN + "[" + DISLAND.BIND + "='" + dataFiels[i] + "']");
		if(column.attr(DISLAND.COLUMN_DEFAULT_VALUE))
			value = column.attr(DISLAND.COLUMN_DEFAULT_VALUE);
		temp += "<" + DISLAND.BOP + " " + DISLAND.BIND + "=\"" + dataFiels[i].replaceAll("#", ".") + "\">" + specialCharHandler(value) + "</" + DISLAND.BOP + ">";
		u.data[dataFiels[i]] = value;
		u.data.index = maxIdx;
	}

	temp += "</" + DISLAND.BO + ">";
	var bo = XMLDomFactory.getInstance(temp).find(DISLAND.BO);
	//添加行级按钮
	var operates = boList.children(DISLAND.OPERATE + "[" + DISLAND.OPERATE_EXPEND + "='true']").clone();
	operates.attr(DISLAND.OPERATE_IDX, maxIdx);
	bo.append(operates);
	boList.append(bo);

	/*
	 * 注:直接使用 
	 * var tableHandler = new TableHandler(boList);
	 * tableHandler.fresh();
	 * 提供了更好的结构, 可不必直接对store进行操作, 且不需手动设置表格的高度, 但在数据量较多时, 将丧失性能.
	 */
	this.container.stopEditing();
	store.insert(store.getCount(), u);
	
	var height = projectStyle.getTableAutoHeight(boList.children(DISLAND.BO).length, this.container);
	this.container.setHeight(height);
};

/**
 * 动态删除(批量)
 */
SysAddRowHandler.prototype.delRow = function() {
	var delRows = this.container.getSelectionModel().getSelections();
    if (delRows.length == 0) {
    	showMSG.showWaring(I18N.ALERT_CHOOSE_RECORD);
        return;
    }
    
    var boList = DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));
    //记录动态删除的记录的id
    var delBoIdArr = new Array();
    //记录动态删除的BO
    var delBoArr = new Array();
    for (var i = 0, length = delRows.length; i < length; i++) {
		var index = delRows[i].data.index;
		
		var delBo = boList.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "=" + index + "]").slice(0);
		delBoArr.push(delBo);
		
		var id = getXmlNoteText(delBo.find(DISLAND.BOP + "[" + DISLAND.BIND + "='id']"));
		if(!!id)
			delBoIdArr.push(id);
	}
    
    //删除数据岛信息
    for(var i = 0, length = delBoArr.length; i < length; i++) {
    	delBoArr[i].remove();
    }
    
    var store = this.container.getStore();
    //从store中删除数据
    store.remove(delRows);
    
    //重置index, 这个操作是必须的, 否则在记录动态删除的BOdelBoArr处会出现错误
    for(var i = 0, length = store.getCount(); i < length; i++) {
    	store.data.items[i].data.index = i + 1;
    }
    
    this.saveDelBoListId(boList, delBoIdArr);
    //重置表格中bo的序号
    DISLAND.resetIndex(boList);
};

/**
 * 用于动态新增行时，保存删除的bolist的id
 */
SysAddRowHandler.prototype.saveDelBoListId = function(boList, delBoIdArr) {
	// 本次删除的Ids
	var newDelBoIds = delBoIdArr.toString();
	//上次删除的Ids
	var oldDelBoIds;
	
	var delBoDom = boList.find(DISLAND.DEL_BO_IDS).slice(0);
	if(delBoDom.length == 0) {
		var temp = "<" + DISLAND.DEL_BO_IDS + ">" + newDelBoIds + "</" + DISLAND.DEL_BO_IDS + ">";
		delBoDom = XMLDomFactory.getInstance(temp).find(DISLAND.DEL_BO_IDS);
		boList.append(delBoDom);
	}
	else {
		oldDelBoIds = getXmlNoteText(delBoDom);
		if(newDelBoIds)
			delBoDom.text(oldDelBoIds + "," + newDelBoIds);
	}
};

/**
 * 表格中的行级js删除, 删除表格中的一行数据
 * @param  btnEvent
 */
function jsDelete(btnEvent) {
	var operateDom = btnEvent.operateDom;
	var boList = btnEvent.boDom;
	var deleteBO = boList.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + operateDom.attr(DISLAND.OPERATE_IDX) + "']");
	var deleteIdx = boList.children(DISLAND.BO).index(deleteBO);

	deleteBO.remove();
	var store = btnEvent.containerDom.store;
	var record = store.getAt(deleteIdx);
	store.remove(record);
	//重置表格中bo的序号
	DISLAND.resetIndex(boList);
}