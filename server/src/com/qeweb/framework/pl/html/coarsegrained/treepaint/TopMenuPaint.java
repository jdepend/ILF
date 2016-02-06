package com.qeweb.framework.pl.html.coarsegrained.treepaint;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pal.coarsegrained.Tree;

public class TopMenuPaint {
	
	private Tree tree;
	
	public TopMenuPaint(Tree tree) {
		this.tree = tree;
	}

	public void paint() {
		StringBuilder menu = new StringBuilder();
		menu.append("<div class=\"submenu\"><ul>");
		List<Map<String, Object>> menuList = tree.createTree(tree);
		
	    String target = ((Menu) tree).getTarget();
		for (Map<String, Object> node : menuList) {
			menu.append("<li>");
			menu.append("<a href=\"#\" target=\"").append(target).append("\" class=\"default\" ")
				.append("onclick=\"saveTopTreeMsg(this, ").append(node.get("id")).append(")\">")
				.append(node.get("text")).append("</a>");
			menu.append("</li>");
		}
		menu.append("</ul></div>");
		
		tree.getPageContextInfo().write(menu.toString());
	}
	
	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
}
