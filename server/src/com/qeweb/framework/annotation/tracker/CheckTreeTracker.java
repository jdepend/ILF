package com.qeweb.framework.annotation.tracker;

import com.qeweb.framework.annotation.CheckTree;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;

/**
 * CheckTree解释器,  用于标识选择树的选择模式和级联模式
 *
 */
public class CheckTreeTracker {
	
	/**
	 * CheckTree解释器,  用于标识选择树的选择模式和级联模式
	 * @param checkTreeBO
	 */
	static public void track(CheckTreeBO checkTreeBO) {
		CheckTree ct = checkTreeBO.getClass().getAnnotation(CheckTree.class);
		if(ct != null) {
			checkTreeBO.setCheckModel(ct.checkModel());
			checkTreeBO.setAllOptional(ct.allOptional());
			checkTreeBO.setCascade(ct.cascade());
		}
	}
}
