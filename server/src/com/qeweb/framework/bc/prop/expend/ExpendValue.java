package com.qeweb.framework.bc.prop.expend;

import com.qeweb.framework.bc.prop.Value;

/**
 * 分段值.
 * <p>
 * qeweb:expend标签修饰的细粒度组件将"分段"显示为"start"和"end", ExpendValue将用于分别存储start和end的值.
 *
 */
public class ExpendValue extends Value {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7698636917934639950L;

	private Value start;
	private Value end;
	
	public Value getStart() {
		if(start == null)
			start = new Value();
		return start;
	}

	public void setStart(Value start) {
		this.start = start;
	}

	public Value getEnd() {
		if(end == null)
			end = new Value();
		return end;
	}

	public void setEnd(Value end) {
		this.end = end;
	}
}
