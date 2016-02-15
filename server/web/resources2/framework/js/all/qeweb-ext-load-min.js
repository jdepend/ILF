
function addDomListener(){containerFresh();addFCListener();addBtnListener();onload();}
function containerFresh(){getDataIslandByJQ().find(DISLAND.BO+","+DISLAND.BOLIST).each(function(){var containerHandler=ContainerHandlerFactory.createContainerHandler($(this));containerHandler.init();containerHandler.freshMyself();});}
function addFCListener(containerId){var bopList;if(!containerId){bopList=getDataIslandByJQ().find(DISLAND.BOP)}
else{bopList=DISLAND.getBODom(containerId).find(DISLAND.BOP);}
var sourceBtnArray=new Array();bopList.each(function(){var fcHandler=FCHandlerFactory.createFCHandler($(this));fcHandler.init();if(DISLANDTODOM.hasSourceBtn(fcHandler.bopDom))
sourceBtnArray.push(DISLANDTODOM.getSourceBtn(fcHandler.bopDom));});for(var i=0,len=sourceBtnArray.length;i<len;i++){DISLANDTODOM.setReadonlyBySbopIds(sourceBtnArray[i]);}}
function addBtnListener(containerId){var operateList;if(!containerId){operateList=getDataIslandByJQ().find(DISLAND.OPERATE);}
else{operateList=DISLAND.getOperateInCon(containerId);}
var arrOpt=[];operateList.each(function(){if($(this).attr(DISLAND.OPERATE_EXPEND)||DISLAND.getSourceOptDom($(this)))
return true;var btnId=$(this).attr(DISLAND.OPERATE_ID);if(!!arrOpt[btnId])
return true;arrOpt[btnId]='true';btnHandler=new ButtonHandler($(this));btnHandler.init();});}
function onload(sheetId){var operate=getDataIslandByJQ().find(DISLAND.PAGE).attr(DISLAND.PAGE_ONLOAD);if(!operate)
return;var sheetLoadArr=[];var arr=operate.split(';');for(var i=0;i<arr.length;i++){var obj=arr[i].split(DISLAND.SPLIT_POINT);if(!Ext.getCmp(obj[0]))
continue;var boName=Ext.getCmp(obj[0]).name;var btnId=(boName+DISLAND.SPLIT_LINE+obj[1]);if(BoFinalMethod.isSysFresh(obj[1])&&CommonDom.isTable(Ext.getCmp(obj[0]))){if(!sheetId||!sheetLoadArr[sheetId]){var bolist=DISLAND.getBOListDom(obj[0]);var tableHandler=ContainerHandlerFactory.createContainerHandler(bolist);tableHandler.reload();}}
else if(Ext.getCmp(btnId)){if(!sheetId||!sheetLoadArr[sheetId]){Ext.getCmp(btnId).fireEvent("click",obj);}}}
if(sheetId)
sheetLoadArr[sheetId]=true;}