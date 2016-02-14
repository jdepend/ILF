/**
 * 表格级按钮增/改/查看方法的处理者
 * @see ExtCommandButton.java
 */
var BtnTablePopuEvent = {
	handle : function(operateId) {
		if(!formPanelArr[operateId].getForm().isValid())
			return false;
		
		var formPanelDom = DISLAND.getFormPanelDom(operateId);
		var operateDom = DISLAND.getBOOperateDom(operateId, formPanelDom);
		var btnExe = new BtnFormExeEvent();
		btnExe.setPanelType(DISLANDTODOM.getBtnMethod(operateId));
		btnExe.handleRequest(operateDom);

		return true;
	},
	
	/**
	 * 清空弹出框内容
	 */
	clear : function(operateId) {
		var formPanel = formPanelArr[operateId];
		formPanel.getForm().reset();
	},
	
	/**
	 * 重置弹出框内容
	 */
	reset : function(operateId) {
		var boDom = DISLAND.getFormPanelDom(operateId);
		var formPanel = formPanelArr[operateId];
		formPanel.items.each(function(item) {
			var bopDom = DISLAND.getFormPanelBOPDom(boDom, item.getId());
			var fcHandler = FCHandlerFactory.createFCHandler(bopDom, item);
			fcHandler.setValue(getRemoveCDATA(bopDom.find(DISLAND.BOP_VALUE).text()));
		});
		
	}
}