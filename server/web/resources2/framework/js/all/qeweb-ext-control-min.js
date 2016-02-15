
function BtnExeEvent(){BtnExeEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);var btnEvent;if(CommonDom.isForm(this.containerDom)){if(BoFinalMethod.isQuery(this.method))
btnEvent=new BtnFormQueryEvent();else if(BoFinalMethod.isClear(this.method)||BoFinalMethod.isReset(this.method))
btnEvent=new BtnFormClearEvent();else
btnEvent=new BtnFormExeEvent();btnEvent.handleRequest(operateDom);}
else if(CommonDom.isTable(this.containerDom)){if(BoFinalMethod.isQuery(this.method)){showMSG.showErr(I18N.ERR_TABLE_QUERY);}
else if(BoFinalMethod.isClear(this.method)){showMSG.showErr(I18N.ERR_TABLE_CLEAR);}
else if(BoFinalMethod.isReset(this.method)){this.containerDom.getStore().rejectChanges();}
else if(BoFinalMethod.isInsert(this.method)||BoFinalMethod.isUpdate(this.method)||BoFinalMethod.isView(this.method)){;}
else{btnEvent=new BtnTableExeEvent();btnEvent.handleRequest(operateDom);}}
else if(CommonDom.isTree(this.containerDom)){if(BoFinalMethod.isQuery(this.method)){showMSG.showErr(I18N.ERR_TREE_QUERY);}
else if(BoFinalMethod.isClear(this.method)){showMSG.showErr(I18N.ERR_TREE_CLEAR);}
else{btnEvent=new BtnTreeExeEvent();btnEvent.handleRequest(operateDom);}}
else if(CommonDom.isTab(this.containerDom)){if(BoFinalMethod.isQuery(this.method)){showMSG.showErr(I18N.ERR_TAB_QUERY);}
else{btnEvent=new BtnTabExeEvent();btnEvent.handleRequest(operateDom);}}
else{if(BoFinalMethod.isQuery(this.method)){showMSG.showErr(I18N.ERR_PAGE_QUERY);}
else if(BoFinalMethod.isClear(this.method)){showMSG.showErr(I18N.ERR_PAGE_CLEAR);}
else{btnEvent=new BtnPageExeEvent();btnEvent.handleRequest(operateDom);}}};this.validate=function(){var containerHandler=ContainerHandlerFactory.createContainerHandler(this.boDom);return containerHandler.validate(this.containerDom);};}
extend(BtnExeEvent,ButtonEvent);BtnExeEvent.prototype.bindData=function(operateDom){};BtnExeEvent.prototype.execute=function(operateDom){var beforeMethodArr=DISLANDTODOM.getBeforeBoMethod(operateDom);if(!beforeMethodArr||beforeMethodArr.length<=1){this._exeMethod(operateDom);}
else{this._exeBeforeMethod(operateDom,beforeMethodArr,0);}};BtnExeEvent.prototype._exeMethod=function(operateDom){var result=true;this.bindData(operateDom);lockSrceen();Ext.Ajax.request({url:appConfig.ctx+actionURL.getGaExe(),method:"POST",params:this.getDataStr(),timeout:appConfig.ajaxTimeout,success:function(response){validateSession(response.responseText);var msg=response.responseText;var resultMsg=new ResultMsg(msg);var btnJSEventAfterExe=new BtnJSEventAfterExe(operateDom);var pageFlow=new PageFlow(msg);resultMsg.setNextHandler(btnJSEventAfterExe);btnJSEventAfterExe.setNextHandler(pageFlow);result=resultMsg.handleRequest(operateDom);},failure:function(response){result=false;showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});return result;};BtnExeEvent.prototype._exeBeforeMethod=function(operateDom,beforeMethodArr,index){if(index==beforeMethodArr.length-1){var afterMethodArr=DISLANDTODOM.getAfterBoMethod(operateDom);var afterMedhodStr=afterMethodArr.join(",");var methodStr=beforeMethodArr[index];if(!!afterMedhodStr)
methodStr+=","+afterMedhodStr;var cloneOptDom=DISLAND.getCloneOptDom(operateDom);DISLAND.operate_setBOMethod(cloneOptDom,methodStr);var btnEvent=this.getNewEvent(cloneOptDom);btnEvent._exeMethod(cloneOptDom);return;}
var cloneOptDom=operateDom.clone();var optSaveModArr=DISLANDTODOM.getOptSaveMod(operateDom);var btnSaveMod=operateDom.attr(DISLAND.OPERATE_SAVEMOD);DISLAND.operate_setBOMethod(cloneOptDom,beforeMethodArr[index]);DISLAND.operate_setSaveMod(cloneOptDom,optSaveModArr[beforeMethodArr[index]]||btnSaveMod);var btnEvent=this.getNewEvent(cloneOptDom);btnEvent.bindData(cloneOptDom);var btnExeEvent=this;lockSrceen();Ext.Ajax.request({url:appConfig.ctx+actionURL.getGaExe(),method:"POST",params:btnEvent.getDataStr(),timeout:appConfig.ajaxTimeout,async:false,success:function(response){validateSession(response.responseText);var msg=response.responseText;var resultMsg=new ResultMsg(msg);var btnJSEventAfterExe=new BtnJSEventAfterExe(cloneOptDom);resultMsg.setNextHandler(btnJSEventAfterExe);if(resultMsg.handleRequest(cloneOptDom)){btnExeEvent._exeBeforeMethod(operateDom,beforeMethodArr,++index);}},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});};BtnExeEvent.prototype.getNewEvent=function(operateDom){return null;};
function BtnFormClearEvent(){BtnFormClearEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);this.clear();}}
extend(BtnFormClearEvent,BtnFormExeEvent);BtnFormClearEvent.prototype.clear=function(){this.containerDom.getForm().reset();};
function BtnFormExeEvent(){BtnFormExeEvent.superclass.constructor.call(this);this.panelType="";this.handleRequest=function(operateDom){this.init(operateDom);if(operateDom.attr(DISLAND.OPERATE_NOTSUBMIT)=='true')
this.execute(operateDom);else if(this.validate())
this.execute(operateDom);};this.submitDataisland=function(){return param(GA.dataIsland)+paramValue(xmlToString(this.boDom));};}
extend(BtnFormExeEvent,BtnExeEvent);BtnFormExeEvent.prototype.setPanelType=function(panelType){this.panelType=panelType;};BtnFormExeEvent.prototype.bindData=function(){BINDDATA.bindBO(this.boDom,this.method,this.panelType);BINDDATA.bindOperate(this.boDom,this.method);};
function BtnFormExportEvent(){BtnFormExportEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(BoFinalMethod.isExport(this.method)&&this.validate())
this.execute(operateDom);else if(this.nextHandler)
this.nextHandler.handleRequest(operateDom);};this.submitDataisland=function(){return param(GA.dataIsland)+paramValue(xmlToString(this.boDom));};}
extend(BtnFormExportEvent,BtnFormExeEvent);BtnFormExportEvent.prototype.execute=function(operateDom){this.bindData();var sourceName=this.containerDom?this.containerDom.name:this.boDom.attr(DISLAND.BIND);var url=appConfig.ctx+actionURL.doExport()
+param(pageFlow.sourceName)+sourceName
+param(GA.operation)+this.method
+param(pageFlow.btnName)+this.btnName
+param(pageFlow.sourcePage)+DISLAND.getSourcepage()
+param(pageFlow.contextOperate)+this.method
+param(GA.relations)+arrayToStr(DISLAND.getRelationBo(this.boDom));var formA=document.getElementById("exportForm");var dataIsland=document.getElementById('dataIsland');var paramVal=xmlToString(getDataIslandByJQ());if(formA){document.body.removeChild(formA);}
formA=document.createElement('form');dataIsland=document.createElement('input');formA.setAttribute("id","exportForm");formA.setAttribute("method","post");formA.setAttribute("action",url);dataIsland.setAttribute("value",paramVal);dataIsland.setAttribute("id","dataIsland");dataIsland.setAttribute("name","dataIsland");dataIsland.setAttribute("type","hidden");formA.appendChild(dataIsland);document.body.appendChild(formA);formA.submit();};
function BtnFormQueryEvent(){this.sourceName='';BtnFormQueryEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);var boId=operateDom.parent().attr(DISLAND.BO_ID);if(observableArr[boId]&&this.validate()){lockSrceen();this.bindData();observableArr[boId].notify();}};}
extend(BtnFormQueryEvent,BtnFormExeEvent);
function BtnJSEvent(){BtnJSEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);var jsMethods=operateDom.attr(DISLAND.OPERATE_JS_BEFORE);var result=true;if(jsMethods){this.disable();var methodArr=DISLANDTODOM.getMethodArr(jsMethods);if(methodArr.length>0){this.bindData();for(var i=0,length=methodArr.length;i<length;i++){result=eval(methodArr[i]);}}
this.unDisable();}
if(result!=false&&this.nextHandler)
this.nextHandler.handleRequest(operateDom);};}
extend(BtnJSEvent,ButtonEvent);BtnJSEvent.prototype.bindData=function(){if(this.boDom.is(DISLAND.BO))
BINDDATA.bindBO(this.boDom,this.method);else if(this.boDom.is(DISLAND.BOLIST))
BINDDATA.bindBOList(this.boDom,this.operateDom);};
function BtnJSEventAfterExe(){BtnJSEventAfterExe.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);var jsMethods=operateDom.attr(DISLAND.OPERATE_JS_AFTER);var result=true;if(jsMethods){this.disable();var methodArr=DISLANDTODOM.getMethodArr(jsMethods);if(methodArr.length>0){this.bindData();for(var i=0,length=methodArr.length;i<length;i++){result=eval(methodArr[i]);}}
this.unDisable();}
if(result!=false&&this.nextHandler)
this.nextHandler.handleRequest(operateDom);};}
extend(BtnJSEventAfterExe,ButtonEvent);BtnJSEventAfterExe.prototype.bindData=function(){if(this.boDom.is(DISLAND.BO))
BINDDATA.bindBO(this.boDom,this.method);else if(this.boDom.is(DISLAND.BOLIST))
BINDDATA.bindBOList(this.boDom,this.operateDom);};
function BtnPageExeEvent(){BtnPageExeEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(operateDom.attr(DISLAND.OPERATE_NOTSUBMIT)=='true')
this.execute(operateDom);else if(this.validate())
this.execute(operateDom);}}
extend(BtnPageExeEvent,BtnExeEvent);BtnPageExeEvent.prototype.bindData=function(operateDom){BINDDATA.bindPage(operateDom);};
function BtnSysAddRowEvent(){BtnSysAddRowEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(BoFinalMethod.isAddRow(this.method)||BoFinalMethod.isDelRow(this.method))
this.execute();else if(this.nextHandler)
this.nextHandler.handleRequest(operateDom);};}
extend(BtnSysAddRowEvent,BtnExeEvent);BtnSysAddRowEvent.prototype.bindData=function(operateDom){BINDDATA.bindBOList(this.boDom,operateDom);};BtnSysAddRowEvent.prototype.execute=function(){var handler=new SysAddRowHandler(this.boDom);if(BoFinalMethod.isAddRow(this.method))
handler.addRow();else
handler.delRow();};
function BtnTabExeEvent(){BtnTabExeEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(this.validate())
this.execute(operateDom);};}
extend(BtnTabExeEvent,BtnExeEvent);BtnTabExeEvent.prototype.bindData=function(){BINDDATA.bindBO(this.boDom,this.method);BINDDATA.bindOperate(this.boDom,this.method);};
function BtnTableExeEvent(){BtnTableExeEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);this.execute(operateDom);};}
extend(BtnTableExeEvent,BtnExeEvent);BtnTableExeEvent.prototype.bindData=function(operateDom){BINDDATA.bindBOList(this.boDom,operateDom);};BtnTableExeEvent.prototype.getNewEvent=function(operateDom){var cloneObj=new BtnTableExeEvent();cloneObj.operateDom=operateDom;cloneObj.method=DISLAND.operate_BOMethod(operateDom);cloneObj.buttonDom=this.buttonDom;cloneObj.containerDom=this.containerDom;cloneObj.btnName=this.btnName;cloneObj.boDom=this.boDom;cloneObj.groupFCHandlers=this.groupFCHandlers;cloneObj.optDomArr=this.optDomArr;cloneObj.nextHandler=this.nextHandler;return cloneObj;};
var BtnTablePopuEvent={validate:function(operateId){var formPanelDom=DISLAND.getFormPanelDom(operateId);if(!formPanelDom)
return false;var handler=ContainerHandlerFactory.createContainerHandler(formPanelDom);var operateDom=DISLAND.getBOOperateDom(operateId,formPanelDom);return handler.validate(operateDom.attr(DISLAND.OPERATE_METHOD));},handle:function(operateId){var formPanelDom=DISLAND.getFormPanelDom(operateId);var btnExe=new BtnFormExeEvent();btnExe.setPanelType(DISLANDTODOM.getBtnMethod(operateId));btnExe.handleRequest(DISLAND.getBOOperateDom(operateId,formPanelDom));},clear:function(operateId){var formPanel=formPanelArr[operateId];formPanel.getForm().reset();},reset:function(operateId){var formPanelDom=DISLAND.getFormPanelDom(operateId);var formPanel=formPanelArr[operateId];this._fcUpdate(formPanel,formPanelDom);},sysWinInit:function(boDom,sysOperate){var sourceBtnArray=new Array();boDom.children(DISLAND.BOP).each(function(){var fc=DISLANDTODOM.getWinFC($(this),sysOperate);var fcHandler=FCHandlerFactory.createFCHandler($(this),fc);fcHandler.init();if(DISLANDTODOM.hasSourceBtn($(this),sysOperate))
sourceBtnArray.push(DISLANDTODOM.getSourceBtn($(this),sysOperate));});for(var i=0,len=sourceBtnArray.length;i<len;i++){DISLANDTODOM.setReadonlyBySbopIds(sourceBtnArray[i],sysOperate,boDom);}},_fcUpdate:function(formPanel,formPanelDom){formPanel.items.each(function(item){var bopDom=DISLAND.getFormPanelBOPDom(formPanelDom,item.getId());var fcHandler=FCHandlerFactory.createFCHandler(bopDom,item);fcHandler.update();});}};
var BtnTableRowEvent={boDom:"",formPanel:"",recordWin:"",handle:function(rowBtn){var btn=$(rowBtn);var boListId=DISLANDTODOM.getContainerIdByBtnId(btn.attr('name'));var bo=DISLAND.getBOListDom(boListId).children(DISLAND.BO+"["+DISLAND.BO_INDEX+"="+btn.attr('idx')+"]");var operateDom=DISLAND.getRowOperate(bo,btn.attr('name'));if(operateDom.length<0)
return;var isUpdate=BoFinalMethod.isUpdate(operateDom.attr(DISLAND.OPERATE_METHOD));if(isUpdate||BoFinalMethod.isView(operateDom.attr(DISLAND.OPERATE_METHOD))){recordWinIdx=DISLANDTODOM.getRecordWinIdx(operateDom);this.formPanel=formPanelArr[recordWinIdx];this.recordWin=recordWinArr[recordWinIdx];this.popuRecordWin(operateDom,isUpdate);}
else{var btnJsEvent=new BtnJSEvent();var expEvent=new BtnFormExportEvent();var confirmEvent=new ConfirmEvent();var btnRowEditEvent=new BtnSysAddRowEvent();var btnExeEvent=new BtnExeEvent();btnJsEvent.setNextHandler(expEvent);expEvent.setNextHandler(confirmEvent);confirmEvent.setNextHandler(btnRowEditEvent);btnRowEditEvent.setNextHandler(btnExeEvent);btnJsEvent.handleRequest(operateDom);}},popuRecordWin:function(operateDom,isUpdate){lockSrceen();this.boDom=formDislandlArr[DISLANDTODOM.getRecordWinIdx(operateDom)];var data=this.createRecordParam(operateDom);var btnTableRowEvent=this;$.ajax({type:"POST",url:appConfig.ctx+actionURL.getGaRecord(),data:data,success:function(data){btnTableRowEvent.loadData(data,isUpdate);unlockScreen();}});},loadData:function(data,isUpdate){if(!data){showMSG.showWaring("该数据对应的ID在数据库中不存在");return;}
var jsonData=StringToJSON(data).data;if(!jsonData){showMSG.showErr(I18N.MSG_GETRECORD_FAILURE);return;}
try{this.recordWin.show(Ext.getBody());}
catch(e){showMSG.showErr('table标签没有配置弹出框，请检查display属�?，其格式是：field1=table:edit,insert,update,view;field2=insert');return;}
var boDom=this.boDom;this.formPanel.items.each(function(item){var bopDom=DISLAND.getFormPanelBOPDom(boDom,CommonDom.getFcId(item));if(bopDom.length==0)
return true;var fcHandler=FCHandlerFactory.createFCHandler(bopDom,item);var bopBind=bopDom.attr(DISLAND.BIND).replaceAll("\\.","#");var value=jsonData[bopBind];var disable=jsonData[bopBind+'-disable'];var hidden=jsonData[bopBind+'-hidden'];var readonly=jsonData[bopBind+'-readonly'];if(value&&value.time&&(fcHandler instanceof DateHandler)){var date;date=new Date(value.time);date.setHours(value.hours);date.setMinutes(value.minutes);date.setSeconds(value.seconds);value=date.pattern("yyyy-MM-dd");}
else if(value===0){value='0';}
fcHandler.setValue(value);if(isUpdate)
fcHandler.updateStatus(disable,hidden,readonly);bopDom.find(DISLAND.BOP_VALUE).text(specialCharHandler(value));});},createRecordParam:function(operateDom){var btnEvent=new ButtonEvent();btnEvent.init(operateDom);return pageFlow.sourceName+btnEvent.boDom.attr(DISLAND.BIND)
+param(GA.recordId)+DISLANDTODOM.getRecordId(operateDom)
+param(GA.operation)+BoFinalMethod.RECORD
+param(GA.winType)+btnEvent.method
+param(pageFlow.btnName)+btnEvent.btnName
+param(pageFlow.sourcePage)+DISLAND.getSourcepage();}};
function BtnTreeExeEvent(){BtnTreeExeEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(this.validate())
this.execute(operateDom);};this.submitDataisland=function(){return param(GA.dataIsland)+paramValue(xmlToString(this.boDom));};}
extend(BtnTreeExeEvent,BtnExeEvent);BtnTreeExeEvent.prototype.bindData=function(){BINDDATA.bindBO(this.boDom,this.method);BINDDATA.bindOperate(this.boDom,this.method);};
function ButtonEvent(){this.operateDom;this.buttonDom;this.method;this.containerDom;this.btnName;this.boDom;this.groupFCHandlers=[];this.optDomArr=[];this.nextHandler=null;this.init=function(operateDom){if(!!this.optDomArr[operateDom])
return;this.operateDom=operateDom;this.method=DISLAND.operate_BOMethod(operateDom);this.buttonDom=DISLANDTODOM.getButton(operateDom);if(operateDom.boDom){this.boDom=operateDom.boDom;}
else if(operateDom.attr(DISLAND.OPERATE_IDX)){this.boDom=operateDom.parent().parent();}
else{this.boDom=operateDom.parent();}
this.containerDom=DISLANDTODOM.getContainer(this.boDom);this.btnName=DISLANDTODOM.getBtnName(operateDom);if(!!operateDom.attr(DISLAND.GROUPNAME))
this.setRelations(operateDom.attr(DISLAND.GROUPNAME));this.optDomArr[operateDom]='true';};this.setNextHandler=function(nextHandler){this.nextHandler=nextHandler;};this.setContainerDom=function(container){this.containerDom=container;};this.handleRequest=function(operateDom){};this.getDataStr=function(){var sourceName="";sourceName=this.containerDom?this.containerDom.name:this.boDom.attr(DISLAND.BIND);return pageFlow.sourceName+sourceName
+param(GA.operation)+this.method
+param(pageFlow.btnName)+this.btnName
+param(pageFlow.sourcePage)+DISLAND.getSourcepage()
+param(pageFlow.contextOperate)+this.method
+param(GA.relations)+arrayToStr(DISLAND.getRelationBo(this.boDom))
+this.submitDataisland();};this.submitDataisland=function(){return param(GA.dataIsland)+paramValue(xmlToString(getDataIslandByJQ()));};this.setRelations=function(groupName){var groupDoms=DISLAND.getFCGroupDoms(groupName,this.boDom.attr(DISLAND.BO_ID));if(groupDoms.length==0)
return;var buttonEvent=this;for(var i=0,length=groupDoms.length;i<length;i++){if(groupDoms[i].is(DISLAND.BOP)){buttonEvent.groupFCHandlers.push(FCHandlerFactory.createFCHandler(groupDoms[i]));}}};this.disable=function(){};this.unDisable=function(){};};
function ButtonHandler(operateDom){this.btn=DISLANDTODOM.getButton(operateDom);this.init=function(){this.addEvent();};this.addEvent=function(){var btnJsEvent=new BtnJSEvent();var expEvent=new BtnFormExportEvent();var confirmEvent=new ConfirmEvent();var btnRowEditEvent=new BtnSysAddRowEvent();var btnExeEvent=new BtnExeEvent();btnJsEvent.setNextHandler(expEvent);expEvent.setNextHandler(confirmEvent);confirmEvent.setNextHandler(btnRowEditEvent);btnRowEditEvent.setNextHandler(btnExeEvent);if(this.btn){var handle=function(){btnJsEvent.handleRequest(operateDom);};if(this.btn.type=="button")
this.btn.on('click',handle);else
this.btn.onclick=handle;}};this.update=function(){this.updateStatus();};this.updateStatus=function(){this._setDisabled(operateDom.attr(DISLAND.STATUS_DISABLE));this._setHidden(operateDom.attr(DISLAND.STATUS_HIDDEN));};this._setDisabled=function(isDisabled){if(!!!this.btn)
return;if(this.btn.type=="button"){if(isDisabled==="true")
this.btn.setDisabled(true);else
this.btn.setDisabled(false);}};this._setHidden=function(isHidden){if(!!!this.btn)
return;if(this.btn.type=="button"){if(isHidden==="true")
this.btn.hide();else
this.btn.show();}};};
var CellEditObservable=function(win){CellEditObservable.superclass.constructor.call(this);this.win=win;this.formHandler;this._getFormHandler=function(tableId,bopBind){if(!this.formHandler){var bolist=DISLAND.getDom(tableId);var formDom=DISLAND.createCellEditFormDom(bolist,bopBind);this.formHandler=ContainerHandlerFactory.createContainerHandler(formDom);}
return this.formHandler;};this.addObserver=function(observer){this.formHandler=this._getFormHandler(observer.tableId,observer.bopBind);this.observers=[];this.observers.push(observer);};this.notify=function(data){if(!this.formHandler.validate())
return;var $super=new Observable();$super.notify.call(this,this.win.returnData);};this.callback=function(){this.win.hide();};};extend(CellEditObservable,Observable);
var CellEditObserver=function(){CellEditObserver.superclass.constructor.call(this);this.tableId;this.boIndex;this.bopBind;this.editParam;this.update=function(observable,data){var bolist=DISLAND.getDom(this.tableId);var bo=bolist.children(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+this.boIndex+"']").slice(0);var bop=bo.children(DISLAND.BOP+"["+DISLAND.BIND+"='"+this.bopBind+"']").slice(0);var curGrid=Ext.getCmp(this.tableId);var store=curGrid.getStore();var record=store.getAt(this.boIndex-1);if(data){bop.attr(DISLAND.BOP_DATA_URL,data.filePath);bop.text(data.fileName);var columnInfo=bolist.children(DISLAND.COLUMNINFO_COLUMN).slice(0);record.set(this.bopBind,data.fileName);this.editParam.value=data.fileName;TableHelper.formatCellForEditRow(bolist,bo,this.editParam);}
observable.callback();};};extend(CellEditObserver,Observer);
function ConfirmEvent(){ConfirmEvent.superclass.constructor.call(this);this.handleRequest=function(operateDom){this.init(operateDom);if(!this.containerDom)
this.pageOperate(operateDom);else if(CommonDom.isTable(this.containerDom))
this.tableOperate(operateDom);else if(CommonDom.isForm(this.containerDom))
this.formOperate(operateDom);else
this.confirm(operateDom);};}
extend(ConfirmEvent,ButtonEvent);ConfirmEvent.prototype._isCheckedOnThisPage=function(container){var records=container.getSelectionModel().getSelections();if(records.length==0){alertMsg.choseRecord(container.title);return false;}
return true;};ConfirmEvent.prototype._isCheckedInHistory=function(container,containerId){var boListId="";if(container)
boListId=container.getId();else
boListId=containerId;var checkedDom=DISLAND.getCheckedDom(DISLAND.getBOListDom(boListId));if(!checkedDom)
return true;return!!checkedDom.text();};ConfirmEvent.prototype._isModifyOnThisPage=function(container){return true;};ConfirmEvent.prototype.confirm=function(operateDom){var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var confirmDisplay=pageDom.attr(DISLAND.CONFIRM_DISPLAY);var hasConfirm=operateDom.attr(DISLAND.OPERATE_HASCONFIRM);var confirmMsg=operateDom.attr(DISLAND.OPERATE_CONFIRMMSG)||I18N.CONFIRM_OPERATE;if(!this.nextHandler)
return;if(DISLAND.CONFIRM_DISPLAY_NO==confirmDisplay||(!hasConfirm&&!BoFinalMethod.isDelete(operateDom.attr(DISLAND.OPERATE_METHOD)))){this.nextHandler.handleRequest(operateDom);}
else{Ext.MessageBox.minWidth=showMSG.getMsgWidth(confirmMsg,280);Ext.Msg.confirm(I18N.CONFIRM_TIPS,showMSG.getMsg(confirmMsg),function(btn){displayStatus.changeConfirmStatus();if(btn=='yes'){this.nextHandler.handleRequest(operateDom);}},this);}};ConfirmEvent.prototype.pageOperate=function(operateDom){if(DISLANDTODOM.getSaveMod(operateDom)){if(DISLANDTODOM.pageBtn_isSelectMod(operateDom)){var containerIds=DISLANDTODOM.getSelectContainerId(operateDom);for(var i=0;i<containerIds.length;i++){if(this._isCheckedInHistory(null,containerIds[i]))
continue;else if(!this._isCheckedOnThisPage(Ext.getCmp(containerIds[i])))
return;}}
else if(DISLANDTODOM.pageBtn_isModifyMod(operateDom)){var containerIds=DISLANDTODOM.getModifyContainerId(operateDom);for(var i=0;i<containerIds.length;i++){if(!this._isModifyOnThisPage(Ext.getCmp(containerIds[i])))
return;}}
else if(DISLANDTODOM.pageBtn_isAdaptiveMod(operateDom)){this.confirm(operateDom);return;}
else if(DISLANDTODOM.btn_isAllMod(operateDom)||DISLANDTODOM.pageBtn_isAllMod(operateDom)){this.confirm(operateDom);return;}
else if(DISLANDTODOM.btn_isEmptyMod(operateDom)||DISLANDTODOM.pageBtn_isEmptyMod(operateDom)){this.confirm(operateDom);return;}
else if(DISLANDTODOM.btn_isSelectMod(operateDom)||DISLANDTODOM.btn_isAdaptiveMod(operateDom)||DISLANDTODOM.btn_isModifyMod(operateDom)||DISLANDTODOM.btn_isEmptyMod(operateDom)){Ext.Msg.alert(I18N.CONFIRM_TIPS,"全局按钮的saveMod格式错误�?br> 正确的格式：tableId.saveMod   �? table1.modify,table2.select,table3.empty");return;}
else{return;}}
this.confirm(operateDom);};ConfirmEvent.prototype.tableOperate=function(operateDom){if(DISLANDTODOM.isTableButton(operateDom)){var isSelectMod=DISLANDTODOM.btn_isSelectMod(operateDom);var isModifyMod=DISLANDTODOM.btn_isModifyMod(operateDom);if(isSelectMod){if(!this._isCheckedInHistory(this.containerDom)&&!this._isCheckedOnThisPage(this.containerDom)){alertMsg.choseRecord(this.containerDom.title);return;}}
else if(isSelectMod||BoFinalMethod.isDelete(this.method)){if(!this._isCheckedOnThisPage(this.containerDom))
return;}
else if(BoFinalMethod.isInsert(this.method)){return;}
else if(isModifyMod){if(!this._isModifyOnThisPage(this.containerDom))
return;}
else if(BoFinalMethod.isAddRow(this.method)||BoFinalMethod.isDelRow(this.method)){this.nextHandler.handleRequest(operateDom);return;}
else if(BoFinalMethod.isClear(this.method)){showMSG.showErr(I18N.ERR_TABLE_CLEAR);return;}
else if(BoFinalMethod.isReset(this.method)){this.containerDom.getStore().rejectChanges();return;}}
this.confirm(operateDom);};ConfirmEvent.prototype.formOperate=function(operateDom){if(BoFinalMethod.isQuery(this.method))
this.nextHandler.handleRequest(operateDom);else
this.confirm(operateDom);};
var ContainerObservable=function(){ContainerObservable.superclass.constructor.call(this);this.containerName;this.addObserver=function(observer,containerId){observer.containerId=containerId;this.observers.push(observer);};this.clearObserversData=function(){this.notify('clear');}};extend(ContainerObservable,Observable);
var ContainerObserver=function(){ContainerObserver.superclass.constructor.call(this);this.containerId;this._method='search';this.update=function(observable,data){this._query(observable);};this._query=function(observable){var observer=this;var boId=observer.containerId;var container=Ext.getCmp(boId);if(!container)
return;var isTable=CommonDom.isTable(container);var isTree=CommonDom.isTree(container);if(isTable){var handler=new TableHandler();handler.clearEdit(observer.containerId);}
var param=observer._getParams(observable,container.name);$.ajax({type:"POST",url:appConfig.ctx+actionURL.getGaSearch(),data:param,success:function(data){if(!data)
return;observer.containerFrsh(data,boId,isTable);var observable=observableArr[boId];if(observable){observable.clearObserversData();}
unlockScreen();}});this.containerFrsh=function(data,boId,isTable){validateSession(data);var newDataIsland=XMLDomFactory.getInstance(data).find(DISLAND.dataIsland);var boDom;if(isTable)
boDom=newDataIsland.find(DISLAND.BOLIST+"["+DISLAND.BOLIST_ID+"='"+boId+"']");else if(isTree)
boDom=newDataIsland.find(DISLAND.TREE+"["+DISLAND.TREE_CHILDREN_ID+"='"+boId+"']");else
boDom=newDataIsland.find(DISLAND.BO+"["+DISLAND.BO_ID+"='"+boId+"']");var targetContainer=ContainerHandlerFactory.createContainerHandler(boDom);targetContainer.fresh();};}
this._getParams=function(observable,bcName){return pageFlow.sourceName+paramValue(observable.containerName)
+param(GA.operation)+this._method
+param(GA.dataIsland)+paramValue(xmlToString(DISLAND.getDataIsland()))
+param(GA.targetName)+bcName;};};extend(ContainerObserver,Observer);
function PageFlow(msg){PageFlow.superclass.constructor.call(this);var jsonMst=StringToJSON(msg)||new Array();this.path=jsonMst[ConstantJSON.MSG_PATH];this.isPopUp=jsonMst[ConstantJSON.IS_POPUP];this.hasCloseBtn=jsonMst[ConstantJSON.HAS_CLOSEBTN];this.dialogWidth=jsonMst[ConstantJSON.DIALOG_WIDTH];this.dialogHeight=jsonMst[ConstantJSON.DIALOG_HEIGHT];this.dialogTitle=jsonMst[ConstantJSON.DIALOG_TITLE];this.msg=jsonMst[ConstantJSON.MSG];this.msgFlag=jsonMst[ConstantJSON.MSG_FLAG];this.closePage=jsonMst[ConstantJSON.CLOSE_PAGE];this.targetVC=jsonMst[ConstantJSON.TARGET_VC];this.closeTargetVC=jsonMst[ConstantJSON.CLOSE_TARGET_VC];this.parentContainerId=jsonMst[ConstantJSON.PAR_CON_ID];this.handleRequest=function(operateDom){this.init(operateDom);if(!!this.path&&this.path!='null'){this.redirect(operateDom);}
else if(this.targetVC||this.closePage){this.unDisable();this.refreshParentContainer();}
else if(operateDom.attr(DISLAND.OPERATE_JS_AFTER)||operateDom.attr(DISLAND.OPERATE_JS_BEFORE)){this.unDisable();unlockScreen();}
else if(!operateDom.attr(DISLAND.OPERATE_METHOD)){this.unDisable();unlockScreen();}};}
extend(PageFlow,ButtonEvent);PageFlow.prototype.redirect=function(operateDom){var path=this.path;var closePage=this.closePage;var paramData=this.getDataStr();if(this.msgFlag){if(!this.msg||this.msg=='null')
this.msg=I18N.MSG_OPERATION_SUCCESS;paramData=paramData+param(pageFlow.returnMsg)+encodeURIComponent(this.msg);}
if(this.isPopUp){unlockScreen();dialog.openMDialog(this,paramData);if(operateDom.attr(DISLAND.OPERATE_EXPEND)=="true")
operateDom.parent().attr(DISLAND.BO_OPERATIONSTATUS,DISLAND.BO_OPERATIONSTATUS_INIT);}
else{Ext.Ajax.request({url:appConfig.ctx+actionURL.saveParam(),method:"POST",params:paramData,timeout:appConfig.ajaxTimeout,success:function(response){if(closePage&&path){parent.window.location.href=appConfig.ctx+actionURL.getRedirect(path);parent.Ext.getCmp("qeweb-dialog").close();unlockScreen();}
else{window.location.href=appConfig.ctx+actionURL.getRedirect(path);}}});}};PageFlow.prototype.refreshParentContainer=function(closeTargetVC){var targetVC=closeTargetVC||this.targetVC||this.parentContainerId;if(!!closeTargetVC){BINDDATA.bindPage();}
var param={};param.paramData=this.getDataStr();param.closePage=this.closePage;param.scope=(param.closePage?parent.window:window);param.scopeDataIsland=xmlToString(param.scope.DISLAND.getDataIsland());param.sourceContainerName=DISLANDTODOM.getBoName(this.boDom);if(targetVC=="empty"){if(param.closePage){closeDialog(param.scope);}
else{unlockScreen();return;}}
var arr=[];if(isNotEmpty(targetVC))
arr=targetVC.split(",");freshTargetVC(arr,0,param);};function freshTargetVC(arr,idx,param){if(!arr||arr.length<0){return;}
if(idx<arr.length){var arr2=arr[idx].replace('.','#').split("#");var targetVCId=arr2[0];var targetVCmethod=(arr2.length>1?arr2[1]:"");Ext.Ajax.request({url:appConfig.ctx+actionURL.saveParam(),method:"POST",async:false,targetVCId:targetVCId,targetVCmethod:targetVCmethod,params:param.paramData,timeout:appConfig.ajaxTimeout,success:function(response,options){if(options.targetVCId=='page'){var pageHandler=new param.scope.PageHandler(param.scope.DISLAND.getDataIsland().find(DISLAND.PAGE));pageHandler.reload(options.targetVCmethod,param.sourceContainerName,param.scopeDataIsland);}
else{var boDom=param.scope.DISLAND.getDom(options.targetVCId);var containerHandler=param.scope.ContainerHandlerFactory.createContainerHandler(boDom);containerHandler.reload(options.targetVCmethod,param.sourceContainerName,param.scopeDataIsland);}
freshTargetVC(arr,idx+1,param);}});}
else{closeDialog(param.scope);}}
function closeDialog(scope){var dialog=scope.Ext.getCmp("qeweb-dialog");if(dialog)
dialog.close();}
function ResultMsg(msg){ResultMsg.superclass.constructor.call(this);var jsonMst=StringToJSON(msg)||new Array();this.success=jsonMst[ConstantJSON.MSG_SUCCESS];this.msg=jsonMst[ConstantJSON.MSG];this.path=jsonMst[ConstantJSON.MSG_PATH];this.closePage=jsonMst[ConstantJSON.CLOSE_PAGE];this.targetVC=jsonMst[ConstantJSON.TARGET_VC];this.handleRequest=function(operateDom){this.init(operateDom);if(!operateDom.attr(DISLAND.OPERATE_METHOD)&&this.nextHandler){this.nextHandler.handleRequest(operateDom);}
else if(!this.success){unlockScreen();if(!this.msg||this.msg=='null')
this.msg=I18N.MSG_OPERATION_FAILURE;ResultMessage.showMsg(this.success,this.msg,true,false);return false;}
else if(this.success&&(!this.path||this.path=='null')){unlockScreen();var containerHandler=ContainerHandlerFactory.createContainerHandler(operateDom.parent())
if(containerHandler instanceof TableHandler)
containerHandler.commitChanges();if(!this.msg||this.msg=='null')
this.msg=I18N.MSG_OPERATION_SUCCESS;if(this.nextHandler&&(this.closePage||this.targetVC)){this.nextHandler.handleRequest(operateDom);ResultMessage.showMsg(true,this.msg,operateDom.attr(DISLAND.OPERATE_HASMSG),this.closePage);}
else{ResultMessage.showMsg(true,this.msg,operateDom.attr(DISLAND.OPERATE_HASMSG),false);if(operateDom.attr(DISLAND.OPERATE_NOTFRESH)!='true'){var resultDom=DISLAND.getUpdateTableDom(this.boDom)||this.boDom;ContainerHandlerFactory.createContainerHandler(resultDom).aftermath();}
if(this.nextHandler)
this.nextHandler.handleRequest(operateDom);}}
else if(this.nextHandler){this.nextHandler.handleRequest(operateDom);}
return true;};}
extend(ResultMsg,ButtonEvent);