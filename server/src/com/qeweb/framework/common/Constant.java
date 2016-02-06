package com.qeweb.framework.common;

public class Constant {
	//数据岛类型
	final public static String DATAISLANDTYPE_XML = "xml";

	final public static String ENCODING_UTF8 = "utf-8";
	final public static String CONTENTTYPE = "text/html;charset=utf-8";
	final public static String CONTENTTYPE_XML = "text/xml;charset=utf-8";

	final public static String CONTENT_JSON_TYPE = "application/x-json";
	final public static String CONTENT_FILE_TYPE = "application/octet-stream";

	//----------国际化key start---------
	final public static String LOCATION_FORM_QUERY = "form.query";
	final public static String LOCATION_FORM_SAVE = "form.save";
	final public static String LOCATION_FORM_REQUIRED = "form.requriedMeg";
	final public static String LOCATION_MESSAGEBOX_TITLE = "Ext.MessageBox.title";
	//----------国际化key end---------


	//----------session信息 start---------------
	/**
	 * 用户信息
	 */
	final public static String USERBO = "userBO";
	/**
	 * 在线用户
	 */
	final public static String ONLINE_USER = "onLineUserBO";
	/**
	 * 用户个性化设置
	 */
	final public static String PREFERENCE_SET = "preferenceSet";
	/**
	 * 实施人员配置信息
	 */
	final public static String IMP_MEMBER = "projectMember";
	/**
	 * 角色
	 */
	final public static String ROLES = "roles";
	/**
	 * 操作级权限
	 */
	final public static String BUTTONS = "buttons";
	/**
	 * displayType样式类型: html/ext/mobile
	 */
	final public static String SESSION_DISPLAYTYPE = "displayType";
	/**
	 * DDT container
	 */
	final public static String DDT_CONTAINER = "ddtContainer";
	/**
	 * 操作级权限
	 */
	final public static String PERMISSIONS = "permissions";
	/**
	 * topMenu的节点信息
	 */
	final public static String TOPMENU_NODE  = "topMenuNode";
	//----------session信息 end---------------

	//终端离线模式缓存页面的xml目录
	final public static String MOBILE_OFFLINE_DIR = "framework/mobileOffLine";
	//终端离线模式缓存页面的xml文件名
	final public static String MOBILE_OFFLINE_FILE = "mobileOffLine.xml";
}
