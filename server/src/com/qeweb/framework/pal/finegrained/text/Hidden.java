package com.qeweb.framework.pal.finegrained.text;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 细粒度组件--hidden
 */
abstract public class Hidden extends FinegrainedComponent {

	/**
	 * 强制将状态设置为隐藏
	 */
	@Override
	final public void init() {
		BOProperty bop = getBc();
		if(bop != null) {
			bop.init();
			bop.getStatus().setHidden(true);
		}
	}
}
