/**
 * 按钮事件的基类。
 * 按钮事件包括两类：1、执行JS方法；2、执行持久化方法。
 *
 * “执行页面跳转操作”通过GA和页面流控制，不体现在按钮事件JS中。
 * 每个按钮可以绑定1个行为.
 *
 */
function ButtonEvent() {
	this.operateDom;		//按钮operate Dom
	this.buttonDom;			//按钮的dom
	this.method;			//按钮绑定的方法名
	this.containerDom;		//按钮所在的粗粒度组件
	this.btnName;			//btnName
	this.boDom;				//按钮所属的BO或BOList
	this.groupFCHandlers = [];	//与按钮同一组的细粒度组件, FCHandler
	this.optDomArr = [];	//operateDom缓存, 使ButtonEvent.init()仅执行一次
	
	this.nextHandler = null;	//下一个处理者

	this.init = function(operateDom) {
		if(!!this.optDomArr[operateDom]) 
			return;
		
		this.operateDom = operateDom;
		this.method = DISLAND.operate_BOMethod(operateDom);
		this.buttonDom = DISLANDTODOM.getButton(operateDom);
		if(operateDom.attr(DISLAND.OPERATE_IDX)) {
			this.boDom = operateDom.parent().parent();
		}
		else {
			this.boDom = operateDom.parent();
		}
		
		this.containerDom = DISLANDTODOM.getContainer(this.boDom);
		this.btnName = DISLANDTODOM.getBtnName(operateDom);
		if(!!operateDom.attr(DISLAND.GROUPNAME))
			this.setRelations(operateDom.attr(DISLAND.GROUPNAME));
		
		this.optDomArr[operateDom] = 'true';
	};

	/**
	 * 设置下一个处理者
	 */
	this.setNextHandler = function(nextHandler) {
		this.nextHandler = nextHandler;
	};

	/**
	 * 设置containerDom, 当数据岛和Vc组件不能一一对应时使用(table的增/改/查看弹出框中)
	 */
	this.setContainerDom = function(container) {
		this.containerDom = container;
	};

	/**
	 * 处理请求，即执行按钮的行为，如果当前处理者不能处理，则向下流传给下一个处理者
	 * 子类将覆写该方法
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {};

	
	/**
	 * 获取Ga的参数
	 */
	this.getDataStr = function() {
		var sourceOptDom = DISLAND.getSourceOptDom(this.operateDom)  
		
		//按钮所在粗粒度组件的boId
		var sourceName = "";
		//sourceOptDom的bindBop属性
		var bindBop = "";
		
		//按钮存在<qeweb:source>子标签时,页面流将根据bindBo和bindBop查找   
		if(sourceOptDom) {
			sourceName = sourceOptDom.attr(DISLAND.SOURCE_BINDBO);
			bindBop = sourceOptDom.attr(DISLAND.SOURCE_BINDBOP);
		}
		else {
			sourceName = this.containerDom ? this.containerDom.attr("name") : this.boDom.attr(DISLAND.BIND);
		}
		
		return pageFlow.sourceName + sourceName
			+ param(GA.operation) + this.method
			+ param(pageFlow.btnName) + this.btnName
			+ param(pageFlow.sourcePage) + DISLAND.getSourcepage()
			+ param(pageFlow.contextOperate) + this.method
			+ param(GA.relations) + arrayToStr(DISLAND.getRelationBo(this.boDom))
			+ this.submitDataisland();
	};
	
	/**
	 * 按钮提交时传递给GA的数据岛, 各子类可根据需要覆写该方法,
	 * 如: 查询时需要传递整个页面的数据岛; 普通的表单提交仅需要传递按钮所在BO的数据岛
	 * submitDataisland供getDataStr()使用, 默认传递整个数据岛
	 */
	this.submitDataisland = function() {
		return param(GA.dataIsland) + paramValue(xmlToString(getDataIslandByJQ()));
	};
	
	/**
	 * 获取与按钮同组的组件
	 * @param {} groupName
	 */
	this.setRelations = function(groupName){
		//1.getSameGroupDoms;
		var groupDoms = DISLAND.getFCGroupDoms(groupName, this.boDom.attr(DISLAND.BO_ID));
		if(groupDoms.length == 0)
			return;

		//2.groupFCHandlers
		var buttonEvent = this;
		for(var i = 0, length = groupDoms.length; i < length; i++) {
			if(groupDoms[i].is(DISLAND.BOP))
				buttonEvent.groupFCHandlers.push(FCHandlerFactory.createFCHandler(groupDoms[i]));
		}
	}

	/**
	 * 按钮无效化事件
	 */
	this.disable = function(){
		this.buttonDom.attr("disabled", true);
	};

	/**
	 * 取消按钮无效化
	 */
	this.unDisable = function(){
		this.buttonDom.attr("disabled", false);
	};
}

