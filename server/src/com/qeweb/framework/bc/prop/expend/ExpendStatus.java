package com.qeweb.framework.bc.prop.expend;

import com.qeweb.framework.bc.prop.Status;

/**
 * 分段状态.
 * <p>
 * qeweb:expend标签修饰的细粒度组件将"分段"显示为"start"和"end", ExpendRange将用于分别修饰start和end的状态.
 *
 */
public class ExpendStatus extends Status {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7698636917934639950L;

	private Status start;
	private Status end;

	public Status getStart() {
		if(start == null)
			start = new Status();
		return start;
	}

	public void setStart(Status start) {
		this.start = start;
	}

	public Status getEnd() {
		if(end == null) 
			end = new Status();
		return end;
	}

	public void setEnd(Status end) {
		this.end = end;
	}
}
