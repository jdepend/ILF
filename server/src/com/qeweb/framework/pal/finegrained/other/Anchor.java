package com.qeweb.framework.pal.finegrained.other;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.coarsegrained.Table;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 *  超链接控件
 */
abstract public class Anchor extends FinegrainedComponent{
	
	// 设置打开链接方式，_self为自身跳转，_blank为新窗口打开链接，默认为_blank
	private String target;  
	
	public void paint() {
		if(getParent() instanceof Table) 
			paintInTable();
		else
			paintInOther();
	}
	
	@Override
	public String getAlign() {
		return StringUtils.isEmpty(super.getAlign()) ? "center" : super.getAlign();
	}
	
	@Override
	public void free() {
		super.free();
		target = null;
	}
	
	/**
	 * 表格中的超链接
	 */
	abstract public void paintInTable();
	
	/**
	 * 其它组力度组件中的超链接
	 */
	abstract public void paintInOther();

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return StringUtils.isEmpty(target) ? "_blank" : target;
	}
}
