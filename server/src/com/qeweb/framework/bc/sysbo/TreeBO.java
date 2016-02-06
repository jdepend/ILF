package com.qeweb.framework.bc.sysbo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BusinessObject;

/**
 * TreeBO 仅供树形结构使用
 *
 */
public abstract class TreeBO extends BusinessObject {
	private static final long serialVersionUID = -9085794068487486743L;

	private String nodeValue;	//节点的值
	private long parentId;		//父节点id
	private int sortIndex;		//排序序号
	
	private List<TreeBO> tree = new LinkedList<TreeBO>();
	
	public TreeBO() {
		super();
	}
	
	public TreeBO(long id, long parentId, String value, int sortIndex) {
		super();
		this.setId(id);
		this.parentId = parentId;
		this.nodeValue = value;
		this.sortIndex = sortIndex;
	}
	
	/**
	 * 添加节点
	 * @param node
	 */
	public void add(TreeBO node) {
		for(TreeBO treeBO : tree) {
			if(treeBO.getId() == node.getId()) 
				return;
		}
		tree.add(node);
	}

	/**
	 * 根节点ID, tree将以id == getRootId() 的节点做为一级菜单
	 * @return
	 */
	abstract public long getRootId();
	
	/**
	 * 构造树结构
	 */
	abstract public void create();
	
	public List<TreeBO> getTree() {
		return tree;
	}
	public void setTree(List<TreeBO> tree) {
		this.tree = tree;
	}
	public String getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public int getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	@Override
	public void free() {
		if(getTree() != null) {
			for(TreeBO bo : getTree()) {
				bo.free();
			}
			tree.clear();
		}
		super.free();
	}
}
