package com.qeweb.framework.app.tag.finegrained;

import java.io.Serializable;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.text.Spinner;

/**
 * 细粒度组件--数字型微调控件，提供自动键击过滤和数字校验
 *
 */
public class SpinnerTag extends FineGrainedTag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -977088070115813557L;

	@Override
	protected FinegrainedComponent getFCInstance() {
		return (Spinner) AppManager.createVC(Spinner.class);
	}
}
