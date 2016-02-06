package com.qeweb.framework.pl.ext.finegrained.enumcomp;

import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.enumcomp.CheckBox;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtCheckBox extends CheckBox {
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		// 当checkbox没有枚举范围时，按“空白"组件画
		if(!hasItems()) {
			ExtWebHelper.appendTail(sbr, "xtype", "label");
			getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
			return;
		}
		
		ExtWebHelper.appendSingleValueHead(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "xtype", "checkboxgroup");
		
		Integer colunms = StringUtils.convertToInteger(getColumns());
		if(colunms != null)
			ExtWebHelper.appendAttr(sbr, "columns", colunms);
		
		String style = "projectStyle.getRCStyle()";
		String lableStyle = "projectStyle.getRCLableStyle()";
		Schema schema = getSchema();
		if(schema != null) {
			lableStyle += "+'" + schema.getStyle() + "'";
			style += "+'" + schema.getStyle() + "'";
		}
		ExtWebHelper.appendObjectAttr(sbr, "labelStyle", lableStyle);
		ExtWebHelper.appendObjectAttr(sbr, "style", style);
		ExtWebHelper.appendAttr(sbr, "defaultType", "checkbox");
		ExtWebHelper.appendAttr(sbr, "autoHeight", true);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendBlank(sbr, this);
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText()); 
		ExtWebHelper.appendAttr(sbr, "hidden", getStatus().isHidden());
		
		String listeners = "{ 'resize':function(){ CheckboxHelper.resize(this); } }  ";
		ExtWebHelper.appendObjectAttr(sbr, "listeners", listeners);

		ExtWebHelper.appendObjectAttr(sbr, "items", getItems(getBc()));

		getPageContextInfo().write(ExtWebHelper.removeEnd(sbr));
	}

	/**
	 * 生成item对象集
	 * 
	 * @param bop
	 * @return
	 */
	private String getItems(BOProperty bop) {
		StringBuilder items = new StringBuilder();
		ExtWebHelper.appendArrayBegin(items);
		Map<String, String> store = bop.toMap();
		int index = 0;
		int len = store.entrySet().size();
		for (Entry<String, String> entry : store.entrySet()) {
			if(StringUtils.isEmpty(entry.getValue()) || StringUtils.isEmpty(entry.getKey()))
				continue;
				
			ExtWebHelper.appendObjectBegin(items);
			ExtWebHelper.appendAttr(items, "id", getId() + ConstantDataIsland.HORIZONTAL_SPLIT + index++);
			if(index == len){
				ExtWebHelper.appendAttr(items, "showInvalidText", true);
				ExtWebHelper.appendAttr(items, "invalidText", AppLocalization.getLocalization(Constant.LOCATION_FORM_REQUIRED));				
			}
			ExtWebHelper.appendAttr(items, "name", getName());		
			ExtWebHelper.appendAttr(items, "disabled", getStatus().isDisable() || getStatus().isReadonly());
			if (getChecked(bop).containsKey(entry.getKey()))
				ExtWebHelper.appendAttr(items, "checked", true);
			ExtWebHelper.appendAttr(items, "inputValue", entry.getKey());
			ExtWebHelper.appendTail(items, "boxLabel", StringUtils.getUnescapedText(entry.getValue()));
			ExtWebHelper.appendObjectAfter(items);
			ExtWebHelper.appendObjectSplit(items);
		}
		
		return ExtWebHelper.removeEnd(items) + "]";
	}
}
