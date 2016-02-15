var DISLANDTODOM = {

	/**
	 * 从bopDom中获取对应控件
	 * @param bopDom 细粒度组件对应bop的数据岛
	 * @returns	细粒度组件
	 */
	getFC : function(bopDom) {
		return Ext.getCmp(this.getFCId(bopDom));
	},

	/**
	 * 从bopDom中获取对应控件
	 * @param bopDom 细粒度组件对应bop的数据岛
	 * @returns	细粒度组件ID
	 */
	getFCId : function(bopDom) {
		var fcId = "";
		if(!!bopDom)
			fcId = bopDom.parent().attr(DISLAND.BO_ID)
				+ DISLAND.SPLIT_LINE
				+ bopDom.parent().attr(DISLAND.BIND)
				+ DISLAND.SPLIT_LINE + bopDom.attr(DISLAND.BIND);
		return fcId;
	},

	/**
	 *
	 */
	getBOPBind : function(fcId) {
		return fcId.split(DISLAND.SPLIT_LINE)[2];
	},

	/**
	 * 根据operateDom获取按钮所在的粗粒度组件的Id
	 */
	getContainerId : function(operateDom) {
		return this.getContainerIdByBtnId(operateDom.attr(DISLAND.OPERATE_ID));
	},

	/**
	 * 根据按钮ID获取按钮所在的粗粒度组件id
	 */
	getContainerIdByBtnId : function(btnId) {
		return btnId.split(DISLAND.SPLIT_LINE)[0];
	},

	/**
	 * 根据operateDom获取其对应的按钮的name.
	 * btnName, containerId-containerBind-btnName或containerBind-btnName(按钮直接在page中时)
	 */
	getBtnName : function(operateDom) {
		var arr = operateDom.attr(DISLAND.OPERATE_ID).split(DISLAND.SPLIT_LINE);
		return arr.length == 3 || arr.length == 4 ? arr[2] : arr[1];
	},

	/**
	 * 获取按钮id
	 */
	getBtnId : function(operateDom) {
		if(!operateDom.attr(DISLAND.OPERATE_IDX))
			return operateDom.attr(DISLAND.OPERATE_ID);
		else
			return operateDom.attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + operateDom.attr(DISLAND.OPERATE_IDX);
	},

	/**
	 * 判断是否是table级按钮
	 */
	isTableButton : function(operateDom) {
		return !operateDom.attr(DISLAND.OPERATE_EXPEND);
	},

	/**
	 * 获取table弹出框recordWinArr[] 的下标
	 */
	getRecordWinIdx : function(operateDom) {
		var arr = this.getBtnId(operateDom).split(DISLAND.SPLIT_LINE);
		return arr[0] + DISLAND.SPLIT_LINE + arr[1] + DISLAND.SPLIT_LINE + operateDom.attr(DISLAND.OPERATE_METHOD);
	},

	getBtnMethod : function(operate) {
		var arr = operate.split(DISLAND.SPLIT_LINE);
		return arr[arr.length - 1];
	},

	/**
	 * 从operateDom中获取对应的button控件
	 * @param operateDom 控制组件对应的operate数据岛
	 * @returns	button控件，jquery对象
	 */
	getButton : function(operateDom) {
		//表格中的行级按钮
		if(!!operateDom.attr(DISLAND.OPERATE_IDX))
			return;
		//其它按钮
		else {
			var btn = Ext.getCmp(operateDom.attr(DISLAND.OPERATE_ID));
			if(btn && btn.type && btn.type == 'anchor') {
				//获取真实的html，a标签
				btn = Ext.get(operateDom.attr(DISLAND.OPERATE_ID) + "_anchor").dom;
			}
			return btn;
		}
	},

	/**
	 * 获取table级按钮的保存模式
	 *
	 */
	getSaveMod : function(operateDom) {
		return operateDom.attr(DISLAND.OPERATE_SAVEMOD);
	},

	/**
	 * 按钮的保存模式是否是modify.
	 * 当saveMod=modify时, 如果表格中的数据未修改, 将提示"没有修改数据", 请求不会发送到后台,
	 * 仅当表格设置了可修改单元格时modify才有实际作用;
	 */
	btn_isModifyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'modify';
	},

	/**
	 * 按钮的保存模式是否是select.
	 * 当saveMod=select时, 将判断是否选中了数据, 如果未选中任何数据, 将提示"请至少选择一条数据", 请求不会发送到后台,
	 * 仅当表格设置了selectionModle时save才有实际作用. table级别的delete方法即使不设置select也将弹出提示.
	 */
	btn_isSelectMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'select';
	},

	/**
	 *按钮的保存模式是否是select.
	 * 当saveMod=adaptive时, 将判断是否选中了数据, 如果未选中任何数据, 请求直接发送到后台,但不会传输任何数据.
	 */
	btn_isAdaptiveMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'adaptive';
	},

	/**
	 * 按钮的保存模式是否是empty.
	 * 当saveMod为empty时, 点击table级按钮不提示任何信息,直接将请求发送到后台
	 */
	btn_isEmptyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'empty';
	},

	/**
	 * 按钮的保存模式是否是all.
	 * 当saveMod为all时, 将把页面中的所有数据转递至后台.
	 */
	btn_isAllMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'all';
	},
	
	/**
	 * 按钮的保存模式是否是混合模式.
	 * 混合模式可以为按钮绑定的方法设置不同的saveMod
	 */
	btn_isMixedMod : function(operateDom) {
		return !!operateDom.attr(DISLAND.OPERATE_OPTMOD);
	},
	
	/**
	 * method是否是"跳转前执行"方法
	 */
	isBeforeBoMethod : function(method) {
		return method.split(".").length == 1;
	},
	
	/**
	 * method是否是"跳转后执行"方法
	 */
	isAfterBoMethod : function(method) {
		return method.split(".").length == 2;
	},
	
	/**
	 * 获取operateDom绑定的"跳转前执行"方法
	 * @param operateDom
	 * @returns []
	 */
	getBeforeBoMethod : function(operateDom) {
		var methodArr = strToArray(DISLAND.operate_BOMethod(operateDom), ",");
		if(!methodArr)
			return null;
		
		//"跳转前执行"方法
		var result = [];
		for(var i = 0; i < methodArr.length; i++) {
			//仅当方法名格式是非bo.operate时, 计数器增加
			if(this.isBeforeBoMethod(methodArr[i]))
				result.push(methodArr[i]);
		}
		
		return result;
	},
	
	/**
	 * 获取operateDom绑定的"跳转后执行"方法
	 * @param operateDom
	 * @returns []
	 */
	getAfterBoMethod : function(operateDom) {
		var methodArr = strToArray(DISLAND.operate_BOMethod(operateDom), ",");
		if(!methodArr)
			return null;
		
		//"跳转后执行"方法
		var result = [];
		for(var i = 0; i < methodArr.length; i++) {
			//仅当方法名格式是bo.operate时, 计数器增加
			if(this.isAfterBoMethod(methodArr[i]))
				result.push(methodArr[i]);
		}
		
		return result;
	},
	
	/**
	 * 获取每个方法的saveMod
	 * @param operateDom
	 * @returns []
	 */
	getOptSaveMod : function(operateDom) {
		var optMod = operateDom.attr(DISLAND.OPERATE_OPTMOD);
		if(!optMod)
			return null;
		
		var optModArr = optMod.split(',');
		var result = [];
		for(var i = 0; i < optModArr.length; i++) {
			var temp = optModArr[i].split('=');
			result[temp[0]] = temp[1];
		}
		
		return result;
	},
	
	/**
	 * 获取记录的Id, 通过recordId可从数据库获得唯一的bo
	 * @param operateDom行级按钮的operate数据岛
	 */
	getRecordId : function(operateDom) {
		var bo = operateDom.parent();
		var bop = bo.find(DISLAND.BOP + "[" + DISLAND.BIND + "='id']");
		return getXmlNoteText(bop);
	},

	/**
	 * 从boDom中获取对应的Container控件
	 * @param boDom 粗粒度组件对应bo的数据岛
	 * @returns	Container控件，jquery对象
	 */
	getContainer : function(boDom){
		return Ext.getCmp(boDom.attr(DISLAND.BO_ID));
	},

	/**
	 * 全局按钮，SaveMod若为modify,获取须有更改的tableID
	 * savemod='orderList.modify,itemList.modify'
	 * return ['orderList','itemList']
	 */
	getRelContainerIdAdapter : function(operateDom, saveMod){
		var rmodifyId = [];
		var sm = this.getSaveMod(operateDom);
		if(!!sm){
			var modifyIds = sm.split(",");
			for(var i = 0; i < modifyIds.length; i++){
				if(modifyIds[i].indexOf(saveMod))
					rmodifyId.push(modifyIds[i].split(".")[0]);
			}
		}
		return rmodifyId;
	},

	/**
	 * 获取savemod select 的ContainerId
	 * @param {} operateDom
	 * @param {} saveMod
	 */
	getSelectContainerId : function(operateDom){
		return this.getRelContainerIdAdapter(operateDom, "select");
	},

	/**
	 *  获取savemod modify 的ContainerId
	 * @param {} operateDom
	 * @return {}
	 */
	getModifyContainerId : function(operateDom){
		return this.getRelContainerIdAdapter(operateDom, "modify");
	},

	/**
	 * 获取savemod adaptive 的ContainerId
	 * @param {} operateDom
	 * @param {} saveMod
	 */
	getAdaptiveContainerId : function(operateDom){
		return this.getRelContainerIdAdapter(operateDom, "adaptive");
	},

	/**
	 * page级按钮的保存模式是否是select.
	 */
	pageBtn_isSelectMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.select') > 0;
	},

	/**
	 * page级按钮的保存模式是否是modify.
	 */
	pageBtn_isModifyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.modify') > 0;
	},

	/**
	 * page级按钮的保存模式是否是adaptive.
	 */
	pageBtn_isAdaptiveMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.adaptive') > 0;
	},
	
	/**
	 * page级按钮的保存模式是否是all.
	 */
	pageBtn_isAllMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.all') > 0;
	},
	
	/**
	 * page级按钮的保存模式是否是empty.
	 */
	pageBtn_isEmptyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.empty') > 0;
	},
	
	getBOBind : function(fcName){
		return fcName.split(DISLAND.SPLIT_LINE)[1];
	},

	getFileID : function(fcId) {
		return fcId + '-file';
	},

	getFileAnchorID : function(fcId) {
		return fcId + '_anchor';
	},

	getFileCheckID : function(fcId) {
		return this.getFileAnchorID(fcId) + '_check';
	},

	getTblOperateId : function(boDom, bopDom){
		return boDom.attr(DISLAND.BO_ID) + DISLAND.SPLIT_LINE
			+ boDom.attr(DISLAND.BIND) + DISLAND.SPLIT_LINE
			+ bopDom.attr(DISLAND.BIND) + '-operate';
	},

	getWinFormId : function(btnId) {
		return 	btnId + "_form";
	},

	getWinFCId : function(bopDom, panelType){
		return bopDom.parent().attr(DISLAND.BO_ID).split(DISLAND.SPLIT_LINE)[0]
				+ DISLAND.SPLIT_LINE + bopDom.parent().attr(DISLAND.BIND)
				+ DISLAND.SPLIT_LINE + bopDom.attr(DISLAND.BIND)
				+ DISLAND.SPLIT_LINE + panelType;
	},

	getWinFC : function(bopDom, winOperate){
		var winFCId = this.getWinFCId(bopDom, winOperate);
		return Ext.getCmp(winFCId);
	},
	
	editFcId : function(boList, bop){
		return boList.attr(DISLAND.BO_ID)
				+ DISLAND.SPLIT_LINE + boList.attr(DISLAND.BIND)
				+ DISLAND.SPLIT_LINE + bop.attr(DISLAND.BIND);
	},
	
	/**
	 * 获取页面中第一个可交互的按钮
	 */
	getFisrstShowBtn : function() {
		var operateList = DISLAND.getOperateList();
		if(!operateList)
			return null;
		
		var result;
		operateList.each(function() {
			var btn = Ext.getCmp($(this).attr(DISLAND.OPERATE_ID));
			if(!btn || btn.hidden || btn.disabled) {
				return true;
			}
			else if(btn.type && btn.type === 'button') {
				result = btn;
				return false;
			}
		});
		
		return result;
	},
		
	/**
	 * 获取组合上传控件的ID
	 * @param {} fcId
	 * @return {}
	 */
	getFileUploadFCId : function(fcId){
		return fcId + "_composite";
	},
	
	/**
	 * 获取表格上传附件单元格的弹出window的id
	 */
	getFileCellWin : function(fcId) {
		return this.getFileUploadFCId(fcId) + "-fileWin";
	},
	
	/**
	 * 超链接弹出类型设置
	 * @param {} bopDom
	 * @param {} columnInfo
	 * @return {}
	 */
	getAnchorTarget : function(bopDom, columnInfo){
		var bopId = bopDom.attr(DISLAND.BIND);
		var column = columnInfo.find(DISLAND.COLUMNINFO_COLUMN + "[" + DISLAND.BIND + "='" + bopId + "']");
		return "target='" + (column.attr(DISLAND.COLUMN_TARGET) ? column.attr(DISLAND.COLUMN_TARGET) : "_self") + "'";
	},
	
	/**
	 * 获取sourceBtn的ID
	 * @param {} bopDom
	 * @return {}
	 */
	getSourceBtnId : function(bopDom, sysOperate){
		var btnId;
		if(sysOperate)
			btnId = DISLANDTODOM.getWinFCId(bopDom, sysOperate) + "_source_btn";
		else
		 	btnId = DISLANDTODOM.getFCId(bopDom) + "_source_btn";
		return btnId;
	},
	
	/**
	 * 判断细粒度组件是否存在sourceBtn
	 * @param bopDom
	 * @return 
	 */
	hasSourceBtn : function(bopDom, sysOperate){
		var btnId = DISLANDTODOM.getSourceBtnId(bopDom, sysOperate);
		return !!Ext.getCmp(btnId);
	},
	
	/**
	 * 获取sourceBtn
	 * @param bopDom
	 * @param sysOperate
	 * @return 
	 */
	getSourceBtn: function(bopDom, sysOperate){
		var btnId = DISLANDTODOM.getSourceBtnId(bopDom, sysOperate);
		return Ext.getCmp(btnId);
	},
	
	
	/**
	 * 将与sourceBtn关联的细粒度组件设置为只读
	 * @param sbtn
	 * @param sysOperate
	 * @param boDom
	 */
	setReadonlyBySbopIds : function(sbtn, sysOperate, boDom){
		var arrS = strToArray(sbtn.sbopIds, DISLAND.SPLIT_COMMA);
		var arrEditAble = strToArray(sbtn.editAble, DISLAND.SPLIT_COMMA);
		for (var i = 0, len = arrS.length; i < len; i++) {
			if(isInArray(arrEditAble, arrS[i]))
				continue;
			
			var bopDom = DISLAND.getBOPByFcId(arrS[i], boDom);
			var fcHandler;
			if(sysOperate) {
				var fc = Ext.getCmp(DISLANDTODOM.getWinFCId(bopDom, sysOperate));
				fcHandler = FCHandlerFactory.createFCHandler(bopDom, fc);
			}
			else {
			 	fcHandler = FCHandlerFactory.createFCHandler(bopDom);
			}
			fcHandler.setReadonly();
		}
	},
	
	/**
	 * 
	 * @param {} fcName
	 * @return {}
	 */
	getBOId : function(fcId){
		return fcId.split(DISLAND.SPLIT_LINE)[0];
	},
	
	/**
	 * 获取系统operate
	 * @param {} boListDom
	 * @return {}
	 */
	getSysOperateByForm : function(boDom){
		var conId = boDom.attr(DISLAND.BOLIST_ID);
		if(!conId)
			return null;
		var arr = conId.split(DISLAND.SPLIT_LINE);
		if(arr.length != 3)
			return null;
		return arr[2].split("_")[0];
	},
	
	/**
	 * 获取超链接对应operate的id
	 * @param {} bopDom
	 * @return {}
	 */
	getAnchorOperateId : function(bopDom){
		var boDom = bopDom.parent();
		return boDom.attr(DISLAND.BO_ID) + DISLAND.SPLIT_LINE 
			+ boDom.attr(DISLAND.BIND) + DISLAND.SPLIT_LINE 
			+ bopDom.attr(DISLAND.BIND);
	},
	
	getBoName : function(boDom){
		return boDom.attr(DISLAND.BO_ID) + DISLAND.SPLIT_LINE + boDom.attr(DISLAND.BIND);
	},
	
	/**
	 * 根据operate返回方法名称数组.
	 * @param operate  按钮绑定的方法, 多个方法以逗号分隔
	 */
	getMethodArr : function(operate) {
		return strToArray(operate, DISLAND.SPLIT_COMMA);
	}
};
