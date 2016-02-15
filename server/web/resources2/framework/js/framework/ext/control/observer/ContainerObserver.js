/**
 * 所有粗粒度组件均被定义为观察者, 当一个粗粒度组件改变时, 与之相关的粗粒度组件(观察者)将自动改变
 */
var ContainerObserver = function(){
	ContainerObserver.superclass.constructor.call(this);
	this.containerId;
	this._method = 'search';
	
    this.update = function(observable, data) {
    	this._query(observable);
    };
    
    this._query = function(observable){
    	var observer = this;
    	var boId = observer.containerId;
    	var container = Ext.getCmp(boId);
    	if(!container)
    		return;
    	//区分组件类型
    	var isTable = CommonDom.isTable(container);
    	var isTree = CommonDom.isTree(container);
    	if (isTable){
    		//当组件为表格时，重置表格中的所有未保存的编辑项
			var handler = new TableHandler();
			handler.clearEdit(observer.containerId);
    	}
    	var param = observer._getParams(observable, container.name);
		$.ajax({
			type : "POST",
			url : appConfig.ctx + actionURL.getGaSearch(),
			data : param,
			success : function(data){
				if(!data)
					return;
					
				observer.containerFrsh(data, boId, isTable);
				
				var observable = observableArr[boId];
				if(observable){
					//TODO 如果观察者本身也具有被观察者的身份，则需要清除它所对应观察者的数据。
					observable.clearObserversData();
				}
				unlockScreen();
			}
		});
    
	    /**
	     * 根据返回内容刷新粗粒组件显示
	     */
	    this.containerFrsh = function(data, boId, isTable){
	   		//如果窜session，跳转到登录后的第一个页面
	    	validateSession(data);
					
			//查询结果数据岛（后台将把查询结果生成数据岛）
			var newDataIsland = XMLDomFactory.getInstance(data).find(DISLAND.dataIsland);
				
			//刷新当前被关联粗粒度组件的显示结果
			var boDom;
			//各种粗粒度组件获取数据岛的方式有所不同
			if(isTable)
				boDom = newDataIsland.find(DISLAND.BOLIST + "[" + DISLAND.BOLIST_ID + "='" + boId + "']");
			else if(isTree)
				boDom = newDataIsland.find(DISLAND.TREE + "[" + DISLAND.TREE_CHILDREN_ID + "='" + boId + "']");
			//表单
			else
				boDom = newDataIsland.find(DISLAND.BO + "[" + DISLAND.BO_ID + "='" + boId + "']");
			
			var targetContainer = ContainerHandlerFactory.createContainerHandler(boDom);
			targetContainer.fresh();
	    };
    }
    
    /**
     * 获取Ga的参数
     */
    this._getParams = function(observable, bcName){    	
		return pageFlow.sourceName + paramValue(observable.containerName)
				+ param(GA.operation) + this._method
				+ param(GA.dataIsland) + paramValue(xmlToString(DISLAND.getDataIsland())) 
				+ param(GA.targetName) + bcName;
    };
};

extend(ContainerObserver, Observer);