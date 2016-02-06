package com.qeweb.framework.pl.html.coarsegrained.treepaint;

import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.pal.coarsegrained.Tree;

/**
 * CheckTreePaint 供CheckTree使用
 *
 */
public class CheckTreePaint extends TreePaint {
	
	public CheckTreePaint(Tree tree) {
		super(tree);
	}

	@Override
	protected void createTree(StringBuilder jsSbr, String treeName) {
		jsSbr.append(treeName + ".config.folderLinks = true;");
		jsSbr.append(treeName + ".config.check = true;");
		
		CheckTreeBO checkTreeBo = (CheckTreeBO)getTree().getBc();
		checkTreeBo.create();
		for(TreeBO node : checkTreeBo.getTree()) {
			CheckTreeBO checkNode = (CheckTreeBO) node;
			addNode(jsSbr, treeName, checkNode);
		}
	}

	private void addNode(StringBuilder jsSbr, String treeName, CheckTreeBO checkNode) {
		jsSbr.append(treeName + ".add(" + checkNode.getId() + "," 
				+ checkNode.getParentId() + "," 
				+ "'" + checkNode.getNodeValue()+ "', " 
				+ "'" + checkNode.isChecked() + "');");
	}
}
