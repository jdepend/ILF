//加载脚本
$().ready(function() {
	containerFresh();
	addFCListener();
	addBtnListener();
	onload();
});

//遍历所有bo/boList,为对应组件加载数据
function containerFresh() {

	getDataIslandByJQ().find(DISLAND.BO + "," + DISLAND.BOLIST).each(function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
		containerHandler.init();
		containerHandler.freshMyself();
	});

	/*
	getDataIslandByJQ().find(DISLAND.BO).each(function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
		containerHandler.freshMyself();
	});
	
	getDataIslandByJQ().find(DISLAND.BOLIST).each(function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
		containerHandler.freshMyself();
	});
	*/
}

//遍历所有bop,为对应组件追加事件
function addFCListener() {
	var bopList = getDataIslandByJQ().find(DISLAND.BOP);
	bopList.each(function() {
		var fcHandler = FCHandlerFactory.createFCHandler($(this));
		fcHandler.init();
	});
}

//遍历所有operate,为对应按钮追加事件
function addBtnListener() {
	var operateList = getDataIslandByJQ().find(DISLAND.OPERATE);
	var arrOpt = [];
	operateList.each(function() {
		//用expend修饰的按钮不在此追加事件
		if(!$(this).attr(DISLAND.OPERATE_EXPEND)) {
			var btnId = $(this).attr(DISLAND.OPERATE_ID);
			if(!!arrOpt[btnId])
				return true;
			arrOpt[btnId] = 'true';
			btnHandler = new ButtonHandler($(this));
			btnHandler.init();
		}
	});
}

//处理页面初始化的操作
function onload(){
	var operate = getDataIslandByJQ().find(DISLAND.PAGE).attr(DISLAND.PAGE_ONLOAD);
	if(!operate)
		return;
	
	var arr = operate.split(';');
	for(var i = 0; i < arr.length; i++){
		var obj = arr[i].split(DISLAND.SPLIT_POINT);
		
		var boName = DISLANDTODOM.getContainerByDomID(obj[0]).attr(DISLAND.CONTAINER_ID);
		var btnId = (boName + DISLAND.SPLIT_LINE + obj[1]);
		
		if(BoFinalMethod.isSysFresh(obj[1])) {
			var bolist = DISLAND.getBOListDom(obj[0]);
			var tableHandler = ContainerHandlerFactory.createContainerHandler(bolist);
			tableHandler.reload();
		}
		else {
			$("#" + btnId).trigger("click");
		}
	}
}
