
/**
 * doSelect处理选择回填, 将弹出页面的选中数据回填到父页面. 当点击弹出页面的选择按钮时会触发该方法.
 * @param container	弹出页面的粗粒度组件
 */
function doSelect(container) {
	var boListStr = "";
	//是否在表格中勾选的记录
	var alreadyChecked = false;
	
	//树
	if(CommonDom.isTree(container)) {
		DISLAND.getDataIsland().children(DISLAND.TREE).each(function() {
			var bind = $(this).attr(DISLAND.BIND);
			boListStr += "<" + DISLAND.BOLIST + " " + DISLAND.BOLIST_ID + "='' " + DISLAND.BIND + "='" + bind + "'>";
			boListStr += treeDataStr(container.getRootNode(), bind);
			boListStr += "</" + DISLAND.BOLIST + ">";
		});
	}
	//表格
	else {
		TableHelper.rememberCheckedRow(container.getId());
		var boList = DISLAND.getDataIsland().children(DISLAND.BOLIST).slice(0);
		//弹出页面是否开启翻页记忆功能
		var checkedDom = DISLAND.getCheckedDom(boList);
		var isRememberChecked = checkedDom && checkedDom.length > 0;
		
		//将弹出页面表格中的所有选中项的index存入idxArr中
		var records = container.getSelectionModel().getSelections();
		var idxArr = [];
		if(!isRememberChecked) {
			for (var i = 0, length = records.length; i < length; i++) {
				idxArr.push(records[i].get(DISLAND.BO_INDEX) + '');
			}
		}

		//如果未开启翻页记忆功能,将当前页选中的数据拼接成结果集; 否则将历史信息拼接成结果集
		boListStr += "<" + DISLAND.BOLIST + " " + DISLAND.BOLIST_ID + "='' " + DISLAND.BIND + "='" + $(this).attr(DISLAND.BIND) + "'>";
		//弹出页面未开启翻页记忆功能
		if(!isRememberChecked) {
			//遍历弹出框页面表格中数据岛, 如果记录的index能够对应在idxArr中找到对应, 表示该项是被选中的, 将该项的数据岛拼接到boListStr
			boList.children(DISLAND.BO).each(function() {
				alreadyChecked = true;
				if(idxArr.exist($(this).attr(DISLAND.BO_INDEX))) {
					boListStr += xmlToString($(this));
				}
			});
		}
		//弹出页面开启了翻页记忆功能
		else {
			checkedDom.children(DISLAND.BO).each(function() {
				alreadyChecked = true;
				boListStr += xmlToString($(this));
			});
		}
		boListStr += "</" + DISLAND.BOLIST + ">";
	}
	boListStr = boListStr.replaceAll("<!--,-->", "<!,>");

	//弹出页面所在的窗口, 该窗口是一个模态对话框
	var win = parent.Ext.getCmp("qeweb-dialog");
	if(win){
		//填充表格
		if("M" == win.dialogType) {
			if(alreadyChecked) {
				dialog.fillbackTable(win.sbopIds, win.tbopIds, boListStr, win.echo);
				win.close();
			}
			else {
				showMSG.showWaring(I18N.ALERT_CHOOSE_RECORD);
			}
		}
		//填充表单中的细粒度组件
		else if("S" == win.dialogType) {
			dialog.fillbackFC(win.sbopIds, win.tbopIds, boListStr, win.sysOperate);
			win.close();
		}
	}
}

/**
 * 将选中的树节点拼装成数据岛
 * @param root	树的根节点
 * @param bind	树绑定的bo
 * @returns {String}
 */
function treeDataStr(root, bind){
	var boStr = "";
	if (!root.hasChildNodes())
		return boStr;

	root.eachChild(function(child) {
		if(child.ui.checkbox && child.ui.checkbox.checked){
			//树结构的节点只有id和value两个信息, 此处将选中的节点信息拼装成数据岛
			boStr += "<" + DISLAND.BO + " " + DISLAND.BIND + "='" + bind + "'>";
			
			boStr += "<" + DISLAND.BOP + " " + DISLAND.BIND + "='" +DISLAND.BOP_ID + "'>";
			boStr += "<" + DISLAND.BOP_VALUE + ">";
			boStr += specialCharHandler(child.id);
			boStr += "</" + DISLAND.BOP_VALUE + ">";
			boStr += "</" + DISLAND.BOP + ">";
			
			boStr += "<" + DISLAND.BOP + " " + DISLAND.BIND + "='" +DISLAND.BOP_VALUE + "'>";
			boStr += "<" + DISLAND.BOP_VALUE + ">";
			boStr += specialCharHandler(child.text);
			boStr += "</" + DISLAND.BOP_VALUE + ">";
			boStr += "</" + DISLAND.BOP + ">";
			
			boStr += "</" + DISLAND.BO + ">";
		}
		boStr += treeDataStr(child, bind);
	});

	return boStr;
}