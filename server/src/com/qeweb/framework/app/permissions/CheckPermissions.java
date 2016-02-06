package com.qeweb.framework.app.permissions;

import com.qeweb.framework.common.SpringConstant;


/**
 * 检查权限的操作级权限
 */
public class CheckPermissions {

	//在applicationContext-bo-sysmgt.xml中配置
	private static ICheckOptPermissions checkOptPermissions;
	
	/**
	 * 判断是否展示该按钮，如果能够展示（即有操作级权限），返回true
	 * @param btnName
	 * @param sourcePage
	 * @return
	 */
	final static public boolean hasOperatePermission(String btnName, String sourcePage) {
		return getCheckOptPermissions().hasOperatePermission(btnName, sourcePage);
	}
	
	public static ICheckOptPermissions getCheckOptPermissions() {
		if(checkOptPermissions == null)
			checkOptPermissions = (ICheckOptPermissions)SpringConstant.getCTX().getBean("checkOptPermissions");
		return checkOptPermissions;
	}
}