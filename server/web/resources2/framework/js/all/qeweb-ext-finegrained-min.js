
function AnchorHandler(bopDom,fc){AnchorHandler.superclass.constructor.call(this,bopDom,fc);this._addRelation=function(){};this._updateRange=function(){};this._setDisabled=function(isHidden){};this._setReadonly=function(isReadOnly){};this._getValue=function(){};this._setValue=function(value){var anchorDom=this.fc.getEl().dom;var operateId=DISLANDTODOM.getAnchorOperateId(this.bopDom);var operateDom=getDataIslandByJQ().find(DISLAND.OPERATE+"["+DISLAND.OPERATE_ID+"='"+operateId+"']");if(operateDom.length>0){var text=anchorDom.innerText.replace("\n","");anchorDom.innerHTML=anchorDom.innerHTML.replace(text,value);anchorDom.innerHTML=anchorDom.innerHTML.replaceAll(text,value);btnHandler=new ButtonHandler(operateDom);btnHandler.init();return;}
var data=this.bopDom.children(DISLAND.BOP_DATA);if(data.length==0){anchorDom.innerText=value;}
else{var url=data.attr(DISLAND.BOP_DATA_URL);if(!url){anchorDom.innerText=value;}
else{if(!isHttp(url))
url=appConfig.ctx+url;var a='<A href="'+url+'" target=_blank>'+value+'</A>';anchorDom.innerText=a;anchorDom.innerHTML=anchorDom.innerHTML.replace(/&lt;A.*&lt;\/A&gt;/,a);}}};this._getValue=function(){var anchorDom=this.fc.getEl().dom;return anchorDom.innerText;};}
extend(AnchorHandler,FCHandler);
var BOPSubmit={submit:function(fcHandler){var bo=fcHandler.boDom;var bop=fcHandler.bopDom;var fc=fcHandler.fc;var sysOperate;if(fc&&fc.tblIndex){BINDDATA.bindTableBop(bo,fcHandler);}
else{sysOperate=DISLANDTODOM.getSysOperateByForm(bo);DISLAND.setBOPValue(bop,fcHandler.getValue());}
Ext.Ajax.request({url:appConfig.ctx+actionURL.getFcToBopSubmit(),method:"POST",timeout:appConfig.ajaxTimeout,params:GA.vcId+DISLANDTODOM.getFCId(bop)+param(GA.dataIsland)+paramValue(xmlToString(bo)),success:function(response){var boDom=XMLDomFactory.getInstance(response.responseText).find(DISLAND.BO);boDom.children(DISLAND.BOP).each(function(){var bopDom=$(this);if(fc&&fc.tblIndex){DISLAND.getBOListDom(bo.attr(DISLAND.BOLIST_ID)).find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+fc.tblIndex+"']").children(DISLAND.BOP+"["+DISLAND.BIND+"='"+$(this).attr(DISLAND.BIND)+"']").each(function(){var value="";if(bopDom.text())
value=bopDom.text();$(this).text(value);Ext.getCmp(bo.attr(DISLAND.BOLIST_ID)).getStore().getAt(fc.tblIndex-1).set(bopDom.attr(DISLAND.BIND),value);if(bopDom.attr(DISLAND.STATUS_DISABLE))
$(this).attr(DISLAND.STATUS_DISABLE,bopDom.attr(DISLAND.STATUS_DISABLE));if(bopDom.attr(DISLAND.BOP_RANGE_ENUM))
$(this).attr(DISLAND.BOP_RANGE_ENUM,bopDom.attr(DISLAND.BOP_RANGE_ENUM));});}
else{var fc
if(sysOperate)
fc=DISLANDTODOM.getWinFC(bopDom,sysOperate);var handler=FCHandlerFactory.createFCHandler(bopDom,fc);handler.update();}});}});},conSubmit:function(fcHandler){if(fcHandler.conSubmit||!fcHandler.fc.validate())
return;var bo=fcHandler.boDom;var bop=fcHandler.bopDom;var boId=bo.attr(DISLAND.BO_ID);var container=Ext.getCmp(boId);var sysOperate;var oldValue=DISLAND.getValue(bop);if(oldValue==fcHandler.getValue())
return;if(CommonDom.isTable(container)){BINDDATA.bindTableBop(bo,fcHandler);}
else if(CommonDom.isForm(container)){sysOperate=DISLANDTODOM.getSysOperateByForm(bo);BINDDATA.bindBO(bo,null,sysOperate);}
lockSrceen();Ext.Ajax.request({url:appConfig.ctx+actionURL.getFcToBoSubmit(),method:"POST",timeout:appConfig.ajaxTimeout,params:GA.vcId+DISLANDTODOM.getFCId(bop)+param(GA.dataIsland)+paramValue(xmlToString(bo)),success:function(response){var boDom=XMLDomFactory.getInstance(response.responseText).find(DISLAND.BO);if(CommonDom.isTable(container)){var store=container.getStore();var boIndex=Number(boDom.attr(DISLAND.BO_INDEX));var tempFlag=boDom.attr("temp");var record=store.getAt(boIndex-1);boDom.children(DISLAND.BOP).each(function(){var fieldName=$(this).attr(DISLAND.BIND);var value=$(this).text();var targetBO=DISLAND.getBOListDom(boDom.attr(DISLAND.BO_ID)).find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+boDom.attr(DISLAND.BO_INDEX)+"']");var disable=$(this).attr(DISLAND.STATUS_DISABLE);var targetBOP=targetBO.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+fieldName+"']");if(disable)
targetBOP.attr(DISLAND.STATUS_DISABLE,disable);if('true'!=tempFlag){targetBOP.text(value);}
if(fieldName==bop.attr(DISLAND.BIND))
return true;record.set(fieldName,value);});fcHandler.conSubmit=true;var boList=DISLAND.getBOListDom(container.getId());BINDDATA.bindModifyModData(boList);TableHelper.formatCell(boList);}
else if(CommonDom.isForm(container)){boDom.children(DISLAND.BOP).each(function(){var fc;if(sysOperate)
fc=DISLANDTODOM.getWinFC($(this),sysOperate);var handler=FCHandlerFactory.createFCHandler($(this),fc);handler.update();});}
unlockScreen();}});}};
function CheckboxHandler(bopDom,fc){CheckboxHandler.superclass.constructor.call(this,bopDom,fc);this._RELATION_EVENT="check";this._addRelation=function(){if(this.bopDom.attr(DISLAND.BOP_ISRELATE)!='true')
return;var fcHandler=this;for(var i=0,length=fc.items.length;i<length;i++){var item=Ext.getCmp(fc.getId()+DISLAND.SPLIT_LINE+i);item.addListener(this._RELATION_EVENT,function(checkbox,checked){BOPSubmit.submit(fcHandler);});}};this._updateItems=function(range){var rangeList=range.children(DISLAND.BOP_RANGE_ENUM);if(rangeList<0)
return;var items=this.fc.items;for(var i=0,length=items.getCount();i<length;i++){var delItems=this.fc.items.items[0];delItems.destroy();this.fc.items.remove(delItems);}
var columns=this.fc.columns.length;var col=this.fc.panel.items.get(0);rangeList.each(function(){$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){var item=new Ext.form.Checkbox({id:fc.getId()+DISLAND.SPLIT_LINE+i++,name:fc.name,value:$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE),boxLabel:$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL),inputValue:$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE),vtype:'bopRange',itemCls:'allow-float',clearCls:'clear-float'});col=fc.panel.items.get(i%columns);col.add(item);fc.items.add(item);});});this.fc.panel.doLayout();};this._getValue=function(){var mutiValue="";for(var i=0,length=fc.items.length;i<length;i++){if(fc.items.get(i).checked){mutiValue+=(mutiValue?",":"")+fc.items.get(i).inputValue;}}
return mutiValue;};this._setValue=function(value){var mutiValue=String(value).split(DISLAND.SPLIT_COMMA);if(mutiValue.length==0)
return;fc.items.each(function(item){for(var i=0,length=mutiValue.length;i<length;i++){if(item.inputValue==mutiValue[i]){if(fc.getXType()=='checkboxgroup')
fc.setValue(item.getId(),true);else
item.setValue(true);return true;}
else{if(fc.getXType()=='checkboxgroup')
fc.setValue(item.getId(),false);else
item.setValue(false);}}});};this._setReadonly=function(isReadOnly){this._setDisabled(isReadOnly);};this._setDisabled=function(isDisabled){this.fc.items.each(function(item){item.setDisabled(isDisabled);});};}
extend(CheckboxHandler,FCHandler);
var CheckboxHelper={resize:function(checkbox){if(!$.browser.msie){var offset=18;if(!checkbox.panel)
return;var height=checkbox.panel.getHeight()+offset;checkbox.panel.setHeight(height);}}};
function DateHandler(bopDom,fc){DateHandler.superclass.constructor.call(this,bopDom,fc);this._addValidate=function(){if(fc.expend){var min=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MIN);var max=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MAX);min.addListener(this._RELATION_EVENT,function(){max.setMinValue(min.getValue()||'1900-1-1');});max.addListener(this._RELATION_EVENT,function(){min.setMaxValue(max.getValue()||'2999-12-31');});}};this._updateRange=function(){if(fc.expend){}
else{var updateRange=this.bopDom.children(DISLAND.BOP_RANGE);var bo=DISLAND.getBODom(this.boDom.attr(DISLAND.BO_ID));var bop=bo.find(DISLAND.BOP+"["+DISLAND.BIND+"="+this.bopDom.attr(DISLAND.BIND)+"]");this._updateRequired(updateRange,bop);}};this._updateStatus=function(){if(fc.expend){}
else{var $super=new FCHandler();$super._updateStatus.call(this);}};this._getValue=function(){if(fc.expend){var min=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MIN);var max=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MAX);return(min.value||'')+DISLAND.SPLIT_COMMA+(max.value||'');}
else{return fc.value;}};this._setValue=function(value){var date=this.getFormatDate(value);this.fc.format='Y-m-d';this.fc.setValue(date);};this._setMinValue=function(value){var min=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MIN);min.format='Y-m-d';var date=this.getFormatDate(value);min.setValue(date);};this._setMaxValue=function(value){var max=Ext.getCmp(fc.getId()+DISLAND.SPLIT_EXPEND+DISLAND.BOP_MAX);max.format='Y-m-d';var date=this.getFormatDate(value);max.setValue(date);}}
extend(DateHandler,FCHandler);DateHandler.prototype.getFormatDate=function(value){var date='';if(value&&typeof value=='object'){date=new Date(value.time);date.setHours(value.hours);date.setMinutes(value.minutes);date.setSeconds(value.seconds);}
else if(value&&typeof value=='string'){date=new Date();date.setDate(parseInt(value.split("-")[2]));date.setMonth(parseInt(value.split("-")[1])-1);date.setFullYear(value.split("-")[0]);}
return date;};
function EditorHandler(bopDom,fc){EditorHandler.superclass.constructor.call(this,bopDom,fc);this._RELATION_EVENT="change";this._setDisabled=function(isDisabled){this._setReadonly(isDisabled);};}
extend(EditorHandler,TextHandler);
function FCHandler(bopDom,fc){this._RELATION_EVENT="change";this.bopDom=bopDom;if(!!bopDom)
this.boDom=bopDom.parent();this.fc=fc;this._addRelation=function(){if(!this.fc)
return;if(this.bopDom.attr(DISLAND.BOP_ISCONRELATE)=='true'){var fcHandler=this;this.fc.addListener(this._RELATION_EVENT,function(){BOPSubmit.conSubmit(fcHandler);});}
if(this.bopDom.attr(DISLAND.BOP_ISRELATE)=='true'){var fcHandler=this;this.fc.addListener(this._RELATION_EVENT,function(){BOPSubmit.submit(fcHandler);});}
var boDom=this.boDom;if(this.bopDom.attr(DISLAND.BOP_ISTIGGER)=='true'&&!!boDom){this.fc.addListener(this._RELATION_EVENT,function(){var boId=boDom.attr(DISLAND.BO_ID);if(observableArr[boId]){lockSrceen();BINDDATA.bindBO(boDom,BoFinalMethod.QUERY);BINDDATA.bindOperate(boDom,BoFinalMethod.QUERY);observableArr[boId].notify();}});}};this._addValidate=function(){};this._addJS=function(){};this._getValue=function(){};this._setValue=function(value){};this._setMinValue=function(value){};this._setMaxValue=function(value){};this._setStatus=function(){this._setHidden(this.getInitHidden());};this._updateRange=function(){var updateRange=this.bopDom.children(DISLAND.BOP_RANGE);if(updateRange!=null&&updateRange.length>0){var bo=DISLAND.getBODom(this.boDom.attr(DISLAND.BO_ID));var bop=bo.find(DISLAND.BOP+"["+DISLAND.BIND+"="+this.bopDom.attr(DISLAND.BIND)+"]");var range=bop.children(DISLAND.BOP_RANGE);range.remove();bop.append(updateRange);this._updateRequired(updateRange,bop);}
this._updateItems(updateRange);if(!!!this.fc||!this.fc.el)
return;if(this.fc&&typeof this.fc.validate=="function")
this.fc.validate();};this._updateRequired=function(range,bop){if(bop.length==0)
return;var required=range.attr(DISLAND.BOP_RANGE_REQUIRED);var fieldLabel=Ext.DomQuery.selectNode('label[for='+DISLANDTODOM.getFCId(bop)+']');if(!fieldLabel)
return;var labelArr=[];if($.browser.msie)
labelArr=fieldLabel.innerHTML.split("</FONT>");else
labelArr=fieldLabel.innerHTML.split("</font>");if(required=="true"){this.fc.allowBlank=false;fieldLabel.innerHTML="<FONT color='red'>*</FONT>"+labelArr[labelArr.length-1];}
else{this.fc.allowBlank=true;fieldLabel.innerHTML=labelArr[labelArr.length-1];}};this._updateItems=function(range){};this._updateValue=function(){var value;var valueDom=this.bopDom.children(DISLAND.BOP_VALUE);if(valueDom.length==2){this._setMinValue(valueDom.slice(0)[0].text);this._setMaxValue(getXmlNoteText(valueDom.slice(1)));}
else if(valueDom.length>0){value=getXmlNoteText(valueDom);}
else{value=getXmlNoteText(this.bopDom);}
this._setValue(decodeToDisplay(getUnescapedText(value)));};this._updateStatus=function(){var status=this.bopDom.children(DISLAND.STATUS);if(status.length==0)
status=this.bopDom;this._setReadonly(status.attr(DISLAND.STATUS_READONLY));this._setDisabled(status.attr(DISLAND.STATUS_DISABLE));this._setHidden(status.attr(DISLAND.STATUS_HIDDEN));};this._setDisabled=function(isDisabled){if(!!!this.fc)
return;this.fc.setDisabled(true);if(isDisabled==="true")
this.fc.setDisabled(true);else
this.fc.setDisabled(false);};this._setHidden=function(isHidden){if(!!!this.fc||!this.fc.el)
return;if(isHidden==="true"){this.fc.hide();this.fc.el.parent().parent().parent().hide();}
else{this.fc.show();this.fc.el.parent().parent().parent().show();}};this._setReadonly=function(isReadOnly){if(!!!this.fc)
return;if(isReadOnly==="true")
this.fc.setReadOnly(true);else
this.fc.setReadOnly(false);};this._setHistory=function(){if(!(this.fc&&this.fc.historyText))
return;this._createIconDiv();this._createTipDiv();};this._createIconDiv=function(){var tipMsg=DISLAND.getBOPLabel(this.bopDom,this.fc.historyText);var bopText=DISLAND.getBOPLabel(this.bopDom);if(isEmpty(tipMsg)||tipMsg==bopText)
return;var fcDom=this._getFcRealDom();var fcParent=this._getFcParentDom(fcDom);var offset=fcDom.offset();var iconDiv=$('div[id="history-'+this.fc.id+'"]');if(iconDiv.length==0){iconDiv=$('<div id="history-'+this.fc.id+'"></div>');}
var left=offset.left+fcDom.width()-16;var top=offset.top-32;var tipContent=this._getTipContent(tipMsg);iconDiv.attr('class','history-icon');iconDiv.css('left',left);iconDiv.css('top',top);iconDiv.hover(function(){var offset1=$(this).offset();var top=offset1.top+30;var tip=$('#his-tipDiv');tip.css('top',top);tip.html(tipContent);var left=offset1.left+30;if(left+tip.width()>$('body').width())
left=$('body').width()-tip.width()-10;tip.css('left',left);tip.css('visibility','visible');tip.fadeIn();},function(){var tip=$('#his-tipDiv');tip.fadeOut();});fcParent.append(iconDiv);};this._createTipDiv=function(){var tipDiv=$('#his-tipDiv');if(tipDiv.length==0)
tipDiv=$('<div id="his-tipDiv"></div>');tipDiv.attr('class','history-tip');tipDiv.css('visibility','hidden');tipDiv.hover(function(){tipDiv.stop(true,true);tipDiv.fadeIn();},function(){tipDiv.fadeOut();});$('body').append(tipDiv);};this._getFcRealDom=function(){return $(this.fc.getEl().dom);};this._getTipContent=function(tipMsg){return I18N.HISTORY+tipMsg;};this._getFcParentDom=function(fcDom){return fcDom.parents('td').children('div');};}
FCHandler.prototype.getInitHidden=function(){return DISLAND.getBOPStatus(this.bopDom).attr(DISLAND.STATUS_HIDDEN);};FCHandler.prototype.getValue=function(){return this._getValue()||"";};FCHandler.prototype.setValue=function(value){this._setValue(value);};FCHandler.prototype.init=function(){this._addRelation();this._addValidate();this._addJS();this._setStatus();this._setHistory();};FCHandler.prototype.update=function(){this._updateRange();this._updateValue();this._updateStatus();};FCHandler.prototype.updateStatus=function(disable,hidden,readonly){var status=this.bopDom.children(DISLAND.STATUS);if(disable!=undefined)
status.attr(DISLAND.STATUS_DISABLE,disable);if(hidden!=undefined)
status.attr(DISLAND.STATUS_HIDDEN,hidden);if(readonly!=undefined)
status.attr(DISLAND.STATUS_READONLY,readonly);this._updateStatus();};FCHandler.prototype.setReadonly=function(){var status=this.bopDom.children(DISLAND.STATUS);status.attr(DISLAND.STATUS_READONLY,'true');this._setReadonly('true');};FCHandler.prototype.resize=function(){var iconDiv=$('div[id="history-'+this.fc.id+'"]');if(iconDiv.length==0)
return;var fcDom=this._getFcRealDom();var offset=$(fcDom).offset();var left=offset.left+fcDom.width()-16;var top=offset.top-32;iconDiv.css('left',left);iconDiv.css('top',top);};FCHandler.prototype.getRelationEventName=function(){return this._RELATION_EVENT;};
function FileHandler(bopDom,fc){FileHandler.superclass.constructor.call(this,bopDom,fc);this._getValue=function(){var check=Ext.getCmp(DISLANDTODOM.getFileCheckID(fc.getId()));return check?check.value:fc.value;};this._setValue=function(value){var check=Ext.getCmp(DISLANDTODOM.getFileCheckID(fc.getId()));if(check)
check.setValue(value);};this._updateValue=function(){this._setValue(bopDom.text());};this._getTipContent=function(tipMsg){var href;if(isHttp(tipMsg))
href=appConfig.ctx+tipMsg;else
href=tipMsg;var fc=this._getFcRealDom();return I18N.HISTORY+'<br/><a href="'+href+'" target="_blank">'+I18N.FILE_DOWNLOAD+'</a>';};this._setHidden=function(isHidden){if(!!!this.fc||!this.fc.el)
return;if(isHidden==="true"){this.fc.hide();this.fc.el.parent().parent().parent().parent().parent().hide();this.fc.el.parent().parent().parent().hide();}
else{this.fc.show();this.fc.el.parent().parent().parent().parent().parent().show();this.fc.el.parent().parent().parent().show();}};this._updateRequired=function(range,bop){if(bop.length==0)
return;var required=range.attr(DISLAND.BOP_RANGE_REQUIRED);var blank=Ext.getCmp(this.fc.getId()+'_anchor_check');blank.allowBlank=!(required=='true');};}
extend(FileHandler,FCHandler);
function HiddenHandler(bopDom,fc){HiddenHandler.superclass.constructor.call(this,bopDom,fc);this._setHidden=function(isHidden){};this._setReadonly=function(isReadOnly){};this._updateRange=function(){};}
extend(HiddenHandler,TextHandler);
function ImgHandler(bopDom,fc){ImgHandler.superclass.constructor.call(this,bopDom,fc);this._IMG_SUFFIX="img";this.img=Ext.getCmp(fc.getId()+DISLAND.SPLIT_LINE+this._IMG_SUFFIX);this._updateValue=function(){var value=bopDom.children(DISLAND.BOP_VALUE).text();this.img.setSrc(value);};this._updateRange=function(){};this._setReadonly=function(isReadOnly){};this._getValue=function(){return this.img.getSrc();};this._setValue=function(value){if(isHttp(value))
this.img.setSrc(value);else
this.img.setSrc(appConfig.ctx+value);};this._getFcRealDom=function(){return $(this.fc.getEl().dom).find('img');};this._getTipContent=function(tipMsg){var src;if(isHttp(tipMsg))
src=appConfig.ctx+tipMsg;else
src=tipMsg;var fc=this._getFcRealDom();return I18N.HISTORY+'<br/><img src="'+src+'" width="'+fc.width()
+'" height="'+fc.height()+'"/>';};}
extend(ImgHandler,FCHandler);
function LabelHandler(bopDom,fc){LabelHandler.superclass.constructor.call(this,bopDom,fc);this._addRelation=function(){};this._updateRange=function(){};this._setHidden=function(isHidden){if(!this.fc.el)
return;if(isHidden==="true"){this.fc.hide();if(!this.fc.fieldLabel){this.fc.el.parent().parent().parent().parent().hide();}
else{this.fc.el.parent().parent().hide();}}
else{this.fc.show();if(!this.fc.fieldLabel){this.fc.el.parent().parent().parent().parent().show();}
else{this.fc.el.parent().parent().show();}}};this._setReadonly=function(isReadOnly){};this._getValue=function(){return this.fc.realValue||this.fc.text;};this._setValue=function(value){var text=value;var realValue="";this.bopDom.find(DISLAND.BOP_RANGE).children().each(function(){if($(this).context.nodeName!=DISLAND.BOP_RANGE_ENUM)
return true;if(value&&$(this).is(DISLAND.BOP_RANGE_ENUM)){$(this).children(DISLAND.BOP_RANGE_ITEM+"["+DISLAND.BOP_RANGE_ITEM_VALUE+"='"+value+"']").each(function(){text=$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);realValue=$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE);return false;});}});this.fc.setText(text);this.fc.realValue=realValue;};}
extend(LabelHandler,FCHandler);
function OptTransHandler(bopDom,fc){OptTransHandler.superclass.constructor.call(this,bopDom,fc);this._updateItems=function(range){var rangeList=range.children();if(rangeList==null||rangeList.length==0)
return;var newData=[];rangeList.each(function(){if($(this).is(DISLAND.BOP_RANGE_ENUM)){$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){newData.push([$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE),$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL)]);});}});fc.fromMultiselect.store.loadData(newData);};this._getValue=function(){var result=[];var items=fc.toStore.data.items;for(var i=0;i<items.length;i++){result.push(items[i].json[0]);}
return result;};}
extend(OptTransHandler,FCHandler);
function RadioHandler(bopDom,fc){RadioHandler.superclass.constructor.call(this,bopDom,fc);this._RELATION_EVENT="change";this._addRelation=function(){if(this.bopDom.attr(DISLAND.BOP_ISRELATE)!='true')
return;var fcHandler=this;fc.addListener(this._RELATION_EVENT,function(){BOPSubmit.submit(fcHandler);});};this._updateItems=function(range){if(range==null||range.children().length==0)
return;};this._getValue=function(){for(var i=0,length=fc.items.length;i<length;i++){try{if(fc.items.get(i).checked)
return fc.items.get(i).value;}catch(e){if(fc.items[i].checked)
return fc.items[i].value;}}
return'';};this._setValue=function(value){fc.items.each(function(item){if(item.value==value)
item.setValue(true);else
item.setValue(false);});};this._setReadonly=function(isReadOnly){};this._setDisabled=function(isDisabled){var flag=false;if(isDisabled==="true")
flag=true;this.fc.items.each(function(item){item.setDisabled(flag);});};}
extend(RadioHandler,FCHandler);
function SelectHandler(bopDom,fc){SelectHandler.superclass.constructor.call(this,bopDom,fc);this._RELATION_EVENT="select";this._updateItems=function(range){var itmesStr=bopDom.attr(DISLAND.BOP_RANGE_ENUM);var newData=[];if(itmesStr){itmesStr=itmesStr.replaceAll("&quot;","\"");var items=eval('('+itmesStr+')');for(var i=0;i<items.length;i++){newData.push([items[i].k,items[i].v]);}
this._loadData(newData);return;}
if(range!=null&&range.children().length>0){var temp=[];range.children(DISLAND.BOP_RANGE_ENUM).each(function(){$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){var key=$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE);if(temp[key])
return true;temp[key]=1;newData.push([key,$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL)]);});});this._loadData(newData);}
else{return;}};this._loadData=function(data){this.fc.getStore().loadData(data);this.fc.setValue(data[0]!=null?data[0][1]:"");};this._getValue=function(){return this.fc.getValue();};this._setValue=function(value){var items=this.fc.getStore().data.items;for(var i=0,length=items.length;i<length;i++){if(items[i].data.valueField==value){this.fc.setValue(value);break;}}};}
extend(SelectHandler,FCHandler);
function TextHandler(bopDom,fc){TextHandler.superclass.constructor.call(this,bopDom,fc);this._RELATION_EVENT="blur";this._setHidden=function(isHidden){if(!this.fc.el)
return;if(isHidden==="true"){this.fc.hide();if(!this.fc.fieldLabel){this.fc.el.parent().parent().parent().parent().hide();}
else{this.fc.el.parent().parent().hide();}}
else{this.fc.show();if(!this.fc.fieldLabel){this.fc.el.parent().parent().parent().parent().show();}
else{this.fc.el.parent().parent().show();}}};this._getValue=function(){return this.fc.realValue||this.fc.getValue();};this._setValue=function(value){if(this.fc.getXType()=="compositefield")
this.fc.items.items[0].setValue(value);else
this.fc.setValue(value);};}
extend(TextHandler,FCHandler);
var FCHandlerFactory={createFCHandler:function(bopDom,fcObj){var fc=fcObj||DISLANDTODOM.getFC(bopDom);if(CommonDom.isLabelDom(fc))
return new LabelHandler(bopDom,fc);else if(CommonDom.isTextDom(fc))
return new TextHandler(bopDom,fc);else if(CommonDom.isHidden(fc))
return new HiddenHandler(bopDom,fc);else if(CommonDom.isSelect(fc))
return new SelectHandler(bopDom,fc);else if(CommonDom.isRadio(fc))
return new RadioHandler(bopDom,fc);else if(CommonDom.isCheckbox(fc))
return new CheckboxHandler(bopDom,fc);else if(CommonDom.isImg(fc))
return new ImgHandler(bopDom,fc);else if(CommonDom.isOptTrans(fc))
return new OptTransHandler(bopDom,fc);else if(CommonDom.isDate(fc))
return new DateHandler(bopDom,fc);else if(CommonDom.isFile(fc))
return new FileHandler(bopDom,fc);else if(CommonDom.isAnchor(fc))
return new AnchorHandler(bopDom,fc);else if(CommonDom.isEditorDom(fc))
return new EditorHandler(bopDom,fc);else
return new FCHandler(bopDom,fc);}};