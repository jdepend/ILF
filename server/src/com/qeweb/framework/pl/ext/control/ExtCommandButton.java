package com.qeweb.framework.pl.ext.control;

import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.finegrained.SourceBtn;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtCommandButton extends CommandButton {
	
	@Override
	public void paint() {
		if(ConstantBOMethod.isInsert(getOperate()) && getParent() instanceof Table)
			paintInsertTbar();
		else
			paintGeneralBtn();
	}
	
	/**
	 * 画出table的insert按钮,insert是table级别按钮, 修改和查看是row级按钮
	 * new Ext.Button({
			id : 'showAll-DemoBO_ShowAllFC-insert',
			name : 'showAll-DemoBO_ShowAllFC-insert',
			hidden : bop.isHidden,
			disabled : bop.isDisabled
			iconCls : 'saveIcon',
			text : '新增',
			listeners : {
				'click':
				function() {
					formPanelArr[btnId].getForm().reset();
					recordWinArr[btnId].show(Ext.getBody());
				}
			}
		})
	 */
	private void paintInsertTbar() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, null, "Ext.Button");
		ExtWebHelper.appendObjectBegin(sbr);
		createBtnCommonAttr(sbr);
		ExtWebHelper.appendObjectSplit(sbr);
		ExtWebHelper.appendObjectTail(sbr, "listeners", "{'click': function(){");
		sbr.append("recordWinArr['" + getInsertBtnId() + "'].show(Ext.getBody());");
		
		//listeners结束
		sbr.append("}}");
		//button结束
		sbr.append("})");
		
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 画出普通按钮(将除table的insert/update/view按钮和table行级按钮外的其它按钮均视为普通按钮)
	 * 
	 *  new Ext.Button({
			id : 'showAll-DemoBO_ShowAllFC-camera',
			name : 'showAll-DemoBO_ShowAllFC-camera',
			hidden : bop.isHidden,
			disabled : bop.isDisabled
			iconCls : 'modifyIcon',
			text : '拍照'
		})
	 */
	private void paintGeneralBtn() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, null, "Ext.Button");
		ExtWebHelper.appendObjectBegin(sbr);
		createBtnCommonAttr(sbr);
		
		SourceBtn sourceBtn = getSourceBtn();
		if(sourceBtn != null) {
			ExtWebHelper.appendObjectSplit(sbr);
			ExtWebHelper.appendObjectTail(sbr, "handler", "function(){");
			sbr.append("var param = {};");
			sbr.append("param.dialogType = 'M';");
			sbr.append("param.path = '").append(sourceBtn.getDialogPath()).append("';");
			sbr.append("param.title = '").append(sourceBtn.getDialogTitle()).append("';");
			//<qeweb:sourceBtn>隶属于按钮
			sbr.append("param.insideBtn = true;");
			if(StringUtils.isNotEmpty(sourceBtn.getEcho()))
				sbr.append("param.echo = '").append(sourceBtn.getEcho()).append("';");
			if(StringUtils.isNotEmpty(sourceBtn.getOperate()))
			    sbr.append("param.operate = '").append(sourceBtn.getOperate()).append("';");
			sbr.append("param.sm = '").append(sourceBtn.getSm()).append("';");
			sbr.append("param.sbopIds = '").append(sourceBtn.getSbopIds()).append("';");
			sbr.append("param.tbopIds = '").append(sourceBtn.getTbopIds()).append("';");
			sbr.append("param.hasCloseBtn = ").append(sourceBtn.hasCloseBtn()).append(";");
			if(StringUtils.isNotEmptyStr(sourceBtn.getDialogWidth()))
				sbr.append("param.width = '").append(sourceBtn.getDialogWidth()).append("';");
			if(StringUtils.isNotEmptyStr(sourceBtn.getDialogHeight()))
				sbr.append("param.height = '").append(sourceBtn.getDialogHeight()).append("';");
			if(StringUtils.isNotEmpty(sourceBtn.getSysOperate()))
				sbr.append("param.sysOperate = '").append(sourceBtn.getSysOperate()).append("';");
			sbr.append("dialog.openDialog(param);}");
		}
		
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		getPageContextInfo().write(sbr.toString());
	}

	/**
	 * 添加按钮的必有属性
	 * @param sbr
	 */
	private void createBtnCommonAttr(StringBuilder sbr) {
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "height", ExtWebHelper.btnHeight);
		
		if(getBc() != null) {
			ExtWebHelper.appendAttr(sbr, "hidden", getStatus().isHidden());
			ExtWebHelper.appendAttr(sbr, "disabled", getStatus().isDisable());
		}
		
		String tipText = getTipText();
		if(StringUtils.isNotEmpty(tipText)) {
			ExtWebHelper.appendAttr(sbr, "tooltip", "asdf");
		}
		
		ExtWebHelper.appendTail(sbr, "text", getText());
	}
		
 
	/**
	 * 获取按钮图片
	 */
	@Override
	public String getIcon() {
		if(StringUtils.isNotEmpty(super.getIcon()))
			return super.getIcon();
		
		String method = getOperate();
		if(ConstantBOMethod.isQuery(method))
			return "searchIcon";
		else if(ConstantBOMethod.isInsert(method) || ConstantBOMethod.isSysAddRow(method))
			return "saveIcon";
		else if(ConstantBOMethod.isDelete(method) || ConstantBOMethod.isSysDelRow(method))
			return "removeIcon";
		else if(ConstantBOMethod.isSysReset(method) || ConstantBOMethod.isSysClear(method))
			return "reset";
		else if(ConstantBOMethod.isSave(method))
			return "save";
		else if(ConstantBOMethod.isBack(VCUtil.getButtonTagName(getName())))
			return "backIcon";
		else 
			return "modifyIcon";
	}
}
