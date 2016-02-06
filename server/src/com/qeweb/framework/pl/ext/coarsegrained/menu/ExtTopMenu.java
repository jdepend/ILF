package com.qeweb.framework.pl.ext.coarsegrained.menu;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pl.ext.ExtWebHelper;
import com.qeweb.framework.pl.ext.coarsegrained.ExtMenu;

/**
 * 
 * 下拉样式的菜单, 通常该菜单在页面顶部使用.
 * 点击菜单项时, 会将菜单项信息存入MsgContext.
 */
public class ExtTopMenu extends ExtMenu {
	
	@Override
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		ExtWebHelper.appendCreateObjectBegin(sbr, getTreePanelName(), "Ext.Panel");
		sbr.append("{");
		ExtWebHelper.appendAttr(sbr, "id", getId());
		ExtWebHelper.appendAttr(sbr, "xtype", "menu");
		ExtWebHelper.appendObjectAttr(sbr, "width", "Ext.getBody().getWidth()");
		ExtWebHelper.appendAttr(sbr, "height", "Ext.getBody().getHeight()");
		ExtWebHelper.appendAttr(sbr, "html", creatMenu());
		ExtWebHelper.appendTail(sbr, "frame", true);
		sbr.append("});");
		sbr.append("itemArray.push(").append(getTreePanelName()).append(");");
		
		getPageContextInfo().write(sbr.toString());
	}
	
	/**
	 * <div class="submenu">
			<ul>
				<li><a href="#" target="iframeLeft" class="selected" onclick=saveTopTreeMsg(this, nodeId)>menu1</a></li>
				<li><a href="#" target="iframeLeft" class="selected" onclick=saveTopTreeMsg(this, nodeId)>menu2</a></li>
			</ul>
		</div>
	 * @return
	 */
	private String creatMenu() {
		StringBuilder menu = new StringBuilder();
		menu.append("<div class=\"submenu\"><ul>");
		List<Map<String, Object>> menuList = createTree(this);
		
	    String target = ((Menu) this).getTarget();
		for (Map<String, Object> node : menuList) {
			menu.append("<li>");
			menu.append("<a href=\"#\" target=\"").append(target).append("\"  class=\"selected\" ")
				.append("onclick=\"saveTopTreeMsg(this, ").append(node.get("id")).append(")\">")
				.append(node.get("text")).append("</a>");
			menu.append("</li>");
		}
		menu.append("</ul></div>");
		
		return menu.toString();
	}
}
