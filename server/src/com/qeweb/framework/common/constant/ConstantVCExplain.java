package com.qeweb.framework.common.constant;

import com.qeweb.framework.pal.coarsegrained.Table;

/**
 * jsp页面的解释xml文件对应的属性
 *
 */
public interface ConstantVCExplain extends ConstantDataIsland {

	final static public String OPERATE_NAME = "name";    	//按钮名称与jsp commandButton name 对应

	final static public String SM_RADIO = Table.SM_RADIO;
	final static public String SM_CHECKBOX = Table.SM_CHECKBOX;
	final static public String SM_EMPTY = Table.SM_EMPTY;

	final static public String VC_WIDTH = "width";				//viewComponent宽度
	final static public String VC_HEIGHT = "height";			//viewComponent高度
	final static public String VC_ROW_NUM = "rownum";			//viewComponent展示所占行数（若不指定为系统默认设置见application.properties）
	final static public String VC_OPT_WIDTH = "optwidth";		//列表操作按钮列宽
	final static public String PAGE_PARENT = "parent";			//弹出窗口的父页面
	
	final static public String TABLE_HASBBAR = "hasBbar";		//grid翻页控件
	final static public String TABLE_PAGESIZE = BOLIST_PAGESIZE;//每页的记录数
	final static public String TABLE_WINDOW = "window";			//表格特定功能窗体（新增、修改、查看）
	final static public String TABLE_WINDOW_WIDTH = "width";	//窗体宽度
	final static public String TABLE_WINDOW_HEIGHT = "height";	//窗体高度
	final static public String TABLE_WINDOW_TYPE = "type";		//窗体类型（insert、update、view）
	final static public String TABLE_AUTOSCROLL = "autoScroll";	//列头是否自适应列表宽度
	/*
	 * 是否开启记忆选择行功能. 如果开启, 将记住每页选择的id, 在翻页后自动勾选.
	 * 该选项仅在sm是checkbox时有效.
	 */
	final static public String TABLE_REMEMBERCHECKED = "rememberChecked";
	
}
