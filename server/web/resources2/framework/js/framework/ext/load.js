//加载脚本
function addDomListener() {
	containerFresh();
	addFCListener();
	addBtnListener();
	onload();
}

//遍历所有bo/boList,为对应组件加载数据
function containerFresh() {
	getDataIslandByJQ().find(DISLAND.BO + "," + DISLAND.BOLIST).each(function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
		containerHandler.init();
		containerHandler.freshMyself();
	});
}

/**
 * 遍历所有bop,为对应组件追加事件
 * @param containerId 在tab组件中使用该参数, 代表一个sheet中的一个container
 */
function addFCListener(containerId) {
	var bopList;
	if(!containerId) {
		bopList = getDataIslandByJQ().find(DISLAND.BOP)
	}
	else { 
		bopList = DISLAND.getBODom(containerId).find(DISLAND.BOP);
	}
		
	var sourceBtnArray = new Array();
	bopList.each(function() {
		var fcHandler = FCHandlerFactory.createFCHandler($(this));
		if(fcHandler instanceof FileHandler) 
			fcHandler.loadSign = true;
		
		fcHandler.init();
			
		//当细粒度组件拥有sourceBtn时，将其与相关细粒度组件的关联关系添加到数组中
		if(DISLANDTODOM.hasSourceBtn(fcHandler.bopDom))
			sourceBtnArray.push(DISLANDTODOM.getSourceBtn(fcHandler.bopDom));		
	});

	//将所有与sourceBtn关联的细粒度组件设置为只读
	for(var i = 0, len = sourceBtnArray.length; i < len; i++){
		DISLANDTODOM.setReadonlyBySbopIds(sourceBtnArray[i]);
	}
}

/**
 * 遍历所有operate,为对应按钮追加事件
 * @param containerId 在tab组件中使用该参数, 代表一个sheet中的一个container
 */
function addBtnListener(containerId) {
	var operateList;
	if(!containerId) {
		operateList = getDataIslandByJQ().find(DISLAND.OPERATE);
	}
	else { 
		operateList = DISLAND.getOperateInCon(containerId);
	}
	
	var arrOpt = [];
	operateList.each(function() {
		//如果是表格行级按钮(用expend修饰的按钮)或带有<qeweb:source>的按钮,则不再此处追加事件
		if($(this).attr(DISLAND.OPERATE_EXPEND) || DISLAND.getSourceOptDom($(this)))
			return true;
		
		var btnId = $(this).attr(DISLAND.OPERATE_ID);
		if(!!arrOpt[btnId])
			return true;
		
		arrOpt[btnId] = 'true';
		btnHandler = new ButtonHandler($(this));
		btnHandler.init();
	});
}


/**
 * 处理页面加载后的操作，执行<qeweb:page onload 中的方法，用于执行查询或刷新表格操作
 * @param sheetId 在tab组件中使用该参数, 代表一个sheet中的一个container
 */
function onload(sheetId){
	var operate = getDataIslandByJQ().find(DISLAND.PAGE).attr(DISLAND.PAGE_ONLOAD);
	if(!operate)
		return;

	//标签页初始化标记
	var sheetLoadArr = [];
	
	var arr = operate.split(';');
	for(var i = 0; i < arr.length; i++){
		var obj = arr[i].split(DISLAND.SPLIT_POINT);
		if(!Ext.getCmp(obj[0]))
			continue;
		
		var boName = Ext.getCmp(obj[0]).name;
		var btnId = (boName + DISLAND.SPLIT_LINE + obj[1]);
		//如果是系统刷新方法，并且组件为表格，初始化时自动刷新表格
		if(BoFinalMethod.isSysFresh(obj[1]) && CommonDom.isTable(Ext.getCmp(obj[0]))) {
			//未使用标签页或首次初始化标签页
			if(!sheetId || !sheetLoadArr[sheetId]) {
				var bolist = DISLAND.getBOListDom(obj[0]);
				var tableHandler = ContainerHandlerFactory.createContainerHandler(bolist);
				tableHandler.reload();
			}
		}
		else if(Ext.getCmp(btnId)) {
			//未使用标签页或首次初始化标签页
			if(!sheetId || !sheetLoadArr[sheetId]) {
				Ext.getCmp(btnId).fireEvent("click", obj);
			}
		}
	}
	
	//sheetId指代的标签页已经初始化
	if(sheetId)
		sheetLoadArr[sheetId] = true;
}