/**
 * 同步数据操作，当提交页面时将展示元素的数据数据岛同步中。
 */
var BINDDATA = {
	/**
	 * 同步表单数据
	 * @param boDom
	 * @param method 按钮方法改变bo状态机
	 * @param panelType   table弹出框的类型, 非必填, 其值的范围是 insert/update/view
	 */
	bindBO : function(boDom, method, panelType) {
		DISLAND.clearPageOptStatus();
		boDom.children(DISLAND.BOP).each(function(){
			var bop = $(this);
			var fc = "";
			//为了弹出框的数据正确性，每种弹出框各有一个小型数据岛，这些数据岛存放在formDislandlArr数组中
			if(!!panelType) {
				var fcId = DISLANDTODOM.getWinFCId(bop, panelType);
				fc = Ext.getCmp(fcId);
			}

			var fcHandler = FCHandlerFactory.createFCHandler(bop, fc);
			//标签页时, 如果没有初始化到某个标签页的内容, fcHandler可能为空
			if(!fcHandler)
				return true;

			var bopValue = trim(fcHandler.getValue());

			//如果fc不存在并且bopValue值为空，则去数据岛里找出对应的数据填充
			if(!fcHandler.fc && !bopValue) {
				bopValue = DISLAND.getValue(bop);
			}
			
			var values = bop.children(DISLAND.BOP_VALUE);
			if(values.length == 1) {
				values.text(specialCharHandler(bopValue));
			}
			//细粒度组件添加了<qeweb:expend>扩展，测试将形成min和max两个<value>，常用于日期段条件
			else {
				var min = bopValue.split(DISLAND.SPLIT_COMMA)[0];
				var max = bopValue.split(DISLAND.SPLIT_COMMA)[1];
				values.slice(0, 1).text(min);
				values.slice(1, 2).text(max);
			}
		});
		if(!!method){
			boDom.attr(DISLAND.BO_OPERATIONSTATUS, method);
		}
	},

	/**
	 * 同步整个页面的数据
	 */
	bindPage : function(operateDom) {
		DISLAND.clearPageOptStatus();
		operateDom = $(operateDom);
		//empty模式, 不绑定任何数据
		if (DISLANDTODOM.btn_isEmptyMod(operateDom) || DISLANDTODOM.pageBtn_isEmptyMod(operateDom))
			return;
		
		var optStatus = DISLAND.operate_BOMethod(operateDom);
		DISLAND.getDataIsland().children().each(function() {
			if($(this).is(DISLAND.BO)) {
				BINDDATA.bindBO($(this), optStatus);
			}
			else if($(this).is(DISLAND.PAGE)) {
				BINDDATA.bindOperate($(this), optStatus);
			}
			else if($(this).is(DISLAND.BOLIST)) {
				BINDDATA.bindPageBOList($(this), operateDom);
			}
			else if($(this).is(DISLAND.TREE)) {
				BINDDATA.bindTree($(this), optStatus);
			}
		});
	},

	/**
	 * 通过全局按钮同步table数据
	 * @param boListDom
	 * @param operateDom   全局按钮对应的数据岛operateDom
	 */
	bindPageBOList : function(boListDom, operateDom) {
		DISLAND.clearPageOptStatus();
		//同步table数据之前，判断table组件是否存在
		if(!Ext.getCmp(boListDom.attr(DISLAND.BOLIST_ID)))
			return;
		
		//记忆选中行
		TableHelper.rememberCheckedRow(boListDom.attr(DISLAND.BOLIST_ID));

		var bindFun;
		//须绑定container ID
		var containerIds = []; 
		operateDom = $(operateDom);
		//如果是select模式，必须选中table中的一行或多行进行同步
		if(DISLANDTODOM.pageBtn_isSelectMod(operateDom)) {
			bindFun = BINDDATA.bindSelectModData;
			containerIds = DISLANDTODOM.getSelectContainerId(operateDom);
		}
		//如果是modify模式，必须修改table中的一行或多行，对修改的数据重新同步
		else if(DISLANDTODOM.pageBtn_isModifyMod(operateDom)) {
			bindFun = BINDDATA.bindModifyModData;
			containerIds = DISLANDTODOM.getModifyContainerId(operateDom);
		}
		//如果是adaptive模式，table中有选中的行时进行同步
		else if(DISLANDTODOM.pageBtn_isAdaptiveMod(operateDom)) {
			bindFun = BINDDATA.bindSelectModData;
			containerIds = DISLANDTODOM.getAdaptiveContainerId(operateDom);
		}
		//对修改的数据重新同步
		else {
			BINDDATA.bindModifyModData(boListDom, DISLAND.operate_BOMethod(operateDom), true);
		}

		var optStatus = DISLAND.operate_BOMethod(operateDom);
		for(var i = 0; i < containerIds.length; i++){
			if(containerIds[i] == boListDom.attr(DISLAND.BO_ID) && !!bindFun){
				bindFun(boListDom, optStatus);
			}
		}
	},

	/**
	 * 通过表格中的按钮同步table数据
	 * @param boListDom
	 * @param operateDom   表格中的按钮对应的数据岛operateDom
	 */
	bindBOList : function(boListDom, operateDom) {
		DISLAND.clearPageOptStatus();
		operateDom = $(operateDom);
		//是否是行级按钮
		var isRowBtn = operateDom.attr(DISLAND.OPERATE_IDX);
		
		if(!isRowBtn) {
			//记忆选中行
			TableHelper.rememberCheckedRow(boListDom.attr(DISLAND.BOLIST_ID));
		}
		
		//行级按钮
		if(!!operateDom.attr(DISLAND.OPERATE_IDX)) {
			this.bindRowData(operateDom);
		}
		//table级别, 判断是否选中
		else if(DISLANDTODOM.btn_isSelectMod(operateDom) || BoFinalMethod.isStandMethod(DISLAND.operate_BOMethod(operateDom))) {
			this.bindSelectModData(boListDom, DISLAND.operate_BOMethod(operateDom));
		}
		//talbe级别, 判断表格数据是否修改
		else if(DISLANDTODOM.btn_isModifyMod(operateDom)) {
			this.bindModifyModData(boListDom, DISLAND.operate_BOMethod(operateDom));
		}
		else if(DISLANDTODOM.btn_isAdaptiveMod(operateDom)){
			this.bindSelectModData(boListDom, DISLAND.operate_BOMethod(operateDom));
		}
		else if(DISLANDTODOM.btn_isEmptyMod(operateDom)) {
			;
		}
		//点击table级别all model 模式按钮触发的数据岛同步
		else if(DISLANDTODOM.btn_isAllMod(operateDom)) {
			this.bindAllModData();
		}
		else {
			this.bindModifyModData(boListDom, DISLAND.operate_BOMethod(operateDom), true);
		}
	},


	/**
	 * 点击行级按钮触发的数据岛同步
	 */
	bindRowData : function(operateDom){
		DISLAND.clearPageOptStatus();
		//将operateDom所在的boList的状态机全部清空.
		var containerId = DISLANDTODOM.getContainerId(operateDom);
		var boListDom = DISLAND.getBOListDom(containerId);
		
		//清空所有行级按钮的状态机
		boListDom.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS, "");
		//为选中行设置状态机
		this.bindOperate(operateDom.parent(), DISLAND.operate_BOMethod(operateDom), operateDom.attr(DISLAND.BIND));
	},

	/**
	 * 点击table级别select model 模式按钮触发的数据岛同步
	 */
	bindSelectModData : function(boListDom, method) {
		DISLAND.clearPageOptStatus();
		//将所有editGrid中的修改数据岛同步
		BINDDATA.bindModifyModData(boListDom, method);

		var bos = DISLAND.getBOListDom(boListDom.attr(DISLAND.BO_ID)).children(DISLAND.BO);
		//清空所有bo的状态机
		bos.attr(DISLAND.BO_OPERATIONSTATUS, '');

		var tableHandler = new TableHandler(boListDom);
		var arr = tableHandler.getSelections();
		//获取选中的bo，将其状态机改为按钮绑定的操作
		bos.each(function() {
			if (isInArray(arr, $(this).attr(DISLAND.BO_INDEX))) {
				$(this).attr(DISLAND.BO_OPERATIONSTATUS, method);
			}
		});
	},

	/**
	 * 点击table级别all model 模式按钮触发的数据岛同步, 将整个页面的数据传递至后台
	 * param boListDom
	 */
	bindAllModData : function() {
		DISLAND.getDataIsland().children().each(function() {
			if($(this).is(DISLAND.BO)) {
				BINDDATA.bindBO($(this));
			}
			else if($(this).is(DISLAND.BOLIST)) {
				//表格中的数据无需同步, 如果表格中的数据有修改, 则在修改单元格的afterEdit事件后同步
			}
			else if($(this).is(DISLAND.TREE)) {
				BINDDATA.bindTree($(this));
			}
		});
		
		//改变page的状态机
		DISLAND.setPageOptStatus();
	},
	
	/**
	 * 点击table级别modify model 模式按钮触发的数据岛同步
	 * @param boListDom
	 * @param method   button的operate方法
	 * @param isEmptyMod button的saveMod是否为empty
	 */
	bindModifyModData : function(boListDom, method, isEmptyMod){
		DISLAND.clearPageOptStatus();

		var tBoListDom = DISLAND.getBOListDom(boListDom.attr(DISLAND.BO_ID));
		var columnInfo = tBoListDom.children(DISLAND.COLUMNINFO);
		var bos = tBoListDom.children(DISLAND.BO);
		//若button saveMod 为空, 更改所有bo状态机
		if(isEmptyMod){
			bos.attr(DISLAND.BO_OPERATIONSTATUS, method);
		}
		
		var tableHandler = new TableHandler(boListDom);
		var records = tableHandler.getModifiedRecords();
		for(var i = 0, ln = records.length; i < ln; i++) {
			var data = records[i].data;
			var rowIndex = data[DISLAND.BO_INDEX];
			var editBO = tBoListDom.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + rowIndex + "']").slice(0);
			
			if(editBO.length == 0)
				continue;

			//解决第一次加载页面data.id 无值的问题（若data.id无值,将o.find(bop[bind='id']).text() 赋值给data.id）
			if(data["id"] == "")
				data["id"] = editBO.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + DISLAND.BOP_ID + "']").text();

			//改变BO的状态机
			//注:为了提升bindModifyModData的性能, 将数据的同步分散到TableHelper.afterEdit中
			editBO.attr(DISLAND.BO_OPERATIONSTATUS, method);
		}
		
		boListDom = tBoListDom;
	},
	
	/**
	 * 同步可编辑bop的值
	 * @param data			编辑后的数据
	 * @param editBO		可编辑单元格所在的bo
	 * @param columnInfo
	 */
	bindEditBop : function(data, editBO, columnInfo) {
		for(var bind in data) {
			if(/\./.test(bind))
				continue;
			
			var bop = editBO.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bind.replaceAll("#", ".") + "']");
			var columnVSR = DISLAND.getColumnVSR(bop, columnInfo);
			if(!bop.attr(DISLAND.BIND) || (columnVSR.status && columnVSR.status.attr(DISLAND.STATUS_HIDDEN) == 'true')) 
				continue;
			
			bop.text(specialCharHandler(data[bind]));
		}
	},

	/**
	 * 同步按钮数据，当点击按钮时改变bo的状态机
	 * @param boDom 按钮所在的boDom
	 * @param method 按钮绑定的操作
	 * @param bind  operateBOP对应的bind
	 */
	bindOperate : function(boDom, method, bind) {
		DISLAND.clearPageOptStatus();
		//表格的行级按钮
		if(!!boDom.attr(DISLAND.BO_INDEX)) {
			var boListId = boDom.parent().attr(DISLAND.BO_ID);
			var boList = DISLAND.getDom(boListId);
			
			var rowBO = boList.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + boDom.attr(DISLAND.BO_INDEX) + "']").slice(0);
			if(rowBO.length > 0) {
				rowBO.attr(DISLAND.BO_OPERATIONSTATUS, method);
				rowBO.attr(DISLAND.BIND, bind);
				DISLAND.getDom(boListId).remove();
				DISLAND.getDataIsland().append(boList);
			}
		}
		else {
			boDom.attr(DISLAND.BO_OPERATIONSTATUS, method);
		}
	},

	/**
	 * 同步树形结构(仅对选择树有效)的数据
	 */
	bindTree : function(treeDom, method) {
		DISLAND.clearPageOptStatus();
		var tree = DISLANDTODOM.getContainer(treeDom);
		//标签页时, 如果没有初始化某个标签页的内容, tree为空
		if(!tree)
			return;

		//清空所有状态机
		treeDom.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS, "");
		//设置选中的节点的状态机
		var checkedNodes = tree.getChecked();
		treeDom.children(DISLAND.BO).each(function(){
			for(var i = 0, length = checkedNodes.length; i < length; i++) {
				if($(this).attr(DISLAND.TREE_CHILDREN_ID) == checkedNodes[i].id) {
					$(this).attr(DISLAND.BO_OPERATIONSTATUS, method);
					break;
				}
			}
		});
	},

	/**
	 * （表格）将编辑内容更新到对应bop中
	 * @param {} boListDom
	 * @param {} fcHandler
	 */
	bindTableBop : function(boDom, fcHandler){
		DISLAND.clearPageOptStatus();
		DISLAND.getBOListDom(boDom.attr(DISLAND.BO_ID))
			.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + boDom.attr(DISLAND.BO_INDEX) + "']")
			.children(DISLAND.BOP).each(function(){
			var bind = $(this).attr(DISLAND.BIND);
			if(!bind)
				return true;
			var bop = boDom.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bind + "']");
			if(!!fcHandler && bind == fcHandler.bopDom.attr(DISLAND.BIND)){
				bop.text(trim(fcHandler.getValue()));
			}
			else if(bop.text() == 'undefined'){
				bop.text($(this).text());
			}
		});
	}
};