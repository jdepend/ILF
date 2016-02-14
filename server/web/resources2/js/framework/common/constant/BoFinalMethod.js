var BoFinalMethod = {
	QUERY : "query",
	UPDATE : "update",
	DELETE : "delete",
	INSERT : "insert",
	VIEW : "view",
	RECORD : "getRecord",
	SYSADDROW : "sysAddRow",	//平台JS方法,表格中动态添加行
	SYSDELROW : "sysDelRow",	//平台JS方法,表格中动态删除行
	JSDELETE : "jsDelete",		//平台JS方法,删除表格弹出回填的行数据
	SYSTEMP : "sys-temp",		//占位方法，当按钮不设置operate属性时，在点击按钮时将BO或BOList设置状态机设置为sys-temp
	SYSCLEAR : "sysClear",		//平台JS方法,表单中清空细粒度组件的值
	SYSRESET : "sysReset",		//平台JS方法,重置，可用于EditorGrid和Form
	SYSFRESH : "sysFresh",		//table的刷新方法，当使用<page onload='tableId.sysFresh'>时，表格初次加载时将执行刷新
	SYSSAVECASE : "sysSaveCase",//平台JS方法,保存查询条件
	SYSOPENCASE : "sysOpenCase",//平台JS方法,打开查询条件
	
	isQuery : function(method) {
		return !!method && this.QUERY === method;
	},
	
	isView : function(method) {
		return !!method && this.VIEW === method;
	},
	
	isUpdate : function(method) {
		return !!method && this.UPDATE === method;
	},
	
	isDelete : function(method) {
		return !!method && this.DELETE === method; 
	},
	
	isInsert : function(method) {
		return !!method && this.INSERT === method;
	},
	
	isStandMethod : function(method) {
		return this.isUpdate(method) || this.isInsert(method) || this.isDelete(method);
	},
	
	/**
	 * table中将弹出对话框的方法
	 */
	isPopMethod : function(method) {
		return this.isUpdate(method) || this.isInsert(method) || this.isView(method);
	},
	
	/**
	 * 判断是否为导出
	 */
	isExport : function(method) {
		return method.indexOf("exp") == 0;
	},
	
	/**
	 * 判断新增行按钮
	 */
	isAddRow : function(method) {
		return !!method && this.SYSADDROW === method;
	},
	
	/**
	 * 判断删除行按钮
	 */
	isDelRow : function(method) {
		return !!method && this.SYSDELROW === method;
	},
	
	isPageFlow : function(method) {
		return !!method && this.SYSTEMP === method;
	},
	
	isClear : function(method) {
		return !!method && this.SYSCLEAR === method;
	},
	
	isReset : function(method) {
		return !!method && this.SYSRESET === method;
	},
	
	isSysFresh : function(method) {
		return !!method && this.SYSFRESH === method;
	}
};