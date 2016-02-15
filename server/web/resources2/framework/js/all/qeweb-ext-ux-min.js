
function resizeContainer(panel,columns){var containers=panel.items.items[0].items.items;var clientWidth=document.body.clientWidth;var scrollBarWidth=16;var sumColspan=0;var padding=10;for(var i=0;i<containers.length;i++){sumColspan+=containers[i].colspan;var con=containers[i].items.items[0];if(con.xtype=='menu'){con.setWidth(clientWidth*containers[i].columnWidth);}
else if(containers[i].colspan>=columns){con.setWidth(clientWidth*containers[i].columnWidth-scrollBarWidth);sumColspan=0;}
else if(sumColspan==columns){con.setWidth(clientWidth*containers[i].columnWidth-scrollBarWidth);sumColspan=0;}
else{con.setWidth(clientWidth*containers[i].columnWidth-padding);}}}
Ext.apply(Ext.form.Checkbox.prototype,{getErrorCt:function(){return this.el.parent().parent().findParent('.x-form-element',5,true)||this.el.parent().parent().findParent('.x-form-field-wrap',5,true);}});Ext.apply(Ext.form.Radio.prototype,{getErrorCt:function(){return this.el.parent().parent().findParent('.x-form-element',5,true)||this.el.parent().parent().findParent('.x-form-field-wrap',5,true);}});function markInvalid(msg){if(!this.rendered||this.preventMark){return;}
this.el.addClass(this.invalidClass);msg=msg||this.invalidText;switch(this.msgTarget){case'qtip':this.el.dom.qtip=msg;this.el.dom.qclass='x-form-invalid-tip';if(Ext.QuickTips){Ext.QuickTips.enable();}
break;case'title':this.el.dom.title=msg;break;case'under':if(!this.errorEl){var elp=this.getErrorCt();this.errorEl=elp.createChild({cls:'x-form-invalid-msg'});this.errorEl.setWidth(elp.getWidth(true)-20);}
this.errorEl.update(msg);Ext.form.Field.msgFx[this.msgFx].show(this.errorEl,this);break;case'side':if(!this.showInvalidText){break;}
if(!this.errorIcon){var elp=this.getErrorCt();this.errorIcon=elp.createChild({cls:'x-form-invalid-icon'});}
this.errorIcon.alignTo(this.el.next(),'tl-tr',[2,0]);this.errorIcon.dom.qtip=msg;this.errorIcon.dom.qclass='x-form-invalid-tip';this.errorIcon.show();this.on('resize',this.alignErrorIcon,this);break;default:var t=Ext.getDom(this.msgTarget);t.innerHTML=msg;t.style.display=this.msgDisplay;break;}
this.fireEvent('invalid',this,msg);}
function clearInvalid(){if(!this.rendered||this.preventMark){return;}
this.el.removeClass(this.invalidClass);switch(this.msgTarget){case'qtip':this.el.dom.qtip='';break;case'title':this.el.dom.title='';break;case'under':if(this.errorEl){Ext.form.Field.msgFx[this.msgFx].hide(this.errorEl,this);}
break;case'side':if(this.errorIcon){this.errorIcon.dom.qtip='';this.errorIcon.hide();this.un('resize',this.alignErrorIcon,this);}
break;default:var t=Ext.getDom(this.msgTarget);if(!!t){t.innerHTML='';t.style.display='none';}
break;}
this.fireEvent('valid',this);}
function validate(){var array=this.ownerCt.find('name',this.name);if(this.validateValue(this.processValue(this.getRawValue()))){for(var i=0;i<array.length;i++){array[i].clearInvalid();}
return true;}
return false;}
function validateValue(){if(!this.getGroupValue()){this.markInvalid();return false;}
return true;}
Ext.form.Checkbox.prototype.markInvalid=Ext.form.Radio.prototype.markInvalid=markInvalid;Ext.form.Checkbox.prototype.clearInvalid=Ext.form.Radio.prototype.clearInvalid=clearInvalid;Ext.form.Checkbox.prototype.validateValue=function(){if(this.ownerCt.allowBlank){return true;}
var array=this.ownerCt.find('name',this.name);for(var i=0;i<array.length;i++){if(array[i]){return true;}}
this.markInvalid();return false;};
function ajaxFileUpload(fileFieldId,fileFieldName){Ext.getBody().mask(I18N.FILE_UPLOADING,'x-mask-loading');var sourceName=DISLANDTODOM.getBOBind(fileFieldName);var bopName=DISLANDTODOM.getBOPBind(fileFieldId);var params={sourceName:sourceName,bopName:bopName};var fc=Ext.getCmp(DISLANDTODOM.getFileUploadFCId(fileFieldId));if(fc&&fc.boIndex){ajaxFileUpload_Table(fileFieldId,fileFieldName,fc);}
else{ajaxFileUpload_Form(fileFieldId,fileFieldName,fc);}}
function ajaxFileUpload_Table(fileFieldId,fileFieldName,fc){var bop=DISLAND.getColumnByFcName(fileFieldName);var params={sourceName:DISLANDTODOM.getBOBind(fileFieldName),bopName:DISLANDTODOM.getBOPBind(fileFieldId),operate:bop.attr(DISLAND.OPERATE)||""};unlockScreen();var fileElementId=DISLANDTODOM.getFileID(fileFieldId);$.ajaxFileUpload({url:appConfig.ctx+actionURL.uploadFile(),secureuri:false,fileElementId:fileElementId,dataType:'json',data:params,timeout:appConfig.ajaxTimeout,success:function(data,status){var fileFiled=Ext.getCmp(fileFieldId);fileFiled.setValue("");var fileFiledMain=$('#'+fileElementId);fileFiledMain.bind('change',function(){fileFiled.setValue(fileFiledMain.val());});if(data.success){if(data.filePath){var anchor=Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));if(anchor&&data.filePath){var anchorStr="<a href='"+data.filePath+"' target='_blank' title='"+data.fileName+"'>"+data.fileName+"</a>";anchor.setText(anchorStr,false);}
Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(data.fileName);}
var winId=DISLANDTODOM.getFileCellWin(fileFieldId);var win=Ext.getCmp(winId);win.returnData=data;}
else{showMSG.showErr(data.message);}
unlockScreen();},error:function(data,status,e){alert('err')
unlockScreen();showMSG.showErr(I18N.FILE_UPLOAD_FAILURE+I18N.FILE_ERROR_POSSIBILITY);}});}
function ajaxFileUpload_Form(fileFieldId,fileFieldName,fc){var bop=DISLAND.getBOPByFcName(fileFieldName);var params={sourceName:DISLANDTODOM.getBOBind(fileFieldName),bopName:DISLANDTODOM.getBOPBind(fileFieldId),operate:bop.attr(DISLAND.OPERATE)||"",lastPath:bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL)||""};var fileElementId=DISLANDTODOM.getFileID(fileFieldId);$.ajaxFileUpload({url:appConfig.ctx+actionURL.uploadFile(),secureuri:false,fileElementId:DISLANDTODOM.getFileID(fileFieldId),dataType:'json',data:params,timeout:appConfig.ajaxTimeout,success:function(data,status){var fileFiled=Ext.getCmp(fileFieldId);fileFiled.setValue("");var fileFiledMain=$('#'+fileElementId);fileFiledMain.bind('change',function(){fileFiled.setValue(fileFiledMain.val());});if(data.success){if(data.fileName){bop.children(DISLAND.BOP_VALUE).text(data.fileName);}
if(data.filePath){bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL,data.filePath);var anchor=Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));if(anchor&&data.filePath){var anchorStr="<a href='"+data.filePath+"' target='_blank' title='"+data.fileName+"'>"+data.fileName+"</a>";anchor.setText(anchorStr,false);}
Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(data.fileName);}
unlockScreen();showMSG.showOK(data.message);}
else{showMSG.showErr(data.message);}},error:function(data,status,e){Ext.getBody().unmask();showMSG.showErr(I18N.FILE_UPLOAD_FAILURE+I18N.FILE_ERROR_POSSIBILITY);}});}
function ajaxMultiFileUpload(fileFieldId,fileFieldName){var sourceName=DISLANDTODOM.getBOBind(fileFieldName);var bopName=DISLANDTODOM.getBOPBind(fileFieldId);var bop=DISLAND.getBOPByFcName(fileFieldName);var bopData=bop.children(DISLAND.BOP_DATA);var params={sourceName:sourceName,bopName:bopName,isMulti:'true',uploadNum:bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM),uploadSize:bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE)||"",operate:bop.attr(DISLAND.OPERATE)||"",lastPath:bopData.attr(DISLAND.BOP_DATA_URL)||""};var dialog=new Ext.ux.UploadDialog.Dialog({url:appConfig.ctx+actionURL.uploadFile(),reset_on_hide:false,allow_close_on_upload:true,base_params:params,fileFieldId:fileFieldId,fileFieldName:fileFieldName});dialog.show(Ext.getBody());dialog.on('uploadsuccess',onUploadSuccess);}
function onUploadSuccess(dialog,filename,resp_data,record){var bop=DISLAND.getBOPByFcName(dialog.fileFieldName);var anchor=Ext.getCmp(dialog.fileFieldId);if(resp_data.fileName){var bopValue=bop.children(DISLAND.BOP_VALUE);var value=getRemoveCDATA(bopValue.text())+resp_data.fileName+DISLAND.SPLIT_TREBLE_BACKSLASH;bopValue.text(value);}
if(resp_data.filePath&&resp_data.uploadNum){var bopData=bop.children(DISLAND.BOP_DATA);var olaDataUrl=bopData.attr(DISLAND.BOP_DATA_URL)||"";var data_url=olaDataUrl+resp_data.filePath+DISLAND.SPLIT_TREBLE_BACKSLASH;bopData.attr(DISLAND.BOP_DATA_URL,data_url);dialog.base_params.uploadNum=resp_data.uploadNum;bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM,resp_data.uploadNum);var check=Ext.getCmp(DISLANDTODOM.getFileCheckID(dialog.fileFieldId));var anchorStr="<a href='#' onclick='viewFileList(\""+dialog.fileFieldId+"\",\""+dialog.fileFieldName+"\");'>"+I18N.MULTI_FILE_UPLOAD+" "+resp_data.uploadNum+"</a>";anchor.setText(anchorStr,false);check.setValue(bop.children(DISLAND.BOP_VALUE).text());}
if(resp_data.uploadSize){dialog.base_params.uploadSize=resp_data.uploadSize;bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_UPLOAD_SIZE,resp_data.uploadSize);}}
function viewFileList(fileFieldId,fileFieldName){var bop=DISLAND.getBOPByFcName(fileFieldName);var value=getRemoveCDATA(bop.children(DISLAND.BOP_VALUE).text()).split(DISLAND.SPLIT_TREBLE_BACKSLASH);var data_url=bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL).split(DISLAND.SPLIT_TREBLE_BACKSLASH);var table=new Array();for(var i=0,len=value.length;i<len;i++){var row=new Array();if(value[i]=="")
continue;row.push(data_url[i]);row.push(value[i]+DISLAND.SPLIT_TREBLE_BACKSLASH+data_url[i]);table.push(row);}
var store=new Ext.data.ArrayStore({fields:['delete','file'],idIndex:0});store.loadData(table);var cm=new Ext.grid.ColumnModel([{header:I18N.MULTI_FILE_TITLE_1,width:70,resizable:false,dataIndex:"delete",sortable:true,align:'center',renderer:function(data,metadata,record,rowIndex,columnIndex,store){return'<a href="#" onclick="delFile(\''+fileFieldId+"\',\'"+fileFieldName+"\',\'"+data+'\');">'+I18N.MULTI_FILE_DELETE+'</a>';}},{header:I18N.MULTI_FILE_TITLE_2,dataIndex:"file",sortable:true,renderer:function(data,metadata,record,rowIndex,columnIndex,store){var array=data.split(DISLAND.SPLIT_TREBLE_BACKSLASH);return'<a href="'+array[1]+'" target="_blank">'+array[0]+'</a>';}}]);var grid=new Ext.grid.GridPanel({id:'qeweb-multifile-list',ds:store,cm:cm,border:true,viewConfig:{autoFill:true,forceFit:true}});var win=new Ext.Window({maximizable:true,collapsible:true,width:450,height:300,isTopContainer:true,modal:true,resizable:false,layout:'fit',constrain:true,buttons:[{text:I18N.DIALOG_CLOSE,iconCls:'no',handler:function(){win.close();}}],items:[grid]});win.show(Ext.getBody());}
function delFile(fileFieldId,fileFieldName,fielPath){var bop=DISLAND.getBOPByFcName(fileFieldName);var value=getRemoveCDATA(bop.children(DISLAND.BOP_VALUE).text()).split(DISLAND.SPLIT_TREBLE_BACKSLASH);var bopData=bop.children(DISLAND.BOP_DATA);var data_url=bopData.attr(DISLAND.BOP_DATA_URL).split(DISLAND.SPLIT_TREBLE_BACKSLASH);var uploadSize=bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE).split(DISLAND.SPLIT_COMMA);var uploadNum=Number(bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM));var new_value="",new_data_url="",new_uploadSize="";for(var i=0,len=value.length;i<len;i++){if(data_url[i]!=fielPath){new_value+=value[i];new_data_url+=data_url[i];new_uploadSize+=uploadSize[i];}
else{uploadNum--;var grid_store=Ext.getCmp('qeweb-multifile-list').getStore();var record=grid_store.getById(data_url[i]);grid_store.remove(record);}
new_value+=DISLAND.SPLIT_TREBLE_BACKSLASH;new_data_url+=DISLAND.SPLIT_TREBLE_BACKSLASH;new_uploadSize+=DISLAND.SPLIT_COMMA;}
bop.children(DISLAND.BOP_VALUE).text(new_value);bopData.attr(DISLAND.BOP_DATA_URL,new_data_url);bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM,uploadNum);bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE,new_uploadSize);var anchor=Ext.getCmp(fileFieldId);var anchorStr;if(uploadNum>0)
anchorStr="<a href='#' onclick='viewFileList(\""+fileFieldId+"\",\""+fileFieldName+"\");'>"+I18N.MULTI_FILE_UPLOAD+" "+uploadNum+"</a>";else
anchorStr=I18N.MULTI_FILE_UPLOAD+" "+uploadNum;var check=Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId));anchor.setText(anchorStr,false);check.setValue(new_value);}
var _max=0;Ext.apply(Ext.form.VTypes,{bopRange:_bopRange,bopRangeText:I18N.RANGE_INPUT_ERROR});function _bopRange(val,vc){var island=getDataIsland();var bopIdArray=vc.id.split("-");var vcParent=vc.findParentByType("form");if(vcParent!=null&&vcParent!='undefined'){if(vcParent.getId().indexOf("-hiddenForm")>0)
bopIdArray[0]=bopIdArray[0]+"-hiddenForm";}
var ddom=island.find("#"+bopIdArray[0]);var rangelist;if(island.find("#"+bopIdArray[0]).length>0&&island.find("#"+bopIdArray[0])[0].tagName==DISLAND.BOLIST)
rangelist=ddom.find(DISLAND.COLUMNINFO).children("column[bind='"+bopIdArray[2]+"']").slice(0).find(DISLAND.BOP_RANGE);else
rangelist=ddom.children("bop[bind='"+bopIdArray[2]+"']").slice(0).find(DISLAND.BOP_RANGE);var isIn=true;var vtype=this;var i=-1;var vrt=false;if(rangelist.attr(DISLAND.BOP_RANGE_REQUIRED)!="true"&&vc.getValue()=="")
return true;var minLength=rangelist.attr(DISLAND.BOP_RANGE_MINLENGTH);var maxLength=rangelist.attr(DISLAND.BOP_RANGE_MAXLENGTH);var valLength=strlen(vc.getValue());if(minLength&&!maxLength){if(valLength<minLength){vtype.bopRangeText=I18N.RANGE_NOT_LESS+minLength+I18N.RANGE_LENGTH_LESS_2;return false;}}
if(maxLength&&!minLength){if(valLength>maxLength){vtype.bopRangeText=I18N.RANGE_NOT_LONG+maxLength+I18N.RANGE_LENGTH_LESS_2;return false;}}
if(maxLength&&minLength){if(valLength>maxLength||valLength<minLength){vtype.bopRangeText=I18N.RANGE_LENGTH_MUST_1+minLength+DISLAND.SPLIT_EXPEND+maxLength+I18N.RANGE_LENGTH_MUST_2+I18N.RANGE_LENGTH_REMARK;return false;}}
rangelist.children().each(function(){var range=$(this);var type=range.context.nodeName;var logic=range.attr(DISLAND.BOP_RANGE_RULE);i++;if(type==DISLAND.BOP_RANGE_ENUM||type==DISLAND.BOP_RANGE_SEQUENCE){vrt=cValidate(vc,range,vtype);}
else if(type==DISLAND.BOP_RANGE_LOGIC){return true;}
if(i==0){isIn=vrt;if(!isIn&&range.next().attr(DISLAND.BOP_RANGE_RULE)==DISLAND.BOP_RANGE_AND){return false;}}
else{switch(logic){case DISLAND.BOP_RANGE_AND:isIn=isIn&&vrt;break;case DISLAND.BOP_RANGE_OR:isIn=isIn||vrt;break;case DISLAND.BOP_RANGE_NOT:isIn=isIn&&!vrt;break;}
if(!isIn&&logic==DISLAND.BOP_RANGE_AND){return false;}}});return isIn;}
function isINSequence(value,max,min,step,scale){if(Number(scale)>0){var flag=String(value).match("^([/-]?)\\d+(\\.\\d{1,"+scale+"})?$");if(flag==null)
return false;}
var lower=NumberUtil.sub(value,min);var upper=NumberUtil.sub(value,max);if(lower<0||0<upper)
return false;if(Number(step)==0)
return true;return NumberUtil.mod(lower,step)==0;}
function isINEnum(value,range){if(!!value){return range.find(DISLAND.BOP_RANGE_ITEM+"["+DISLAND.BOP_VALUE+"='"+value+"']").length>0;}
else{return true;}}
function cValidate(vc,range,vtype){var rt=true;if(range.context.nodeName==DISLAND.BOP_RANGE_SEQUENCE){if(isNaN(vc.getValue())){vtype.bopRangeText=I18N.RANGE_NAN;return false;}
var max=getRealValue(range.find(DISLAND.BOP_RANGE_MAX));var min=getRealValue(range.find(DISLAND.BOP_RANGE_MIN));var step=getRealValue(range.find(DISLAND.BOP_RANGE_STEP));var scale=getRealValue(range.find(DISLAND.BOP_RANGE_SCALE));rt=isINSequence(vc.getValue(),max,min,step,scale);if(!rt){var errMsg=I18N.RANGE_MUST_IN+min+I18N.RANGE_MUST_IN_TO+max+I18N.RANGE_MUST_IN_END;if(Number(scale)>0){errMsg+=","+I18N.RANGE_SCALE+scale;}
if(Number(step)!=0){errMsg+=","+I18N.RANGE_MUST_IN_STRP+step;}
vtype.bopRangeText=errMsg;}}
else if(range.context.nodeName==DISLAND.BOP_RANGE_ENUM){var value=vc.getValue();if(CommonDom.isSelect(vc)){value=vc.getRawValue();}
rt=isINEnum(value,range);if(!rt){vtype.bopRangeText=I18N.RANGE_NOTIN_RANGE;}}
return rt;}
function serverValidate(fcHandler){var flag=true;var fc=fcHandler.fc;var data={};data.vcId=fc.id;data.value=fcHandler.getValue();var rangelist=fcHandler.bopDom.find(DISLAND.BOP_RANGE);rangelist.children().each(function(){if(fcHandler instanceof LabelHandler)
return true;var type=$(this).context.nodeName;if(type==DISLAND.BOP_RANGE_LOGIC){data.rangeClass=$(this).attr(DISLAND.BOP_RANGE_LOGIC_CLASS);$.ajax({type:"POST",url:appConfig.ctx+actionURL.getFcServerValidate(),async:false,data:data,success:function(msg){if(msg!=""){fc.isValid(false);fc.markInvalid(msg);flag=false;}}});return false;}});return flag;};
Ext.namespace("Ext.ux.layout");Ext.ux.layout.TableFormLayout=Ext.extend(Ext.layout.TableLayout,{monitorResize:true,setContainer:function(){Ext.layout.FormLayout.prototype.setContainer.apply(this,arguments);this.currentRow=0;this.currentColumn=0;this.cells=[];},renderItem:function(c,position,target){if(c&&!c.rendered){var cell=Ext.get(this.getNextCell(c));cell.addClass("x-table-layout-column-"+this.currentColumn);Ext.layout.FormLayout.prototype.renderItem.call(this,c,0,cell);}},getLayoutTargetSize:Ext.layout.AnchorLayout.prototype.getLayoutTargetSize,getTemplateArgs:Ext.layout.FormLayout.prototype.getTemplateArgs,onLayout:function(ct,target){Ext.ux.layout.TableFormLayout.superclass.onLayout.call(this,ct,target);if(!target.hasClass("x-table-form-layout-ct")){target.addClass("x-table-form-layout-ct");}
var viewSize=this.getLayoutTargetSize(ct,target);var aw,ah;if(ct.anchorSize){if(typeof ct.anchorSize=="number"){aw=ct.anchorSize;}else{aw=ct.anchorSize.width;ah=ct.anchorSize.height;}}else{aw=ct.initialConfig.width;ah=ct.initialConfig.height;}
var cs=this.getRenderedItems(ct),len=cs.length,i,c,a,cw,ch,el,vs,boxes=[];var x,w,h,col,colWidth,offset;for(i=0;i<len;i++){c=cs[i];x=c.getEl().parent(".x-table-layout-cell");if(this.columnWidths){col=parseInt(x.dom.className.replace(/.*x\-table\-layout\-column\-([\d]+).*/,"$1"));colWidth=0,offset=0;for(var j=col;j<(col+(c.colspan||1));j++){colWidth+=this.columnWidths[j];offset+=10;}
w=(viewSize.width*colWidth)-offset;}else{w=(viewSize.width/this.columns)*(c.colspan||1);}
x.setWidth(w);if((c.type&&c.type=='anchor')||(c.xtype&&c.xtype=='fieldset')){w=w-130;}else{w=w-15;}
h=x.getHeight();if(!c.anchor&&c.items&&!Ext.isNumber(c.width)&&!(Ext.isIE6&&Ext.isStrict)){c.anchor=this.defaultAnchor;}
if(c.anchor){a=c.anchorSpec;if(!a){vs=c.anchor.split(' ');c.anchorSpec=a={right:this.parseAnchor(vs[0],c.initialConfig.width,aw),bottom:this.parseAnchor(vs[1],c.initialConfig.height,ah)};}
cw=a.right?this.adjustWidthAnchor(a.right(w),c):undefined;ch=a.bottom?this.adjustHeightAnchor(a.bottom(h),c):undefined;if(cw||ch){boxes.push({comp:c,width:cw||undefined,height:ch||undefined});}}}
for(i=0,len=boxes.length;i<len;i++){c=boxes[i];c.comp.setSize(c.width,c.height);}},parseAnchor:function(a,start,cstart){if(a&&a!="none"){var last;if(/^(r|right|b|bottom)$/i.test(a)){var diff=cstart-start;return function(v){if(v!==last){last=v;return v-diff;}};}else if(a.indexOf("%")!=-1){var ratio=parseFloat(a.replace("%",""))*.01;return function(v){if(v!==last){last=v;return Math.floor(v*ratio);}};}else{a=parseInt(a,10);if(!isNaN(a)){return function(v){if(v!==last){last=v;return v+a;}};}}}
return false;},adjustWidthAnchor:function(value,comp){return value-(comp.isFormField?(comp.hideLabel?0:this.labelAdjust):0);},adjustHeightAnchor:function(value,comp){return value;},isValidParent:function(c,target){return c.getPositionEl().up('table',6).dom.parentNode===(target.dom||target);},getLabelStyle:Ext.layout.FormLayout.prototype.getLabelStyle,labelSeparator:Ext.layout.FormLayout.prototype.labelSeparator,trackLabels:Ext.layout.FormLayout.prototype.trackLabels});Ext.Container.LAYOUTS['tableform']=Ext.ux.layout.TableFormLayout;if(Ext.grid.CheckboxSelectionModel){var interceptOnMouseDown=Ext.grid.CheckboxSelectionModel.prototype.onMouseDown.createInterceptor(function(e,t){this.on('rowdeselect',this.handleDeselect,this);this.on('rowselect',this.handleSelect,this);});Ext.override(Ext.grid.CheckboxSelectionModel,{hd:null,onMouseDown:interceptOnMouseDown,handleSelect:function(){if(this.grid.store.getCount()!=this.selections.length)
return;var hd=Ext.fly(this.grid.getView().innerHd).child('div.x-grid3-hd-checker');if(!hd.hasClass('x-grid3-hd-checker-on'))
hd.addClass('x-grid3-hd-checker-on');},handleDeselect:function(){if(this.grid.store.getCount()!=this.selections.length+1)
return;var hd=Ext.fly(this.grid.getView().innerHd).child('div.x-grid3-hd-checker');if(hd.hasClass('x-grid3-hd-checker-on'))
hd.removeClass('x-grid3-hd-checker-on');}});}