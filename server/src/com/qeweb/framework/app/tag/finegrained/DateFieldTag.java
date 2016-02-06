package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.other.DateField;

/**
 * 细粒度组件-日期文本框
 *
 */
public class DateFieldTag extends FineGrainedTag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7345009463586539837L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (DateField) AppManager.createVC(DateField.class);
	}
}
