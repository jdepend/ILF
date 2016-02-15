var I18N = {
	COMMON_INIT : "正在初始化,请稍等...",
	COMMON_LOADING : "数据加载中...",
	COMMON_WAITING : "正在提交数据...",
	COMMON_CANCEL : "取消",
	
	DATE_TIMELABEL : "时间",

	CONFIRM_THEMESET : "进行该操作后，工作区域的所有标签页都将关闭，是否继续？",
	CONFIRM_OPERATE : "确认执行该操作？",
	CONFIRM_TIPS : "提示",
	CONFIRM_PROCESS_PROMPT : "是否以后不弹出此类提示框？",
	CONFIRM_TABLE_EDIT : "数据未保存，是否放弃翻页？",
	SYS_SAVECASE_LABEL : "保存为新条件",
	DIALOG_CLOSE : "关闭",
	DIALOG_YES : "保存",
	
	SYS_SAVECASE_TITLE : "保存查询条件",
	SYS_SAVECASE_IPT : "查询条件名称",
	SYS_OPENCASE_TITLE : "使用查询条件，双击选择",
	SYS_SAVECASE_LABEL : "保存为新条件",
	SYS_TABLESETTING_FAILURE : "保存表格设置失败！",
	
	BTN_CHOSE : "选择",
	BTN_DEL : "删除",

	ERR_TABLE_QUERY : "table中不能设置查询按钮！",
	RANGE_REQUIRED : "必填项",
	ERR_TABLE_CLEAR : "table中不能设置清空按钮！",
	ERR_TREE_QUERY : "tree中不能设置查询按钮！",
	ERR_TREE_CLEAR : "tree中不能设置清空按钮！",
	ERR_PAGE_QUERY : "page中不能设置查询按钮！",
	ERR_PAGE_CLEAR : "page中不能设置清空按钮！",
	ERR_TAB_QUERY : "tab中不能设置查询按钮！",
	ERR_TABLE_CHECKED : "开启选择项记忆功能时，必须在table中添加&lt;qeweb:hidden bind='id'/&gt;！",

	MSG_OPERATION_SUCCESS : "操作成功！",
	MSG_OPERATION_FAILURE : "操作失败！",
	MSG_GETRECORD_FAILURE : "获取数据失败！",
	MSG_AJAX_FAILURE : "Ajax请求失败，可能是网络链接失败或BO方法返回了平台不接受的数据。",

	ALERT_CHOOSE_RECORD : "请至少选择一条记录！",
	ALERT_CHOOSE_BEFORE : "请在",
	ALERT_CHOOSE_AFTER : "中至少选择一条记录！",
	ALERT_MODIFIED_RECORD : "没有修改数据！",
	ALERT_MODIFIED_AFTER : "中没有修改数据！",

	//文件上传
	FILE_BROSWER : "浏览…",
	FILE_UPLOAD : "上传",
	FILE_DOWNLOAD : "下载",
	FILE_NAME : "文件名称",
	FILE_CHOISE : "请选择一个文件",
	FILE_UPLOAD_FILE : "上传文件",
	FILE_CLOSE : "关闭",
	FILE_PLEASE_WAIT : "请稍后",
	FILE_UPLOAD_SUCCESS : "上传成功",
	FILE_UPLOAD_FAILURE : "上传失败！",
	FILE_UPLOADING : "文件上传中...",
	FILE_ERROR_POSSIBILITY : "可能是操作超时或执行上传方法是抛出了异常。",
	MULTI_FILE_UPLOAD : "已上传文件",
	MULTI_FILE_TITLE_1 : "操作",
	MULTI_FILE_TITLE_2 : "文件名",
	MULTI_FILE_DELETE : "删除",

	SYSTEM_MSG : "系统消息",
	SYSTEM_EXIT : "退出系统",
	SYSTEM_CURRENT_USER : "当前用户",
	SYSTEM_CURRENT_ORG : "部门",
	SYSTEM_NOTICE : "公告管理",

	RANGE_INPUT_ERROR : "输入内容有误",
	RANGE_LENGTH_LESS_2 :  "个字符",
	RANGE_LENGTH_MUST_1 : "长度必须在",
	RANGE_LENGTH_MUST_2 : "个字符之间",
	RANGE_LENGTH_REMARK : "（汉字和全角输入占2字符）", 
	RANGE_NOT_LESS :  "不能小于",
	RANGE_NOT_LONG :  "不能大于",
	RANGE_MUST_IN : "必须在 ",
	RANGE_MUST_IN_TO : " 到 ",
	RANGE_MUST_IN_END : "之间",
	RANGE_MUST_IN_STRP : "且步长为",
	RANGE_SCALE : "且最大小数位长度为",
	RANGE_NOTIN_RANGE : "不在范围之内",
	RANGE_NAN : "输入内容必须为数字",
	
	MOBILE_MAP_NOT_FIND : "地址未识别！",
	
	HISTORY : "历史对比：",
	
	UX_CLOSE_TAB : "关闭标签页",
	UX_CLOSE_OTHER_TAB : "关闭其它标签页",
	UX_CLOSE_ALL_TAB : "关闭全部标签页",
	ALERT_LOGOUT : "密码修改成功！系统将退出，请使用新密码重新登录",
	
	SYS_MODIFY_PWD : "密码修改",
	SYS_LOCK : "系统锁定",
	SYS_CONFIG : "首选项",
	SYS_PWD : "帐户密码",
	SYS_LOCK_TIP : "提示：系统已锁定，解锁请输入登录帐户密码",
	SYS_UNLOCK : "解锁",
	SYS_RELOGIN : "重新登录",
	SYS_PWD_ERROR_MSG : "密码错误，请重新输入",
	
	EDITOR_HR : "插入水平线",
	EDITOR_IMAGE : "插入图片",
	EDITOR_WORD : "粘帖word文档",
	EDITOR_LINK : "插入超链接",
	EDITOR_TABLE : "插入表格",
	EDITOR_CLEAR : "清空 ",

	showPageBar : function(totalPage, curPage, start, end, totalCount) {
		if(totalPage == 0)
			return "没有可显示的数据";
		else
			return "第" + curPage + "页&nbsp;共 " + totalPage + "页"
		 		+ "&nbsp;&nbsp;&nbsp;&nbsp;显示 " + (~~start + 1) + "-" + end;
		 		+ "&nbsp;&nbsp;共 " + totalCount + "条&nbsp;";
	}
};