package com.qeweb.framework.bc.sysbop;

import com.qeweb.framework.bc.BOProperty;

/**
 * 
 * 为按钮指定 NOSortBOP后,点击列头不进行排序
 */
public class NOSortBOP extends BOProperty {
	static final long serialVersionUID = -9016010929792352485L;

	@Override
	public Boolean isSortAble() {
		return false;
	}
}
