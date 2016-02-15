Ext.override(Ext.form.FormPanel, {
	//当页面可见宽度变化时，改变表单的宽度
    resize : function() {
    	var BOPS = DISLAND.getBODom(this.getId()).children(DISLAND.BOP);
    	// 历史修改图标的resize
    	BOPS.each(function(){
			var fcHandler = FCHandlerFactory.createFCHandler($(this), null);
			if(!fcHandler)
				return true;
			if(!fcHandler.fc)
				return true;
			fcHandler.fc.ownerCt.on("afterlayout", function(){
				fcHandler.resize();
			});
		});
		this.setWidth(document.body.clientWidth);
    }
});