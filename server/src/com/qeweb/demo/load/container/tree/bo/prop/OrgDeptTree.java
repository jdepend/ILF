package com.qeweb.demo.load.container.tree.bo.prop;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.prop.TreeRange;

public class OrgDeptTree extends TreeRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7445737045156933995L;

	@Override
	public List<Node> create() {
		List<Node> nodeList = new LinkedList<Node>();
		nodeList.add(new Node("1", "部门-1", getRootValue(), 1));
		nodeList.add(new Node("2", "部门-2", getRootValue(), 2));
		nodeList.add(new Node("11", "部门-1-1", "1", 1));
		nodeList.add(new Node("12", "部门-1-2", "1", 2));
		nodeList.add(new Node("21", "部门-2-1", "2", 1));
		nodeList.add(new Node("22", "部门-2-2", "2", 2));
		
		return nodeList;
	}
	
	@Override
	public String getRootText() {
		return "上级部门";
	}
}
