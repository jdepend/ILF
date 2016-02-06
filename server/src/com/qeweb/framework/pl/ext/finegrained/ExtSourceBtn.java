package com.qeweb.framework.pl.ext.finegrained;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.SourceBtn;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtSourceBtn extends SourceBtn {

	/**
	 * 
	 *  new Ext.Button({
	 *  	id:"_source_btn",
	 *  	sbopIds: sbopIds,
	 *  	editAble: editAble,
	 *  	flex:10,
	 *  
	 *  	text:'选择', 
	 *  	handler:function(){
	 *  		var param = {};
	 *  		param.dialogType = 'S';
	 *  		param.path = dialogPath;
	 *  		param.title = 'title';
	 *  		param.hasCloseBtn = true;
	 *  		param.sm = 'radio/checkbox';
	 *  	    param.operate = 'bo.method';
	 *  		param.sbopIds = sbopIds;
	 *  		param.tbopIds = tbopIds;
	 *  		param.echo = echo;
	 *  		param.sysOperate = sysOperate;
	 *  		param.width = width;
	 *  		param.height = height;
	 *  		dialog.openSDialog(dialogWidth, dialogHeight, path);
	 *  	}
	 *  }),
	 *  new Ext.Button({
	 *  	flex:10,
	 *  	text:'清除', 
	 *  	handler:function(){
	 *  		var param = {};
	 *  		param.sbopIds = '';
	 *  		dialog.clear(param);
	 *  	}
	 *  })
	 */
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		//选择按钮
		sbr.append("new Ext.Button({");
		ExtWebHelper.appendAttr(sbr, "id", getSourceBtnId());
		ExtWebHelper.appendAttr(sbr, "sbopIds", getSbopIds());
		ExtWebHelper.appendAttr(sbr, "editAble", getEditAble());
		ExtWebHelper.appendAttr(sbr, "flex", 10);
		ExtWebHelper.appendAttr(sbr, "text", AppLocalization.getLocalization("select"));
		ExtWebHelper.appendObjectTail(sbr, "handler", "function(){");
		sbr.append("var param = {};");
		sbr.append("param.dialogType = 'S';");
		sbr.append("param.path = '").append(getDialogPath()).append("';");
		sbr.append("param.title = '").append(getDialogTitle()).append("';");
		if(StringUtils.isNotEmpty(getEcho()))
			sbr.append("param.echo = '").append(getEcho()).append("';");
		if(StringUtils.isNotEmpty(getOperate()))
		    sbr.append("param.operate = '").append(getOperate()).append("';");
		sbr.append("param.sm = '").append(getSm()).append("';");
		sbr.append("param.sbopIds = '").append(getSbopIds()).append("';");
		sbr.append("param.tbopIds = '").append(getTbopIds()).append("';");
		sbr.append("param.hasCloseBtn = ").append(hasCloseBtn()).append(";");
		if(StringUtils.isNotEmptyStr(getDialogWidth()))
			sbr.append("param.width = '").append(getDialogWidth()).append("';");
		if(StringUtils.isNotEmptyStr(getDialogHeight()))
			sbr.append("param.height = '").append(getDialogHeight()).append("';");
		if(StringUtils.isNotEmpty(getSysOperate()))
			sbr.append("param.sysOperate = '").append(getSysOperate()).append("';");
		sbr.append("dialog.openDialog(param);}");
		sbr.append("}),");
		
		//清除按钮
		sbr.append("new Ext.Button({");
		ExtWebHelper.appendAttr(sbr, "flex", 10);
		ExtWebHelper.appendAttr(sbr, "text", AppLocalization.getLocalization("clear"));
		ExtWebHelper.appendObjectTail(sbr, "handler", "function(){");
		sbr.append("var param = {};");
		sbr.append("param.sbopIds = '").append(getSbopIds()).append("';");
		if(StringUtils.isNotEmpty(getSysOperate()))
			sbr.append("param.sysOperate = '").append(getSysOperate()).append("';");
		sbr.append("dialog.clear(param);}");
		sbr.append("})");
		getPageContextInfo().write(sbr.toString());
	}
	
	
	private String getSourceBtnId(){
		//.getPrevBCID()
		return this.getParent().getId() + "_source_btn";
	}
	
}
