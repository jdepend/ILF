
var CommonDom=function(){};CommonDom.isLabelDom=function(fc){return fc?(fc.getXType()=='label'&&fc.defaultType&&fc.defaultType=='label'):false;};CommonDom.isTextDom=function(fc){return fc?(fc.getXType()=='textfield'||fc.getXType()=='textarea'||fc.getXType()=='password'||(fc.getXType()=='compositefield'&&fc.defaultType&&fc.defaultType=='textfield')):false;};CommonDom.isEditorDom=function(fc){if(!fc)
return false;return fc.getXType()=='htmleditor'||fc.getXType()=='displayfield';};CommonDom.isHidden=function(fc){return fc?(fc.getXType()=='hidden'):false;};CommonDom.isRadio=function(fc){return fc?((fc.getXType()=='fieldset'&&fc.defaultType&&fc.defaultType=='radio')||fc.getXType()=='radiogroup'):false;};CommonDom.isCheckbox=function(fc){return fc?((fc.getXType()=='fieldset'&&fc.defaultType&&fc.defaultType=='checkbox')||fc.getXType()=='checkboxgroup'):false;};CommonDom.isSelect=function(fc){return fc?fc.getXType()=='combo':false;};CommonDom.isImg=function(fc){return fc?fc.defaultType=='image':false;};CommonDom.isOptTrans=function(fc){return fc?fc.getXType()=='itemselector':false;};CommonDom.isDate=function(fc){return fc?(fc.getXType()=='datefield'||(fc.defaultType&&fc.defaultType=='datefield')):false;};CommonDom.isFile=function(fc){return fc?(fc.getXType()=='fileuploadfield'||(fc.defaultType&&fc.defaultType=='filefield')):false;};CommonDom.isAnchor=function(fc){return fc?(fc.type&&fc.type=='anchor'):false;};CommonDom.isButton=function(vc){return vc?vc.getXType()=="button":false;};CommonDom.isForm=function(container){return container?container.getXType()=='form':false;};CommonDom.isTable=function(container){return container?container.getXType()=='editorgrid':false;};CommonDom.isTree=function(container){return container?container.getXType()=="treepanel":false;};CommonDom.isPage=function(xmlDom){return xmlDom?xmlDom.is(DISLAND.PAGE):false;};CommonDom.isTab=function(container){return container?container.getXType()=="tabpanel":false;};CommonDom.getFcId=function(cmp){var fcId=cmp.getId();if(cmp.getXType()=="compositefield")
fcId=cmp.items.items[0].getId();return fcId;};
var DISLANDTODOM={getFC:function(bopDom){return Ext.getCmp(this.getFCId(bopDom));},getFCId:function(bopDom){var fcId="";if(!!bopDom)
fcId=bopDom.parent().attr(DISLAND.BO_ID)
+DISLAND.SPLIT_LINE
+bopDom.parent().attr(DISLAND.BIND)
+DISLAND.SPLIT_LINE+bopDom.attr(DISLAND.BIND);return fcId;},getBOPBind:function(fcId){return fcId.split(DISLAND.SPLIT_LINE)[2];},getContainerId:function(operateDom){return this.getContainerIdByBtnId(operateDom.attr(DISLAND.OPERATE_ID));},getContainerIdByBtnId:function(btnId){return btnId.split(DISLAND.SPLIT_LINE)[0];},getBtnName:function(operateDom){var arr=operateDom.attr(DISLAND.OPERATE_ID).split(DISLAND.SPLIT_LINE);return arr.length==3||arr.length==4?arr[2]:arr[1];},getBtnId:function(operateDom){return operateDom.attr(DISLAND.OPERATE_ID);},isTableButton:function(operateDom){return!operateDom.attr(DISLAND.OPERATE_EXPEND);},getRecordWinIdx:function(operateDom){var arr=this.getBtnId(operateDom).split(DISLAND.SPLIT_LINE);return arr[0]+DISLAND.SPLIT_LINE+arr[1]+DISLAND.SPLIT_LINE+operateDom.attr(DISLAND.OPERATE_METHOD);},getBtnMethod:function(operate){var arr=operate.split(DISLAND.SPLIT_LINE);return arr[arr.length-1];},getButton:function(operateDom){if(!!operateDom.attr(DISLAND.OPERATE_IDX))
return;else{var btn=Ext.getCmp(operateDom.attr(DISLAND.OPERATE_ID));if(btn&&btn.type&&btn.type=='anchor'){btn=Ext.get(operateDom.attr(DISLAND.OPERATE_ID)+"_anchor").dom;}
return btn;}},getSaveMod:function(operateDom){return operateDom.attr(DISLAND.OPERATE_SAVEMOD);},btn_isModifyMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm=='modify';},btn_isSelectMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm=='select';},btn_isAdaptiveMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm=='adaptive';},btn_isEmptyMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm=='empty';},btn_isAllMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm=='all';},btn_isMixedMod:function(operateDom){return!!operateDom.attr(DISLAND.OPERATE_OPTMOD);},isBeforeBoMethod:function(method){return method.split(".").length==1;},isAfterBoMethod:function(method){return method.split(".").length==2;},getBeforeBoMethod:function(operateDom){var methodArr=strToArray(DISLAND.operate_BOMethod(operateDom),",");if(!methodArr)
return null;var result=[];for(var i=0;i<methodArr.length;i++){if(this.isBeforeBoMethod(methodArr[i]))
result.push(methodArr[i]);}
return result;},getAfterBoMethod:function(operateDom){var methodArr=strToArray(DISLAND.operate_BOMethod(operateDom),",");if(!methodArr)
return null;var result=[];for(var i=0;i<methodArr.length;i++){if(this.isAfterBoMethod(methodArr[i]))
result.push(methodArr[i]);}
return result;},getOptSaveMod:function(operateDom){var optMod=operateDom.attr(DISLAND.OPERATE_OPTMOD);if(!optMod)
return null;var optModArr=optMod.split(',');var result=[];for(var i=0;i<optModArr.length;i++){var temp=optModArr[i].split('=');result[temp[0]]=temp[1];}
return result;},getRecordId:function(operateDom){var bo=operateDom.parent();var bop=bo.find(DISLAND.BOP+"["+DISLAND.BIND+"='id']");return getXmlNoteText(bop);},getContainer:function(boDom){return Ext.getCmp(boDom.attr(DISLAND.BO_ID));},getRelContainerIdAdapter:function(operateDom,saveMod){var rmodifyId=[];var sm=this.getSaveMod(operateDom);if(!!sm){var modifyIds=sm.split(",");for(var i=0;i<modifyIds.length;i++){if(modifyIds[i].indexOf(saveMod))
rmodifyId.push(modifyIds[i].split(".")[0]);}}
return rmodifyId;},getSelectContainerId:function(operateDom){return this.getRelContainerIdAdapter(operateDom,"select");},getModifyContainerId:function(operateDom){return this.getRelContainerIdAdapter(operateDom,"modify");},getAdaptiveContainerId:function(operateDom){return this.getRelContainerIdAdapter(operateDom,"adaptive");},pageBtn_isSelectMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm.indexOf('.select')>0;},pageBtn_isModifyMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm.indexOf('.modify')>0;},pageBtn_isAdaptiveMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm.indexOf('.adaptive')>0;},pageBtn_isAllMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm.indexOf('.all')>0;},pageBtn_isEmptyMod:function(operateDom){var sm=this.getSaveMod(operateDom);return!!sm&&sm.indexOf('.empty')>0;},getBOBind:function(fcName){return fcName.split(DISLAND.SPLIT_LINE)[1];},getFileID:function(fcId){return fcId+'-file';},getFileAnchorID:function(fcId){return fcId+'_anchor';},getFileCheckID:function(fcId){return this.getFileAnchorID(fcId)+'_check';},getTblOperateId:function(boDom,bopDom){return boDom.attr(DISLAND.BO_ID)+DISLAND.SPLIT_LINE
+boDom.attr(DISLAND.BIND)+DISLAND.SPLIT_LINE
+bopDom.attr(DISLAND.BIND)+'-operate';},getWinFormId:function(btnId){return btnId+"_form";},getWinFCId:function(bopDom,panelType){return bopDom.parent().attr(DISLAND.BO_ID).split(DISLAND.SPLIT_LINE)[0]
+DISLAND.SPLIT_LINE+bopDom.parent().attr(DISLAND.BIND)
+DISLAND.SPLIT_LINE+bopDom.attr(DISLAND.BIND)
+DISLAND.SPLIT_LINE+panelType;},getWinFC:function(bopDom,winOperate){var winFCId=this.getWinFCId(bopDom,winOperate);return Ext.getCmp(winFCId);},editFcId:function(boList,bop){return boList.attr(DISLAND.BO_ID)
+DISLAND.SPLIT_LINE+boList.attr(DISLAND.BIND)
+DISLAND.SPLIT_LINE+bop.attr(DISLAND.BIND);},getFisrstShowBtn:function(){var operateList=DISLAND.getOperateList();if(!operateList)
return null;var result;operateList.each(function(){var btn=Ext.getCmp($(this).attr(DISLAND.OPERATE_ID));if(!btn||btn.hidden||btn.disabled){return true;}
else if(btn.type&&btn.type==='button'){result=btn;return false;}});return result;},getFileUploadFCId:function(fcId){return fcId+"_composite";},getFileCellWin:function(fcId){return this.getFileUploadFCId(fcId)+"-fileWin";},getAnchorTarget:function(bopDom,columnInfo){var bopId=bopDom.attr(DISLAND.BIND);var column=columnInfo.find(DISLAND.COLUMNINFO_COLUMN+"["+DISLAND.BIND+"='"+bopId+"']");return"target='"+(column.attr(DISLAND.COLUMN_TARGET)?column.attr(DISLAND.COLUMN_TARGET):"_self")+"'";},getSourceBtnId:function(bopDom,sysOperate){var btnId;if(sysOperate)
btnId=DISLANDTODOM.getWinFCId(bopDom,sysOperate)+"_source_btn";else
btnId=DISLANDTODOM.getFCId(bopDom)+"_source_btn";return btnId;},hasSourceBtn:function(bopDom,sysOperate){var btnId=DISLANDTODOM.getSourceBtnId(bopDom,sysOperate);return!!Ext.getCmp(btnId);},getSourceBtn:function(bopDom,sysOperate){var btnId=DISLANDTODOM.getSourceBtnId(bopDom,sysOperate);return Ext.getCmp(btnId);},setReadonlyBySbopIds:function(sbtn,sysOperate,boDom){var arrS=strToArray(sbtn.sbopIds,DISLAND.SPLIT_COMMA);var arrEditAble=strToArray(sbtn.editAble,DISLAND.SPLIT_COMMA);for(var i=0,len=arrS.length;i<len;i++){if(isInArray(arrEditAble,arrS[i]))
continue;var bopDom=DISLAND.getBOPByFcId(arrS[i],boDom);var fcHandler;if(sysOperate){var fc=Ext.getCmp(DISLANDTODOM.getWinFCId(bopDom,sysOperate));fcHandler=FCHandlerFactory.createFCHandler(bopDom,fc);}
else{fcHandler=FCHandlerFactory.createFCHandler(bopDom);}
fcHandler.setReadonly();}},getBOId:function(fcId){return fcId.split(DISLAND.SPLIT_LINE)[0];},getSysOperateByForm:function(boDom){var conId=boDom.attr(DISLAND.BOLIST_ID);if(!conId)
return null;var arr=conId.split(DISLAND.SPLIT_LINE);if(arr.length!=3)
return null;return arr[2].split("_")[0];},getAnchorOperateId:function(bopDom){var boDom=bopDom.parent();return boDom.attr(DISLAND.BO_ID)+DISLAND.SPLIT_LINE
+boDom.attr(DISLAND.BIND)+DISLAND.SPLIT_LINE
+bopDom.attr(DISLAND.BIND);},getBoName:function(boDom){return boDom.attr(DISLAND.BO_ID)+DISLAND.SPLIT_LINE+boDom.attr(DISLAND.BIND);},getMethodArr:function(operate){return strToArray(operate,DISLAND.SPLIT_COMMA);}};
var dialog={defWidth:Ext.getBody().getWidth()-50,defHeight:Ext.getBody().getHeight()-20,openMDialog:function(pageFlowEvent,paramData){Ext.Ajax.request({url:appConfig.ctx+actionURL.saveParam(),method:"POST",params:paramData,timeout:appConfig.ajaxTimeout,success:function(response,options){var win=new Ext.Window({title:pageFlowEvent.dialogTitle,dialogType:'M',id:"qeweb-dialog",maximizable:true,collapsible:true,width:getAdaptiveSize(Number(pageFlowEvent.dialogWidth||dialog.defWidth)),height:getAdaptiveSize(Number(pageFlowEvent.dialogHeight||dialog.defHeight)),modal:true,html:"<iframe src='"
+appConfig.ctx+actionURL.getRedirect(pageFlowEvent.path)
+"' style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>",listeners:{'close':function(){if(!!pageFlowEvent.closeTargetVC)
pageFlowEvent.refreshParentContainer(pageFlowEvent.closeTargetVC);}}});if(pageFlowEvent.hasCloseBtn){win.addButton({text:I18N.DIALOG_CLOSE,iconCls:'noIcon',height:25,handler:function(){win.close();}});}
win.show(Ext.getBody());}});},openDialog:function(paramData){if(!paramData.path)
return false;var boId=paramData.sbopIds.split(DISLAND.SPLIT_LINE)[0];var boBind=paramData.sbopIds.split(DISLAND.SPLIT_LINE)[1];var bo;var dIsland;if(paramData.sysOperate){bo=DISLAND.getFormPanelDom(boId+DISLAND.SPLIT_LINE+boBind+DISLAND.SPLIT_LINE+paramData.sysOperate);BINDDATA.bindBO(bo,paramData.sysOperate,paramData.sysOperate);dIsland=xmlToString(bo);}
else if(!paramData.insideBtn){bo=DISLAND.getBODom(boId);BINDDATA.bindBO(bo,"sysOperate",paramData.sysOperate);dIsland=xmlToString(bo);}
else{bo=DISLAND.getBOListDom(boId);DISLAND.getDataIsland().children(DISLAND.BO).each(function(){BINDDATA.bindBO($(this));});DISLAND.setPageOptStatus();dIsland=xmlToString(DISLAND.getDataIsland());}
var dialogParams=pageFlow.sourceName+paramValue(DISLANDTODOM.getBoName(bo))
+param(GA.dataIsland)+paramValue(dIsland);this.handle(dialogParams,paramData);},handle:function(dialogParams,paramData){lockSrceen();Ext.Ajax.request({url:appConfig.ctx+actionURL.getGaExe(),method:"POST",params:dialogParams+param(GA.operation)+paramData.operate,timeout:appConfig.ajaxTimeout,success:function(response,options){unlockScreen();validateSession(response.responseText);var resultMsg=new ResultMsg(response.responseText);if(resultMsg.success){dialog.open(dialogParams,paramData);}
else{if(!resultMsg.msg||resultMsg.msg=='null')
resultMsg.msg=I18N.MSG_OPERATION_FAILURE;ResultMessage.showMsg(resultMsg.success,resultMsg.msg,true,false);}},failure:function(response){unlockScreen();showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});},open:function(dialogParams,paramData){if(paramData.operate)
dialogParams+=param(pageFlow.contextOperate)+paramData.operate;Ext.Ajax.request({url:appConfig.ctx+actionURL.saveParam(),method:"POST",params:dialogParams,timeout:appConfig.ajaxTimeout,success:function(response,options){var win=new Ext.Window({sbopIds:paramData.sbopIds,tbopIds:paramData.tbopIds,sysOperate:paramData.sysOperate,title:paramData.title,echo:paramData.echo,maximizable:true,collapsible:true,dialogType:paramData.dialogType,id:"qeweb-dialog",width:getAdaptiveSize(Number(paramData.width||dialog.defWidth)),height:getAdaptiveSize(Number(paramData.height||dialog.defHeight)),modal:true,html:"<iframe src='"
+appConfig.ctx+actionURL.getRedirect(paramData.path)
+param(sourceBtn.sm)+paramData.sm
+"' style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>"});if(paramData.hasCloseBtn){win.addButton({text:I18N.DIALOG_CLOSE,iconCls:'noIcon',handler:function(){win.close();}});}
win.show(Ext.getBody());},failure:function(){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});},fillbackFC:function(sbopIds,tbopIds,boListDisland,sysOperate){var boList=XMLDomFactory.getInstance(boListDisland).find(DISLAND.BOLIST);var bos=boList.children(DISLAND.BO);if(!bos||bos.length==0)
return;var arrS=strToArray(sbopIds,DISLAND.SPLIT_COMMA);var arrT=strToArray(tbopIds,DISLAND.SPLIT_COMMA);var cmp=null;for(var i=0,length=arrS.length;i<length;i++){cmp=dialog.getWinCmp(arrS[i],sysOperate,true);if(!cmp)
continue;else if(isLabel(cmp))
dialog.getWinCmp(arrS[i],sysOperate,true).setText("");else
dialog.getWinCmp(arrS[i],sysOperate,true).setValue("");}
bos.each(function(){var cmp=null;for(var i=0;i<arrT.length;i++){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='"+DISLANDTODOM.getBOPBind(arrT[i])+"']");if(!bop)
continue;var result=null;cmp=dialog.getWinCmp(arrS[i],sysOperate,true);if(cmp){if(isLabel(cmp))
result=cmp.text;else
result=cmp.getValue();}
if(!!result)
result+=(DISLAND.SPLIT_COMMA+getXmlNoteText(bop));else
result=getXmlNoteText(bop);dialog.setCmpValue(cmp,result);}});var cmp=dialog.getWinCmp(arrS[0],sysOperate,true);if(cmp&&!isLabel(cmp))
cmp.focus(true,true);},fillbackTable:function(sbopIds,tbopIds,boListDisland,echo){var arrS=strToArray(sbopIds,DISLAND.SPLIT_COMMA);var arrT=strToArray(tbopIds,DISLAND.SPLIT_COMMA);if(!arrS||!arrT||arrS.length!=arrT.length)
return;var tboListDisland=XMLDomFactory.getInstance(boListDisland);var tboList=tboListDisland.find(DISLAND.BOLIST);if(tboList.children().length==0)
return;var tboLst=XMLDomFactory.getInstance(boListDisland).find(DISLAND.BOLIST);var tbos=tboLst.children();tbos.each(function(){$(this).children(DISLAND.BOP).each(function(){for(var i=0,length=arrT.length;i<length;i++){if(DISLANDTODOM.getBOPBind(arrT[i])==$(this).attr(DISLAND.BIND)){$(this).attr(DISLAND.BIND,DISLANDTODOM.getBOPBind(arrS[i]));return true;}}
$(this).remove();});});var sboList=parent.DISLAND.getDataIsland().find(DISLAND.BOLIST+"["+DISLAND.BO_ID+"='"+DISLANDTODOM.getBOId(arrS[0])+"']");this._appendMissingBop(sboList,tbos);this._addOptDoms(sboList,tbos);this._appendToSBoList(sboList,tbos,echo);var containerHandler=parent.ContainerHandlerFactory.createContainerHandler(sboList);containerHandler.fresh();},_addOptDoms:function(sboList,tbos){var optDoms=sboList.children(DISLAND.OPERATE+"["+DISLAND.OPERATE_EXPEND+"='true']");var maxIdx=DISLAND.getMaxIdx(sboList);tbos.each(function(){maxIdx++;var copy=optDoms.clone();copy.attr(DISLAND.OPERATE_IDX,maxIdx);copy.appendTo($(this));$(this).attr(DISLAND.BO_INDEX,maxIdx);});},_appendToSBoList:function(sboList,tbos,echo){var echoBoIdxArr=this._getEchoBoIdxArr(sboList,tbos,echo);if(echoBoIdxArr.length==0){sboList.append(tbos);}
else{tbos.each(function(){if(!isInArray(echoBoIdxArr,$(this).attr(DISLAND.BO_INDEX)))
sboList.append($(this));});}},_appendMissingBop:function(sboList,tbos){var missingBopsStr="";var targetBops=tbos.slice(0).children(DISLAND.BOP);var columnInfo=sboList.children(DISLAND.COLUMNINFO);columnInfo.children(DISLAND.COLUMNINFO_COLUMN).each(function(){var columnBind=$(this).attr(DISLAND.BIND);var flag=true;targetBops.each(function(){if($(this).attr(DISLAND.BIND)==columnBind){flag=false;return false;}});if(flag)
missingBopsStr+="<"+DISLAND.BOP+" "+DISLAND.BIND+"=\""+columnBind+"\">"+"</"+DISLAND.BOP+">";});if(!!missingBopsStr){missingBopsStr="<"+DISLAND.BO+">"+missingBopsStr+"</"+DISLAND.BO+">"
var missingBops=XMLDomFactory.getInstance(missingBopsStr).find(DISLAND.BO).children();tbos.each(function(){var copy=missingBops.clone();copy.appendTo($(this));});}},_getEchoBoIdxArr:function(sboList,tbos,echo){if(!echo)
return[];var echoArr=[];sboList.children(DISLAND.BO).each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='"+echo+"']");if(bop.length>0)
echoArr.push(getRealValue(bop));});var echoBoIdxArr=[];tbos.each(function(){var bop=$(this).find(DISLAND.BOP+"["+DISLAND.BIND+"='"+echo+"']");if(bop.length>0&&isInArray(echoArr,getRealValue(bop))){echoBoIdxArr.push($(this).attr(DISLAND.BO_INDEX));}});return echoBoIdxArr;},clear:function(paramData){var arrS=strToArray(paramData.sbopIds,DISLAND.SPLIT_COMMA);var cmp=null;for(var i=0,length=arrS.length;i<length;i++){cmp=dialog.getWinCmp(arrS[i],paramData.sysOperate);this.setCmpValue(cmp,"");}},getWinCmp:function(fcId,sysOperate,isParent){var ext=isParent?parent.Ext:Ext;var winFcId=sysOperate?fcId+"-"+sysOperate:fcId;return ext.getCmp(winFcId);},setCmpValue:function(cmp,value){if(!cmp)
return;if(isLabel(cmp))
cmp.setText(value);else
cmp.setValue(value);var relationEventName=FCHandlerFactory.createFCHandler(null,cmp).getRelationEventName();cmp.fireEvent(relationEventName);}};
function doSelect(container){var boListStr="";var alreadyChecked=false;if(CommonDom.isTree(container)){DISLAND.getDataIsland().children(DISLAND.TREE).each(function(){var bind=$(this).attr(DISLAND.BIND);boListStr+="<"+DISLAND.BOLIST+" "+DISLAND.BOLIST_ID+"='' "+DISLAND.BIND+"='"+bind+"'>";boListStr+=treeDataStr(container.getRootNode(),bind);boListStr+="</"+DISLAND.BOLIST+">";});}
else{TableHelper.rememberCheckedRow(container.getId());var boList=DISLAND.getDataIsland().children(DISLAND.BOLIST).slice(0);var checkedDom=DISLAND.getCheckedDom(boList);var isRememberChecked=checkedDom&&checkedDom.length>0;var records=container.getSelectionModel().getSelections();var idxArr=[];if(!isRememberChecked){for(var i=0,length=records.length;i<length;i++){idxArr.push(records[i].get(DISLAND.BO_INDEX)+'');}}
boListStr+="<"+DISLAND.BOLIST+" "+DISLAND.BOLIST_ID+"='' "+DISLAND.BIND+"='"+$(this).attr(DISLAND.BIND)+"'>";if(!isRememberChecked){boList.children(DISLAND.BO).each(function(){alreadyChecked=true;if(idxArr.exist($(this).attr(DISLAND.BO_INDEX))){boListStr+=xmlToString($(this));}});}
else{checkedDom.children(DISLAND.BO).each(function(){alreadyChecked=true;boListStr+=xmlToString($(this));});}
boListStr+="</"+DISLAND.BOLIST+">";}
boListStr=boListStr.replaceAll("<!--,-->","<!,>");var win=parent.Ext.getCmp("qeweb-dialog");if(win){if("M"==win.dialogType){if(alreadyChecked){dialog.fillbackTable(win.sbopIds,win.tbopIds,boListStr,win.echo);win.close();}
else{showMSG.showWaring(I18N.ALERT_CHOOSE_RECORD);}}
else if("S"==win.dialogType){dialog.fillbackFC(win.sbopIds,win.tbopIds,boListStr,win.sysOperate);win.close();}}}
function treeDataStr(root,bind){var boStr="";if(!root.hasChildNodes())
return boStr;root.eachChild(function(child){if(child.ui.checkbox&&child.ui.checkbox.checked){boStr+="<"+DISLAND.BO+" "+DISLAND.BIND+"='"+bind+"'>";boStr+="<"+DISLAND.BOP+" "+DISLAND.BIND+"='"+DISLAND.BOP_ID+"'>";boStr+="<"+DISLAND.BOP_VALUE+">";boStr+=specialCharHandler(child.id);boStr+="</"+DISLAND.BOP_VALUE+">";boStr+="</"+DISLAND.BOP+">";boStr+="<"+DISLAND.BOP+" "+DISLAND.BIND+"='"+DISLAND.BOP_VALUE+"'>";boStr+="<"+DISLAND.BOP_VALUE+">";boStr+=specialCharHandler(child.text);boStr+="</"+DISLAND.BOP_VALUE+">";boStr+="</"+DISLAND.BOP+">";boStr+="</"+DISLAND.BO+">";}
boStr+=treeDataStr(child,bind);});return boStr;}
function showReturnMsg(){var message=$('#returnMsg').val();if(message&&message!='null'){var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var tipsType=pageDom.attr(DISLAND.TIPS_TYPE);if('simple'==tipsType){var delay=pageDom.attr(DISLAND.TIPS_DELAY);showTips.showOK(message,(isNaN(delay)?10:delay));}
else if('popup'==tipsType){var display=pageDom.attr(DISLAND.TIPS_DISPLAY);if("0"===display&&this.success)
return;showMSG.showOK(message);}}}
var oldId="";var oldCaseName="";var saveCaseWin=null;function sysSaveCase(saveBtnEvent){if(saveCaseWin==null){saveCaseWin=new Ext.Window({width:projectStyle.getSaveCaseWinWidth(),height:projectStyle.getSaveCaseWinHeight(),modal:true,title:I18N.SYS_SAVECASE_TITLE,layout:'fit',closeAction:'hide',maximizable:true,collapsible:true,items:new Ext.FormPanel({bodyStyle:{padding:'30'},labelWidth:100,border:false,labelAlign:'right',items:[{id:'saveCaseWin-input',xtype:'textfield',anchor:'98%',fieldLabel:'<font color=red>*</font> '+I18N.SYS_SAVECASE_IPT,allowBlank:false,maxLength:50}]}),buttons:[{xtype:'checkbox',id:'saveForNewCase',height:27},{xtype:'label',id:'saveForNewCase_label',html:I18N.SYS_SAVECASE_LABEL},{text:I18N.DIALOG_YES,iconCls:'yesIcon',handler:function(){var caseName=Ext.getCmp('saveCaseWin-input');if(!caseName.validateValue(caseName.getValue()))
return;var bo=DISLAND.getBODom(saveBtnEvent.containerDom.getId()).slice(0);var data=paramValue(xmlToString(bo));var isNewCase=Ext.getCmp('saveForNewCase').getValue();if(isNewCase){oldId="";oldCaseName="";}
else if(!!oldCaseName){oldCaseName=caseName.getValue();}
Ext.Ajax.request({url:appConfig.ctx+actionURL.saveCase(),method:"POST",params:saveCase.sourcePage+DISLAND.getSourcepage()+
param(saveCase.caseName)+encodeURIComponent(caseName.getValue())+
param(saveCase.containerId)+saveBtnEvent.containerDom.getId()+
param(saveCase.saveForNewCase)+isNewCase+
param(saveCase.oldId)+oldId+
param(saveCase.dataIsland)+data,timeout:appConfig.ajaxTimeout,success:function(response,options){validateSession(response.responseText);var resultMsg=new ResultMsg(response.responseText);if(resultMsg.success){ResultMessage.showMsg(resultMsg.success,I18N.MSG_OPERATION_SUCCESS,true,false);}
else{if(!resultMsg.msg||resultMsg.msg=='null')
resultMsg.msg=I18N.MSG_OPERATION_FAILURE;ResultMessage.showMsg(resultMsg.success,resultMsg.msg,true,false);}},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});saveCaseWin.hide();}}]});if(appConfig.hasCloseBtn){saveCaseWin.addButton({text:I18N.DIALOG_CLOSE,iconCls:'noIcon',handler:function(){saveCaseWin.hide();}});}}
Ext.getCmp('saveCaseWin-input').reset();if(!!oldCaseName){Ext.getCmp('saveCaseWin-input').setValue(oldCaseName);Ext.getCmp('saveForNewCase_label').show();Ext.getCmp('saveForNewCase').show();}
else{Ext.getCmp('saveForNewCase_label').hide();Ext.getCmp('saveForNewCase').hide();}
saveCaseWin.show(Ext.getBody());return false;}
var openCaseWin=null;var openCaseStore=null;function sysOpenCase(openCaseBtn){if(openCaseWin==null){openCaseStore=new Ext.data.JsonStore({url:appConfig.ctx+actionURL.findQueryCase()+param(saveCase.sourcePage)+DISLAND.getSourcepage(),root:'data',autoLoad:true,fields:[{name:'id'},{name:'caseName'},{name:'disland'}]});openCaseWin=new Ext.Window({width:projectStyle.getUseCaseWinWidth(),height:projectStyle.getUseCaseWinHeight(),modal:true,title:I18N.SYS_OPENCASE_TITLE,layout:'fit',closeAction:'hide',maximizable:true,collapsible:true,items:[new Ext.grid.GridPanel({id:'openCaseGrid',ds:openCaseStore,loadMask:true,stripeRows:true,sm:new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}),cm:new Ext.grid.ColumnModel({columns:[new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}),new Ext.grid.RowNumberer(),{dataIndex:'id',hidden:true,hideable:false},{header:I18N.SYS_SAVECASE_IPT,sortable:true,dataIndex:'caseName',width:200,fixed:true,menuDisabled:true,renderer:function(value,cell){return decodeToValue(value);}},{dataIndex:'disland',hidden:true,hideable:false}]}),tbar:[new Ext.Button({iconCls:'remove',text:I18N.BTN_DEL,handler:function(){var container=Ext.getCmp('openCaseGrid');var records=container.getSelectionModel().getSelections();if(records==0){alertMsg.choseRecord(I18N.SYS_CAVECASE_IPT);return;}
var ids=[];for(var i=0;i<records.length;i++){ids.push(records[i].data['id']);}
if(ids.exist(oldId)){oldId="";oldCaseName="";}
Ext.Ajax.request({url:appConfig.ctx+actionURL.delCase(),method:"POST",params:"ids="+ids.join(','),timeout:appConfig.ajaxTimeout,success:function(response,options){validateSession(response.responseText);openCaseStore.reload();},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});openCaseStore.load();}})],listeners:{'rowdblclick':function(grid,rowIndex){var recordData=grid.store.data.items[rowIndex].data;var boDom=XMLDomFactory.getInstance(recordData['disland']);oldId=recordData['id'];oldCaseName=recordData['caseName'];var formHandler=new FormHandler();formHandler.freshMyself(boDom);if(appConfig.isAutoSearch){var queryDom=boDom.find(DISLAND.OPERATE+"["+DISLAND.OPERATE_METHOD+"='"+BoFinalMethod.QUERY+"']");Ext.getCmp(queryDom.attr(DISLAND.OPERATE_ID)).fireEvent("click");}
openCaseWin.hide();}}})]});if(appConfig.hasCloseBtn){openCaseWin.addButton({text:I18N.DIALOG_CLOSE,iconCls:'noIcon',handler:function(){openCaseWin.hide();}});}}
openCaseStore.load();openCaseWin.show(Ext.getBody());return false;};
var showMSG={display_status_check:'qeweb_display_status',showMsg:function(message,icon,width,isNotAdd){unlockScreen();var msgWidth=showMSG.getMsgWidth(message);if(Ext.Msg.YES!=icon)
msg_check='';Ext.Msg.show({title:I18N.CONFIRM_TIPS,msg:showMSG.getMsg(message,isNotAdd),buttons:Ext.Msg.OK,icon:icon,fn:displayStatus.changeAlertStatus,width:width||msgWidth});},getMsgWidth:function(message,width){var msgWidth=(width?width:240);if(!!message&&message.length>9)
msgWidth=22*message.length;return msgWidth>500?500:msgWidth;},getMsg:function(message,isNotAdd){if(isNotAdd)
return message;var msg_check="";if(appConfig.optRemember=='1'){msg_check="<br><br><span style='text-align:center;'><input id='"
+showMSG.display_status_check
+"' type='checkbox'/>&nbsp;"
+I18N.CONFIRM_PROCESS_PROMPT
+"</span>";}
return message+msg_check;},showOK:function(message,width){this.showMsg(message,Ext.Msg.YES,width);},showWaring:function(message,width){this.showMsg(message,Ext.Msg.WARNING,width,true);},showErr:function(message,width){this.showMsg(message,Ext.Msg.ERROR,width,true);}};var alertMsg={choseRecord:function(title){if(!!title)
Ext.Msg.alert(I18N.CONFIRM_TIPS,I18N.ALERT_CHOOSE_BEFORE+title+I18N.ALERT_CHOOSE_AFTER);else
Ext.Msg.alert(I18N.CONFIRM_TIPS,I18N.ALERT_CHOOSE_RECORD);},modify:function(title){if(!!title)
Ext.Msg.alert(I18N.CONFIRM_TIPS,title+I18N.ALERT_MODIFIED_AFTER);else
Ext.Msg.alert(I18N.CONFIRM_TIPS,I18N.ALERT_MODIFIED_RECORD);}};var displayStatus={changeConfirmStatus:function(){var check=document.getElementById(showMSG.display_status_check)
if(!check||!check.checked)
return;Ext.Ajax.request({url:appConfig.ctx+actionURL.setConfirmStatus(),method:"POST",success:function(response){var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var json=StringToJSON(response.responseText)||new Array();pageDom.attr(DISLAND.CONFIRM_DISPLAY,json['confirmStatus']);}});},changeAlertStatus:function(){var check=document.getElementById(showMSG.display_status_check)
if(!check||!check.checked)
return;Ext.Ajax.request({url:appConfig.ctx+actionURL.setPopupStatus(),method:"POST",success:function(response){var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var json=StringToJSON(response.responseText)||new Array();pageDom.attr(DISLAND.TIPS_DISPLAY,json['popupStatus']);}});}};ResultMessage={showMsg:function(success,message,hasMsg,parentFlag){if(!hasMsg)
return;var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var tipsType=pageDom.attr(DISLAND.TIPS_TYPE);if(DISLAND.TIPS_TYPE_SIMPLE==tipsType){var delay=pageDom.attr(DISLAND.TIPS_DELAY)||DISLAND.TIPS_DELAY_DEF;if(parentFlag){if(success)
parent.showTips.showOK(message,delay);else
parent.showTips.showErr(message,delay);}
else{if(success)
showTips.showOK(message,delay);else
showTips.showErr(message,delay);}}
else if(DISLAND.TIPS_TYPE_POPUP==tipsType){if(success&&DISLAND.TIPS_DISPLAY_NO===pageDom.attr(DISLAND.TIPS_DISPLAY))
return;if(parentFlag){if(success)
parent.showMSG.showOK(message);else
parent.showMSG.showErr(message);}
else{if(success)
showMSG.showOK(message);else
showMSG.showErr(message);}}},showErrMsg:function(message){var pageDom=DISLAND.getDataIsland().children(DISLAND.PAGE);var tipsType=pageDom.attr(DISLAND.TIPS_TYPE);if(!message||message=='null')
message=I18N.MSG_OPERATION_FAILURE;if(DISLAND.TIPS_TYPE_SIMPLE==tipsType){var delay=pageDom.attr(DISLAND.TIPS_DELAY)||DISLAND.TIPS_DELAY_DEF;showTips.showErr(message,delay);}
else if(DISLAND.TIPS_TYPE_POPUP==tipsType){showMSG.showErr(message);}}};
var TableSetting={saveHiddenChange:function(columnModel,columnIndex,hidden){var hiddenBop=columnModel.columns[columnIndex].dataIndex;Ext.Ajax.request({url:appConfig.ctx+actionURL.saveHiddenChange(),method:"POST",params:tableSettingParam.sourcePage+DISLAND.getSourcepage()+
param(tableSettingParam.tableId)+columnModel.gridId+
param(tableSettingParam.hiddenBop)+hiddenBop+
param(tableSettingParam.isHidden)+hidden,timeout:appConfig.ajaxTimeout,success:function(response,options){validateSession(response.responseText);var resultMsg=new ResultMsg(response.responseText);if(!resultMsg.success){ResultMessage.showMsg(resultMsg.success,I18N.SYS_TABLESETTING_FAILURE,true,false);}},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});},saveColMoved:function(columnModel,oldIndex,newIndex){if(oldIndex==newIndex||columnModel.columns[newIndex].dataIndex=='index')
return;var position=[];for(var i=0;i<columnModel.columns.length;i++){var dataIndex=columnModel.columns[i].dataIndex;if(!!dataIndex)
position.push(dataIndex)}
Ext.Ajax.request({url:appConfig.ctx+actionURL.saveColMoved(),method:"POST",params:tableSettingParam.sourcePage+DISLAND.getSourcepage()+
param(tableSettingParam.tableId)+columnModel.gridId+
param(tableSettingParam.position)+position.join(','),timeout:appConfig.ajaxTimeout,success:function(response,options){validateSession(response.responseText);var resultMsg=new ResultMsg(response.responseText);if(!resultMsg.success){ResultMessage.showMsg(resultMsg.success,I18N.SYS_TABLESETTING_FAILURE,true,false);}},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});},saveWidthChange:function(columnIndex,newWidth,tableId){var columnModel=Ext.getCmp(tableId).getColumnModel();Ext.Ajax.request({url:appConfig.ctx+actionURL.saveWidthChanged(),method:"POST",params:tableSettingParam.sourcePage+DISLAND.getSourcepage()+
param(tableSettingParam.tableId)+tableId+
param(tableSettingParam.changeWidthBop)+columnModel.columns[columnIndex].dataIndex+
param(tableSettingParam.newWidth)+newWidth,timeout:appConfig.ajaxTimeout,success:function(response,options){validateSession(response.responseText);var resultMsg=new ResultMsg(response.responseText);if(!resultMsg.success){ResultMessage.showMsg(resultMsg.success,I18N.SYS_TABLESETTING_FAILURE,true,false);}},failure:function(response){showMSG.showErr(I18N.MSG_AJAX_FAILURE);}});}};