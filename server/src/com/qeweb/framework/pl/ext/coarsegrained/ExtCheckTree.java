package com.qeweb.framework.pl.ext.coarsegrained;

import net.sf.json.JSONArray;

import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.PageContextInfo;
import com.qeweb.framework.pal.coarsegrained.CheckTree;
import com.qeweb.framework.pl.ext.ExtWebHelper;

public class ExtCheckTree extends CheckTree {
	/**
	 * 画出ExtCheckTree
	 * @param tree
	 */
	public void paint() {
		PageContextInfo out = this.getPageContextInfo();
		String children = JSONArray.fromObject(createTree(this)).toString();
		
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getTreePanelName(), "Ext.tree.TreePanel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "name", getName());
		ExtWebHelper.appendAttr(sbr, "title", getText());
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		ExtWebHelper.appendAttr(sbr, "collapsible", true);
		ExtWebHelper.appendAttr(sbr, "useArrows", true);
		ExtWebHelper.appendAttr(sbr, "frame", true);
		ExtWebHelper.appendAttr(sbr, "autoScroll", true);
		ExtWebHelper.appendAttr(sbr, "animate", true);
		ExtWebHelper.appendAttr(sbr, "enableDD", false);
		ExtWebHelper.appendAttr(sbr, "containerScroll", true);
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "border", true);
		ExtWebHelper.appendAttr(sbr, "rootVisible", false);
		ExtWebHelper.appendAttr(sbr, "deep", false);
		ExtWebHelper.appendAttr(sbr, "anim", false);
		ExtWebHelper.appendAttr(sbr, "cm", getBc().getCheckModel());
		ExtWebHelper.appendAttr(sbr, "iscascade", getBc().isCascade() + "");
		ExtWebHelper.appendAttr(sbr, "style", "margin-left:1px;margin-top:1px;");
		ExtWebHelper.appendObjectAttr(sbr, "loader", "new Ext.tree.TreeLoader()");
		ExtWebHelper.appendObjectAttr(sbr, "root", "new Ext.tree.AsyncTreeNode({expanded:true,children:" + children + "})");
		ExtWebHelper.appendObjectAttr(sbr, "listeners", "{'beforedestroy':function(){return false;}}");
		
		if(ContainerUtil.isNull(this.getButtonList()) && !isFill()) {
			out.write(ExtWebHelper.removeEnd(sbr));
		}
		else {
			out.write(sbr.toString());
	        ExtContainerHelper.paintButtons(this.getButtonList().values(), isFill(), this.getTreePanelName(), out);
		}
		
		sbr = new StringBuilder();
		sbr.append("});");
		sbr.append("itemArray.push(").append(getTreePanelName()).append(");");
		sbr.append(getTreePanelName()).append(".on('load',function(){").append(getTreePanelName()).append(".expandAll();});");
		
		//获取粗粒度组件的数据
		TreeBO treeBO = this.getBc();
		if(treeBO != null){ 
			String treeIsland = AppManager.createDataIsland().createTreeDataIsland(this);
			//替换岛中的数据
			sbr.append("updateTreeBO(\"").append(treeIsland).append("\");");
		}
		
		out.write(sbr.toString());
	}
}
