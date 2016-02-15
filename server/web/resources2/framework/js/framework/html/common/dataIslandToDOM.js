var DISLANDTODOM = {

	/**
	 * 从bopDom中获取对应控件
	 * @param bopDom 细粒度组件对应bop的数据岛
	 * @returns	细粒度控件，jquery对象
	 */
	getFC : function(bopDom) {
		var fcId = bopDom.parent().attr(DISLAND.BO_ID) + DISLAND.SPLIT_LINE
					+ bopDom.parent().attr(DISLAND.BIND) + DISLAND.SPLIT_LINE
					+ bopDom.attr(DISLAND.BIND);
		return $("#" + fcId.replace(DISLAND.SPLIT_POINT, "\\."));
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
	 * 从operateDom中获取对应的button控件
	 * @param operateDom 控制组件对应的operate数据岛
	 * @returns	button控件，jquery对象
	 */
	getButton : function(operateDom) {
		//表格中的行级按钮
		if(!!operateDom.attr(DISLAND.OPERATE_IDX))
			return $("#" + operateDom.attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + operateDom.attr(DISLAND.OPERATE_IDX));
		//其它按钮
		else
			return $("#" + operateDom.attr(DISLAND.OPERATE_ID));
	},

	/**
	 * 根据operateDom获取其对应的按钮的name.
	 * btnName, containerId-containerBind-btnName或containerBind-btnName(按钮直接在page中时)
	 */
	getBtnName : function(operateDom) {
		var arr = operateDom.attr(DISLAND.OPERATE_ID).split(DISLAND.SPLIT_LINE);
		return arr.length == 3 || arr.length == 4 ? arr[2] : arr[1];
	},

	getBtnId : function(operateDom) {
		return operateDom.attr(DISLAND.OPERATE_ID);
	},
	
	/**
	 * 判断是否是table级按钮
	 */
	isTableButton : function(operateDom) {
		return !operateDom.attr(DISLAND.OPERATE_EXPEND);
	},

	/**
	 * 从boDom中获取对应的Container控件
	 * @param boDom 粗粒度组件对应bo的数据岛
	 * @returns	Container控件，jquery对象
	 */
	getContainer : function(boDom){
		return $("#"+boDom.attr(DISLAND.BO_ID)).children().eq(1).children();
	},
	
	/**
	 * 通过BOID获取对应的Container控件
	 * @param boId 粗粒度组件对应bo的id
	 * @returns	Container控件，jquery对象
	 */
	getContainerByDomID : function(boId){
		return $("#"+boId).children().eq(1).children();
	},

	/**
	 * 根据operateDom获取按钮所在的粗粒度组件的Id
	 */
	getContainerId : function(operateDom) {
		return operateDom.attr(DISLAND.OPERATE_ID).split(DISLAND.SPLIT_LINE)[0];
	},

	/**
	 * 获取table级按钮的保存模式
	 *
	 */
	getSaveMod : function(operateDom) {
		return operateDom.attr(DISLAND.OPERATE_SAVEMOD);
	},

	/**
	 * table级按钮的保存模式是否是select.
	 * 当saveMod=select时, 将判断是否选中了数据, 如果未选中任何数据, 将提示"请至少选择一条数据", 请求不会发送到后台,
	 * 仅当表格设置了selectionModle时save才有实际作用. table级别的delete方法即使不设置select也将弹出提示.
	 */
	btn_isSelectMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'select';
	},

	/**
	 * table级按钮的保存模式是否是modify.
	 * 当saveMod=modify时, 如果表格中的数据未修改, 将提示"没有修改数据", 请求不会发送到后台,
	 * 仅当表格设置了可修改单元格时modify才有实际作用;
	 */
	btn_isModifyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'modify';
	},

	/**
	 * table级按钮的保存模式是否是empty.
	 * 当saveMod为empty时, 点击table级按钮不提示任何信息,直接将请求发送到后台
	 */
	btn_isEmptyMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'empty';
	},

	/**
	 * table级按钮的保存模式是否是all.
	 * 当saveMod为all时, 将把页面中的所有数据转递至后台.
	 */
	btn_isAllMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm == 'all';
	},
	
	/**
	 * page级按钮的保存模式是否是adaptive.
	 */
	pageBtn_isAdaptiveMod : function(operateDom) {
		var sm = this.getSaveMod(operateDom);
		return !!sm && sm.indexOf('.adaptive') > 0;
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
	 * 根据operate返回方法名称数组.
	 * @param operate  按钮绑定的方法, 多个方法以逗号分隔
	 */
	getMethodArr : function(operate) {
		return strToArray(operate, DISLAND.SPLIT_COMMA);
	}
};