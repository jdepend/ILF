package com.qeweb.framework.pl.android.coarsegrained.treepaint;

import java.util.Collections;
import java.util.List;

import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.pal.coarsegrained.Tree;
import com.qeweb.framework.pl.JSTemplate;
import com.qeweb.framework.pl.android.NodeComparator;
import com.qeweb.framework.pl.html.HTMLWebHelper;

public class AndroidTreePaint {
	//dtree的根节点
	final protected long ROOTID = -1L;
	private Tree tree;
	
	public AndroidTreePaint(Tree tree) {
		this.tree = tree;
	}
	
	public void paint() {
		StringBuilder sbr = new StringBuilder();
		HTMLWebHelper.appendStartTag(sbr, "div");
		HTMLWebHelper.appendAttr(sbr, "id", tree.getId());
		HTMLWebHelper.appendEndTag(sbr);
        StringBuilder jsSbr = new StringBuilder();
        String treeName = tree.getId();
        jsSbr.append("var "+ treeName + " = new mobileTree('"+ treeName +"');");        
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
        List<TreeBO> nodeList = treeBo.getTree();
        Collections.sort(nodeList, new NodeComparator());
        for(TreeBO node : nodeList) {
            addNode(jsSbr, treeName, node);
        }
    }

    private void addNode(StringBuilder jsSbr, String treeName, TreeBO node) {
        if(node instanceof MenuBO){
            MenuBO menuNode = (MenuBO) node;
            jsSbr.append(treeName + ".add(" + menuNode.getId() + "," 
                    + menuNode.getParentId() + "," 
                    + "'" + menuNode.getNodeValue() + "',"
                    + "'" + menuNode.getPath() +"');");
        } else{
            jsSbr.append(treeName + ".add(" + node.getId() + "," 
                    + node.getParentId() + "," 
                    + "'" + node.getValue() + "');");
        }
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