package com.qeweb.framework.common.constant;

/**
 * application.properties 相关信息
 * 用于全局配置
 */
public interface ConstantAppProp {

	final public static String PROPFILE_APP = "/prop/application.properties";
	//展现类型
	final public static String PROPFILE_DISPLAYTYPE = "displayType";
	//数据岛类型
	final public static String PROPFILE_DATAISLANDTYPE = "dataIslandType";
	//首页布局样式
	final public static String PROPFILE_LAYOUTTYPE = "layoutType";
	//默认上传路径
	final public static String PROPFILE_FILEUPLOADPATH = "fileUploadPath";
	//限制文件上传大小,系统默认值 该值不能大于struts.xml中的struts.multipart.maxSize
	final public static String PROPFILE_MULTIPART_MAXSIZE = "struts.multipart.maxSize";
	//用户指定限制上传大小值，若不设置使用上面系统默认允许大小
	final public static String PROPFILE_ALLOWMAXSIZE = "allowMaxSize";
	//允许上传文件格式
	final public static String PROPFILE_ALLOWEDTYPE = "allowedType";
	//不允许上传文件格式
	final public static String PROPFILE_NOTALLOWEDTYPE = "notAllowedType";
	//多文件上传，最大可上传文件数
	final public static String MULTIFILE_MAXNUM = "multifile.maxNum";
	//多文件上传，最大可上传文件存储总量
	final public static String MULTIFILE_MAXSIZE = "multifile.maxSize";
	//允许图片类型格式
	final public static String PROPFILE_ALLOWIMG_TYPE = "allowImgType";
	//页面展示类型 true:页面弹出 false:页面跳转
	final public static String PROPFILE_ALLOW_PAGE_POPUP = "isPopup";

	//pl层的类型
	final public static String DISPLAYTYPE_HTML = "html";
	final public static String DISPLAYTYPE_EXT = "ext";
	final public static String DISPLAYTYPE_ANDROID = "Android";
	final public static String DISPLAYTYPE_IPHONE = "iphone";
	final public static String DISPLAYTYPE_PAD = "Android_PAD";

	//首页布局样式: border: ext border layout 布局; frame:frame布局; desktop: 桌面布局
	final public static String LAYOUTTYPE_BORDER = "border";
	final public static String LAYOUTTYPE_FRAME_LEFTMENU = "frame_leftmenu";
	final public static String LAYOUTTYPE_FRAME_TOPMENU = "frame_topmenu";
	final public static String LAYOUTTYPE_DESKTOP = "desktop";
	
	//系统菜单类型
	final public static String MENUTYPE = "menuType";
	final public static String MENUTYPE_SIMPLE = "simple";	//树形结构
	final public static String MENUTYPE_LEVEL = "level";	//风琴结构
	final public static String MENUTYPE_TOP = "top";		//下拉结构
	
	//组件校验风格(qtip,side,under).
	public static final String MSGTARGET = "msgTarget";
	public static final String MSGTARGET_QTIP = "qtip";		//当鼠标移动到控件上时显示提示; 
	public static final String MSGTARGET_UNDER = "under";	//在控件底部显示错误提示; 
	public static final String MSGTARGET_SIDE = "side";		//在控件右边显示一个错误图标,鼠标指向图标时显示错误提示.
	
	//表格级按钮的位置, left:表格左上角; right:表格右上角
	public static final String TBTNALIGH = "tbtnAligh";
	public static final String TBTNALIGH_LEFT = "left";
	public static final String TBTNALIGH_RIGHT = "right";
	
	//表格级按钮的位置, left:行内左方; right:行内右方
	public static final String RBTNALIGH = "rbtnAligh";
	public static final String RBTNALIGH_LEFT = "left";
	public static final String RBTNALIGH_RIGHT = "right";
	
	//国际化文件路径
	final public static String LOCALIZATION_PATH = "localizationPath";
	//国际化文件名
	final public static String LOCALIZATION_FILE_NAME = "localizationFileName";
	// 默认的语言种类
	final public static String DEFAULTLANGUAGE = "defaultLanguage";
	// 默认的bop名称的类型，long或short
	final public static String DEFAULTBOPNAMETYPE = "defaultBopnameType";
	// bop的全名
	final public static String All = "all";
	// bop的简写
	final public static String SHORT = "-short";

	//layout中默认的列数,服务器、手机、PAD
	final public static String LAYOUT_COLUMN_NUMBER = "layout_culumn_number";
	final public static String LAYOUT_COLUMN_NUMBER_MOBILE = "layout_culumn_number_mobile";
	final public static String LAYOUT_COLUMN_NUMBER_PAD = "layout_culumn_number_pad";
	//table显示的行数
	final public static String TABLE_ROW_NUM = "table_row_num";
	//table每行最多显示几个table级按钮
	final public static String TABLE_LAYOUT_CULUMN = "table_layout_culumn";
	//如果在没有配置粗粒度组件布局的情况下, 最后一个组件是表格, 则表格高度撑满剩余区域
	final public static String TABLE_FULL_HEIGHT = "table_full_height";
	//当行级按钮全部隐藏时, 操作列是否自动隐藏
	final public static String TABLE_AUTOHIDE_OPTCOL = "table_autoHide_optCol"; 
	//没有pagebar时表格的默认行数 (值： number or auto)，
	//如果table_nopagebar_row_num=number, 则表格的高度 = 表格行高度 * number, 当表格总行数 > number时, 将出现纵向滚动条;
	//如果table_nopagebar_row_num=auto, 则表格高度 = 表格行高度 * 总行数, 不会出现纵向滚动条.
	final public static String TABLE_NOPAGEBAR_ROW_NUM = "table_nopagebar_row_num";	
	final public static String TABLE_ROW_NUM_AUTO = "auto";
	//table是否支持列拖动
	final public static String TABLE_ENABLECOLUMNMOVE = "enableColumnMove";
	//表格的页数信息是否出现进度条
	final public static String TABLE_PROGRESSBARPAGER = "progressBarPager";

	//组件与组件说明间的分隔符,用户页面展示,  如: 用户名称 LABEL_SPLIT <input type='textField'.....
	final public static String LABEL_SPLIT = "：";
	//提示信息类型
	final public static String TIPS_TYPE = "tipsType";
	final public static String TIPS_TYPE_SIMPLE = "simple";
	final public static String TIPS_TYPE_POPUP = "popup";

	//是否记忆操作(弹出确认信息和提示信息),0 不记忆, 1 记忆
	public static final String OPT_OPTREMEMBER = "optRemember";

	//#提示信息框延迟消失时间
	public static final String SHOW_TIPS_DELAY = "showTipsDelay";
	// Ajax请求超时时间,60000为60秒
	public static final String AJAX_TIMEOUT = "ajaxTimeout";
	// 页面流路径
	public static final String PAGEFLOW_PATH = "pageflowPath";
	// 业务设置项
	public static final String BUSINESSSETTING_PATH = "businessSettingPath";
	//操作时延,在线用户统计使用
	public static final String OPT_TIMEOUT = "optTimeout";

	//项目模式
	//debug=true:  开发模式, 修改页面流文件或DDT配置后无需重启服务
	//bebug=false: 用户模式, 项目交付测试或发布给最终用户时使用
	public static final String DEBUG = "debug";
	//DDT方案对应的应用名,可根据不同项目信息修改appName
	public static final String DDT_APPNAME = "appName";

	//DDT schema方案编码
	public static final String SCHEMA_CODE = "schemaCode";

	//默认排序方案
	public static final String HAS_SORT = "hasSort";
	//使用只读事物的方法前缀
	public static final String READ_ONLY_METHOD_PREFIX = "readOnlyMethodPrefix";
	//地图调用API类型
	public static final String DISPLAY_MAP_TYPE = "displayMapType";
	public static final String DISPLAY_MAP_TYPE_BAIDU = "BAIDU";
	public static final String DISPLAY_MAP_TYPE_GOOGLE = "GOOGLE";

	//默认数值精度
	public static final String NUMBER_SCALE = "numberScale";
	
	//终端应用的“关于”信息
	final public static String MOBILE_ABOUT = "mobileAbout";
	
	//样式风格(blue,deepBlue,gray)
	final public static String THEME_TYPE = "themeType";
	
	//弹出式提示信息框显示状态，0：否,1：是
	final public static String POPUP_STATUS = "popupStatus";
	
	//操作提示信息框显示状态，0：否,1：是
	final public static String CONFIRM_STATUS = "confirmStatus";
	
	//LOGO默认类型，0：使用系统logo,1：用户自定义类型 
	final public static String LOGO_TYPE = "logoType";
	
	//自定义LOGO路径
	final public static String LOGO_IMG_PATH = "logoImgPath";
	
	//底部信息栏内容
	final public static String BOTTOM_MSG = "bottomMsg";
	
	//表格组件单元格内容换行开关
	final public static String CELL_STYLE_NEWLINE = "cellStyleNewline";
	
	//当textfeild为readonly时是否可显示sourceBtn
	final public static String SOURCEBTN_DISPLAY_ON_READONLY = "sourceBtnDisplayOnReadonly";
	
	//是否自动添加"保存查询条件"功能
	final public static String SAVECASE = "saveCase";
	//使用查询条件时是否自动触发查询
	final public static String SAVECASE_AUTO_SEARCH = "autoSearch";
	
	//是否开启"记忆表格"设置功能, 表格设置将记录表格的隐藏列,列宽,列的位置
	final public static String TABLESETTING = "tableSetting";
	
	//弹出页面是否默认带有关闭按钮
	final public static String HASCLOSEBTN = "hasCloseBtn";
}
