package com.qeweb.framework.pal.coarsegrained;

import java.util.List;
import java.util.Map;

import com.qeweb.framework.annotation.tracker.CheckTreeTracker;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;


/**
 * 菜单组件, 继承自Tree组件, 绑定CheckTreeBO, 用于生成带选择框（单选或级联多选）的树
 *
 */
public abstract class CheckTree extends Tree {
	
	//是否回填值，即是否需要将映射到opernt页面，用于控制弹出页面的是否画出选择按钮
	private boolean isFill;

	 /**
     * 构造树形结构
     * @param tree
     * @return
     */
    public List<Map<String, Object>> createTree(Tree tree) {
    	if(getData() == null) {
    		CheckTree ct = (CheckTree) tree;
    		CheckTreeTracker.track(ct.getBc());
    	}
    	
    	return super.createTree(tree);
    }
    
	public CheckTreeBO getBc() {
        return (CheckTreeBO) super.getBc();
	}

	public boolean isFill() {
		return isFill;
	}

	public void setFill(boolean isFill) {
		this.isFill = isFill;
	}

	 @Override
	public void free() {
		 isFill = false;

		 super.free();
	}
}
