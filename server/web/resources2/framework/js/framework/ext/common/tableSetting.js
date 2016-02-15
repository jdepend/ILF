
var TableSetting = {
	/**
	 * 当某一列隐藏或“反隐藏”时记忆隐藏状态, 供下次加载页面时使用
	 * @param columnModel
	 * @param columnIndex	Number
	 * @param hidden		boolean		true:隐藏 false:反隐藏
	 */
	saveHiddenChange : function(columnModel, columnIndex, hidden) {
		var hiddenBop = columnModel.columns[columnIndex].dataIndex;
		
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveHiddenChange(),
			method : "POST",
			params : tableSettingParam.sourcePage + DISLAND.getSourcepage() +
				param(tableSettingParam.tableId) + columnModel.gridId +
				param(tableSettingParam.hiddenBop) + hiddenBop + 
				param(tableSettingParam.isHidden) + hidden,
			timeout : appConfig.ajaxTimeout,
			success : function(response, options) {
				//如果窜session，跳转到登录后的第一个页面
				validateSession(response.responseText);
				var resultMsg = new ResultMsg(response.responseText);
				//操作失败
				if(!resultMsg.success) {
					ResultMessage.showMsg(resultMsg.success, I18N.SYS_TABLESETTING_FAILURE, true, false);
				}
			},
			failure : function(response) {
				showMSG.showErr(I18N.MSG_AJAX_FAILURE);
			}
		});
	}, 
	
	/**
	 * 当某一列位置改变时记忆该列位置, 供下次加载页面时使用
	 * @param columnModel
	 * @param oldIndex		Number
	 * @param newIndex		Number
	 */
	saveColMoved : function(columnModel, oldIndex, newIndex) {
		//位置未发生改变或移动的是操作列
		if(oldIndex == newIndex || columnModel.columns[newIndex].dataIndex == 'index')
			return;
		
		//表格展示列的位置
		var position = [];
		for(var i = 0; i < columnModel.columns.length; i++) {
			var dataIndex = columnModel.columns[i].dataIndex;
			if(!!dataIndex) 
				position.push(dataIndex)
		}

		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveColMoved(),
			method : "POST",
			params : tableSettingParam.sourcePage + DISLAND.getSourcepage() +
				param(tableSettingParam.tableId) + columnModel.gridId +
				param(tableSettingParam.position) + position.join(','),
			timeout : appConfig.ajaxTimeout,
			success : function(response, options) {
				//如果窜session，跳转到登录后的第一个页面
				validateSession(response.responseText);
				var resultMsg = new ResultMsg(response.responseText);
				//操作失败
				if(!resultMsg.success) {
					ResultMessage.showMsg(resultMsg.success, I18N.SYS_TABLESETTING_FAILURE, true, false);
				}
			},
			failure : function(response) {
				showMSG.showErr(I18N.MSG_AJAX_FAILURE);
			}
		});
	},

	/**
	 * 当某一列列宽改变时记忆列宽, 供下次加载页面时使用
	 * @param columnIndex	Number
	 * @param newWidth		Number
	 * @param tableId
	 */
	saveWidthChange : function(columnIndex, newWidth, tableId) {
		var columnModel = Ext.getCmp(tableId).getColumnModel();
		
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveWidthChanged(),
			method : "POST",
			params : tableSettingParam.sourcePage + DISLAND.getSourcepage() +
				param(tableSettingParam.tableId) + tableId +
				param(tableSettingParam.changeWidthBop) + columnModel.columns[columnIndex].dataIndex +
				param(tableSettingParam.newWidth) + newWidth,
			timeout : appConfig.ajaxTimeout,
			success : function(response, options) {
				//如果窜session，跳转到登录后的第一个页面
				validateSession(response.responseText);
				var resultMsg = new ResultMsg(response.responseText);
				//操作失败
				if(!resultMsg.success) {
					ResultMessage.showMsg(resultMsg.success, I18N.SYS_TABLESETTING_FAILURE, true, false);
				}
			},
			failure : function(response) {
				showMSG.showErr(I18N.MSG_AJAX_FAILURE);
			}
		});
	}
};
