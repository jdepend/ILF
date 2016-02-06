package com.qeweb.framework.pl.ext.coarsegrained;

import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.common.constant.ConstantURL;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.Form;
import com.qeweb.framework.pal.coarsegrained.Tab;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;
import com.qeweb.framework.pal.finegrained.text.Label;
import com.qeweb.framework.pal.layout.others.Layout;
import com.qeweb.framework.pal.layout.others.LayoutFactory;
import com.qeweb.framework.pal.layout.others.interpreter.Cell;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtForm extends Form {
	
	public String getFormPanelName() {
		return ExtContainerHelper.getContainerPanelName(this);
	}	
	
	@Override
	public void paint() {
		paintDeptTree();
		PageContextInfo out = getPageContextInfo();
		StringBuilder sbr = new StringBuilder();
		sbr.append("var ").append(getFormPanelName()).append(" = new Ext.form.FormPanel({");
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "title", getText());
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		
		if(StringUtils.isEmpty(getStyleContent())) {
			ExtWebHelper.appendAttr(sbr, "frame", true);
		}
		else {
			ExtWebHelper.appendAttr(sbr, "frame", false);
			ExtWebHelper.appendAttr(sbr, "bodyStyle", "padding:10 10 0;" + getStyleContent());
		}
		
		ExtWebHelper.appendAttr(sbr, "collapsible", true);
		ExtWebHelper.appendAttr(sbr, "hidden", getStatus().isHidden());
		ExtWebHelper.appendAttr(sbr, "hideMode", "offsets");
		ExtWebHelper.appendAttr(sbr, "labelAlign", "right");
		ExtWebHelper.appendAttr(sbr, "labelWidth", getMaxLabelWidth());
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "layout", "tableform");
		ExtWebHelper.appendAttr(sbr, "url", out.getContextPath() + ConstantURL.CONTAINER_SUBMIT);
		out.write(sbr.toString());
		
		paintFCList();
		paintBtnList();
			
		sbr = new StringBuilder();
		ExtWebHelper.appendTail(sbr, "method", "post");
		sbr.append("});");
		//如果在tab中，就不把自己放入itemArray
		if(!(this.getParent() instanceof Tab)) {
			sbr.append("itemArray.push(");
			sbr.append(getFormPanelName());
			sbr.append(");");
		}
		out.write(sbr.toString());
	}

	/**
	 * 如果select控件是下拉树, 在此绘制
	 */
	private void paintDeptTree() {
		Map<String, FinegrainedComponent> fcList = getFcList();
		if(ContainerUtil.isNull(getFcList()))
			return;
		
		for (Entry<String, FinegrainedComponent> entry : fcList.entrySet()) {
			if(entry.getValue() instanceof Select) {
				Select select = (Select) entry.getValue();
				if(select.isDeptTree())
					select.paintDeptTree();
			}
		}
	}
	
	private void paintBtnList() {
		PageContextInfo out = getPageContextInfo();
		if (ContainerUtil.isNotNull(getButtonList())) {
			ExtContainerHelper.paintButtons(getButtonList().values(), out);
			out.write(",");
		}
	}
	
	private void paintFCList() {
		Map<String, FinegrainedComponent> fcList = getFcList();
		if(ContainerUtil.isNull(fcList))
			return;
		
		PageContextInfo out = getPageContextInfo();
		StringBuilder sbr = new StringBuilder();
		Layout layout = LayoutFactory.createLayout(getLayoutStr(), LayoutFactory.LAYOUTTYPE_FORM);
		
		//表单列数
		int columns = layout.getColumns();
		ExtWebHelper.appendObjectAttr(sbr, "layoutConfig", "{columns:" + columns + "}");
		ExtWebHelper.appendContent(sbr, "items: [");

		Map<String, Cell> cellMap = layout.getCellMap();
		//当前行的剩余列数
		int restColumns = columns;
		for (Entry<String, FinegrainedComponent> entry : fcList.entrySet()) {
			//当前细粒度组件
			String currentBopBind = entry.getKey();
			FinegrainedComponent currentFC = entry.getValue();
			
			//当前细粒度组件占的列数
			int currentFCCollSpan = 1;
			int currentFCRowSpan = 1;
			if(cellMap.containsKey(currentBopBind)) {
				currentFCCollSpan = cellMap.get(currentBopBind).getCollSpan();
				currentFCRowSpan = cellMap.get(currentBopBind).getRowSpan();
				currentFC.setCollSpan(currentFCCollSpan);
			}
			
			//当本行剩余单元格为空时, 重置restColumns
			if(restColumns == 0) {
				//重置restColumns
				restColumns = columns;
			}
			//当本行剩余单元格不足以容纳当前细粒度组件时, 需要在上一组件后填充空白单元格
			else if(restColumns - currentFCCollSpan < 0) {
				//填充空白单元格
				ExtWebHelper.appendObjectBegin(sbr);
				ExtWebHelper.appendAttr(sbr, "xtype", "label");
				ExtWebHelper.appendTail(sbr, "colspan", restColumns);
				ExtWebHelper.appendObjectAfter(sbr);
				ExtWebHelper.appendObjectSplit(sbr);
				
				//重置restColumns
				restColumns = columns;
			}
			
			ExtWebHelper.appendObjectBegin(sbr);
			ExtWebHelper.appendAttr(sbr, "colspan", currentFCCollSpan);
			ExtWebHelper.appendAttr(sbr, "rowspan", currentFCRowSpan);
			
			if(!(entry.getValue() instanceof Label))
				ExtWebHelper.appendAttr(sbr, "anchor", "98%");
			
			out.write(sbr.toString());
			currentFC.paint();
			out.write("}");
			sbr = new StringBuilder();
			ExtWebHelper.appendObjectSplit(sbr);	
			
			//计算剩余单元格
			restColumns -= currentFCCollSpan;
		}
		
		sbr = new StringBuilder();
		ExtWebHelper.appendArrayAfter(sbr);

		out.write(sbr.toString());
		out.write(",");
	}
}
