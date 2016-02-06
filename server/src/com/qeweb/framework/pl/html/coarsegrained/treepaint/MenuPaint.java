package com.qeweb.framework.pl.html.coarsegrained.treepaint;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.pal.coarsegrained.Menu;
import com.qeweb.framework.pal.coarsegrained.Tree;

/**
 * MenuPaint 供HtmlMenu使用
 *
 */
public class MenuPaint extends TreePaint {
	
	public MenuPaint(Tree tree) {
		super(tree);
	}

	@Override
	protected void createTree(StringBuilder jsSbr, String treeName) {
		String target = ((Menu) getTree()).getTarget();
		jsSbr.append(treeName + ".config.isMenu = true;");
		jsSbr.append(treeName + ".config.target = '" + target + "';");
		
		MenuBO menu = (MenuBO)getTree().getBc();
		menu.create();
		for(TreeBO node : menu.getTree()) {
			MenuBO menuNode = (MenuBO) node;
			addNode(jsSbr, treeName, menuNode);
		}
	}

	private void addNode(StringBuilder jsSbr, String treeName, MenuBO menuNode) {
		jsSbr.append(treeName + ".add(" + menuNode.getId() + "," 
				+ menuNode.getParentId() + "," 
				+ "'" + menuNode.getNodeValue() + "', " 
				+ "'" + menuNode.getPath() + "');");
	}
}
