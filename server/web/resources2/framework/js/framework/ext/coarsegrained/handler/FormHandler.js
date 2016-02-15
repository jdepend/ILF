/**
 * 表单处理js，负责所有<qeweb:form>表单的操作
 * @param boDom
 * @returns {FormHandler}
 */
function FormHandler(boDom) {
	FormHandler.superclass.constructor.call(this, boDom);

	/**
	 * 表单校验
	 */
	this.validate = function(winOperate) {
		//前台校验
		var flag = this.container.getForm().isValid();
		//服务器校验
		this.boDom.find(DISLAND.BOP).each(function(){
			var fc = null;
			//区分系统弹出框与普通form
			//系统弹出的表单
			if(typeof winOperate == "string")
				fc = DISLANDTODOM.getWinFC($(this) , winOperate);
			//普通表单
			else
				fc = DISLANDTODOM.getFC($(this));
			
			if(!fc)
				return true;
			var fcHandler = FCHandlerFactory.createFCHandler($(this), fc);
			flag = flag && serverValidate(fcHandler);
		});
		return flag;
	};

	/**
	 * 装载组件的数据
	 */
	this.reload = function(method, sourceContainerName, scopeDataIsland) {
		lockSrceen();
		var param = this._getParams(this.container.name, method, sourceContainerName, scopeDataIsland);
		var boId = this.boDom.attr(DISLAND.BO_ID);
		var url = appConfig.ctx + (method ? actionURL.reloadTargetVC() : actionURL.getGaSearch());
		
		$.ajax({
			type : "POST",
			url : url,
			data : param,
			success : function(data){
				if(!data)
					return;
					
		   		//如果窜session，跳转到登录后的第一个页面
				validateSession(data);

				var disland = XMLDomFactory.getInstance(data);
				var boDom = disland.find(DISLAND.BO + "[" + DISLAND.BO_ID + "='" + boId + "']");

				boDom.find(DISLAND.BOP).each(function(){
					var fcHandler = FCHandlerFactory.createFCHandler($(this));
					fcHandler.update();
				});
				boDom.find(DISLAND.OPERATE).each(function(){
					var btnHandler = new ButtonHandler($(this));
					btnHandler.updateStatus();
				});
				
				unlockScreen();
			}
		});
	};
	
	this._getParams = function(bcName, method, sourceContainerName, scopeDataIsland){
		if(method)
			return param(GA.operation) + method
					+ param(GA.dataIsland) + paramValue(scopeDataIsland) 
					+ param(GA.targetXML) + paramValue("<page>" + xmlToString(this.boDom) + "</page>")
					+ param(pageFlow.sourceName) + sourceContainerName
					+ param(GA.targetName) + DISLANDTODOM.getBoName(this.boDom);
		return param(GA.operation) + "search"
				+ param(GA.dataIsland) + paramValue(xmlToString(scopeDataIsland)) 
				+ param(GA.targetName) + bcName;
    };
    
    this.freshMyself = function(boDom) { 
    	if(boDom) {
    		boDom.find(DISLAND.BOP).each(function(){
				var fcHandler = FCHandlerFactory.createFCHandler($(this));
				fcHandler.update();
			});
    	}
    };
}
extend(FormHandler, ContainerHandler);
