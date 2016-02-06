package com.qeweb.framework.pl.ext.finegrained.other;

import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.FileBopUtil;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.other.FileField;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * 细粒度组件ExtFileField的ext封装实现
 *
 */
public class ExtFileField extends FileField {

	@Override
	public void paintSingle() {
		//不需要上传按钮
		if(StringUtils.isEmpty(getOperate()) && !isDownload())
			paintNoBtn();
		else
			paintBtn();
	}
	

	@Override
	public void paintMultiple() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendAttrName(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "xtype", "compositefield");
		ExtWebHelper.appendAttr(sbr, "combineErrors", false);
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
		String style = "'padding-top: 2px;padding-left: 15px;'";
		ExtWebHelper.appendObjectAttr(sbr, "style", style);		
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		else if(getStyle() != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", getStyle());
		}
		sbr.append("items:[");
		//已上传文件列表链接
		sbr.append("new Ext.form.Label({");
		ExtWebHelper.appendAttr(sbr, "id", this.getId());
		//判断是否为文件上传控件
		ExtWebHelper.appendAttr(sbr, "defaultType", "filefield");
		ExtWebHelper.appendAttr(sbr, "flex", 2500);
		ExtWebHelper.appendAttr(sbr, "height", 21);
		ExtWebHelper.appendTail(sbr, "html", getMultiFileView());
		sbr.append("})");				
		
		getPageContextInfo().write(sbr.toString());
		if(!getStatus().isDisable()){
			// 上传按钮
			if(StringUtils.isNotEmpty(getOperate())){
				getPageContextInfo().write(",");
				sbr = new StringBuilder();
				sbr.append("new Ext.Button({");
				ExtWebHelper.appendAttr(sbr, "text", StringUtils.isEmpty(getOperateText()) ? AppLocalization.getLocalization("upload") : getOperateText());
				ExtWebHelper.appendAttr(sbr, "iconCls", "upload");
				ExtWebHelper.appendObjectTail(sbr, "handler", "function(){");
				sbr.append("ajaxMultiFileUpload('");	
				sbr.append(this.getId());
				sbr.append("','");	
				sbr.append(this.getName());
				sbr.append("');");	
				sbr.append("}})");
				
				getPageContextInfo().write(sbr.toString());
			}
			// 显示错误校验信息
			sbr = new StringBuilder();
			sbr.append(",{");
			ExtWebHelper.appendAttr(sbr, "flex", 1);
			ExtWebHelper.appendAttr(sbr, "id", getCheckId());
			ExtWebHelper.appendBlank(sbr, this);
			ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
			ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
			ExtWebHelper.appendTail(sbr, "cls", "label");
			sbr.append("}");
			// 填充用，无业务意义
			sbr.append(",new Ext.form.Label({");
			ExtWebHelper.appendTail(sbr, "flex", 6500);
			sbr.append("})");				
				
			getPageContextInfo().write(sbr.toString());
		}
		
		getPageContextInfo().write("]");
	}
	
	private void paintNoBtn(){
		getPageContextInfo().write(getFileField(true));		
	}
	
	private void paintBtn(){
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendAttrName(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "id", getCompositefieldId());
		ExtWebHelper.appendAttr(sbr, "xtype", "compositefield");
		ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
		ExtWebHelper.appendAttr(sbr, "combineErrors", false);
		ExtWebHelper.appendAttr(sbr, "defaultType", "filefield");
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		sbr.append("items:[{");
		sbr.append(getFileField(false)).append("}");
		getPageContextInfo().write(sbr.toString());
		if(!getStatus().isDisable()){
			// 上传按钮
			if(StringUtils.isNotEmpty(getOperate())){
				getPageContextInfo().write(",");
				sbr = new StringBuilder();
				sbr.append("{");
				ExtWebHelper.appendAttr(sbr, "xtype", "button");
				ExtWebHelper.appendAttr(sbr, "text", getOperateText());
				ExtWebHelper.appendAttr(sbr, "iconCls", "upload");
				if(isShowRange())
					ExtWebHelper.appendAttr(sbr, "tooltip", getShowRanageStr());
				ExtWebHelper.appendObjectTail(sbr, "handler", "function(){");
				sbr.append("ajaxFileUpload('");	
				sbr.append(this.getId());
				sbr.append("','");	
				sbr.append(this.getName());
				sbr.append("');");	
				sbr.append("}}");
				
				getPageContextInfo().write(sbr.toString());
			}
			sbr = new StringBuilder();
			sbr.append(",{");
			ExtWebHelper.appendAttr(sbr, "flex", 1);
			ExtWebHelper.appendAttr(sbr, "id", getCheckId());
			ExtWebHelper.appendBlank(sbr, this);
			ExtWebHelper.appendAttr(sbr, "xtype", "textfield");
			if(StringUtils.isNotEmpty(getBc().toLink()))
				ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
			ExtWebHelper.appendTail(sbr, "cls", "label");
			sbr.append("}");
			// 显示连接
			if(isDownload()){
				sbr.append(",{");
				ExtWebHelper.appendAttr(sbr, "xtype", "label");
				ExtWebHelper.appendAttr(sbr, "id", getAnchorId());
				ExtWebHelper.appendAttr(sbr, "flex", 3000);
				ExtWebHelper.appendAttr(sbr, "height", 20);
				if(StringUtils.isNotEmpty(getBc().toLink()))
					ExtWebHelper.appendAttr(sbr, "html", getAnchor());
				ExtWebHelper.appendTail(sbr, "style", "padding-top:2px;padding-left:15px;");
				sbr.append("}");				
			}
			getPageContextInfo().write(sbr.toString());
		}
		
		getPageContextInfo().write("]");
	}

	/**
	 * 拼装上传限制文本信息
	 * @return
	 */
	private String getShowRanageStr() {
		StringBuilder sbr = new StringBuilder();
		FileBOP bop = (FileBOP)BoOperateUtil.getRealBop(this.getBc());
		sbr.append("<span>");
		sbr.append(AppLocalization.getLocalization("file.upload.mulipart.maxSize"));
		sbr.append((bop.getMaxSize() / 1024 / 1024) + "M");
		//允许上传类型
		String allowedTypeStr = FileBopUtil.getTypeStr(bop.getAllowedType());
		if(StringUtils.isNotEmpty(allowedTypeStr)){
			sbr.append("<br>");
			sbr.append(AppLocalization.getLocalization("file.upload.allowedType"));
			sbr.append(allowedTypeStr);
		}
		//不允许类型
		String notAllowedTypeStr = FileBopUtil.getTypeStr(bop.getNotAllowedType());
		if(StringUtils.isNotEmpty(notAllowedTypeStr)){
			sbr.append("<br>");
			sbr.append(AppLocalization.getLocalization("file.upload.notAllowedType"));
			sbr.append(notAllowedTypeStr);
		}
		sbr.append("</span>");
		return StringUtils.trim(sbr.toString());
	}


	private String getFileField(boolean notCompositefield){
		StringBuilder sbr = new StringBuilder(); 

		if(notCompositefield){
			ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
			ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
			if(getHistoryBC() != null)
				ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
			Schema schema = getSchema();
			if(schema != null) {
				ExtWebHelper.appendObjectAttr(sbr, "labelStyle", schema.getStyle());
			}
			ExtWebHelper.appendAttr(sbr, "defaultType", "filefield");
		}
		else {
			if(!isDownload())
				ExtWebHelper.appendBlank(sbr, this);
			ExtWebHelper.appendAttr(sbr, "flex", 5000);
		}
		ExtWebHelper.appendAttr(sbr, "id", getId());
		//判断是否为文件上传控件
		ExtWebHelper.appendAttr(sbr, "baseCls", "x-plain");
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toLink()); 
		ExtWebHelper.appendAttr(sbr, "xtype", "fileuploadfield");
		ExtWebHelper.appendAttr(sbr, "name", getName());	
		ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
		
		return ExtWebHelper.removeEnd(sbr);
	}
	
	private String getCompositefieldId(){
		return getId() + "_composite";
		
	}
	
	private String getAnchorId(){
		return getId() + "_anchor";
	}
	
	private String getCheckId(){
		return getAnchorId() + "_check";
	}
	
	private String getAnchor() {
		FileBOP fileBop = (FileBOP) BoOperateUtil.getRealBop(getBc());
		return "<a href=\"" + fileBop.toLink() + "\" target=\"_blank\"" +
				" title=\"" + StringUtils.html(fileBop.getDisplayName()) + "\">"
				+ fileBop.getDisplayName() + "</a>";
	}
	
	private String getMultiFileView() {
		StringBuilder viewStr = new StringBuilder();
		if(getMultiFileBOP().getFiles().isEmpty())
			viewStr.append(AppLocalization.getLocalization("multifile.upload.str")).append(" ").append(0);
		else{
			viewStr.append("<a href=\\'#\\' onclick=\\'viewFileList(\"");
			viewStr.append(getId());
			viewStr.append("\",\"");
			viewStr.append(getName());
			viewStr.append("\");\\'>");
			viewStr.append(AppLocalization.getLocalization("multifile.upload.str"));
			viewStr.append(" ");
			viewStr.append(getMultiFileBOP().getFiles().size());
			viewStr.append("</a>");
		}
		return viewStr.toString();
	}
}
