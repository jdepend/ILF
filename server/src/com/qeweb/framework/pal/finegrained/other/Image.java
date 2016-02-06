package com.qeweb.framework.pal.finegrained.other;

import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;

/**
 * 细粒度组件--Image
 * @author alex
 *
 */
abstract public class Image extends FinegrainedComponent {
	@Override
	public String getAlign() {
		return StringUtils.isEmpty(super.getAlign()) ? "center" : super.getAlign();
	}
}
