
var CellRender={rowBtnRender:function(btnId,btnName,btnText,btnIcon,index){var id=btnId+DISLAND.SPLIT_LINE+index;var result="";if(isEmpty(btnIcon)||/(Icon)$/.test(btnIcon))
result="<span>&nbsp;&nbsp;<a href='#' id='"
+id+"' name='"+btnName+"' "+"idx='"+index+"' onclick='BtnTableRowEvent.handle(this)' style='cursor:hand'>"+btnText+"</a></span>";else{result="<span><span id='"
+id+"' name='"+btnName+"' "+"idx='"+index+"' style='cursor:hand' title='"+btnText+"'"
+"onclick='BtnTableRowEvent.handle(this)' class='"+btnIcon+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span>";}
return result;},anchorRender:function(value,cell,record,rowIndex,tableId,bopBind){var boListDom=TableHelper.getBoListDom(tableId);var boDom=boListDom.find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+(rowIndex+1)+"']");var bop=boDom.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+bopBind+"']");var url=bop.attr(DISLAND.BOP_DATA_URL);var operateId=DISLANDTODOM.getTblOperateId(boListDom,bop);var operate=DISLAND.getRowOperate(bop.parent(),operateId);var display=bop.attr(DISLAND.BOP_VALUE_DISPLAY);if(operate.length>0){if(bop.attr(DISLAND.STATUS_DISABLE)!='true'&&bop.attr(DISLAND.STATUS_READONLY)!='true')
return"<a href='#' id='"+operateId+"' name='"+operateId+"' idx='"+(rowIndex+1)+"' "
+"onclick='BtnTableRowEvent.handle(this)'>"+value+"</a>";else
return value;}
else if(url){var anchorStr="";if(bop.attr(DISLAND.STATUS_DISABLE)=='true'||bop.attr(DISLAND.STATUS_READONLY)=='true'){anchorStr=value;}
else if(DISLAND.isMutiFileBOP(bop)){var columnInfo=boListDom.children(DISLAND.COLUMNINFO);var files_url=url.split(DISLAND.SPLIT_TREBLE_BACKSLASH);var files_name=value.split(DISLAND.SPLIT_TREBLE_BACKSLASH);var target=DISLANDTODOM.getAnchorTarget(bop,columnInfo);for(var i=0,len=files_url.length;i<len;i++){if(!files_url[i]||!files_name[i])
continue;anchorStr+="<a href='"+files_url[i]+"' "+target+">"+files_name[i]+"</a>&nbsp;&nbsp;";}}
else{var columnInfo=boListDom.children(DISLAND.COLUMNINFO);anchorStr="<a href='"+url+"' "+DISLANDTODOM.getAnchorTarget(bop,columnInfo)+">"+value+"</a>";}
return anchorStr;}
else if(isNotEmpty(display)){if(bop.attr(DISLAND.STATUS_DISABLE)!='true'&&bop.attr(DISLAND.STATUS_READONLY)!='true'){var columnInfo=boListDom.children(DISLAND.COLUMNINFO);return"<a href='"+getXmlNoteText(bop)+"' "+DISLANDTODOM.getAnchorTarget(bop,columnInfo)+">"+display+"</a>";}
else{return display;}}
else{return"<a href='"+value+"'>"+value+"</a>"}},iconRender:function(value,cell,imgStore,valueStore){if(imgStore[value])
return"<img src='"+appConfig.ctx+imgStore[value]+"' title='"+valueStore[value]+"'>";else
return valueStore[value];},dateRender:function(value,format){var result="";if(format)
result=Ext.util.Format.date(value,format);else
result=Ext.util.Format.date(value,'Y-m-d');return/^NaN/.test(result)?value:result;},dateFCRender:function(value){if(/\d+\s00:00:00$/.test(value)||/\d+[-]\d+[-]\d+/.test(value))
return value.replace(/\s00:00:00$/,"");else if(!!value)
return Ext.util.Format.date(value,'Y-m-d');return'';},imageRender:function(value,isAdaptive,height,width){var img='';if(value){var imgArr=strToArray(value,",");for(var i=0,length=imgArr.length;i<length;i++){var src=imgArr[i]
if(!isHttp(imgArr[i]))
src=appConfig.ctx+imgArr[i];if(isAdaptive){img+="<a href='"+src+"' target='_blank' style='cursor:hand'><img src='"+src+"'></a>";}
else{height=height||projectStyle.getColImgHeight();width=width||projectStyle.getColImgWidth();img+="<a href='"+src+"' target='_blank' style='cursor:hand'><img src='"+src+"' height='"+height+"' width='"+width+"'></a>";}
if(i<length)
img+=projectStyle.getColImgMargin();}
return img}
else{return"";}},checkboxRender:function(value,bopStore){var result="";if(!value)
return result;var arr=strToArray(value,",")
for(var i=0;i<arr.length;i++){if(!!bopStore[arr[i]])
result+=bopStore[arr[i]]+",";}
return!!result?removeEnd(result):result;},enumRender:function(value,bopStore){return bopStore[value];},locationRender:function(value,tableId){var boListDom=TableHelper.getBoListDom(tableId);var columnInfo=boListDom.children(DISLAND.COLUMNINFO);var VSR=DISLAND.getColumnVSR(bop,columnInfo);var display=decodeToDisplay(VSR.value);if(!!display&&display!="--"){return String.format("<a href=\"#\" onclick=\"getMapWin('{0}', '{1}', '{2}');\">{2}</a>",display.split(",")[0],display.split(",")[1],display.split(",")[2]);}
else if(display=="--"){return String.format("<a href=\"#\">{0}</a>",I18N.MOBILE_MAP_NOT_FIND);}},cellStyleRender:function(value,cell){cell.style+=';border:1px #b5b8c8 solid;';return value;}};
function ContainerHandler(boDom){this.boDom=boDom;this.container=boDom?Ext.getCmp(boDom.attr(DISLAND.BO_ID)):null;this.relations=boDom?DISLAND.getRelationBo(this.boDom):null;this.fresh=function(){DISLAND.updateBO(this.boDom);if(this.boDom.attr(DISLAND.STATUS_HIDDEN)!='true'){this.show();this.freshMyself();}
else{this.hide();}};this.freshMyself=function(){};this.reload=function(method,sourceContainerId,scopeDataIsland){};this.validate=function(){return true;};this.aftermath=function(){};this._addRelation=function(){};this.hide=function(){if(this.container)
this.container.hide();};this.show=function(){if(this.container)
this.container.show();}}
ContainerHandler.prototype.init=function(){this._addRelation();}
var ContainerHandlerFactory={createContainerHandler:function(boDom){var containerDom=DISLANDTODOM.getContainer(boDom);if(CommonDom.isForm(containerDom))
return new FormHandler(boDom);else if(CommonDom.isTable(containerDom))
return new TableHandler(boDom);else if(CommonDom.isTab(containerDom))
return new TabHandler(boDom);else if(CommonDom.isPage(boDom))
return new PageHandler(boDom);else
return new ContainerHandler(boDom);}};
var EditRender={generalRender:function(bolist,bop,editParam){var fcId=DISLANDTODOM.editFcId(bolist,bop);bop.parent().attr(DISLAND.BO_ID,bolist.attr(DISLAND.BO_ID));bop.parent().attr(DISLAND.BIND,bolist.attr(DISLAND.BIND));var type=bop.attr(DISLAND.COLUMN_TYPE);if(type=="file")
fcId=DISLANDTODOM.getFileUploadFCId(fcId);var fc=Ext.getCmp(fcId);if(fc){fc.tblIndex=bop.parent().attr(DISLAND.BO_INDEX);var fcHandler=FCHandlerFactory.createFCHandler(bop,fc);fcHandler.init();fcHandler.update();if(fcHandler instanceof DateHandler){var value=decodeToDisplay(editParam.value);if(/\d+\s00:00:00$/.test(value)){value=value.replace(/\s00:00:00$/,"");editParam.record.data[bop.attr(DISLAND.BIND)]=Date.parseDate(value,'Y-m-d');}}}},fileRender:function(bolist,boIndex,bop,column,editParam){if(column.attr(DISLAND.COLUMN_TYPE)!="file")
return;var fileFieldId=DISLANDTODOM.editFcId(bolist,bop);var cellFc=Ext.getCmp(DISLANDTODOM.getFileUploadFCId(fileFieldId));cellFc.boIndex=boIndex;var winId=DISLANDTODOM.getFileCellWin(fileFieldId);var formId=cellFc.getId()+"-filwWinForm";var winForm=Ext.getCmp(formId)||new Ext.FormPanel({id:formId,bodyStyle:{padding:'20'},border:false,labelAlign:'right',labelWidth:120,layout:'fit',items:[cellFc]});var win=Ext.getCmp(winId)||new Ext.Window({id:winId,title:cellFc.fieldLabel,width:'400',height:'150',modal:true,layout:'fit',closeAction:'hide',maximizable:true,collapsible:true,constrain:true,items:[winForm],buttons:[{text:I18N.DIALOG_YES,iconCls:'save',height:25,handler:function(){observableArr[winId].notify();}},{text:I18N.DIALOG_CLOSE,iconCls:'noIcon',height:25,handler:function(){win.hide();}}]});winForm.insert(0,cellFc);win.show(Ext.getBody(),function(){var file_ID=DISLANDTODOM.getFileID(fileFieldId);var $fileArray=jQuery("#"+winId).find("#"+file_ID+":gt(0)");if($fileArray.length>0){$fileArray.each(function(i,n){$(n).parent()[0].removeChild(n);});}});var anchor=Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));if(!anchor)
return true;if(bop.attr(DISLAND.BOP_DATA_URL)){var fileName=getXmlNoteText(bop);var anchorStr="<a href='"+bop.attr(DISLAND.BOP_DATA_URL)+"' target='_blank' title='"+fileName+"'>"+fileName+"</a>";anchor.setText(anchorStr,false);Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(bop.text());}
else{anchor.setText("",false);Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue("");}
observableArr[winId]=new CellEditObservable(win);var observer=new CellEditObserver();observer.tableId=bolist.attr(DISLAND.BOLIST_ID);observer.boIndex=boIndex;observer.bopBind=column.attr(DISLAND.BIND);observer.editParam=editParam;observableArr[winId].addObserver(observer);}};
function FormHandler(boDom){FormHandler.superclass.constructor.call(this,boDom);this.validate=function(winOperate){var flag=this.container.getForm().isValid();this.boDom.find(DISLAND.BOP).each(function(){var fc=null;if(typeof winOperate=="string")
fc=DISLANDTODOM.getWinFC($(this),winOperate);else
fc=DISLANDTODOM.getFC($(this));if(!fc)
return true;var fcHandler=FCHandlerFactory.createFCHandler($(this),fc);flag=flag&&serverValidate(fcHandler);});return flag;};this.reload=function(method,sourceContainerName,scopeDataIsland){lockSrceen();var param=this._getParams(this.container.name,method,sourceContainerName,scopeDataIsland);var boId=this.boDom.attr(DISLAND.BO_ID);var url=appConfig.ctx+(method?actionURL.reloadTargetVC():actionURL.getGaSearch());$.ajax({type:"POST",url:url,data:param,success:function(data){if(!data)
return;validateSession(data);var disland=XMLDomFactory.getInstance(data);var boDom=disland.find(DISLAND.BO+"["+DISLAND.BO_ID+"='"+boId+"']");boDom.find(DISLAND.BOP).each(function(){var fcHandler=FCHandlerFactory.createFCHandler($(this));fcHandler.update();});boDom.find(DISLAND.OPERATE).each(function(){var btnHandler=new ButtonHandler($(this));btnHandler.updateStatus();});unlockScreen();}});};this._getParams=function(bcName,method,sourceContainerName,scopeDataIsland){if(method)
return param(GA.operation)+method
+param(GA.dataIsland)+paramValue(scopeDataIsland)
+param(GA.targetXML)+paramValue("<page>"+xmlToString(this.boDom)+"</page>")
+param(pageFlow.sourceName)+sourceContainerName
+param(GA.targetName)+DISLANDTODOM.getBoName(this.boDom);return param(GA.operation)+"search"
+param(GA.dataIsland)+paramValue(xmlToString(scopeDataIsland))
+param(GA.targetName)+bcName;};this.freshMyself=function(boDom){if(boDom){boDom.find(DISLAND.BOP).each(function(){var fcHandler=FCHandlerFactory.createFCHandler($(this));fcHandler.update();});}};}
extend(FormHandler,ContainerHandler);
function PageHandler(pageDom){this.pageDom=pageDom;this.aftermath=function(){DISLAND.getDataIsland().find(DISLAND.BOLIST).each(function(){var containerHandler=ContainerHandlerFactory.createContainerHandler($(this));containerHandler.aftermath();});};this.validate=function(){var result=true;DISLAND.getDataIsland().children().each(function(){var containerHandler=ContainerHandlerFactory.createContainerHandler($(this));if(containerHandler instanceof PageHandler)
return true;if(!containerHandler.validate()){result=false;return false;}});return result;};this.reload=function(method,sourceContainerName,scopeDataIsland){if(!this.pageDom.attr(DISLAND.BIND)){showMSG.showWaring("page标签没有bind属�?，不能刷新全�?��钮！");return;}
var param=this._getParams(method,sourceContainerName,scopeDataIsland);var url=appConfig.ctx+actionURL.reloadPageBtn();$.ajax({type:"POST",url:url,data:param,success:function(data){if(!data){unlockScreen();return;}
validateSession(data);var disland=XMLDomFactory.getInstance(data);disland.children(DISLAND.PAGE).children(DISLAND.OPERATE).each(function(){new ButtonHandler($(this)).update();});}});};}
PageHandler.prototype._getParams=function(method,sourceContainerName,scopeDataIsland){return param(GA.operation)+method
+param(GA.dataIsland)+paramValue(scopeDataIsland)
+param(GA.targetXML)+paramValue(xmlToString(this.pageDom))
+param(pageFlow.sourceName)+sourceContainerName
+param(GA.targetName)+this.pageDom.attr(DISLAND.BIND);};
function SysAddRowHandler(boDom){SysAddRowHandler.superclass.constructor.call(this,boDom);}
extend(SysAddRowHandler,TableHandler);SysAddRowHandler.prototype.addRow=function(){var store=this.container.store;var boList=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));var maxIdx=store.getCount()+1;var dataFiels=store.recordType.prototype.fields.keys;var boList=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));var columnInfo=boList.children(DISLAND.COLUMNINFO);var temp="<"+DISLAND.BO+" "+DISLAND.BO_INDEX+"=\""
+maxIdx+"\" "+DISLAND.BO_OPERATIONSTATUS+"=\""+DISLAND.BO_OPERATIONSTATUS_INIT+"\">";var u=new store.recordType();u.data={};for(var i=0;i<dataFiels.length;i++){var value="";var column=columnInfo.find(DISLAND.COLUMNINFO_COLUMN+"["+DISLAND.BIND+"='"+dataFiels[i]+"']");if(column.attr(DISLAND.COLUMN_DEFAULT_VALUE))
value=column.attr(DISLAND.COLUMN_DEFAULT_VALUE);temp+="<"+DISLAND.BOP+" "+DISLAND.BIND+"=\""+dataFiels[i].replaceAll("#",".")+"\">"+specialCharHandler(value)+"</"+DISLAND.BOP+">";u.data[dataFiels[i]]=value;u.data.index=maxIdx;}
temp+="</"+DISLAND.BO+">";var bo=XMLDomFactory.getInstance(temp).find(DISLAND.BO);var operates=boList.children(DISLAND.OPERATE+"["+DISLAND.OPERATE_EXPEND+"='true']").clone();operates.attr(DISLAND.OPERATE_IDX,maxIdx);bo.append(operates);boList.append(bo);this.container.stopEditing();store.insert(store.getCount(),u);var height=projectStyle.getTableAutoHeight(boList.children(DISLAND.BO).length,this.container);this.container.setHeight(height);};SysAddRowHandler.prototype.delRow=function(){var delRows=this.container.getSelectionModel().getSelections();if(delRows.length==0){showMSG.showWaring(I18N.ALERT_CHOOSE_RECORD);return;}
var boList=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));var delBoIdArr=new Array();var delBoArr=new Array();for(var i=0,length=delRows.length;i<length;i++){var index=delRows[i].data.index;var delBo=boList.find(DISLAND.BO+"["+DISLAND.BO_INDEX+"="+index+"]").slice(0);delBoArr.push(delBo);var id=getXmlNoteText(delBo.find(DISLAND.BOP+"["+DISLAND.BIND+"='id']"));if(!!id)
delBoIdArr.push(id);}
for(var i=0,length=delBoArr.length;i<length;i++){delBoArr[i].remove();}
var store=this.container.getStore();store.remove(delRows);for(var i=0,length=store.getCount();i<length;i++){store.data.items[i].data.index=i+1;}
this.saveDelBoListId(boList,delBoIdArr);DISLAND.resetIndex(boList);};SysAddRowHandler.prototype.saveDelBoListId=function(boList,delBoIdArr){var newDelBoIds=delBoIdArr.toString();var oldDelBoIds;var delBoDom=boList.find(DISLAND.DEL_BO_IDS).slice(0);if(delBoDom.length==0){var temp="<"+DISLAND.DEL_BO_IDS+">"+newDelBoIds+"</"+DISLAND.DEL_BO_IDS+">";delBoDom=XMLDomFactory.getInstance(temp).find(DISLAND.DEL_BO_IDS);boList.append(delBoDom);}
else{oldDelBoIds=getXmlNoteText(delBoDom);if(newDelBoIds)
delBoDom.text(oldDelBoIds+","+newDelBoIds);}};function jsDelete(btnEvent){var operateDom=btnEvent.operateDom;var boList=btnEvent.boDom;var deleteBO=boList.children(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+operateDom.attr(DISLAND.OPERATE_IDX)+"']");var deleteIdx=boList.children(DISLAND.BO).index(deleteBO);deleteBO.remove();var store=btnEvent.containerDom.store;var record=store.getAt(deleteIdx);store.remove(record);DISLAND.resetIndex(boList);}
function TabHandler(boDom){TabHandler.superclass.constructor.call(this,boDom);this.validate=function(){return this._validate(this.container,this.container.items,0);};this._validate=function(container,items,activateIndex){var result=true;if(!items)
return result;for(var i=0;i<items.length;i++){var item=items.items[i];if(CommonDom.isForm(item)&&!item.getForm().isValid()){container.activate(activateIndex);result=item.getForm().isValid();}
else if(CommonDom.isForm(item)&&item.getForm().isValid()){continue;}
else{result=this._validate(container,item.items,i);}
if(!result)
break;}
return result;}}
extend(TabHandler,ContainerHandler);
function TableHandler(boDom){TableHandler.superclass.constructor.call(this,boDom);this._ROW_EVENT="rowdblclick";this.freshMyself=function(){this.showTableFields();this._clearHistory();this._clearThisPage();if(this.container){var tableStore=this.container.getStore();tableStore.baseParams.dataIsland=xmlToString(DISLAND.getDataIsland());}};this.changeTOExtItemName=function(itemName){return itemName.replaceAll("\\.","#");};this.reload=function(method,sourceContainerName,scopeDataIsland,freshTargetVC){TableHelper.beforeTurnPage(this.container.getId());lockSrceen();this.container.getStore().reload();if(typeof freshTargetVC==='function')
freshTargetVC();};this._addRelation=function(){if(this.relations&&this.relations.length>0){var tableHandler=this;this.container.addListener(this._ROW_EVENT,function(grid,rowIndex,e){tableHandler.addBOT(rowIndex+1);observableArr[tableHandler.boDom.attr(DISLAND.BO_ID)].notify();});}};this.aftermath=function(){this._clearHistory();this.reload();this._clearThisPage();};this._clearHistory=function(){var boListDom=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BOLIST_ID));DISLAND.clearCheckedDom(boListDom);},this._clearThisPage=function(){var sm=this.container.getSelectionModel();if(!sm||(sm.getSelections&&sm.getSelections().length==0))
return;sm.clearSelections();},this.getSelections=function(){var arr=[];var records=this.container.getSelectionModel().getSelections();Ext.each(records,function(record){arr.push(record.get(DISLAND.BO_INDEX)+'');});return arr;},this.getModifiedRecords=function(){return this.container.getStore().getModifiedRecords();},this.commitChanges=function(){this.container.getStore().commitChanges();};this.addBOT=function(rowNum){var boList=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));var bot=boList.find(DISLAND.BOLIST_BOT);bot.remove();var newBot=XMLDomFactory.getInstance('<'+DISLAND.BOLIST_BOT+'></'+DISLAND.BOLIST_BOT+'>').find(DISLAND.BOLIST_BOT);var bo=boList.find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+rowNum+"']").clone();newBot.append(bo);boList.append(newBot);};}
extend(TableHandler,ContainerHandler);TableHandler.prototype.showTableFields=function(){var tableStore="{"+this.showPageBar()+",'";var changeTOExtItemName=this.changeTOExtItemName;var newRow='';this.boDom.children(DISLAND.BO).each(function(){newRow+="{'"+DISLAND.BO_INDEX+"':"+$(this).attr(DISLAND.BO_INDEX)+",";$(this).children(DISLAND.BOP).each(function(){var text=getXmlNoteText($(this));if(isEmpty(text))
text='';else
text=decodeToValue(getUnescapedText(text));var enumRange=$(this).attr(DISLAND.BOP_RANGE_ENUM);if(enumRange&&text){enumRange=decodeToValue(getUnescapedText(enumRange));var items=eval('('+enumRange+')');for(var i=0;i<items.length;i++){if(items[i].k==text){text=items[i].v;break;}}}
newRow+="'"+changeTOExtItemName($(this).attr(DISLAND.BIND))+"':'"+text+"',";});newRow=removeEnd(newRow);newRow+="},";});tableStore+=ConstantJSON.PAGEBAR_ROOT+"':["+removeEnd(newRow)+"]}";this.container.getStore().loadData(StringToJSON(tableStore),false);};TableHandler.prototype.showPageBar=function(){var pageInation=DISLAND.getPagination(this.boDom);return"'limit':"+(pageInation.limit||0)
+",'total':"+(pageInation.total||0)
+",'start':"+(pageInation.start||0);};TableHandler.prototype.relationHandle=function(data){if(!data)
return;var newDataIsland=XMLDomFactory.getInstance(data.responseText).find(DISLAND.dataIsland);var boList=DISLAND.getBOListDom(this.boDom.attr(DISLAND.BO_ID));boList.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS,DISLAND.BO_OPERATIONSTATUS_INIT);var relations=this.relations;for(var i in relations){var targetTable=newDataIsland.find(DISLAND.BOLIST+"["+DISLAND.BOLIST_ID+"='"+i+"']").slice(0);targetContainer=ContainerHandlerFactory.createContainerHandler(targetTable);targetContainer.fresh();}};TableHandler.prototype.isEdit=function(boListId){if(!this.container)
this.container=Ext.getCmp(boListId);if(this.getModifiedRecords()==0)
return false;var result=false;unlockScreen();if(confirm(I18N.CONFIRM_TABLE_EDIT)){result=true;}
else{lockSrceen();this.commitChanges();}
return result;};TableHandler.prototype.clearEdit=function(boListId){if(!this.container)
this.container=Ext.getCmp(boListId);this.container.getStore().rejectChanges();};
var TableHelper={beforeTurnPage:function(boListId){var sorceBOID=DISLAND.getSourceBOID(boListId);if(sorceBOID)
BINDDATA.bindBO(DISLAND.getDom(sorceBOID));var tableStore=Ext.getCmp(boListId).getStore();var dataParam=xmlToString(DISLAND.getDataIsland());tableStore.baseParams.sourceName=sorceBOID;tableStore.baseParams.dataIsland=dataParam;if(tableStore.lastOptions&&tableStore.lastOptions.params)
tableStore.lastOptions.params.dataIsland=dataParam;},formatCell:function(boListDom,editParam){if(boListDom.children(DISLAND.BOLIST_HEIGHT).length>0){var curGrid=Ext.getCmp(boListDom.attr(DISLAND.BO_ID));boListDom.children(DISLAND.BO).each(function(rowIndex){TableHelper._formatOneRow($(this),curGrid,rowIndex);});}},_formatOneRow:function(boDom,curGrid,rowIndex){var cm=curGrid.getColumnModel();boDom.children(DISLAND.BOP+'['+DISLAND.BOP_HIGHLIGHT+']').each(function(){var colIndex=cm.findColumnIndex($(this).attr(DISLAND.BIND).replaceAll("\\.","#"));if(colIndex<0)
return true;var cell=curGrid.getView().getCell(rowIndex,colIndex);$(cell).find('div').attr("style",$(this).attr(DISLAND.BOP_HIGHLIGHT));});},setBtnState:function(tableId){var boList=this.getBoListDom(tableId);if(!boList||boList.children(DISLAND.OPERATE+'['+DISLAND.OPERATE_EXPEND+']').length==0)
return;boList.children(DISLAND.BO+"["+DISLAND.BO_ROWOPT_STATUS+"]").each(function(){var idx=$(this).attr(DISLAND.BO_INDEX);$(this).children(DISLAND.OPERATE+'['+DISLAND.OPERATE_EXPEND+']').each(function(){var rowBtnId=$(this).attr(DISLAND.OPERATE_ID)+DISLAND.SPLIT_LINE+idx;if($(this).attr(DISLAND.STATUS_HIDDEN)=='true'){$('#'+rowBtnId).parent().hide();}
else if($(this).attr(DISLAND.STATUS_DISABLE)=='true'||$(this).attr(DISLAND.STATUS_READONLY)=='true'){$('#'+rowBtnId).attr('disabled',true);$('#'+rowBtnId).attr('style','color:gray');$('#'+rowBtnId).removeAttr('onclick');}});});},setHeight:function(tableId){var boListDom=this.getBoListDom(tableId);if(!boListDom)
return;var tableHandler=ContainerHandlerFactory.createContainerHandler(boListDom);if(tableHandler.container.staticHeight){tableHandler.container.setHeight(tableHandler.container.staticHeight);}
else if(appConfig.isTableHeightFull&&itemArray.length==2&&!hasConLayout()&&itemArray[0]instanceof Ext.form.FormPanel&&itemArray[1]instanceof Ext.grid.EditorGridPanel){tableHandler.container.setHeight(Ext.getBody().getHeight()-itemArray[0].getHeight()-12);}
else{var pageSize=boListDom.children(DISLAND.BO).length;var height=projectStyle.getTableAutoHeight(pageSize,tableHandler.container);if(height!=0)
tableHandler.container.setHeight(height);}},rememberCheckedRow:function(tableId){var boListDom=this.getBoListDom(tableId);var checkedDom=DISLAND.getCheckedDom(boListDom);if(!checkedDom||checkedDom.length==0)
return;var sm=Ext.getCmp(tableId).getSelectionModel();if(!sm.getSelections)
return;var checkedInHistory=strToArray(checkedDom.attr(DISLAND.BO_ID),',');if(!checkedInHistory)
checkedInHistory=[];var records=sm.getSelections();this._addCheckedInThisPage(boListDom,checkedDom,records,checkedInHistory);this._removeNotCheckedInHistory(boListDom,checkedDom,records,checkedInHistory);DISLAND.updateBO(boListDom);},_addCheckedInThisPage:function(boListDom,checkedDom,records,checkedInHistory){var checkedInThisPageArr=[];Ext.each(records,function(record){if(!!checkedInHistory&&!checkedInHistory.exist(record.data.id)){checkedInHistory.push(record.data.id);checkedInThisPageArr.push(record.data.id);}});if(checkedInHistory.length>0)
checkedDom.attr(DISLAND.BO_ID,checkedInHistory.valueOf()+'');else
checkedDom.attr(DISLAND.BO_ID,"");if(checkedInThisPageArr.length==0)
return;boListDom.children(DISLAND.BO).each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='"+DISLAND.BOP_ID+"']").slice(0);if(checkedInThisPageArr.exist(getXmlNoteText(bop))){checkedDom.append($(this).clone());}});},_removeNotCheckedInHistory:function(boListDom,checkedDom,records,checkedInHistory){var checkedInThisPage=[];Ext.each(records,function(record){checkedInThisPage.push(record.data.id);});var unCheckedInThisPageArr=[];boListDom.children(DISLAND.BO).each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='id']");var id=getXmlNoteText(bop);if(!checkedInThisPage.exist(id)&&checkedInHistory.exist(id)){checkedInHistory.remove(id);unCheckedInThisPageArr.push(id);}});if(unCheckedInThisPageArr.length==0)
return;if(checkedInHistory.length>0){checkedDom.attr(DISLAND.BO_ID,checkedInHistory.valueOf()+'');checkedDom.children().each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='"+DISLAND.BOP_ID+"']").slice(0);if(unCheckedInThisPageArr.exist(bop.text())){$(this).remove();}});}
else{checkedDom.remove();}},checkedOnThisPage:function(tableId){var boListDom=this.getBoListDom(tableId);var checkedDom=DISLAND.getCheckedDom(boListDom);if(checkedDom.length==0)
return;var checkedInHistory=strToArray(checkedDom.attr(DISLAND.BO_ID),',');if(!checkedInHistory)
return;var sm=Ext.getCmp(tableId).getSelectionModel();boListDom.children(DISLAND.BO).each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='id']");if(bop.length==0){showMSG.showWaring(I18N.ERR_TABLE_CHECKED);return false;}
if(checkedInHistory.exist(bop.text())){sm.selectRow($(this).attr(DISLAND.BO_INDEX)-1,true);}});},setTableStatus:function(tableId){var boListDom=this.getBoListDom(tableId);if(!!boListDom){if(boListDom.attr(DISLAND.STATUS_HIDDEN)=='true'){var containerHandler=ContainerHandlerFactory.createContainerHandler(boListDom);containerHandler.hide();return;}
else{this.formatCell(boListDom);}}},updataTableIsland:function(tableId){var boListDom=this.getBoListDom(tableId);if(!!boListDom)
DISLAND.updateBO(boListDom);},getBoListDom:function(tableId){var store=Ext.getCmp(tableId).store;var tableIsland=store.reader.jsonData?store.reader.jsonData.tableIsland:"";if(!tableIsland)
return DISLAND.getBOListDom(tableId);else
return XMLDomFactory.getInstance(tableIsland).find(DISLAND.BOLIST);},changeNoWidth:function(pagebar,index,tableId){var colModel=Ext.getCmp(tableId).colModel;colModel.setColumnWidth(index,projectStyle.getNoWidth(pagebar));},gridCheckboxFix:function(tableId){if(!Ext.getCmp(tableId).view||!Ext.getCmp(tableId).view.innerHd)
return;var dom=Ext.fly(Ext.getCmp(tableId).view.innerHd).query("div.x-grid3-hd-checker-on");if(dom)
Ext.get(dom).removeClass("x-grid3-hd-checker-on");},beforeEdit:function(editParam){var bopBind=editParam.field.replaceAll("#",".");var boList=DISLAND.getDom(editParam.grid.id);var bo=boList.children(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+(editParam.row+1)+"']");var bop=bo.children(DISLAND.BOP+"["+DISLAND.BIND+"='"+bopBind+"']");var columnInfo=boList.children(DISLAND.COLUMNINFO);var column=columnInfo.children(DISLAND.COLUMNINFO_COLUMN+"["+DISLAND.BIND+"='"+bopBind+"']");var tempBop=DISLAND.createTempBop(columnInfo,column,editParam.row,editParam.grid,bop);EditRender.generalRender(boList,tempBop,editParam);EditRender.fileRender(boList,(editParam.row+1),bop,column,editParam);},afterEdit:function(editParam){var gridId=editParam.grid.getId();TableHelper.setBtnState(gridId);var boList=DISLAND.getBOListDom(gridId);var editBO=boList.find(DISLAND.BO+"["+DISLAND.BO_INDEX+"="+(editParam.row+1)+"]");var columnInfo=boList.children(DISLAND.COLUMNINFO);BINDDATA.bindEditBop(editParam.record.data,editBO,columnInfo);},formatCellForEditRow:function(boListDom,editBO,editParam){if(boListDom.length==0||editBO.length==0)
return;var curGrid=Ext.getCmp(boListDom.attr(DISLAND.BO_ID));this._formatOneRow(editBO,curGrid,editParam.row);}};