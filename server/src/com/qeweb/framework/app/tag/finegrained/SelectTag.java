package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.Select;


/**
 * 细粒度组件--select标签
 *
 */
public class SelectTag extends FineGrainedTag implements Serializable {
	private static final long serialVersionUID = 312345890234L;
	
	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Select) AppManager.createVC(Select.class);
	}
	
}
