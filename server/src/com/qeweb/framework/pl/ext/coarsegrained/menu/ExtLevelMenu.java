package com.qeweb.framework.pl.ext.coarsegrained.menu;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.coarsegrained.ExtMenu;

/**
 * 构造Ext类型的菜单（风琴结构菜单） 
 */
public class ExtLevelMenu extends ExtMenu {
	
	public void paint() {
		List<Map<String, Object>> menuList = createTree(this);
		
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getTreePanelName(), "Ext.Panel");
		ExtWebHelper.appendObjectBegin(sbr);
		ExtWebHelper.appendAttr(sbr, "id", this.getId());
		ExtWebHelper.appendAttr(sbr, "name", this.getName());
		ExtWebHelper.appendAttr(sbr, "iconCls", getIcon());
		ExtWebHelper.appendAttr(sbr, "isLevel", true);
		ExtWebHelper.appendAttr(sbr, "header", isHeader());
		ExtWebHelper.appendAttr(sbr, "xtype", "menu");
		if(StringUtils.isNotEmpty(getText())){
			ExtWebHelper.appendAttr(sbr, "title", getText());
			ExtWebHelper.appendAttr(sbr, "collapsible", true);
		}
		ExtWebHelper.appendAttr(sbr, "layout", "accordion");
		ExtWebHelper.appendObjectAttr(sbr, "defaults", "{border : false,frame : false}");
		ExtWebHelper.appendObjectAttr(sbr, "layoutConfig", "{animate : true}");
		sbr.append("items:[").append(creatMenu(menuList)).append("]});");
		
		sbr.append("if(!isUseBorderLayout()) {");
		sbr.append(getTreePanelName()).append(".on('resize',function(){");
		sbr.append(getTreePanelName()).append(".doLayout();").append("});");
		sbr.append("new Ext.Viewport({layout:'fit',items:[").append(getTreePanelName()).append("]}).show();");
		sbr.append("}");
		sbr.append("else  {");
		String treeName = getTreePanelName();
		sbr.append(treeName).append(".region=").append("'west';");
		sbr.append(treeName).append(".split=").append("true;");
		sbr.append(treeName).append(".width=").append("220;");
		sbr.append(treeName).append(".collapsible=").append("true;");
		sbr.append(treeName).append(".autoScroll=").append("true;");
		sbr.append("}");
		
		this.getPageContextInfo().write(sbr.toString());
	}
	
	private String creatMenu(List<Map<String, Object>> menuList) {
	    StringBuilder menu = new StringBuilder();
        String target = ((Menu) this).getTarget();
		for (Map<String, Object> levelmenu : menuList) {
			ExtWebHelper.appendObjectBegin(menu);
			ExtWebHelper.appendAttr(menu, "title", levelmenu.get("text") + "");
			ExtWebHelper.appendAttr(menu, "border", false);
			ExtWebHelper.appendAttr(menu, "firstLevel", true);
			ExtWebHelper.appendAttr(menu, "frame", false);
			ExtWebHelper.appendAttr(menu, "autoScroll", true);
			ExtWebHelper.appendObjectTail(menu, "items", "[new Ext.tree.TreePanel({");
			ExtWebHelper.appendAttr(menu, "border", false);
			ExtWebHelper.appendAttr(menu, "frame", false);
			ExtWebHelper.appendAttr(menu, "target", target);
			menu.append("listeners: {'click' : function(node, event) {;");
			menu.append("navigationMenu(node, event, '" + getTarget() + "');");
			menu.append("}},");
			ExtWebHelper.appendAttr(menu, "rootVisible", false);
			ExtWebHelper.appendObjectAttr(menu, "loader", "new Ext.tree.TreeLoader()");
			String children = JSONArray.fromObject(
					levelmenu.get("children") == null ? levelmenu : levelmenu.get("children")).toString();
			ExtWebHelper.appendObjectTail(menu, "root", "new Ext.tree.AsyncTreeNode({expanded:true,children:" + children + "})");
			menu.append("})]}");
			ExtWebHelper.appendObjectSplit(menu);
		}
		
        return ExtWebHelper.removeEnd(menu);
    }
	
	@Override
	public String getText() {
		//此处仅判断text != null, 如果text == "", 表示强制不使用fieldLabel
		if(text != null)
			text = AppLocalization.getLocalization(text);
		else if(getBc() != null)
			text = getLocalName(); 
		else 
			text = "";
		
		return StringUtils.getUnescapedText(text);
	}
}
