package com.qeweb.framework.bc.prop.expend;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.Value;

/**
 * 分段范围.
 * <p>
 * qeweb:expend标签修饰的细粒度组件将"分段"显示为"start"和"end", ExpendRange将用于分别修饰start和end的范围.
 *
 */
public class ExpendRange extends BCRange {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7698636917934639950L;

	private BCRange start;
	private BCRange end;
	
	@Override
	public boolean isIN(Value value) {
		if(value instanceof ExpendValue) {
			ExpendValue expendValue = (ExpendValue) value;
			return getStart().isIN(expendValue.getStart()) && getEnd().isIN(expendValue.getEnd());
		}
		return getStart().isIN(value) && getEnd().isIN(value);
	}

	public BCRange getStart() {
		if(start == null)
			start = new BCRange();
		return start;
	}

	/**
	 * 设置"开始"范围
	 * @param start		start不能是ExpendRange类型
	 */
	public void setStart(BCRange start) {
		this.start = start;
	}

	public BCRange getEnd() {
		if(end == null)
			end = new BCRange();
		return end;
	}

	/**
	 * 设置"结束"范围
	 * @param end		end不能是ExpendRange类型
	 */
	public void setEnd(BCRange end) {
		this.end = end;
	}
}
