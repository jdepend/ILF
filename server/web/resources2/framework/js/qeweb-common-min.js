
var BoFinalMethod={QUERY:"query",UPDATE:"update",DELETE:"delete",INSERT:"insert",VIEW:"view",RECORD:"getRecord",SYSADDROW:"sysAddRow",SYSDELROW:"sysDelRow",JSDELETE:"jsDelete",SYSTEMP:"sys-temp",SYSCLEAR:"sysClear",SYSRESET:"sysReset",SYSFRESH:"sysFresh",SYSSAVECASE:"sysSaveCase",SYSOPENCASE:"sysOpenCase",isQuery:function(method){return!!method&&this.QUERY===method;},isView:function(method){return!!method&&this.VIEW===method;},isUpdate:function(method){return!!method&&this.UPDATE===method;},isDelete:function(method){return!!method&&this.DELETE===method;},isInsert:function(method){return!!method&&this.INSERT===method;},isStandMethod:function(method){return this.isUpdate(method)||this.isInsert(method)||this.isDelete(method);},isPopMethod:function(method){return this.isUpdate(method)||this.isInsert(method)||this.isView(method);},isExport:function(method){return method.indexOf("exp")==0;},isAddRow:function(method){return!!method&&this.SYSADDROW===method;},isDelRow:function(method){return!!method&&this.SYSDELROW===method;},isPageFlow:function(method){return!!method&&this.SYSTEMP===method;},isClear:function(method){return!!method&&this.SYSCLEAR===method;},isReset:function(method){return!!method&&this.SYSRESET===method;},isSysFresh:function(method){return!!method&&this.SYSFRESH===method;}};
function getDataIslandByJQ(){return getDataIsland().find(DISLAND.dataIsland).slice(0);}
$(document).ready(function(){getDataIsland=initDataisland();});function hasConLayout(){return $('#dataIsland').attr('layout')=='true';}
function initDataisland(){var _island=XMLDomFactory.getInstance();function manageDataIsland(dataIslandStr){if(!!dataIslandStr)
_island=XMLDomFactory.getInstance(dataIslandStr);return _island;}
return manageDataIsland;}
String.prototype.replaceAll=function(s1,s2){return this.replace(new RegExp(s1,"g"),s2);}
function specialCharHandler(str){if(str instanceof Array)
str=str.toString();return"<![CDATA["+replaceAll(str,"&,%",":amp;,%25")+"]]>";}
function getCDATAContent(str){return"<![CDATA["+str+"]]>";}
function specialCharEncode(str){return replaceAll(str,":amp;,%25","&,%");}
KeyDown={on:function(evt){if(evt.keyCode==13){KeyDown.submit();}},submit:function(){var ae=document.activeElement;if(ae.type&&ae.type==='textarea')
return;var operateList=DISLAND.getOperateList();if(!operateList)
return;var btn=DISLANDTODOM.getFisrstShowBtn();if(!!btn)
btn.fireEvent("click");}};function addKeyDownListener(){if(document.addEventListener){document.addEventListener("keypress",KeyDown.on,true);}
else{document.attachEvent("onkeypress",KeyDown.on);}}
addKeyDownListener();function isUseExt(){return typeof(Ext)=="object";}
function isUseBorderLayout(){return typeof addTab=='function';}
function sleep(milliSeconds){var startTime=new Date().getTime();while(new Date().getTime()<startTime+milliSeconds);}
function validateSession(actionView){if(actionView==ConstantJSON.RELOGIN){window.parent.location.href=appConfig.ctx+actionURL.getRelogin();}}
function getFCValue(btnEvent,bopBind){var bo=btnEvent.operateDom.parent();var bop=bo.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+bopBind+"']");return getXmlNoteText(bop);}
function getAdaptiveSize(size){return screen.width>1280?size:size/1280*screen.width}
var NumberUtil={add:function(arg1,arg2){var r1,r2,m;try{r1=String(arg1).split(".")[1].length;}catch(e){r1=0;}
try{r2=String(arg2).split(".")[1].length;}catch(e){r2=0;}
m=Math.pow(10,Math.max(r1,r2));return(arg1*m+arg2*m)/m;},sub:function(arg1,arg2){var r1,r2,m,n;try{r1=String(arg1).split(".")[1].length;}catch(e){r1=0;}
try{r2=String(arg2).split(".")[1].length;}catch(e){r2=0;}
m=Math.pow(10,Math.max(r1,r2));n=(r1>=r2)?r1:r2;return((arg1*m-arg2*m)/m).toFixed(n);},mul:function(arg1,arg2)
{var m=0,s1=String(arg1),s2=String(arg2);try{m+=s1.split(".")[1].length;}catch(e){}
try{m+=s2.split(".")[1].length;}catch(e){}
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);},div:function(arg1,arg2){var t1=0,t2=0,r1,r2;try{t1=String(arg1).split(".")[1].length;}catch(e){}
try{t2=String(arg2).split(".")[1].length;}catch(e){}
with(Math){r1=Number(String(arg1).replace(".",""));r2=Number(String(arg2).replace(".",""));return(r1/r2)*pow(10,t2-t1);}},mod:function(arg1,arg2){var t1=0,t2=0,r1,r2;try{t1=String(arg1).split(".")[1].length;}catch(e){}
try{t2=String(arg2).split(".")[1].length;}catch(e){}
with(Math){r1=Number(String(arg1).replace(".",""));r2=Number(String(arg2).replace(".",""));return(r1%r2)*pow(10,t2-t1);}}};
function Observable(){this.observers=[];this.notify=function(data){var length=this.observers.length;if(length>0){for(var i=0;i<length;i++){this.observers[i].update(this,data);}
this.callback();}};this.addObserver=function(observer){this.observers.push(observer);};this.callback=function(){};};
function Observer(){this.update=function(observable,data){}}
var TRANSDISLANDDOM={transMaxValue:function(maxValue){var xmlStr="<value expend=\"max\">"+maxRawValue+"</value>"
return XMLDomFactory.getInstance(xmlStr);}};
var XMLDomFactory=(function(){return{getInstance:function(dataIslandStr){var xmlDom;if(dataIslandStr==undefined)
dataIslandStr=$('#'+DISLAND.hiddenId).val();if($.browser.msie){xmlDom=new ActiveXObject("Microsoft.XMLDOM");xmlDom.loadXML(dataIslandStr);}
else{var domParser=new DOMParser();xmlDom=domParser.parseFromString(dataIslandStr,"text/xml");}
return $(xmlDom);}};})();
var actionURL={getSecurityURL:function(url){var disland=getDataIslandByJQ();return url+"?timestemp="+new Date().getTime()
+"&sessionTicket="+disland.attr(DISLAND.SESSIONTICKET)
+"&tokenTicket="+disland.attr(DISLAND.TOKEN_TICKET);},getRelogin:function(){return"/system/login.action";},getFcToBopSubmit:function(){return this.getSecurityURL("/system/finegrainedSubmitToBopAC.action");},getFcToBoSubmit:function(){return this.getSecurityURL("/system/finegrainedSubmitToBoAC.action");},getFcServerValidate:function(){return this.getSecurityURL("/system/serverValidate.action");},getBoRelation:function(){return this.getSecurityURL("/system/boRelationAC.action");},getGaRecord:function(){return this.getSecurityURL("/system/generalRecordAC.action");},getGaSearch:function(){return this.getSecurityURL("/system/generalSearchAC.action");},getGaExe:function(){return this.getSecurityURL("/system/generalExeAC.action");},getRedirect:function(target){if(!!target)
return this.getSecurityURL("/system/generalredirectAC.action?redirectStr="+target);else
return"/system/generalredirectAC.action?redirectStr=";},saveParam:function(){return this.getSecurityURL("/system/generalSaveParamAC.action");},uploadFile:function(){return this.getSecurityURL("/system/fileUploadAC.action");},uploadImg:function(){return this.getSecurityURL("/system/imgUploadAC.action");},saveCase:function(){return this.getSecurityURL("/system/saveCaseAC!saveCase.action");},findQueryCase:function(){return this.getSecurityURL("/system/saveCaseAC!findQueryCase.action");},saveHiddenChange:function(){return this.getSecurityURL("/system/tableSettingAC!saveHiddenChange.action");},saveColMoved:function(){return this.getSecurityURL("/system/tableSettingAC!saveColMoved.action");},saveWidthChanged:function(){return this.getSecurityURL("/system/tableSettingAC!saveWidthChanged.action");},delCase:function(){return this.getSecurityURL("/system/saveCaseAC!delCase.action");},getMap:function(){return"/framework/common/map/gmap.jsp";},doExport:function(){return this.getSecurityURL("/system/export.action");},setPopupStatus:function(){return this.getSecurityURL("/system/setPopupStatus.action");},setConfirmStatus:function(){return this.getSecurityURL("/system/setConfirmStatus.action");},reloadTargetVC:function(){return this.getSecurityURL("/system/reloadTargetVC.action");},reloadPageBtn:function(){return this.getSecurityURL("/system/reloadPageBtn.action");}};
Array.prototype.indexOf=function(val){for(var i=0;i<this.length;i++){if(this[i]==val)return i;}
return-1;};Array.prototype.exist=function(val){return this.indexOf(val)!=-1;};Array.prototype.remove=function(val){var index=this.indexOf(val);if(index>-1){this.splice(index,1);}};Date.prototype.pattern=function(fmt){var o={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours()%12==0?12:this.getHours()%12,"H+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),"S":this.getMilliseconds()};var week={"0":"\u65e5","1":"\u4e00","2":"\u4e8c","3":"\u4e09","4":"\u56db","5":"\u4e94","6":"\u516d"};if(/(y+)/.test(fmt)){fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));}
if(/(E+)/.test(fmt)){fmt=fmt.replace(RegExp.$1,((RegExp.$1.length>1)?(RegExp.$1.length>2?"\u661f\u671f":"\u5468"):"")+week[this.getDay()+""]);}
for(var k in o){if(new RegExp("("+k+")").test(fmt)){fmt=fmt.replace(RegExp.$1,(RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length)));}}
return fmt;};
var BINDDATA={bindBO:function(boDom,method,panelType){DISLAND.clearPageOptStatus();boDom.children(DISLAND.BOP).each(function(){var bop=$(this);var fc="";if(!!panelType){var fcId=DISLANDTODOM.getWinFCId(bop,panelType);fc=Ext.getCmp(fcId);}
var fcHandler=FCHandlerFactory.createFCHandler(bop,fc);if(!fcHandler)
return true;var bopValue=trim(fcHandler.getValue());if(!fcHandler.fc&&!bopValue){bopValue=DISLAND.getValue(bop);}
var values=bop.children(DISLAND.BOP_VALUE);if(values.length==1){values.text(specialCharHandler(bopValue));}
else{var min=bopValue.split(DISLAND.SPLIT_COMMA)[0];var max=bopValue.split(DISLAND.SPLIT_COMMA)[1];values.slice(0,1).text(min);values.slice(1,2).text(max);}});if(!!method){boDom.attr(DISLAND.BO_OPERATIONSTATUS,method);}},bindPage:function(operateDom){DISLAND.clearPageOptStatus();operateDom=$(operateDom);if(DISLANDTODOM.btn_isEmptyMod(operateDom)||DISLANDTODOM.pageBtn_isEmptyMod(operateDom))
return;var optStatus=DISLAND.operate_BOMethod(operateDom);DISLAND.getDataIsland().children().each(function(){if($(this).is(DISLAND.BO)){BINDDATA.bindBO($(this),optStatus);}
else if($(this).is(DISLAND.PAGE)){BINDDATA.bindOperate($(this),optStatus);}
else if($(this).is(DISLAND.BOLIST)){BINDDATA.bindPageBOList($(this),operateDom);}
else if($(this).is(DISLAND.TREE)){BINDDATA.bindTree($(this),optStatus);}});},bindPageBOList:function(boListDom,operateDom){DISLAND.clearPageOptStatus();if(!Ext.getCmp(boListDom.attr(DISLAND.BOLIST_ID)))
return;TableHelper.rememberCheckedRow(boListDom.attr(DISLAND.BOLIST_ID));var bindFun;var containerIds=[];operateDom=$(operateDom);if(DISLANDTODOM.pageBtn_isSelectMod(operateDom)){bindFun=BINDDATA.bindSelectModData;containerIds=DISLANDTODOM.getSelectContainerId(operateDom);}
else if(DISLANDTODOM.pageBtn_isModifyMod(operateDom)){bindFun=BINDDATA.bindModifyModData;containerIds=DISLANDTODOM.getModifyContainerId(operateDom);}
else if(DISLANDTODOM.pageBtn_isAdaptiveMod(operateDom)){bindFun=BINDDATA.bindSelectModData;containerIds=DISLANDTODOM.getAdaptiveContainerId(operateDom);}
else{BINDDATA.bindModifyModData(boListDom,DISLAND.operate_BOMethod(operateDom),true);}
var optStatus=DISLAND.operate_BOMethod(operateDom);for(var i=0;i<containerIds.length;i++){if(containerIds[i]==boListDom.attr(DISLAND.BO_ID)&&!!bindFun){bindFun(boListDom,optStatus);}}},bindBOList:function(boListDom,operateDom){DISLAND.clearPageOptStatus();operateDom=$(operateDom);var isRowBtn=operateDom.attr(DISLAND.OPERATE_IDX);if(!isRowBtn){TableHelper.rememberCheckedRow(boListDom.attr(DISLAND.BOLIST_ID));}
if(!!operateDom.attr(DISLAND.OPERATE_IDX)){this.bindRowData(operateDom);}
else if(DISLANDTODOM.btn_isSelectMod(operateDom)||BoFinalMethod.isStandMethod(DISLAND.operate_BOMethod(operateDom))){this.bindSelectModData(boListDom,DISLAND.operate_BOMethod(operateDom));}
else if(DISLANDTODOM.btn_isModifyMod(operateDom)){this.bindModifyModData(boListDom,DISLAND.operate_BOMethod(operateDom));}
else if(DISLANDTODOM.btn_isAdaptiveMod(operateDom)){this.bindSelectModData(boListDom,DISLAND.operate_BOMethod(operateDom));}
else if(DISLANDTODOM.btn_isEmptyMod(operateDom)){;}
else if(DISLANDTODOM.btn_isAllMod(operateDom)){this.bindAllModData(boListDom);}
else{this.bindModifyModData(boListDom,DISLAND.operate_BOMethod(operateDom),true);}},bindRowData:function(operateDom){DISLAND.clearPageOptStatus();var containerId=DISLANDTODOM.getContainerId(operateDom);var boListDom=DISLAND.getBOListDom(containerId);boListDom.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS,"");this.bindOperate(operateDom.parent(),DISLAND.operate_BOMethod(operateDom),operateDom.attr(DISLAND.BIND));},bindSelectModData:function(boListDom,method){DISLAND.clearPageOptStatus();BINDDATA.bindModifyModData(boListDom,method);var bos=DISLAND.getBOListDom(boListDom.attr(DISLAND.BO_ID)).children(DISLAND.BO);bos.attr(DISLAND.BO_OPERATIONSTATUS,'');var tableHandler=new TableHandler(boListDom);var arr=tableHandler.getSelections();bos.each(function(){if(isInArray(arr,$(this).attr(DISLAND.BO_INDEX))){$(this).attr(DISLAND.BO_OPERATIONSTATUS,method);}});},bindAllModData:function(boListDom){var tableHandler=new TableHandler(boListDom);var records=tableHandler.getModifiedRecords();var modifyData=[];for(var i=0;i<records.length;i++){modifyData[records[i].data["id"]]=records[i].data;}
var columnInfo=boListDom.children(DISLAND.COLUMNINFO);DISLAND.getBOListDom(boListDom.attr(DISLAND.BO_ID)).children(DISLAND.BO).each(function(){BINDDATA.bindBO($(this));});DISLAND.setPageOptStatus();},bindModifyModData:function(boListDom,method,isEmptyMod){DISLAND.clearPageOptStatus();var tBoListDom=DISLAND.getBOListDom(boListDom.attr(DISLAND.BO_ID));var columnInfo=tBoListDom.children(DISLAND.COLUMNINFO);var bos=tBoListDom.children(DISLAND.BO);if(isEmptyMod){bos.attr(DISLAND.BO_OPERATIONSTATUS,method);}
var tableHandler=new TableHandler(boListDom);var records=tableHandler.getModifiedRecords();for(var i=0,ln=records.length;i<ln;i++){var data=records[i].data;var rowIndex=data[DISLAND.BO_INDEX];var editBO=tBoListDom.find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+rowIndex+"']").slice(0);if(editBO.length==0)
continue;if(data["id"]=="")
data["id"]=editBO.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+DISLAND.BOP_ID+"']").text();editBO.attr(DISLAND.BO_OPERATIONSTATUS,method);}
DISLAND.updateBO(tBoListDom);},bindEditBop:function(data,editBO,columnInfo){for(var bind in data){if(/\./.test(bind))
continue;var bop=editBO.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+bind.replaceAll("#",".")+"']");var columnVSR=DISLAND.getColumnVSR(bop,columnInfo);if(!bop.attr(DISLAND.BIND)||(columnVSR.status&&columnVSR.status.attr(DISLAND.STATUS_HIDDEN)=='true'))
continue;bop.text(specialCharHandler(data[bind]));}},bindOperate:function(boDom,method,bind){DISLAND.clearPageOptStatus();if(!!boDom.attr(DISLAND.BO_INDEX)){var boListId=boDom.parent().attr(DISLAND.BO_ID);var boList=DISLAND.getDom(boListId);var rowBO=boList.children(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+boDom.attr(DISLAND.BO_INDEX)+"']").slice(0);if(rowBO.length>0){rowBO.attr(DISLAND.BO_OPERATIONSTATUS,method);rowBO.attr(DISLAND.BIND,bind);DISLAND.getDom(boListId).remove();DISLAND.getDataIsland().append(boList);}}
else{boDom.attr(DISLAND.BO_OPERATIONSTATUS,method);}},bindTree:function(treeDom,method){DISLAND.clearPageOptStatus();var tree=DISLANDTODOM.getContainer(treeDom);if(!tree)
return;treeDom.children(DISLAND.BO).attr(DISLAND.BO_OPERATIONSTATUS,"");var checkedNodes=tree.getChecked();treeDom.children(DISLAND.BO).each(function(){for(var i=0,length=checkedNodes.length;i<length;i++){if($(this).attr(DISLAND.TREE_CHILDREN_ID)==checkedNodes[i].id){$(this).attr(DISLAND.BO_OPERATIONSTATUS,method);break;}}});},bindTableBop:function(boDom,fcHandler){DISLAND.clearPageOptStatus();DISLAND.getBOListDom(boDom.attr(DISLAND.BO_ID)).find(DISLAND.BO+"["+DISLAND.BO_INDEX+"='"+boDom.attr(DISLAND.BO_INDEX)+"']").children(DISLAND.BOP).each(function(){var bind=$(this).attr(DISLAND.BIND);if(!bind)
return true;var bop=boDom.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+bind+"']");if(!!fcHandler&&bind==fcHandler.bopDom.attr(DISLAND.BIND)){bop.text(trim(fcHandler.getValue()));}
else if(bop.text()=='undefined'){bop.text($(this).text());}});}};
function __firefox()
{HTMLElement.prototype.__defineGetter__("runtimeStyle",__element_style);window.constructor.prototype.__defineGetter__("event",__window_event);Event.prototype.__defineGetter__("srcElement",__event_srcElement);}
function __element_style()
{return this.style;}
function __window_event()
{return __window_event_constructor();}
function __event_srcElement()
{return this.target;}
function __window_event_constructor()
{if(document.all)
{return window.event;}
var _caller=__window_event_constructor.caller;while(_caller!=null)
{var _argument=_caller.arguments[0];if(_argument)
{var _temp=_argument.constructor;if(_temp.toString().indexOf("Event")!=-1)
{return _argument;}}
_caller=_caller.caller;}
return null;}
if(window.addEventListener)
{__firefox();}
var ConstantJSON={PAGEBAR_ROWINDEX:"rowIndex",PAGEBAR_START:"start",PAGEBAR_LIMIT:"limit",PAGEBAR_TOTAL:"total",PAGEBAR_ROOT:"data",DATA_INDEX:"index",MSG_SUCCESS:"success",MSG:"msg",MSG_PATH:"path",IS_POPUP:"isPopUp",HAS_CLOSEBTN:"hasCloseBtn",DIALOG_WIDTH:"dialogWidth",DIALOG_HEIGHT:"dialogHeight",DIALOG_TITLE:'dialogTitle',CLOSE_PAGE:"closePage",MSG_FLAG:"msgFlag",PAR_CON_ID:"parentContainerId",TARGET_VC:"targetVC",CLOSE_TARGET_VC:"closeTargetVC",RELOGIN:"relogin"};
function extend(subClass,superClass){var F=function(){};F.prototype=superClass.prototype;subClass.prototype=new F();subClass.prototype.constructor=subClass;subClass.superclass=superClass.prototype;if(superClass.prototype.constructor==Object.prototype.constructor){superClass.prototype.constructor=superClass;}}
function param(param){return"&"+param;}
var pageFlow={sourceName:"sourceName=",btnName:"btnName=",sourcePage:"sourcePage=",contextOperate:"contextOperate=",returnMsg:"returnMsg="};var saveCase={dataIsland:"dataIsland=",sourcePage:"sourcePage=",caseName:"caseName=",containerId:"containerId=",saveForNewCase:"saveForNewCase=",oldId:"oldId="};var tableSettingParam={sourcePage:"sourcePage=",tableId:"tableId=",hiddenBop:"hiddenBop=",isHidden:"hidden=",position:"position=",changeWidthBop:"changeWidthBop=",newWidth:"newWidth="};var sourceBtn={sm:"sm="};var GA={relations:"relations=",operation:"operation=",dataIsland:"dataIsland=",vcId:"vcId=",recordId:"recordId=",winType:"winType=",targetName:"targetName=",targetXML:"targetXML="};function paramValue(value){value=value.replace(/%/g,"%25");value=value.replace(/\+/g,"%2B");value=value.replace(/\&/g,"%26");return encodeURIComponent(value);}
function isEmpty(str){return str==null||str==""||str=='undefined'||str=='null';}
function isNotEmpty(str){return!isEmpty(str);}
function isHttp(url){return/^(https?:\/\/)/.test(url);}
function removeEnd(str){return str.substring(0,str.length-1);}
function arrayToStr(array){if(array=='undefined'||array==null)
return"";return","+array.valueOf()+",";}
function strToArray(str,symbol){if(!!str)
return str.split(symbol);else
return null;}
function isInArray(array,str){return array?jQuery.inArray(str,array)!=-1:false;}
function xmlToString(xmlObj){var result="";if(typeof(xmlObj)=="object"){if(!xmlObj.get()[0])
return"";if($.browser.msie)
result=xmlObj.get()[0].xml;else
result=new XMLSerializer().serializeToString(xmlObj.get()[0]);}
else if(typeof(xmlObj)=="string"){result=xmlObj;}
return replaceAll(result,"&lt;,&gt;","<,>");}
function replaceAll(str,s1,s2){if(str instanceof Date){str=Ext.util.Format.date(str,'Y-m-d');}
str=str+"";var olds=s1.split(",");var news=s2.split(",");for(var i=0;i<olds.length;i++){str=str.replace(new RegExp(olds[i],"gm"),news[i]);}
return str;}
function StringToJSON(obj){var patrn=/^{/;if(!patrn.exec(obj))
return null;else
return eval('('+obj+')');}
function getUnescapedText(text){return!!text?text.replace(/\\/g,'\\\\').replace('<![CDATA[','').replace(']]>','').replace(/\r/g,"\\r").replace(/\n/g,"\\n"):"";}
function getRemoveCDATA(text){if(!!text&&text instanceof Date)
return text;return!!text?text.replace('<![CDATA[','').replace(']]>','').replace('<!--[CDATA[','').replace(']]-->',''):"";}
function JsonToStr(o){var arr=[];var fmt=function(s){if(typeof s=='object'&&s!=null)
return JsonToStr(s);else
return/^(string|number)$/.test(typeof s)?"'"+s+"'":s;};for(var i in o)
arr.push("'"+i+"':"+fmt(o[i]));return'{'+arr.join(',')+'}';}
String.prototype.format=function(){var args=arguments;return this.replace(/\{(\d+)\}/g,function(m,i){return args[i];});};function getXmlNoteText(note){var value="";if($.browser.msie)
value=note.text().replace("<![CDATA[","").replace("]]>","");else if(note[0]&&note[0].innerHTML&&note.html().indexOf("<!--[CDATA[")!=-1)
value=note.html().replace("<!--[CDATA[","").replace("]]-->","");else
value=note.text();return decodeToDisplay(value);}
function trim(str){if(str!=null||typeof(str)!='undefined'){var regExp=/^\s*(.*?)\s*$/;return String(str).replace(regExp,"$1");}
return str;}
function strlen(str){str=str||"";var len=0;for(var i=0,strlen=str.length;i<strlen;i++){var c=str.charCodeAt(i);if((c>=0x0001&&c<=0x007e)||(0xff60<=c&&c<=0xff9f))
len++;else
len+=2;}
return len;}
function getRealValue(obj){if(typeof obj=='string')
return getRemoveCDATA(decodeToDisplay(obj));return getRemoveCDATA(getXmlNoteText(obj))||"";}
function decodeToValue(str){if(isUseExt()&&Ext.isDate(str))
return str;return str.replaceAll(":amp;","\\&").replaceAll(":apos;","\\'").replaceAll(":quot;","\\\"").replaceAll("<","&lt;").replaceAll(">","&gt;");}
function decodeToDisplay(str){if(!str)
return"";else if(isUseExt()&&Ext.isDate(str))
return str.format('Y-m-d');else
return str.replaceAll(":amp;","\&").replaceAll(":apos;","\'").replaceAll(":quot;","\"").replaceAll("&lt;","<").replaceAll("&gt;",">").replace(/\\n/g,"\n");}
var DISLAND={hiddenId:"dataIsland",dataIsland:"dataisland",SOURCEPAGE:"sourcepage",SESSIONTICKET:"sessionTicket",TOKEN_TICKET:"tokenTicket",BIND:"bind",STATUS:"status",STATUS_DISABLE:"disable",STATUS_HIDDEN:"hidden",STATUS_READONLY:"readonly",BO:"bo",BO_ID:"id",BO_INDEX:"index",BO_ROWOPT_STATUS:"rowoptstatus",BO_OPERATIONSTATUS:"operationstatus",BO_CURRENTFIELD:"currentfield",BO_OPERATIONSTATUS_INIT:"init",BOLIST_CHECKEDIDS:"checkedids",BOLIST_BOT:"bot",BOLIST_HEIGHT:"highLight",DEL_BO_IDS:"delboids",OPERATE:"operate",OPERATE_ID:"id",OPERATE_METHOD:"method",OPERATE_JS_BEFORE:"jsbefore",OPERATE_JS_AFTER:"jsafter",OPERATE_SOURCE_ALL:"all",OPERATE_EXPEND:"expend",OPERATE_TEXT:"text",OPERATE_SAVEMOD:"savemod",OPERATE_OPTMOD:"optmod",OPERATE_ISFILL:"isfill",OPERATE_HASCONFIRM:"hasconfirm",OPERATE_CONFIRMMSG:"confirmmsg",OPERATE_HASMSG:"hasmsg",OPERATE_IDX:"idx",OPERATE_NOTSUBMIT:"notsubmit",OPERATE_NOTFRESH:"notfresh",SOURCE:"source",SOURCE_BINDBO:"bindBo",SOURCE_BINDBOP:"bindBop",SOURCE_OPERATE:"operate",SOURCE_SM:"sm",SOURCE_TEXT:"text",HIDDENFORM:"-hiddenForm",OPERATE_ISPOPUPPAGE:"ispopup",BOLIST:"bolist",BOLIST_ID:"id",BOP:"bop",BOP_ID:"id",BOP_MAX:"max",BOP_MIN:"min",BOP_RANGE:"range",BOP_RANGE_REQUIRED:"required",BOP_RANGE_MINLENGTH:"minlength",BOP_RANGE_MAXLENGTH:"maxlength",BOP_RANGE_ENUM:"enum",BOP_RANGE_ITEM:"item",BOP_RANGE_ITEM_VALUE:"value",BOP_RANGE_ITEM_LABEL:"label",BOP_RANGE_SEQUENCE:"sequence",BOP_RANGE_MAX:"max",BOP_RANGE_MIN:"min",BOP_RANGE_STEP:"step",BOP_RANGE_SCALE:"scale",BOP_RANGE_LOGIC:"logic",BOP_RANGE_LOGIC_CLASS:"rangeClass",BOP_RANGE_RULE:"rule",BOP_RANGE_AND:"AND",BOP_RANGE_OR:"OR",BOP_RANGE_NOT:"NOT",BOP_VALUE:"value",BOP_VALUE_DISPLAY:"display",BOP_VALUE_EXPEND:"expend",BOP_DATA:"data",BOP_DATA_URL:"url",BOP_DATA_UPLOAD_NUM:"uploadNum",BOP_DATA_UPLOAD_SIZE:"uploadSize",BOP_JS:"js",BOP_ISRELATE:"isrelate",BOP_ISCONRELATE:"isconrelate",BOP_ISTIGGER:"istigger",BOP_HIGHLIGHT:"highlight",BOP_ISFILE:"isfile",BOP_TERMINAL_LOCATION:'tm_location',BOP_TERMINAL_PIC:'tm_pic',TREE:"tree",TREE_CHILDREN_ID:"id",TREE_BO:"bo",TREE_CHECKED:"checked",GROUPNAME:"groupname",PAGINATION:"pagination",PAGINATION_LIMIT:"limit",PAGINATION_START:"start",PAGINATION_TOTAL:"total",COLUMNINFO:"columninfo",COLUMNINFO_TABLEFIELDS:"tablefields",COLUMNINFO_COLUMN:"column",COLUMNINFO_SELECTIONMODEL:"selectionmodel",COLUMN_TYPE:"type",COLUMN_TYPE_FILE:"file",COLUMN_TYPE_IMG:"img",COLUMN_TYPE_IMG_ISADAPTIVE:"adaptive",COLUMN_TYPE_IMG_HEIGHT:"height",COLUMN_TYPE_IMG_WIDTH:"width",COLUMN_DEFAULT_VALUE:"default",COLUMN_TARGET:"target",SPLIT_LINE:"-",SPLIT_EXPEND:"~",SPLIT_POINT:".",SPLIT_COMMA:",",SPLIT_TREBLE_BACKSLASH:"///",GROUPSTRING:"groupString",GROUP:"group",GROUP_SOURCE:"source",GROUP_TARGET:"target",PAGE:"page",PAGE_ONLOAD:"onload",TIPS_TYPE:"tipstype",TIPS_TYPE_SIMPLE:"simple",TIPS_TYPE_POPUP:"popup",TIPS_DELAY:"tipsdelay",TIPS_DELAY_DEF:"10",TIPS_DISPLAY:"tipdisplay",TIPS_DISPLAY_NO:"0",CONFIRM_DISPLAY:"confirmdisplay",CONFIRM_DISPLAY_NO:"0",getDataIsland:function(){return getDataIsland().find(this.dataIsland).slice(0);},getSourcepage:function(){return this.getDataIsland().attr(this.SOURCEPAGE);},removeBO:function(boId){this.getDataIsland().find("#"+boId).remove();},addBO:function(bo){if($.browser.msie)
this.getDataIsland().append(bo);else
this.getDataIsland().append($(xmlToString(bo)));},updateBO:function(boDom){var boId=boDom.attr(this.BO_ID);this.removeBO(boId);this.addBO(boDom);},setBOPValue:function(bopDom,content){var valueDom=bopDom.children(this.BOP_VALUE);if(valueDom.length>1){var ranageValue=content.split(',');if(ranageValue.length!=2)
return;valueDom.slice(0,1).text(ranageValue[0]);valueDom.slice(1,2).text(ranageValue[1]);}
else
valueDom.text(content);},range_getRangeDom:function(bopDom){return bopDom.children(this.BOP_RANGE);},range_getRangeList:function(bopDom){return this.range_getRangeDom(bopDom).slice(0).children();},range_getEnumRange:function(rangeDom){var result=[];rangeDom.each(function(){if($(this).is(DISLAND.BOP_RANGE_ENUM))
result.push($(this));});return result;},range_getEnumRangeMap:function(enumDoms){var result=[];if(enumDoms==null||enumDoms.length==0)
return result;if(jQuery.isArray(enumDoms)){for(var i=0,length=enumDoms.length;i<length;i++){enumDoms[i].children().each(function(){result[$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE)]=$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);});}}
else{enumDoms.children().each(function(){result[$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE)]=$(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);});}
return result;},operate_BOMethod:function(operateDom){var sourceDom=this.getSourceOptDom(operateDom);if(sourceDom)
return sourceDom.attr(this.SOURCE_OPERATE)||BoFinalMethod.SYSTEMP;else
return operateDom.attr(this.OPERATE_METHOD)||BoFinalMethod.SYSTEMP;},operate_setBOMethod:function(operateDom,method){var sourceDom=this.getSourceOptDom(operateDom);if(sourceDom)
sourceDom.attr(this.SOURCE_OPERATE,method);else
operateDom.attr(this.OPERATE_METHOD,method);},operate_setSaveMod:function(operateDom,saveMod){var sourceDom=this.getSourceOptDom(operateDom);if(sourceDom)
sourceDom.attr(this.OPERATE_SAVEMOD,saveMod);else
operateDom.attr(this.OPERATE_SAVEMOD,saveMod);},getFCGroupDoms:function(groupName,boId){var groupDoms=[];this.getDom(boId).children(DISLAND.BOP+"["+this.GROUPNAME+"='"+groupName+"']").each(function(){groupDoms.push($(this));});return groupDoms;},getRelationBo:function(boDom){var relations=[];var sourceBoId=boDom.attr(this.BO_ID);var relationsGroup=XMLDomFactory.getInstance($("#"+this.GROUPSTRING).val());if(!!relationsGroup){relationsGroup.find(this.GROUP+"["+this.GROUP_SOURCE+"='"+sourceBoId+"']").each(function(){var relation=$(this);relation.children(DISLAND.GROUP_TARGET).each(function(){relations.push($(this).text());});});}
return relations;},getSourceBOID:function(targetBoId){var result="";var group=XMLDomFactory.getInstance($('#'+this.GROUPSTRING).val());if(!group)
return result;group.find(this.GROUP).each(function(){var source=$(this);source.children(DISLAND.GROUP_TARGET).each(function(){if($(this).text()==targetBoId){result=source.attr(DISLAND.GROUP_SOURCE);return true;}});});return result;},getBOPJS:function(bopDom){return bopDom.attr(this.BOP_JS);},getDom:function(id){return this.getDataIsland().find("#"+id);},getBODom:function(id){return this.getDataIsland().find(this.BO+"["+this.BO_ID+"='"+id+"']");},getBOListDom:function(id){return this.getDataIsland().find(this.BOLIST+"["+this.BO_ID+"='"+id+"']");},getFormPanelDom:function(operateId){var temp=formDislandlArr[operateId];temp.find(this.OPERATE).attr(this.OPERATE_ID,operateId);temp.find(this.OPERATE).attr(this.OPERATE_METHOD,DISLANDTODOM.getBtnMethod(operateId));formDislandlArr[operateId]=temp;return formDislandlArr[operateId];},getFormPanelBOPDom:function(formPandlDom,formPanelFcId){var bopBind=formPanelFcId.split(this.SPLIT_LINE)[2];return formPandlDom.find(this.BOP+"["+this.BIND+"='"+bopBind+"']");},getBOOperateDom:function(operateId,boDom){var boDomTemp=boDom||this.getBODom(operateId.split(this.SPLIT_LINE)[0]);return boDomTemp.find(this.OPERATE+"["+this.OPERATE_ID+"='"+operateId+"']");},getBOListOperateDom:function(operateId){var boDom=this.getBOListDom(operateId.split(SPLIT_LINE)[0]);return boDom.find(this.OPERATE+"["+this.OPERATE_ID+"='"+operateId+"']");},getParentDom:function(fcId){return this.getDom(fcId.split(this.SPLIT_LINE)[0]);},getOperateList:function(){return this.getDataIsland().find(this.OPERATE);},getOperateInCon:function(containerId){var boDom=this.getBODom(containerId);if(boDom.length!=0){return boDom.find(this.OPERATE);}
else{return this.getBOListDom(containerId).find(this.OPERATE);}},getRowOperate:function(bo,operateDomId){return bo.children(this.OPERATE+"["+DISLAND.OPERATE_ID+"='"+operateDomId+"']").slice(0);},getBOPByFcName:function(fcName){var bopBind=fcName.split(this.SPLIT_LINE)[2];var bo=this.getDom(fcName.split(this.SPLIT_LINE)[0]);return bo.children(this.BOP+"["+DISLAND.BIND+"='"+bopBind+"']").slice(0);},getColumnVSR:function(bopDom,columnInfo){var result={};var value;if(bopDom.attr(this.BOP_RANGE_ENUM)){var newData=new Array();var items=eval('('+bopDom.attr(this.BOP_RANGE_ENUM)+')');for(var i=0;i<items.length;i++){newData.push([items[i].k,items[i].v]);}
value=newData[bopDom.text()];}
else{var bopId=bopDom.attr(this.BIND);var column=columnInfo.find(this.COLUMNINFO_COLUMN+"["+this.BIND+"='"+bopId+"']");result.status=column.children(this.STATUS);result.range=column.children(this.BOP_RANGE);if(column.attr(this.COLUMN_TYPE_IMG_ISADAPTIVE)=='true'){result.isAdaptive=true;}
else{result.height=column.attr(this.COLUMN_TYPE_IMG_HEIGHT)||projectStyle.getColImgHeight();result.width=column.attr(this.COLUMN_TYPE_IMG_WIDTH)||projectStyle.getColImgWidth();}
value=specialCharEncode(this.getBOLIST_BOPLabel(bopDom,result));}
result.value=value;return result;},getBOPStatus:function(bopDom){return bopDom.find(this.STATUS);},getBOLIST_BOPLabel:function(bopDom,bop){var enumRange=this.range_getEnumRange(bop.range.children());var enumRangeMap=this.range_getEnumRangeMap(enumRange);var text=getXmlNoteText(bopDom);var enumRangeValue;var display=bopDom.attr(this.BOP_VALUE_DISPLAY);if(display){bop.hasDisplay=true;bop.alt=enumRangeMap[text];}
else if(text.indexOf(this.SPLIT_COMMA)>0){var arr=text.split(this.SPLIT_COMMA);var flag=false;for(var i=0,length=arr.length;i<length;i++){if(flag)
enumRangeValue+=this.SPLIT_COMMA+(enumRangeMap[arr[i]]||arr[i])
else
enumRangeValue=enumRangeMap[arr[i]]||arr[i];flag=true;}}
else{enumRangeValue=enumRangeMap[text];}
return display||enumRangeValue||text||"";},getPagination:function(boDom){var pagination=boDom.children(DISLAND.PAGINATION).slice(0);var result={limit:pagination.attr(this.PAGINATION_LIMIT),total:pagination.attr(this.PAGINATION_TOTAL),start:pagination.attr(this.PAGINATION_START)};return result;},isOptStatusChanged:function(boDom){var optStatus=boDom.attr(this.BO_OPERATIONSTATUS);return!!optStatus&&optStatus!=this.BO_OPERATIONSTATUS_INIT;},isMutiFileBOP:function(bop){var url=bop.attr(this.BOP_DATA_URL);return url.indexOf(this.SPLIT_TREBLE_BACKSLASH)>0;},getCheckedDom:function(boListDom){return boListDom.children(this.BOLIST_CHECKEDIDS);},clearCheckedDom:function(boListDom){var checkedDom=this.getCheckedDom(boListDom);if(checkedDom.length>0){checkedDom.attr(this.BO_ID,"");checkedDom.children().remove();}},getUpdateTableDom:function(updateFormDom){boId=updateFormDom.attr(this.BO_ID);if(boId&&boId.indexOf("update_form"))
return this.getDom(boId.split(this.SPLIT_LINE)[0]);else
return null;},sm_checkbox:function(columnInfo){var sm=columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);return!sm||sm=='checkBox';},sm_radio:function(columnInfo){return'radio'==columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);},sm_empty:function(columnInfo){return'empty'==columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);},getColumnByFcName:function(fcName){var bopBind=fcName.split(this.SPLIT_LINE)[2];var boList=this.getDom(fcName.split(this.SPLIT_LINE)[0]);return this.getColumnByBopBind(boList,bopBind);},getColumnByBopBind:function(boList,bopBind){return boList.children(this.COLUMNINFO).children(this.COLUMNINFO_COLUMN+"["+this.BIND+"='"+bopBind+"']");},getBOPByFcId:function(fcId,boDom){var bopBind=DISLANDTODOM.getBOPBind(fcId);var bo=boDom?boDom:this.getDom(DISLANDTODOM.getBOId(fcId));return bo.find(DISLAND.BOP+"["+DISLAND.BIND+"='"+bopBind+"']");},getBOPLabel:function(bop,value){var enumRange=this.range_getEnumRange(bop.children(DISLAND.BOP_RANGE).children());var enumRangeMap=this.range_getEnumRangeMap(enumRange);var bopValue=bop.children(DISLAND.BOP_VALUE).text();if(isNotEmpty(value))
bopValue=value;var enumRangeValue;var display=bop.attr(this.BOP_VALUE_DISPLAY);if(display){bop.alt=enumRangeMap[bopValue];}
else if(bopValue.indexOf(DISLAND.SPLIT_COMMA)>0){var arr=bopValue.split(DISLAND.SPLIT_COMMA);var flag=false;for(var i=0;i<arr.length;i++){if(flag)
enumRangeValue+=DISLAND.SPLIT_COMMA+(enumRangeMap[arr[i]]||arr[i])
else
enumRangeValue=enumRangeMap[arr[i]]||arr[i];flag=true;}}
else
enumRangeValue=enumRangeMap[bopValue];return display||enumRangeValue||bopValue||"";},getSourceOptDom:function(operateDom){var sourceDom=operateDom.children(DISLAND.SOURCE);return sourceDom.length==0?null:sourceDom.slice(0);},getMaxIdx:function(boListDom){if(!boListDom)
return 0;var bos=boListDom.children(this.BO);if(bos.length>0)
return bos.slice(bos.length-1).attr(this.BO_INDEX);else
return 0;},setPageOptStatus:function(){this.getDataIsland().attr(this.BO_OPERATIONSTATUS,BoFinalMethod.SYSTEMP);},clearPageOptStatus:function(){this.getDataIsland().removeAttr(this.BO_OPERATIONSTATUS);},createTempBop:function(columnInfo,editColumn,rowIndex,grid,bop){var store=grid.getStore();var record=store.getAt(rowIndex);var boDomStr="<"+this.BO+" "+this.BO_INDEX+"='"+(rowIndex+1)+"' temp='true'>";columnInfo.children(this.COLUMNINFO_COLUMN).each(function(){var column=$(this);var bopDomStr="<"+DISLAND.BOP+" "+DISLAND.BIND+"='"+column.attr(DISLAND.BIND)+"' ";if(column.attr(DISLAND.BIND)==editColumn.attr(DISLAND.BIND)){if(column.attr(DISLAND.BOP_ISRELATE)==='true')
bopDomStr+=DISLAND.BOP_ISRELATE+"='true' ";if(column.attr(DISLAND.BOP_ISCONRELATE)=='true')
bopDomStr+=DISLAND.BOP_ISCONRELATE+"='true' ";}
if(column.attr(DISLAND.COLUMN_TYPE))
bopDomStr+=DISLAND.COLUMN_TYPE+"='"+column.attr(DISLAND.COLUMN_TYPE)+"' ";bopDomStr+="><![CDATA["+record.get(column.attr(DISLAND.BIND))+"]]></"+DISLAND.BOP+">";boDomStr+=bopDomStr;});boDomStr+="</"+DISLAND.BO+">";var xmlDom=XMLDomFactory.getInstance(boDomStr);var cBop=xmlDom.children().children(DISLAND.BOP+"["+this.BIND+"='"+editColumn.attr(DISLAND.BIND)+"']");if(bop.text())
cBop.text(bop.text());if(bop.attr(this.STATUS_DISABLE))
cBop.attr(this.STATUS_DISABLE,bop.attr(this.STATUS_DISABLE));else if(bop.attr(this.STATUS_READONLY))
cBop.attr(this.STATUS_READONLY,bop.attr(this.STATUS_READONLY));var enumStr=bop.attr(this.BOP_RANGE_ENUM);if(!enumStr)
enumStr=this._getEnum(editColumn);if(enumStr)
cBop.attr(this.BOP_RANGE_ENUM,enumStr);return cBop;},_getEnum:function(column){var returnStr=null;var items=column.find(this.BOP_RANGE_ITEM);if(items.length==1){var value="";if(items.attr(this.BOP_RANGE_ITEM_VALUE))
value=items.attr(this.BOP_RANGE_ITEM_VALUE);returnStr="[{'k':'"+value+"','v':'"+items.attr(this.BOP_RANGE_ITEM_LABEL)+"'}]";}
return returnStr;},createCellEditFormDom:function(bolist,bind){var bobind=bolist.attr(this.BIND);var formId=bolist.attr(this.BOLIST_ID)+"_celledit_form";var columnInfo=bolist.children(this.COLUMNINFO);var column=columnInfo.children(this.COLUMNINFO_COLUMN+"["+this.BIND+"='"+bind+"']");var str=xmlToString(column).replaceAll("<"+this.COLUMNINFO_COLUMN,"<"+this.BOP).replaceAll("</"+this.COLUMNINFO_COLUMN,"</"+this.BOP);var boDomStr="<"+this.dataIsland+"><"+this.BO+" "+this.BO_ID+"='"+formId+"' "+this.BIND+"='"+bobind+"'>";boDomStr+=str;boDomStr+="</"+this.BO+"></"+this.dataIsland+">";var xmlDom=XMLDomFactory.getInstance(boDomStr);var boDom=xmlDom.find(this.BO+"["+this.BIND+"='"+bobind+"']");return boDom;},getValue:function(bop){var valueDom=bop.find(this.BOP_VALUE);if(valueDom.length!=0)
return getRemoveCDATA(valueDom.text());else
return getRemoveCDATA(bop.text());},getEnumRange:function(range){return range.children(DISLAND.BOP_RANGE_ENUM);},getCloneOptDom:function(operateDom){var cloneOptDom=operateDom.clone();if(operateDom.attr(DISLAND.OPERATE_IDX)){cloneOptDom.boDom=operateDom.parent().parent();}
else{cloneOptDom.boDom=operateDom.parent();}
return cloneOptDom;},resetIndex:function(boList){boList.children(this.BO).each(function(index){var oldIndex=$(this).attr(DISLAND.BO_INDEX);var newIndex=index+1;$(this).attr(DISLAND.BO_INDEX,newIndex);$(this).children(DISLAND.OPERATE).each(function(){var btnId=$(this).attr(DISLAND.OPERATE_ID)+DISLAND.SPLIT_LINE+oldIndex;$(this).attr(DISLAND.OPERATE_IDX,newIndex);var btn=$('#'+btnId);var newId=btnId.replace(/\d+$/,newIndex);btn.attr('id',newId);btn.attr('idx',newIndex)});});}};