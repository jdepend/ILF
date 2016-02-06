package com.qeweb.framework.pal.finegrained.enumcomp;

import com.qeweb.framework.common.constant.ConstantDataIsland;



/**
 * 细粒度组件--select
 *
 */
abstract public class Select extends EnumFcComponent {
	
	/**
	 * 是否是下拉树
	 * @return
	 */
	public boolean isDeptTree() {
		return getBc().getRange().hasTreeRange();
	}
	
	/**
	 * 画出下拉树
	 */
	abstract public void paintDeptTree();
	
	public String getDeptTreeName() {
		//getDeptTreeName 在定义js变量时使用: var getDeptTreeName, 故此处需要将'-'转换为'_'
		return (getId() + ConstantDataIsland.COMPONENTSPLIT + "dept")
				.replaceAll(ConstantDataIsland.HORIZONTAL_SPLIT, ConstantDataIsland.COMPONENTSPLIT)
				.replaceAll("\\.", ConstantDataIsland.COMPONENTSPLIT);
	}
}
