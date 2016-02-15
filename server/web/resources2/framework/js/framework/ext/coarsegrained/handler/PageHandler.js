/**
 * Page处理类
 * @param pageDom
 * @returns {ContainerHandler}
 */
function PageHandler(pageDom) {
	this.pageDom = pageDom;

	/**
	 * 善后工作, 在点击按钮执行行为成功后触发. 表格组件刷新表格, 并清除选中项.
	 */
	this.aftermath = function() {
		DISLAND.getDataIsland().find(DISLAND.BOLIST).each(function() {
			var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
			containerHandler.aftermath();
		});
	};
	
	/**
	 * 整体页面校验
	 */
	this.validate = function() {
		var result = true;
		//循环执行页面上每个粗粒度组件的validate
		DISLAND.getDataIsland().children().each(function() {
			var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
			if(containerHandler instanceof PageHandler)
				return true;
			
			//仅当粗粒度组件状态为"显示"时对其进行校验 
			if(!containerHandler.isHidden() && !containerHandler.validate()){
				result = false;
				return false;
			}
		});
		
		return result;
	};
	
	/**
	 * 刷新全局按钮
	 */
	this.reload = function(method, sourceContainerName, scopeDataIsland) {
		if(!this.pageDom.attr(DISLAND.BIND)) {
			showMSG.showWaring("page标签没有bind属性，不能刷新全局按钮！");
			return;
		}
		
		var param = this._getParams(method, sourceContainerName, scopeDataIsland);
		var url = appConfig.ctx + actionURL.reloadPageBtn();
		
		$.ajax({
			type : "POST",
			url : url,
			data : param,
			success : function(data){
				if(!data) {
					unlockScreen();
					return;
				}
					
		   		//如果窜session，跳转到登录后的第一个页面
				validateSession(data);

				var disland = XMLDomFactory.getInstance(data);
				disland.children(DISLAND.PAGE).children(DISLAND.OPERATE).each(function() {
					new ButtonHandler($(this)).update();
				});
			}
		});
	};
}

PageHandler.prototype._getParams = function(method, sourceContainerName, scopeDataIsland){
	return param(GA.operation) + method
			+ param(GA.dataIsland) + paramValue(scopeDataIsland) 
			+ param(GA.targetXML) + paramValue(xmlToString(this.pageDom))
			+ param(pageFlow.sourceName) + sourceContainerName
			+ param(GA.targetName) + this.pageDom.attr(DISLAND.BIND);
};
