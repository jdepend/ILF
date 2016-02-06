package com.qeweb.framework.pl.ext.finegrained.enumcomp;

/**
 * 细粒度组件radio的ext封装实现
 */
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.Schema;
import com.qeweb.framework.pal.finegrained.enumcomp.Radio;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtRadio extends Radio {
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendAttrName(sbr, "fieldLabel", this);
		ExtWebHelper.appendAttr(sbr, "xtype", "radiogroup");
		
		Integer colunms = StringUtils.convertToInteger(getColumns());
		if(colunms != null)
			ExtWebHelper.appendAttr(sbr, "columns", colunms);
		
		ExtWebHelper.appendAttr(sbr, "defaultType", "radio");
		ExtWebHelper.appendAttr(sbr, "autoHeight", true);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendBlank(sbr, this);
		if(getHistoryBC() != null)
			ExtWebHelper.appendAttr(sbr, "historyText", getHistoryBC().toText());
		ExtWebHelper.appendAttr(sbr, "hidden", getStatus().isHidden());
		String style = "projectStyle.getRCStyle()";
		String lableStyle = "projectStyle.getRCLableStyle()";
		Schema schema = getSchema();
		if(schema != null) {
			lableStyle += "+'" + schema.getStyle() + "'";
			style += "+'" + schema.getStyle() + "'";
		}
		ExtWebHelper.appendObjectAttr(sbr, "labelStyle", lableStyle);
		ExtWebHelper.appendObjectAttr(sbr, "style", style);		
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
		for (Entry<String, String> entry : store.entrySet()) {
			if(StringUtils.isEmpty(entry.getValue()) || StringUtils.isEmpty(entry.getKey()))
				continue;
			
			ExtWebHelper.appendObjectBegin(items);
			ExtWebHelper.appendAttr(items, "id", getId() + ConstantDataIsland.HORIZONTAL_SPLIT + index++);
			ExtWebHelper.appendAttr(items, "name", getName());
			ExtWebHelper.appendAttr(items, "disabled", getStatus().isDisable() || getStatus().isReadonly());
			ExtWebHelper.appendAttr(items, "value", entry.getKey());
			ExtWebHelper.appendAttr(items, "boxLabel", StringUtils.getUnescapedText(entry.getValue()));
			ExtWebHelper.appendAttr(items, "inputValue", entry.getKey());
			if (entry.getKey().equals(bop.getValue().getValue()))
				ExtWebHelper.appendAttr(items, "checked", true);
			ExtWebHelper.appendAttr(items, "itemCls", "allow-float");
			ExtWebHelper.appendTail(items, "clearCls", "clear-float");

			ExtWebHelper.appendObjectAfter(items);
			ExtWebHelper.appendObjectSplit(items);
		}
		
		return ExtWebHelper.removeEnd(items) + "]";
	}
}
