package com.qeweb.demo.load.container.tree.bo;

import com.qeweb.framework.annotation.CheckTree;

/**
 * demo: 树示例. 单选树, 仅叶子节点可选中
 * 路径: 装载-树-选择树
 */
@CheckTree(checkModel="single", allOptional=false)
public class DemoTreeBO_2_radio_singleOpt extends DemoTreeBO_2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6615205383685112018L;
}
