package com.qeweb.framework.pl.ext.coarsegrained;

import net.sf.json.JSONArray;

import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtTree extends Tree {
	
	@Override
	/**
	 * 画出ExtTree结构
	 * @param tree
	 */
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getTreePanelName(), "Ext.tree.TreePanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "title", getText());
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		ExtWebHelper.appendAttr(sbr, "useArrows", true);
		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
		ExtWebHelper.appendAttr(sbr, "animate", true);
		ExtWebHelper.appendAttr(sbr, "enableDD", false);
		ExtWebHelper.appendAttr(sbr, "containerScroll", true);
		ExtWebHelper.appendAttr(sbr, "frame", true);
		ExtWebHelper.appendAttr(sbr, "border", false);
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "collapsible", true);
		ExtWebHelper.appendObjectAttr(sbr, "loader", "new Ext.tree.TreeLoader()");
		ExtWebHelper.appendObjectAttr(sbr, "root",
				"new Ext.tree.AsyncTreeNode({expanded:true,children:"
						+ JSONArray.fromObject(createTree(this)).toString() + "})");
		ExtWebHelper.appendTail(sbr, "rootVisible", false);
		ExtWebHelper.appendObjectAfter(sbr);
		ExtWebHelper.appendCreateObjectEnd(sbr);
		sbr.append(";");
		sbr.append(getTreePanelName()).append(".on('load',function(){").append(getTreePanelName()).append(".expandAll();});");
		sbr.append("itemArray.push(").append(getTreePanelName()).append(");");
		
		getPageContextInfo().write(sbr.toString());
	}
}
