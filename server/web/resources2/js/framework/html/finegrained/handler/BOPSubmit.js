/**
 * 细粒度组件提交.
 * private method
 * 
 * 当一个细粒度组件与其它细粒度组件有关联关系时, 这个细粒度组件的值/状态/范围改变后将触发bopSubmit,
 * bopSubmit根据这个细粒度组件的值/状态/范围改变它所关联的其它细粒度组件的值值/状态/范围.
 * @param bopDom	细粒度组件数据岛
 * @param divDom	细粒度组件的Div区域, 对于html展现,所有细粒度组件都包含于div区域中
 * @param value 	细粒度组件的值
 * 
 */
var BOPSubmit = {
	submit : function(fcHandler) {
//		DISLAND.setBOPValue(bopDom, value);
//		var fcId = divDom.attr('id');
		
		var bo = fcHandler.boDom; 
		var bop = fcHandler.bopDom; 
		var fc = fcHandler.fcDom;
		
		
		var sysOperate;
		if(fc && fc.tblIndex) {
			BINDDATA.bindTableBop(bo, fcHandler);
		}
		else {
			sysOperate = DISLANDTODOM.getSysOperateByForm(bo);
			DISLAND.setBOPValue(bop, fcHandler.getValue());
		}

		$.ajax({
			type : "POST",
			url : appConfig.ctx + actionURL.getFcToBopSubmit(),
			data : GA.vcId + DISLANDTODOM.getFCId(bop) + param(GA.dataIsland) + paramValue(xmlToString(bo)),
			success : function(data) {
				var boDom = XMLDomFactory.getInstance(data).find(DISLAND.BO);
				boDom.children(DISLAND.BOP).each(function(){
					var bopDom = $(this);
					if(fc && fc.tblIndex){
						
					}
					else{
						var handler = FCHandlerFactory.createFCHandler(bopDom);
						handler.update();
					}
				});
			}
		});
	}
}
