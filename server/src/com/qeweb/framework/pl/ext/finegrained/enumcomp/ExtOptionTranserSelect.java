package com.qeweb.framework.pl.ext.finegrained.enumcomp;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.enumcomp.OptionTranserSelect;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtOptionTranserSelect extends OptionTranserSelect {

	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();

		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "viewlabel", getText());
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "xtype", "itemselector");
		ExtWebHelper.appendAttr(sbr, "imagePath", Envir.getContextPath() + "/framework/js/ext/ext3.2/ux/images/");
		Schema schema = getSchema();
		if(schema != null) {
			ExtWebHelper.appendAttr(sbr, "labelStyle", schema.getStyle());
		}
		ExtWebHelper.appendStatus(sbr, getStatus());
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
		ExtWebHelper.appendObjectAttr(sbr, "multiselects", getMultiselects(getBc()));

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}

	private String getMultiselects(BOProperty bop) {
		StringBuilder multiselects = new StringBuilder();
		ExtWebHelper.appendArrayBegin(multiselects);
		// 左边
		ExtWebHelper.appendObjectBegin(multiselects);
		ExtWebHelper.appendAttr(multiselects, "itemSelectorId", getId());
		ExtWebHelper.appendAttr(multiselects, "width", getOTSWidth());
		ExtWebHelper.appendAttr(multiselects, "height", getOTSHeight());
		ExtWebHelper.appendAttr(multiselects, "displayField", "text");
		ExtWebHelper.appendAttr(multiselects, "valueField", "value");
		ExtWebHelper.appendObjectTail(multiselects, "store", getLeftStore(bop));
		ExtWebHelper.appendObjectAfter(multiselects);
		// -----------------------
		ExtWebHelper.appendObjectSplit(multiselects);
		// 右边
		ExtWebHelper.appendObjectBegin(multiselects);
		ExtWebHelper.appendAttr(multiselects, "width", getOTSWidth());
		ExtWebHelper.appendAttr(multiselects, "height", getOTSHeight());
		ExtWebHelper.appendObjectTail(multiselects, "store", getRightStore(bop));
		ExtWebHelper.appendObjectAfter(multiselects);
		// -----------------------
		ExtWebHelper.appendArrayAfter(multiselects);
		return multiselects.toString();
	}

	/**
	 * 添加双向选择的左侧源内容
	 * 
	 * @param bop
	 */
	private String getLeftStore(BOProperty bop) {
		StringBuilder sbr = new StringBuilder();
		sbr.append("new Ext.data.ArrayStore({");
		sbr.append("data: ");
		sbr.append(ExtWebHelper.getSimpleDataStr(getSourceOptions(bop)));
		sbr.append(",");
		ExtWebHelper.appendObjectAttr(sbr, "fields", "['value','text']");
		sbr.append("sortInfo: ");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "field", "value");
		ExtWebHelper.appendTail(sbr, "direction", "ASC");
		ExtWebHelper.appendObjectAfter(sbr);
		sbr.append("})");

		return sbr.toString();
	}

	/**
	 * 添加双向选择的右侧目标内容
	 * 
	 * @param bop
	 */
	private String getRightStore(BOProperty bop) {
		return ExtWebHelper.getSimpleDataStr(getTargetOptions(bop));
	}
	
	private float getOTSHeight() {
		return getHeight() <= 0.0f ? 200 : getHeight();
	}
	
	private float getOTSWidth() {
		return getHeight() <= 0.0f ? 250 : getWidth();
	}
}
