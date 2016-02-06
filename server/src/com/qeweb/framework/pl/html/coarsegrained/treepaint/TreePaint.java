package com.qeweb.framework.pl.html.coarsegrained.treepaint;

import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.JSTemplate;
import com.qeweb.framework.pl.html.HTMLWebHelper;

/**
 * TreePaint 供HtmlTree使用,其paint是一个模板
 *
 */
public class TreePaint {
	//dtree的根节点
	final protected long ROOTID = -1L;
	private Tree tree;
	
	public TreePaint(Tree tree) {
		this.tree = tree;
	}
	
	/**
	 * 使用dtree画出tree结构
	 * @param tree
	 */
	final public void paint() {
		StringBuilder sbr = new StringBuilder();
		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "id", tree.getId());
		HTMLWebHelper.appendEndTag(sbr);
		
		StringBuilder jsSbr = new StringBuilder();
		String treeName = tree.getId();
		jsSbr.append("var "+ treeName + " = new dTree('"+ treeName +"');");
		
		createTree(jsSbr, treeName);
		
		addRoot(jsSbr, treeName, (TreeBO)tree.getBc());
		jsSbr.append("document.write(" + treeName + ");");
		sbr.append(new StringBuilder(JSTemplate.getJsContent(jsSbr.toString())));
		
		HTMLWebHelper.appendEndTag(sbr, "div");
		
		tree.getPageContextInfo().write(sbr.toString());
	}
	
	protected void createTree(StringBuilder jsSbr, String treeName) {
		TreeBO treeBo = (TreeBO)getTree().getBc();
		treeBo.create();
		for(TreeBO node : treeBo.getTree()) {
			addNode(jsSbr, treeName, node);
		}
	}

	private void addNode(StringBuilder jsSbr, String treeName, TreeBO menuNode) {
		jsSbr.append(treeName + ".add(" + menuNode.getId() + "," 
				+ menuNode.getParentId() + "," 
				+ "'" + menuNode.getNodeValue() + "');");
	}

	/**
	 * 添加根节点, 将所有node挂在一个根节点上, 也就是说tree必须有一个根节点
	 * @param jsSbr
	 * @param treeName
	 * @param menuNode
	 */
	private void addRoot(StringBuilder jsSbr, String treeName, TreeBO menuNode) {
		jsSbr.append(treeName + ".add(" + menuNode.getRootId() + "," 
				+ ROOTID + "," 
				+ "'" + menuNode.getLocalName() + "');"); 
	}
	
	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
}
