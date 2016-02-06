package com.qeweb.framework.common.constant;

/**
 * 数据岛相关的常量
 *
 * 注：一些属性如果含有大写字母，将会产生浏览器不兼容的情况
 */
public interface ConstantDataIsland {
	//枚举型组件的值的分隔符
	//如=当组件是checkbox时; 值是所有选中的项; 1&2&3
	final static public String SPLIT_ENUM_VALUE = "&";

	//以下是数据岛xml中的节点;其具体含义请参照<<平台通讯协议.doc>>
	final static public String DATAISLAND = "dataisland";
	final static public String SESSION_TICKET = "sessionTicket";		//蹿session的ticket
	final static public String TOKEN_TICKET = "tokenTicket";			//防止重复刷新的令牌
	final static public String SOURCEPAGE = "sourcepage";				//源页面路径
	final static public String BO = "bo";								//bo标签
	final static public String BO_ID = "id";							//bo标签id属性
	final static public String BO_OPERATIONSTATUS = "operationstatus"; 	//bo标签-操作状态属性
	final static public String BO_CURRENTFIELD = "currentfield"; 		//bo标签-currentfield,触发提交动作的超链接控件对应的bopBind.
	final static public String BO_INDEX = "index"; 						//bo标签-index属性，仅在表格中是使用
	final static public String BO_ROWOPT_STATUS = "rowoptstatus";		//bo标签-rowoptstatus属性,仅在表格中使用,标识本行数据是否设置了行级按钮的状态
	final static public String DEL_BO_IDS = "delboids";					//用于动态新增/删除行时，保存删除的bolist的id
	final static public String BOP = "bop"; 							//bop标签
	final static public String OPERATE = "operate";						//operate标签
	final static public String OPERATE_ID = "id";						//operate标签id属性
	final static public String OPERATE_METHOD = "method";				//operate标签method属性，表示按钮绑定的bo方法
	final static public String OPERATE_JS_BEFORE = "jsbefore";			//operate标签jsbefore属性，表示按钮对应的js操作,在执行bo的方法前执行
	final static public String OPERATE_JS_AFTER = "jsafter";			//operate标签jsafter属性，表示按钮对应的js操作,在执行bo的方法后,页面流跳转前执行
	public static final String OPERATE_EXPEND = "expend";				//operate标签expend属性，表示按钮被<qeweb:expend>修饰
	public static final String OPERATE_TEXT = "text";					//operate标签text属性，表示按钮的名称
	public static final String OPERATE_IDX = "idx";						//如果有改属性，表示按钮是行级按钮，idx表示当前第idx行
	public static final String OPERATE_HASCONFIRM = "hasconfirm";		//标记按钮是否需要前置的确认框，用于注册按钮事件，控制确认框弹出。
	public static final String OPERATE_CONFIRMMSG = "confirmmsg";		//confirm的提示信息
	public static final String OPERATE_HASMSG = "hasmsg";				//标记按钮是否需要操作结果提示，用于注册按钮事件，控制提示框弹出。
	public static final String OPERATE_ISPOPUPPAGE = "ispopup";			//页面跳转是否以弹出框形式展现
	public static final String OPERATE_SAVEMOD = "savemod";				//级按钮的保存模式
	public static final String OPERATE_OPTMOD = "optmod";				//按钮绑定方法的保存模式
	public static final String OPERATE_ISFILL = "isfill"; 				//是否有回填值操作
	public static final String OPERATE_NOTSUBMIT = "notsubmit"; 		//是否不需要提交校验
	public static final String OPERATE_NOTFRESH = "notfresh"; 			//操作成功后是否不需要刷新控件
	public static final String SOURCE = "source";						//source标签, <operate>中包含<source>，则表示该按钮需要弹出页面
	public static final String SOURCE_BINDBO = "bindBo";				//source标签的bindBo属性
	public static final String SOURCE_BINDBOP = "bindBop";				//source标签的bindBop属性
	public static final String SOURCE_OPERATE = "operate";				//source标签的operate属性, 按钮的操作
	public static final String SOURCE_SM = "sm";						//source标签的sm属性
	public static final String SOURCE_TEXT = "text";					//source标签的text属性
	final static public String BOLIST = "bolist"; 						//bolist标签
	final static public String BOLIST_PAGESIZE = "pagesize"; 			//表格每页显示的记录数
	final static public String BOLIST_CHECKEDIDS = "checkedids";		//table翻页时用于记录曾经勾选过id
	final static public String BOLIST_HEIGHT = "highLight";				//table中的某一单元格设置了高亮显示
	final static public String BOLIST_BOT = "bot";						//boList的bot节点, 作为其关联粗粒度组件的查询条件, 在触发关联时创建
	final static public String SELECTIONMODEL = "selectionmodel"; 		//Table选择类型（单选、多选）
	final static public String BOP_RANGE = "range"; 					//范围标签
	final static public String BOP_LABEL = "label";
	final static public String BOP_DISPLAY = "display";					//bop 的display属性
	final static public String BOP_RANGE_REQUIRED = "required"; 		//范围标签-是否必填
	final static public String BOP_RANGE_MINLENGTH = "minlength"; 		//范围标签-最小长度属性
	final static public String BOP_RANGE_MAXLENGTH = "maxlength"; 		//范围标签-最大长度属性
	final static public String BOP_RANGE_ENUM = "enum"; 				//枚举型标签
	final static public String BOP_RANGE_ITEM = "item";					//枚举项标签
	final static public String BOP_RANGE_ITEM_VALUE = "value"; 			//枚举项标签-值属性
	final static public String BOP_RANGE_ITEM_LABEL = BOP_LABEL; 		//枚举项标签-展示信息属性
	final static public String BOP_RANGE_SEQUENCE = "sequence"; 		//连续型标签
	final static public String BOP_RANGE_MAX = "max"; 					//连续型标签-最大值标签
	final static public String BOP_RANGE_MIN = "min"; 					//连续型标签-最小值标签
	final static public String BOP_RANGE_STEP = "step"; 				//连续型标签-步进值标签
	final static public String BOP_RANGE_LOGIC = "logic"; 				//逻辑型标签
	final static public String BOP_RANGE_RULE = "rule"; 				//规则属性
	final static public String BOP_RANGE_AND = "AND"; 					//逻辑型标签-"与"属性值
	final static public String BOP_RANGE_OR = "OR"; 					//逻辑型标签-"或"属性值
	final static public String BOP_RANGE_NOT = "NOT"; 					//逻辑型标签-"非"属性值
	final static public String BOP_VALUE = "value"; 					//值标签
	final static public String BOP_VALUE_EXPEND = "expend"; 			//值标签-扩展属性
	final static public String BOP_DATA = "data"; 						//data标签
	final static public String BOP_DATA_URL = "url"; 					//data标签属性url
	final static public String BOP_DATA_UPLOAD_NUM = "uploadNum"; 		//data标签属性uploadNum
	final static public String BOP_DATA_UPLOAD_SIZE = "uploadSize"; 	//data标签属性uploadSize
	final static public String BOP_ISRELATE = "isrelate"; 				//bop标签-是否存在关联属性
	final static public String BOP_CLASS = "class";						//bop的class名称,如果bop的class和bo构造函数中的bopClass不同,则需要设置BOP_CLASS
	final static public String BOP_ISCONRELATE = "isconrelate"; 		//bop标签-是否存在与粗粒度组件关联与属性
	final static public String BOP_ISTIGGER = "istigger";				//bop标签-BOP改变时是否能触发粗粒度组件关联
	final static public String BOP_HIGHLIGHT = "highlight"; 			//bop高亮设置
	final static public String BOP_JS = "js";							//bop的js属性，表示bop使用了自定义js方法
	final static public String BOP_ISFILE = "isfile";					//bop的是否为文件
	final static public String BOP_TERMINAL_LOCATION = "tm_location";	//终端bop-地址信息
	final static public String BOP_TERMINAL_PIC = "tm_pic";				//终端bop-照片信息
	final static public String BIND = "bind"; 							//bop标签-绑定属性
	final static public String STATUS = "status"; 						//状态标签
	final static public String STATUS_DISABLE = "disable"; 				//状态标签-是否不可交互属性
	final static public String STATUS_HIDDEN = "hidden"; 				//状态标签-是否隐藏属性
	final static public String STATUS_READONLY = "readonly"; 			//状态标签-是否只读属性
	final static public String PAGINATION = "pagination"; 				//pagination标签
	final static public String PAGINATION_LIMIT = "limit"; 				//pagination标签-limit属性
	final static public String PAGINATION_START = "start"; 				//pagination标签-start属性
	final static public String PAGINATION_TOTAL = "total"; 				//pagination标签-total属性
	final static public String COLUMNINFO = "columninfo"; 				//columnInfo标签
	final static public String COLUMNINFO_TABLEFIELDS = "tablefields";	//columnInfo标签的tableFields属性，表示哪些列需要展示
	final static public String COLUMN = "column"; 						//column标签
	final static public String TREE = "tree";							//tree标签
	final static public String TREE_ID = "id";
	final static public String TREE_CHECKED = "checked";				//树型数据岛节点的checked属性
	final static public String TREE_BO = "bo";
	final static public String TREE_BO_ID = "id";
	final static public String COLUMN_TYPE = "type";					//column的type属性，标识需要特别渲染的列类型
	final static public String COLUMN_TYPE_FILE = "file";				//column的type属性值，文件类型
	final static public String COLUMN_TYPE_IMG = "img";					//column的type属性值，图片类型
	final static public String COLUMN_TYPE_IMG_ISADAPTIVE = "adaptive";	//如果isAdaptive==true, 将根据图片的实际大小展示图片;否则根据设定的height和weight展示
	final static public String COLUMN_TYPE_IMG_HEIGHT = "height";		//图片的高度
	final static public String COLUMN_TYPE_IMG_WIDTH = "width";			//图片的宽度
	final static public String COLUMN_TARGET = "target";				//column的target属性值，超链接类型时的弹出类型
	final static public String COLUMN_DEFAULT_VALUE = "default";		//column的defaultValue属性，表示动态新增行时的默认值
	//分隔符常量
	final static public String SPLIT_LINE = ConstantSplit.GA_PARAM_SPLIT; 	//横线分隔符
	final static public String SPLIT_POINT = ConstantSplit.BIND_SPLIT; 		//点分隔符

	final static public String PAGE = "page";							//page标签
	final static public String PAGE_ONLOAD = "onload";					//page标签的onload属性，在页面加载后将执行onload中的方法
	final static public String PAGE_SOURCE = "source";					//page标签-资源属性
	public static final String TIPS_TYPE = "tipstype"; 					//提示信息框类型
	public static final String TIPS_DELAY = "tipsdelay"; 				//非弹出式提示框延时消失时间
	public static final String TIPS_DISPLAY = "tipdisplay";				//弹出式提示框显示状态
	public static final String CONFIRM_DISPLAY = "confirmdisplay";		//弹出式提示框显示状态
	final static public String BTNNAME = "btnName";						//page标签-资源属性

	final static public String STATEMACHINE_INIT = "init";				//Bo的状态机，init不做任何操作

	//BO关联
	final static public String BORELATION = "borelation";
	final static public String GROUP = "group";							//粗粒度关联group标签
	final static public String GROUPNAME = "groupname";                   //细粒度关联groupName属性
	final static public String GROUP_SOURCE = "source";					//粗粒度关联group标签source属性
	final static public String GROUP_TARGET = "target";					//粗粒度关联group标签targer标签
	
	//粗粒度组件和细粒度组件间的分隔符,细粒度组件的id为:  containerId-containerBind-bind
	final public static String COMPONENTSPLIT = "_";
	final public static String HORIZONTAL_SPLIT = "-";
	//当细粒度组件用expend标签修饰时, 细粒度组件的id为: containerId-containerBind-bind~min 和 containerId-containerBind-bind~max
	//@see com.qeweb.framework.bc.BusinessComponent.ViewComponent.id
	final public static String EXPEND_SPLIT = "~";
	final public static String EXPEND_MIN = "min";
	final public static String EXPEND_MAX = "max";
}
