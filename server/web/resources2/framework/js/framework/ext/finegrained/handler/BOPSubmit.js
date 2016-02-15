/**
 * 细粒度组件提交. private method
 * 
 * 1.当一个细粒度组件与其它细粒度组件有关联关系时, 这个细粒度组件的值/状态/范围改变后将触发bopSubmit中的submit,
 * submit根据这个细粒度组件的值/状态/范围改变它所关联的其它细粒度组件的值值/状态/范围.
 * 
 * 2.当一个细粒度组件与其它粗粒度组件有关联关系时, 这个细粒度组件的值/状态/范围改变后将触发bopSubmit中的conSubmit,
 * conSubmit, 将改变相应的粗粒度组件的值/状态/范围.
 * 
 * @param fcHandler
 */
var BOPSubmit = {
	/**
	 * 细粒度组件提交:关联其它细粒度组件
	 */
	submit : function(fcHandler) {
		var bo = fcHandler.boDom; 
		var bop = fcHandler.bopDom; 
		var fc = fcHandler.fc;
		var sysOperate;
		if(fc && fc.tblIndex) {
			BINDDATA.bindTableBop(bo, fcHandler);
		}
		else {
			sysOperate = DISLANDTODOM.getSysOperateByForm(bo);
			DISLAND.setBOPValue(bop, fcHandler.getValue());
		}
			
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.getFcToBopSubmit(),
			method : "POST",
			timeout : appConfig.ajaxTimeout,
			params : GA.vcId + DISLANDTODOM.getFCId(bop) + param(GA.dataIsland) + paramValue(xmlToString(bo)),
			success : function(response) {
				var boDom = XMLDomFactory.getInstance(response.responseText).find(DISLAND.BO);
				boDom.children(DISLAND.BOP).each(function(){
					var bopDom = $(this);
					// 更新细粒度组件
					if(fc && fc.tblIndex){
						//表格
						DISLAND.getBOListDom(bo.attr(DISLAND.BOLIST_ID))
							.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + fc.tblIndex + "']")
							.children(DISLAND.BOP + "[" + DISLAND.BIND + "='" + $(this).attr(DISLAND.BIND) + "']").each(function(){
							var value = "";
							if(bopDom.text())
								value = bopDom.text();
							$(this).text(value);							
							Ext.getCmp(bo.attr(DISLAND.BOLIST_ID)).getStore().getAt(fc.tblIndex-1).set(bopDom.attr(DISLAND.BIND), value);
							if(bopDom.attr(DISLAND.STATUS_DISABLE))
								$(this).attr(DISLAND.STATUS_DISABLE, bopDom.attr(DISLAND.STATUS_DISABLE));
							if(bopDom.attr(DISLAND.BOP_RANGE_ENUM))
								$(this).attr(DISLAND.BOP_RANGE_ENUM, bopDom.attr(DISLAND.BOP_RANGE_ENUM));
						});
					}
					else {
						// 表单
						var fc
						if(sysOperate)
							fc = DISLANDTODOM.getWinFC(bopDom, sysOperate);
						var handler = FCHandlerFactory.createFCHandler(bopDom, fc);
						handler.update();
					}
				});
			}
		});
	},
	
	/**
	 * 细粒度组件提交:关联其它粗粒度组件
	 */
	conSubmit : function(fcHandler) {
		if(fcHandler.conSubmit || !fcHandler.fc.validate())
			return;

		var bo = fcHandler.boDom; 
		var bop = fcHandler.bopDom; 
		var boId = bo.attr(DISLAND.BO_ID);
		var container = Ext.getCmp(boId);
		var sysOperate;
		
		//如果值未改变, 不进行提交
		var oldValue = DISLAND.getValue(bop);
		if(oldValue == fcHandler.getValue())
			return;
		
		if(CommonDom.isTable(container)) {
			BINDDATA.bindTableBop(bo, fcHandler);
		}
		else if(CommonDom.isForm(container)) {
			sysOperate = DISLANDTODOM.getSysOperateByForm(bo);
			BINDDATA.bindBO(bo, null, sysOperate);
		}
		
		lockSrceen();
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.getFcToBoSubmit(),
			method : "POST",
			timeout : appConfig.ajaxTimeout,
			params : GA.vcId + DISLANDTODOM.getFCId(bop) + param(GA.dataIsland) + paramValue(xmlToString(bo)),
			success : function(response) {
				var boDom = XMLDomFactory.getInstance(response.responseText).find(DISLAND.BO);
				//table
				if(CommonDom.isTable(container)){
					var store = container.getStore();
					var boIndex = Number(boDom.attr(DISLAND.BO_INDEX));
					var record = store.getAt(boIndex - 1);
					var boList = DISLAND.getBOListDom(container.getId());
					//数据岛中的对应BO,需要将修改结果映射到targetBO中
					var targetBO = boList.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + boDom.attr(DISLAND.BO_INDEX) + "']");
						
					//设置该行细粒度组件的值
					boDom.children(DISLAND.BOP).each(function(){
						var fieldName = $(this).attr(DISLAND.BIND);
						if(fieldName != bop.attr(DISLAND.BIND))
							record.set(fieldName, $(this).text());
					});
					
					//设置该行按钮的状态
					boDom.children(DISLAND.OPERATE).each(function(){
						var btnId = DISLANDTODOM.getBtnId($(this));
						var btn = $('#' + btnId);
						//按钮隐藏
						if($(this).attr(DISLAND.STATUS_HIDDEN) == 'true') {
							btn.parent().hide();
						}
						//按钮只读或不可交互
						else if($(this).attr(DISLAND.STATUS_DISABLE) == 'true' || $(this).attr(DISLAND.STATUS_READONLY) == 'true') {
							btn.attr('disabled', true);
							btn.attr('style', 'color:gray');
							btn.removeAttr('onclick');
						}
					});
					
					//防止重复提交调用
					fcHandler.conSubmit = true;
					var targetBO = boList.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + boDom.attr(DISLAND.BO_INDEX) + "']");
					targetBO.remove();
					boList.append(boDom);
					TableHelper.formatCell(boList);
				}
				//form
				else if(CommonDom.isForm(container)){
					boDom.children(DISLAND.BOP).each(function(){
						var fc;
						if(sysOperate)
							fc = DISLANDTODOM.getWinFC($(this), sysOperate);
						var handler = FCHandlerFactory.createFCHandler($(this), fc);
						handler.update();
					});
				}
				
				unlockScreen();
			}
		});
	}
};
