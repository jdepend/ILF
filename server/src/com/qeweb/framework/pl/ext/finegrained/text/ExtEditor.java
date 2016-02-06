package com.qeweb.framework.pl.ext.finegrained.text;

import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.pal.finegrained.text.Editor;
import com.qeweb.framework.pl.ext.ExtWebHelper;

/**
 * 细粒度组件--富文本编辑器
 * 一个典型的富文本编辑器包括: 
 * 		字体, 前景色, 背景色, align, 段落编辑, source edit, 插入超链接, 插入图片, find/raplace, 标题, 制表等功能
 *
 */
public class ExtEditor extends Editor {
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "htmleditor");
		ExtWebHelper.appendAttr(sbr, "emptyText", getBc().getValue().getEmptyValue());
		if(getHeight() > 0)
			ExtWebHelper.appendAttr(sbr, "height", getHeight());
		ExtWebHelper.appendObjectAttr(sbr, "plugins", "Ext.ux.form.HtmlEditor.plugins()");
		
		if(getBc() == null) {
			ExtWebHelper.removeEnd(sbr);
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
		}
		
		ExtWebHelper.appendAttr(sbr, "value", getBc().toText());
		if(isValidateable())
			ExtWebHelper.appendAttr(sbr, "vtype", "bopRange");
		
		Status status = getStatus();
		if(status.isDisable() || status.isReadonly())
			ExtWebHelper.appendAttr(sbr, "readOnly", true);
		if(status.isHidden())
			ExtWebHelper.appendAttr(sbr, "hidden", true);
		
		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}
}
