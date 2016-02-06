package com.qeweb.framework.app.permissions;

/**
 * 检查权限的操作级权限
 */
public interface ICheckOptPermissions {

	/**
	 * 判断是否展示该按钮，如果能够展示（即有操作级权限），返回true
	 * @param btnName		按钮名称
	 * @param sourcePage	页面路径(型如:/WEB-INF/qeweb/demo/demo_1.jsp)
	 * @return
	 */
	boolean hasOperatePermission(String btnName, String sourcePage);
}
