package com.qeweb.framework.common.constant;

/**
 * 标签属性中用到的系统常量
 *
 */
public class ConstantTag {

	/*
	 * table的display属性的表达式 
	 * <qeweb:table id="userTable" bind="userBO" 
			display='username=table:edit, insert, update, view; userNameEn=insert'>
	 */
	final static public String TAG_TABLE_DISPLAY_TABLE = "table";
	final static public String TAG_TABLE_DISPLAY_EDIT = "table:edit";
	final static public String TAG_TABLE_DISPLAY_INSERT = "insert";
	final static public String TAG_TABLE_DISPLAY_UPDATE = "update";
	final static public String TAG_TABLE_DISPLAY_VIEW = "view";
}
