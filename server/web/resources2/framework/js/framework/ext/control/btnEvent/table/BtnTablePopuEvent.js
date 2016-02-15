/**
 * 表格行级按钮增/改/查看方法的处理者
 * @see ExtTable.java
 */
var BtnTablePopuEvent = {
	/**
	 * @param operateId
	 */
	validate : function(operateId) {
		var formPanelDom = DISLAND.getFormPanelDom(operateId);
		if(!formPanelDom)
			return false;
			
		var handler  = ContainerHandlerFactory.createContainerHandler(formPanelDom);
		var operateDom = DISLAND.getBOOperateDom(operateId, formPanelDom);
		
		return handler.validate(operateDom.attr(DISLAND.OPERATE_METHOD));
	},

	/**
	 * 表格自带弹出框的持久化操作
	 * @param operateId
	 */
	handle : function(operateId) {
		var formPanelDom = DISLAND.getFormPanelDom(operateId);
		var btnExe = new BtnFormExeEvent();
		btnExe.setPanelType(DISLANDTODOM.getBtnMethod(operateId));
		btnExe.handleRequest(DISLAND.getBOOperateDom(operateId, formPanelDom));
	},
	
	/**
	 * 清空弹出框内容
	 * @param operateId
	 */
	clear : function(operateId) {
		var formPanel = formPanelArr[operateId];
		formPanel.getForm().reset();
	},
	
	/**
	 * 重置弹出框内容
	 * @param operateId
	 */
	reset : function(operateId) {
		var formPanelDom = DISLAND.getFormPanelDom(operateId);
		var formPanel = formPanelArr[operateId];
		this._fcUpdate(formPanel, formPanelDom);
	},
	
	/**
	 * 设置系统弹出框（新增/修改）中细粒度组件的初始状态
	 * @param boDom
	 * @param sysOperate
	 */
	sysWinInit : function(boDom, sysOperate) {
		var sourceBtnArray = new Array();
		boDom.children(DISLAND.BOP).each(function() {
			var fc = DISLANDTODOM.getWinFC($(this), sysOperate);
			var fcHandler = FCHandlerFactory.createFCHandler($(this), fc);
			fcHandler.init();
				
			//当细粒度组件拥有sourceBtn时，将其与相关细粒度组件的关联关系添加到数组中
			if(DISLANDTODOM.hasSourceBtn($(this), sysOperate))
				sourceBtnArray.push(DISLANDTODOM.getSourceBtn($(this), sysOperate));
		});
		
		//将所有与sourceBtn关联的细粒度组件设置为只读
		for(var i = 0, len = sourceBtnArray.length; i < len; i++) {
			DISLANDTODOM.setReadonlyBySbopIds(sourceBtnArray[i], sysOperate, boDom);
		}
	},
	
	/**
	 * 重置所有细粒度组件
	 * @param formPanel
	 * @param formPanelDom
	 */
	_fcUpdate : function(formPanel, formPanelDom){
		formPanel.items.each(function(item) {
			var bopDom = DISLAND.getFormPanelBOPDom(formPanelDom, item.getId());
			var fcHandler = FCHandlerFactory.createFCHandler(bopDom, item);
			fcHandler.update();
		});
	}
};