/**
 * 平台的数据岛.
 * 数据岛的展示形式是Xml dom, this 修改集遍历数据岛dom的操作.
 * 注：一些属性如果含有大写字母，将会产生浏览器不兼容的情况
 */
var DISLAND = {
	//以下是数据岛常量, 即数据岛dom的节点标识或属性, 其具体含义请参照<<平台通讯协议.doc>>
	hiddenId : "dataIsland",				//数据岛在id为dataIsland的隐藏域中
	dataIsland : "dataisland", 				//数据岛标签
	SOURCEPAGE : "sourcepage",				//源页面路径
	SESSIONTICKET : "sessionTicket",		//蹿session的ticket
	TOKEN_TICKET : "tokenTicket",			//防止重复刷新的令牌
	BIND : "bind", 							//绑定属性
	STATUS : "status", 						//状态标签
	STATUS_DISABLE : "disable", 			//状态标签-是否不可交互属性
	STATUS_HIDDEN : "hidden", 				//状态标签-是否隐藏属性
	STATUS_READONLY : "readonly", 			//状态标签-是否只读属性
	BO : "bo",								//bo标签
	BO_ID : "id",							//bo标签id属性
	BO_INDEX : "index",						//table中bo的行号
	BO_ROWOPT_STATUS : "rowoptstatus",		//bo标签-rowoptstatus属性,仅在表格中使用,标识本行数据是否设置了行级按钮的状态
	BO_OPERATIONSTATUS : "operationstatus", //bo标签-操作状态属性
	BO_CURRENTFIELD : "currentfield", 		//bo标签-currentfield,触发提交动作的超链接控件对应的bopBind.
	BO_OPERATIONSTATUS_INIT : "init",		//bo标签-操作状态, init指没有改变操作状态
	BOLIST_CHECKEDIDS : "checkedids",		//boList的checkedids标签，table翻页时用于记录曾经勾选过的id
	BOLIST_BOT : "bot",						//boList的bot节点, 作为其关联粗粒度组件的查询条件, 在触发关联时创建
	BOLIST_HEIGHT : "highLight",			//table中的某一单元格设置了高亮显示
	DEL_BO_IDS : "delboids",				//用于动态新增/删除行时，保存删除的bolist的id
	OPERATE : "operate",					//operate标签
	OPERATE_ID : "id",						//operate标签id属性
	OPERATE_METHOD : "method",				//operate标签method属性，表示按钮绑定的bo方法
	OPERATE_JS_BEFORE : "jsbefore",			//operate标签jsbefore属性，表示按钮对应的js操作,在执行bo的方法前执行
	OPERATE_JS_AFTER : "jsafter",			//operate标签jsafter属性，表示按钮对应的js操作,在执行bo的方法后,页面流跳转前执行
	OPERATE_SOURCE_ALL : "all",				//operate标签source属性, all表示获取页面全部数据。
	OPERATE_EXPEND : "expend",				//operate标签expend属性，表示按钮被<qeweb:expend>修饰
	OPERATE_TEXT : "text",					//operate标签text属性，表示按钮的名称
	OPERATE_SAVEMOD : "savemod",			//按钮时校验模式模式, 可选值:modify, select @see CommandButton.java
	OPERATE_OPTMOD : "optmod",				//按钮绑定方法的保存模式
	OPERATE_ISFILL : "isfill",              //是否要做回填值操作
	OPERATE_HASCONFIRM : "hasconfirm",		//operate标签是否需要Confirm
	OPERATE_CONFIRMMSG : "confirmmsg",		//confirm的提示信息
	OPERATE_HASMSG : "hasmsg",				//operate标签后置提示
	OPERATE_IDX : "idx",					//如果有改属性，表示按钮是行级按钮，idx表示当前第idx行
	OPERATE_NOTSUBMIT : "notsubmit",		//是否不需要提交校验, 返回按钮或其它不绑定操作的按钮无需提交校验
	OPERATE_NOTFRESH : "notfresh", 			//操作成功后是否不需要刷新控件
	SOURCE : "source",						//source标签, <operate>中包含<source>，则表示该按钮需要弹出页面
	SOURCE_BINDBO : "bindBo",				//source标签的bindBo属性
	SOURCE_BINDBOP : "bindBop",				//source标签的bindBop属性
	SOURCE_OPERATE : "operate",				//source标签的operate属性, 按钮的操作
	SOURCE_SM : "sm",						//source标签的sm属性
	SOURCE_TEXT : "text",					//source标签的text属性
	HIDDENFORM : "-hiddenForm",				//有隐藏form时，id加上该后缀与table中的按钮区别
	OPERATE_ISPOPUPPAGE : "ispopup",		//页面跳转是否以弹出框形式展现
	BOLIST : "bolist", 						//bolist标签
	BOLIST_ID : "id",						//bolist的id属性
	BOP : "bop", 							//bop标签
	BOP_ID : "id",							//bop标签id属性
	BOP_MAX : "max",						//bop的最大值
	BOP_MIN : "min",						//bop的最小值
	BOP_RANGE : "range", 					//范围标签
	BOP_RANGE_REQUIRED : "required", 		//范围标签-是否必填
	BOP_RANGE_MINLENGTH : "minlength", 		//范围标签-最小长度属性
	BOP_RANGE_MAXLENGTH : "maxlength", 		//范围标签-最大长度属性
	BOP_RANGE_ENUM : "enum", 				//枚举型标签
	BOP_RANGE_ITEM : "item",				//枚举项标签
	BOP_RANGE_ITEM_VALUE : "value", 		//枚举项标签-值属性
	BOP_RANGE_ITEM_LABEL : "label", 		//枚举项标签-展示信息属性
	BOP_RANGE_SEQUENCE : "sequence", 		//连续型标签
	BOP_RANGE_MAX : "max", 					//连续型标签-最大值标签
	BOP_RANGE_MIN : "min", 					//连续型标签-最小值标签
	BOP_RANGE_STEP : "step", 				//连续型标签-步进值标签
	BOP_RANGE_SCALE : "scale",				//连续型标签-小数精度标签
	BOP_RANGE_LOGIC : "logic", 				//逻辑型标签
	BOP_RANGE_LOGIC_CLASS : "rangeClass", 	//逻辑型标签-range类名
	BOP_RANGE_RULE : "rule", 				//规则属性
	BOP_RANGE_AND : "AND", 					//逻辑型标签-"与"属性值
	BOP_RANGE_OR : "OR", 					//逻辑型标签-"或"属性值
	BOP_RANGE_NOT : "NOT", 					//逻辑型标签-"非"属性值
	BOP_VALUE : "value", 					//值标签
	BOP_VALUE_DISPLAY : "display", 			//值标签
	BOP_VALUE_EXPEND : "expend", 			//值标签-扩展属性,表示细粒度对应的标签被<qeweb:expend>修饰	
	BOP_DATA : "data", 						//data标签
	BOP_DATA_URL : "url", 					//data标签属性url
	BOP_DATA_UPLOAD_NUM : "uploadNum", 		//data标签属性uploadNum，多附件上传时存储当前已上传附件数
	BOP_DATA_UPLOAD_SIZE : "uploadSize", 	//data标签属性uploadSize，多附件上传时存储当前已上传各文件的大小,以逗号分隔
	BOP_JS : "js",							//bop的js属性，表示bop使用了自定义js方法
	BOP_ISRELATE : "isrelate", 				//bop标签-是否存在关联属性
	BOP_ISCONRELATE : "isconrelate", 		//bop标签-是否存在与粗粒度组件的关联属性
	BOP_ISTIGGER : "istigger",				//bop标签-BOP改变时是否能触发粗粒度组件关联
	BOP_HIGHLIGHT : "highlight",			//bop高亮设置
	BOP_ISFILE : "isfile",					//bop的是否为文件
	BOP_TERMINAL_LOCATION : 'tm_location',	//终端bop-地址信息
	BOP_TERMINAL_PIC : 'tm_pic',			//终端bop-照片信息
	TREE : "tree",							//tree标签
	TREE_CHILDREN_ID : "id",
	TREE_BO : "bo",
	TREE_CHECKED : "checked",				//tree节点被选中
	GROUPNAME : "groupname",				//对应细粒度组件和控制组件的groupName属性, 应用于组合控件
	PAGINATION : "pagination", 				//pagination标签
	PAGINATION_LIMIT : "limit", 			//pagination标签-limit属性
	PAGINATION_START : "start", 			//pagination标签-start属性
	PAGINATION_TOTAL : "total", 			//pagination标签-total属性
	COLUMNINFO : "columninfo", 				//columnInfo标签
	COLUMNINFO_TABLEFIELDS : "tablefields",	//columnInfo标签的tableFields属性，表示哪些列需要展示
	COLUMNINFO_COLUMN : "column", 			//columnInfo标签
	COLUMNINFO_SELECTIONMODEL : "selectionmodel", 	//columnInfo标签 选择类型 单选 多选
	COLUMN_TYPE : "type", 					//column标签-type属性，表示特殊渲染列类型属性
	COLUMN_TYPE_FILE : "file",				//column的type属性值，文件类型
	COLUMN_TYPE_IMG : "img",				//column的type属性值，图片类型
	COLUMN_TYPE_IMG_ISADAPTIVE : "adaptive",//如果isAdaptive==true, 将根据图片的实际大小展示图片;否则根据设定的height和weight展示
	COLUMN_TYPE_IMG_HEIGHT : "height",		//图片的高度
	COLUMN_TYPE_IMG_WIDTH : "width",		//图片的宽度
	COLUMN_DEFAULT_VALUE : "default", 		//column的defaultValue属性，标识动态新增行时的默认值
	COLUMN_TARGET : "target",				//column的target属性值，超链接类型时的弹出类型

	//分隔符常量
	SPLIT_LINE : "-", 						//横线分隔符,用于粗/细/控制组件的Id和Name
	SPLIT_EXPEND : "~",						//用于被<qeweb:extend>修饰的细粒度组件, ~max和~min表示细粒度组件的范围区间
	SPLIT_POINT : ".", 						//点分隔符
	SPLIT_COMMA : ",",						//逗号分隔符
	SPLIT_TREBLE_BACKSLASH : "///",			//三反斜杠隔符

	//粗粒度关联
	GROUPSTRING : "groupString",			//粗粒度关联dom的id
	GROUP : "group",						//粗粒度关联group标签
	GROUP_SOURCE : "source",				//粗粒度关联group标签source属性
	GROUP_TARGET : "target",				//粗粒度关联group标签targer标签

	PAGE : "page",							//page标签
	PAGE_ONLOAD : "onload",					//page标签的onload属性，在页面加载后将执行onload中的方法

	TIPS_TYPE : "tipstype",					//提示信息框类型
	TIPS_TYPE_SIMPLE : "simple",			//提示信息框类型,simple表示提示信息将以文字形式显示在页面上方
	TIPS_TYPE_POPUP : "popup",				//提示信息框类型,popup表示提示信息将alert形式显示
	TIPS_DELAY : "tipsdelay",				//非弹出式提示框延时消失时间
	TIPS_DELAY_DEF : "10",					//提示信息默认消失时间(秒)
	TIPS_DISPLAY : "tipdisplay",			//弹出式提示框显示状态
	TIPS_DISPLAY_NO : "0",					//弹出式提示框显示状态,TIPS_DISPLAY_NO表示不显示"操作成功"提示信息
	CONFIRM_DISPLAY : "confirmdisplay",		//操作提示显示状态
	CONFIRM_DISPLAY_NO : "0",				//操作提示显示状态,CONFIRM_DISPLAY_NO表示不弹出confirm

	getDataIsland : function() {
		return getDataIsland().find(this.dataIsland).slice(0);
	},

	getSourcepage : function() {
		return this.getDataIsland().attr(this.SOURCEPAGE);
	},
	
	/**
	 * 移除bo或BoList
	 * @param boId
	 */
	removeBO : function(boId){
		this.getDataIsland().find("#" + boId).remove();
	},

	/**
	 * 添加bo
	 * @param bo
	 */
	addBO : function(bo){
		//兼容ie ，ie下xml格式字符串使用$()转xml对象不成功
		//if($.browser.msie)
        if(window.ActiveXObject)
			this.getDataIsland().append(bo);
		else
			this.getDataIsland().append($(xmlToString(bo)));
	},

	/**
	 * 替换数据岛中的BO
	 * @param boDom
	 */
	updateBO : function(boDom) {
		var boId = boDom.attr(this.BO_ID);
		this.removeBO(boId);
		this.addBO(boDom);
	},

	/**
	 * 为bopDom的value节点设置内容. bopDom 格式: <bop .... > <value display=''>content</value>
	 * </bop>
	 */
	setBOPValue : function(bopDom, content) {
		var valueDom = bopDom.children(this.BOP_VALUE);
		if(valueDom.length > 1){
			//是范围型，需要分别注入min与max的值
			var ranageValue = content.split(',');
			//如果不是两个值时不能使用
			if(ranageValue.length != 2)
				return;
			//min
			valueDom.slice(0,1).text(ranageValue[0]);
			//max
		  	valueDom.slice(1,2).text(ranageValue[1]);
		}
		else
			valueDom.text(content);
	},

	/**
	 * 获取bopDom的范围
	 * @returns rangeDom
	 */
	range_getRangeDom : function(bopDom) {
		return bopDom.children(this.BOP_RANGE);
	},

	/**
	 * 获取bopDom的范围
	 * @returns rangeList
	 */
	range_getRangeList : function(bopDom) {
		return this.range_getRangeDom(bopDom).slice(0).children();
	},

	/**
	 * 获取bopDom的枚举型范围, 一个Bop可能包含多个枚举型范围
	 * @returns 枚举型范围[]
	 */
	range_getEnumRange : function(rangeDom) {
		var result = [];

		rangeDom.each(function() {
			if($(this).is(DISLAND.BOP_RANGE_ENUM))
				result.push($(this));
		});
		return result;
	},

	/**
	 * 获取枚举型范围的map
	 * key: value   value:label
	 */
	range_getEnumRangeMap : function(enumDoms) {
		var result = [];
		if(enumDoms == null || enumDoms.length == 0)
			return result;

		if(jQuery.isArray(enumDoms)) {
			for(var i = 0, length = enumDoms.length; i < length; i++) {
				enumDoms[i].children().each(function() {
					result[$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE)] = $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);
				});
			}
		}
		else {
			enumDoms.children().each(function() {
				result[$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE)] = $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);
			});
		}

		return result;
	},

	/**
	 * 获取按钮绑定的方法名
	 * @param operateDom 数据岛中operate节点的dom
	 * @returns 按钮绑定的方法名
	 */
	operate_BOMethod : function(operateDom) {
		//按钮sourceDom,即<qeweb:source>的数据岛
		var sourceDom = this.getSourceOptDom(operateDom);
		if(sourceDom)
			return sourceDom.attr(this.SOURCE_OPERATE) || BoFinalMethod.SYSTEMP;
		else
			return operateDom.attr(this.OPERATE_METHOD) || BoFinalMethod.SYSTEMP;
	},
	
	/**
	 * 为operateDom设置method
	 */
	operate_setBOMethod : function(operateDom, method) {
		var sourceDom = this.getSourceOptDom(operateDom);
		if(sourceDom)
			sourceDom.attr(this.SOURCE_OPERATE, method);
		else
			operateDom.attr(this.OPERATE_METHOD, method);
	},
	
	/**
	 * 为operateDom设置saveMod
	 */
	operate_setSaveMod : function(operateDom, saveMod) {
		var sourceDom = this.getSourceOptDom(operateDom);
		if(sourceDom)
			sourceDom.attr(this.OPERATE_SAVEMOD, saveMod);
		else
			operateDom.attr(this.OPERATE_SAVEMOD, saveMod);
	},

	/**
	 * 获取和按钮同组的组件的数据岛Dom
	 * @param {} groupName  按钮的group属性名
	 * @param {} boId
	 */
	getFCGroupDoms : function(groupName, boId) {
		var groupDoms = [];

		this.getDom(boId).children(DISLAND.BOP + "[" + this.GROUPNAME + "='" + groupName + "']").each(function() {
			groupDoms.push($(this));
		});

		return groupDoms;
	},

	/**
	 * 获取boDom关联的BO
	 * return Array[]，关联BoId
	 */
	getRelationBo : function(boDom) {
	  	var relations = [];
	  	var sourceBoId = boDom.attr(this.BO_ID);
	  	var relationsGroup = XMLDomFactory.getInstance($("#" + this.GROUPSTRING).val());

	  	if(!!relationsGroup) {
		  	//查询所有与boDom关联的组件
	  		relationsGroup.find(this.GROUP + "[" + this.GROUP_SOURCE + "='" + sourceBoId + "']" ).each(function(){
		  		var relation = $(this);
		  		relation.children(DISLAND.GROUP_TARGET).each(function(){
		  			relations.push($(this).text());
		  		});
		  	});
	  	}

	  	return relations;
	},

	/**
	 * 获取关联boDom的源BOID
	 */
	getSourceBOID : function(targetBoId) {
		var result = "";
		var group = XMLDomFactory.getInstance($('#' + this.GROUPSTRING).val());
		if(!group)
			return result;

		group.find(this.GROUP).each(function(){
			var source = $(this);
			source.children(DISLAND.GROUP_TARGET).each(function(){
				if($(this).text() == targetBoId){
					result = source.attr(DISLAND.GROUP_SOURCE);
					return true;
				}
			});
		});

		return result;
	},

	/**
	 * 获取bop绑定的js方法
	 */
	getBOPJS : function(bopDom) {
		return bopDom.attr(this.BOP_JS);
	},

	/**
	 * 根据id获取数据岛中的Dom节点.
	 */
	getDom : function(id){
		return this.getDataIsland().find("#" + id);
	},

	/**
	 * 根据id获取数据岛中的BODom节点
	 */
	getBODom : function(id) {
		return this.getDataIsland().find(this.BO + "[" + this.BO_ID + "='" + id + "']");
	},

	/**
	 * 根据id获取数据岛中的BOListDom节点
	 */
	getBOListDom : function(id) {
		return this.getDataIsland().find(this.BOLIST + "[" + this.BO_ID + "='" + id + "']");
	},

	/**
	 * 获取table增/改/查看弹出框的数据岛
	 */
	getFormPanelDom : function(operateId) {
		//var tableId = operateId.split(this.SPLIT_LINE)[0];
		var temp = formDislandlArr[operateId];
		temp.find(this.OPERATE).attr(this.OPERATE_ID, operateId);
		temp.find(this.OPERATE).attr(this.OPERATE_METHOD, DISLANDTODOM.getBtnMethod(operateId));
		formDislandlArr[operateId] = temp;
		return formDislandlArr[operateId];
	},

	/**
	 * 根据弹出框的细粒度组件Id 获取相应细粒度组件的bopDom
	 */
	getFormPanelBOPDom : function(formPandlDom, formPanelFcId) {
		var bopBind = formPanelFcId.split(this.SPLIT_LINE)[2];
		return formPandlDom.find(this.BOP + "[" + this.BIND + "='" + bopBind + "']");
	},

	/**
	 * 根据operateId获取数据岛中的BO中的operateDom节点
	 */
	getBOOperateDom : function(operateId, boDom) {
		var boDomTemp = boDom || this.getBODom(operateId.split(this.SPLIT_LINE)[0]);
		return boDomTemp.find(this.OPERATE + "[" + this.OPERATE_ID + "='" + operateId + "']");
	},

	/**
	 * 根据operateId获取数据岛中的BOList中的operateDom节点
	 */
	getBOListOperateDom : function(operateId) {
		var boDom = this.getBOListDom(operateId.split(SPLIT_LINE)[0]);
		return boDom.find(this.OPERATE + "[" + this.OPERATE_ID + "='" + operateId + "']");
	},

	/**
	 * 根据细粒度组件Id获取其数据岛的parentDom
	 * @param {} fcId
	 * @return {}
	 */
	getParentDom : function(fcId){
		return this.getDom(fcId.split(this.SPLIT_LINE)[0]);
	},
	
	getOperateList : function(){
		return this.getDataIsland().find(this.OPERATE);
	},
	
	/**
	 * 根据粗粒度组件id获取粗粒度组件中的operateDoms
	 */
	getOperateInCon : function(containerId) {
		var boDom = this.getBODom(containerId);
		//表单中的按钮
		if(boDom.length != 0) {
			return boDom.find(this.OPERATE);
		}
		//表格中的按钮
		else {
			return this.getBOListDom(containerId).find(this.OPERATE);
		}
	},
	
	/**
	 * 查询行级bo下的operateDom节点
	 */
	getRowOperate : function(bo, operateDomId) {
		return bo.children(this.OPERATE + "[" + DISLAND.OPERATE_ID + "='" + operateDomId + "']").slice(0);
	},

	/**
	 * 根据细粒度组件dom的name获取bopDom
	 */
	getBOPByFcName : function(fcName) {
		var bopBind = fcName.split(this.SPLIT_LINE)[2];
		var bo = this.getDom(fcName.split(this.SPLIT_LINE)[0]);
		return bo.children(this.BOP + "[" + DISLAND.BIND + "='" + bopBind + "']").slice(0);
	},

	/**
	 * 获取table数据岛中bop的展示值、状态、范围
	 * @return result
	 * result.status		状态
	 * result.range			范围
	 * result.value			展示值(如果有displayValue, 展示值是displayValue; 如果是枚举值, 展示值是对应的text; 如果是普通值, 展示值是value)
	 * result.format		日期格式
	 * result.alt			图标的alt, 用于statusBOP
	 * result.hasDisplay	是否有展示值
	 * result.height		如果bop是图片类型, height表示图片高度
	 * result.width			如果bop是图片类型, width表示图片宽度
	 * result.isAdaptive	如果isAdaptive==true, 将根据图片的实际大小展示图片;否则根据设定的height和weight展示
	 */
	getColumnVSR : function(bopDom, columnInfo) {		
		var result = {};
		var value;
		if(bopDom.attr(this.BOP_RANGE_ENUM)){
			var newData = new Array();
			var items =  eval('(' + bopDom.attr(this.BOP_RANGE_ENUM) + ')');
			for(var i = 0; i < items.length; i++){
				newData.push([items[i].k, items[i].v]);
			}
			value = newData[bopDom.text()];
		}
		else {
			var bopId = bopDom.attr(this.BIND);
			var column = columnInfo.find(this.COLUMNINFO_COLUMN + "[" + this.BIND + "='" + bopId + "']");
			result.status = column.children(this.STATUS);
			result.range = column.children(this.BOP_RANGE);
			if(column.attr(this.COLUMN_TYPE_IMG_ISADAPTIVE) == 'true') {
				result.isAdaptive = true;
			}
			else {
				result.height = column.attr(this.COLUMN_TYPE_IMG_HEIGHT) || projectStyle.getColImgHeight();
				result.width = column.attr(this.COLUMN_TYPE_IMG_WIDTH) || projectStyle.getColImgWidth();
			}
			value = specialCharEncode(this.getBOLIST_BOPLabel(bopDom, result));
		}
		result.value = value;
		return result;
	},

	/**
	 * 获取bop的状态
	 */
	getBOPStatus : function(bopDom) {
		return bopDom.find(this.STATUS);
	},
	
	/**
	 * 获取table中bop的展示值, 该方法将为bop添加alt和hasDisplay两个属性
	 * @param bopDom
	 * @param bop 	
	 */
	getBOLIST_BOPLabel : function(bopDom, bop) {
		var enumRange = this.range_getEnumRange(bop.range.children());
		var enumRangeMap = this.range_getEnumRangeMap(enumRange);
		var text = getXmlNoteText(bopDom);
		var enumRangeValue;
		
		var display = bopDom.attr(this.BOP_VALUE_DISPLAY);
		if(display) {
			bop.hasDisplay = true;
			bop.alt = enumRangeMap[text];
		}
		else if(text.indexOf(this.SPLIT_COMMA) > 0) {
			var arr = text.split(this.SPLIT_COMMA);
			var flag = false;
			for(var i = 0, length = arr.length; i < length; i++){
				if(flag)
					enumRangeValue += this.SPLIT_COMMA + (enumRangeMap[arr[i]] || arr[i])
				else
					enumRangeValue = enumRangeMap[arr[i]] || arr[i];
				flag = true;
			}
		}
		else {
			enumRangeValue = enumRangeMap[text];
		}
			
		return display || enumRangeValue || text || "";
	},


	/**
	 * 获取table的分页信息
	 */
	getPagination : function(boDom) {
		var pagination = boDom.children(DISLAND.PAGINATION).slice(0);
		var result = {
			limit : pagination.attr(this.PAGINATION_LIMIT),
			total : pagination.attr(this.PAGINATION_TOTAL),
			start : pagination.attr(this.PAGINATION_START)
		};

		return result;
	},

	/**
	 * boDom的状态机是否已经改变
	 * true: 已改变
	 */
	isOptStatusChanged : function(boDom) {
		var optStatus = boDom.attr(this.BO_OPERATIONSTATUS);
		return !!optStatus && optStatus != this.BO_OPERATIONSTATUS_INIT;
	},
	
	/**
	 * 是否是多附件bop
	 */
	isMutiFileBOP : function(bop) {
		var url = bop.attr(this.BOP_DATA_URL);
		return url.indexOf(this.SPLIT_TREBLE_BACKSLASH) > 0;
	},

	/**
	 * 获取checked 节点,即选择记忆节点
	 */
	getCheckedDom : function(boListDom) {
		return boListDom.children(this.BOLIST_CHECKEDIDS);
	},

	/**
	 * 清空历史选中项
	 */
	clearCheckedDom : function(boListDom) {
		var checkedDom = this.getCheckedDom(boListDom);
		if(checkedDom.length > 0) {
			checkedDom.attr(this.BO_ID, "");
			checkedDom.children().remove();
		}
	},
	
	/**
	 * 根据系统弹出框（修改）表单DOM或获取对应表格DOM
	 */
	getUpdateTableDom : function(updateFormDom){
		boId = updateFormDom.attr(this.BO_ID);
		if(boId && boId.indexOf("update_form"))
			return this.getDom(boId.split(this.SPLIT_LINE)[0]);
		else
			return null;
	},
	
	/**
	 * 表格的COLUMNINFO_SELECTIONMODEL是否是checkbox
	 */
	sm_checkbox : function(columnInfo) {
		var sm = columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);
		return !sm || sm == 'checkBox';
	},
	
	/**
	 * 表格的COLUMNINFO_SELECTIONMODEL是否是radio
	 */
	sm_radio : function(columnInfo) {
		return 'radio' == columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);
	},
	
	/**
	 * 表格的COLUMNINFO_SELECTIONMODEL是否是empty
	 */
	sm_empty: function(columnInfo) {
		return 'empty' == columnInfo.attr(DISLAND.COLUMNINFO_SELECTIONMODEL);
	},
	
	/**
	 * 根据细粒度组件dom的name获取Column
	 */
	getColumnByFcName : function(fcName) {
		var bopBind = fcName.split(this.SPLIT_LINE)[2];
		var boList = this.getDom(fcName.split(this.SPLIT_LINE)[0]);
		return this.getColumnByBopBind(boList, bopBind);
	},
	
	/**
	 * 根据细粒度组件bopBind获取Column
	 */
	getColumnByBopBind : function(boList, bopBind) {
		return boList.children(this.COLUMNINFO).children(this.COLUMNINFO_COLUMN + "[" + this.BIND + "='" + bopBind + "']");
	},
	
	/**
	 * 从特定boDom（如不设置则取页面中的对应boDom）中根据fcId获得bopDom
	 * @param {} fcId
	 * @param {} boDom
	 * @return {}
	 */
	getBOPByFcId : function(fcId, boDom) {
		var bopBind = DISLANDTODOM.getBOPBind(fcId);
		var bo = boDom ? boDom : this.getDom(DISLANDTODOM.getBOId(fcId));
		return bo.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bopBind + "']");
	},
	
	/**
	 * 获取BOP展示值
	 * @param bop		bop数据岛
	 * @param value		组件的值{可选}
	 */
	getBOPLabel : function(bop, value) {
		var enumRange = this.range_getEnumRange(bop.children(DISLAND.BOP_RANGE).children());
		var enumRangeMap = this.range_getEnumRangeMap(enumRange);
		var bopValue = bop.children(DISLAND.BOP_VALUE).text();
		if(isNotEmpty(value))
			bopValue = value;
		var enumRangeValue;
		
		var display = bop.attr(this.BOP_VALUE_DISPLAY);
		if(display) {
			bop.alt = enumRangeMap[bopValue];
		}
		else if(bopValue.indexOf(DISLAND.SPLIT_COMMA) > 0){
			var arr = bopValue.split(DISLAND.SPLIT_COMMA);
			var flag = false;
			for(var i = 0; i < arr.length; i++){
				if(flag)
					enumRangeValue += DISLAND.SPLIT_COMMA + (enumRangeMap[arr[i]] || arr[i])
				else
					enumRangeValue = enumRangeMap[arr[i]] || arr[i];
				flag = true;
			}
		}
		else
			enumRangeValue = enumRangeMap[bopValue];
			
		return display || enumRangeValue || bopValue || "";
	},
	
	/**
	 * 根据按钮的operateDom获取sourceDom, 即<qeweb:source>的数据岛
	 */
	getSourceOptDom : function(operateDom) {
		var sourceDom = operateDom.children(DISLAND.SOURCE);
		return sourceDom.length == 0 ? null : sourceDom.slice(0);
	},
	
	/**
	 * 根据boListDom获取最大的index, 即获取表格中的记录数
	 * 注: 该方法等同于Ext.getCmp(gridId).getStore().getCount();
	 */
	getMaxIdx : function(boListDom) {
		if(!boListDom)
			return 0;

		var bos = boListDom.children(this.BO);
		if(bos.length > 0)
			return bos.slice(bos.length - 1).attr(this.BO_INDEX);
		else 
			return 0;
	},
	
	/**
	 * 设置整个数据岛的状态机, 即在数据岛根节点上设置operationstatus=sys-temp
	 * 当某个按钮的保存模式是all时, 将还原整个页面的数据, 此时设置整个数据岛的状态机
	 */
	setPageOptStatus : function() {
		this.getDataIsland().attr(this.BO_OPERATIONSTATUS, BoFinalMethod.SYSTEMP);
	},
	
	/**
	 * 清空整个数据岛的状态机
	 */
	clearPageOptStatus : function() {
		this.getDataIsland().removeAttr(this.BO_OPERATIONSTATUS);
	},
	
	/**
	 * 创建细粒度组建在表格中创建关联、提交时使用的临时BOP
	 * @param {} columnInfo
	 * @param {} editColumn
	 * @param {} rowIndex
	 * @param {} grid
	 * @param {} bop
	 * @return {} 
	 */
	createTempBop : function(columnInfo, editColumn, rowIndex, grid, bop){
		var store = grid.getStore();
		var record = store.getAt(rowIndex);
		
		var operateDoms = bop.parent().children(this.OPERATE);
		var operateStr = "";
		operateDoms.each(function() {
			operateStr += xmlToString($(this))
		});
		
		//创建临时bop，并添加细粒度组件关联方式（细细/细粗）
		var boDomStr = "<" + this.BO + " " + this.BO_INDEX + "='" + (rowIndex + 1) + "' temp='true'>";
		boDomStr += operateStr;
		columnInfo.children(this.COLUMNINFO_COLUMN).each(function() {
			var column = $(this);
			var bopDomStr = "<" + DISLAND.BOP+ " " + DISLAND.BIND + "='" + column.attr(DISLAND.BIND) + "' ";
			
			if(column.attr(DISLAND.BIND) == editColumn.attr(DISLAND.BIND)){
				if(column.attr(DISLAND.BOP_ISRELATE) === 'true')
					bopDomStr += DISLAND.BOP_ISRELATE + "='true' ";
				if(column.attr(DISLAND.BOP_ISCONRELATE) == 'true')
					bopDomStr += DISLAND.BOP_ISCONRELATE + "='true' ";
			}
			if(column.attr(DISLAND.COLUMN_TYPE))
				bopDomStr += DISLAND.COLUMN_TYPE + "='" + column.attr(DISLAND.COLUMN_TYPE) + "' ";
			bopDomStr += "><![CDATA[" + record.get(column.attr(DISLAND.BIND)) + "]]></" + DISLAND.BOP + ">";
			boDomStr += bopDomStr;
		});
		boDomStr += "</" + DISLAND.BO + ">";
		var xmlDom = XMLDomFactory.getInstance(boDomStr);
		var cBop =  xmlDom.children().children(DISLAND.BOP + "[" + this.BIND + "='" + editColumn.attr(DISLAND.BIND) + "']");
		
		//向临时bop中添加因上级关联项变更而产生的新的值，状态，范围
		if(bop.text())
			cBop.text(bop.text());
		
		if(bop.attr(this.STATUS_DISABLE))
			cBop.attr(this.STATUS_DISABLE, bop.attr(this.STATUS_DISABLE));
		else if(bop.attr(this.STATUS_READONLY))
			cBop.attr(this.STATUS_READONLY, bop.attr(this.STATUS_READONLY));
		
		var enumStr = bop.attr(this.BOP_RANGE_ENUM);
		if(!enumStr)
			enumStr = this._getEnum(editColumn);
		if(enumStr)
			cBop.attr(this.BOP_RANGE_ENUM, enumStr);
			
		return cBop;
	},
	
	/**
	 * 在表格细粒度组件关联时，当上一级并未选择时，将当前级别的选项修正为初始选项<br>
	 * @param {} column
	 * @return {}
	 */
	_getEnum : function(column){
		var returnStr = null;
		var items = column.find(this.BOP_RANGE_ITEM);
		if(items.length == 1){
			var value = "";
			if(items.attr(this.BOP_RANGE_ITEM_VALUE))
				value = items.attr(this.BOP_RANGE_ITEM_VALUE);
			returnStr = "[{'k':'" + value +"','v':'" + items.attr(this.BOP_RANGE_ITEM_LABEL) + "'}]";
		}
		return returnStr;
	},
	
	/**
	 * 创建表格弹出式单元格编辑form的临时dom
	 * @param {} bolist
	 * @param {} bind
	 * @return {}
	 */
	createCellEditFormDom : function(bolist, bind){
		var bobind = bolist.attr(this.BIND);
		var formId = bolist.attr(this.BOLIST_ID) + "_celledit_form";
		var columnInfo = bolist.children(this.COLUMNINFO);
		var column = columnInfo.children(this.COLUMNINFO_COLUMN + "[" + this.BIND + "='" + bind + "']");	
		var str = xmlToString(column)
					.replaceAll("<" + this.COLUMNINFO_COLUMN, "<" + this.BOP)
					.replaceAll("</" + this.COLUMNINFO_COLUMN, "</" + this.BOP);
		var boDomStr = "<" + this.dataIsland + "><" + this.BO + " " + this.BO_ID + "='" + formId + "' " + this.BIND + "='" + bobind + "'>";
		boDomStr += str;
		boDomStr += "</" + this.BO + "></" + this.dataIsland + ">";
		var xmlDom = XMLDomFactory.getInstance(boDomStr);
		var boDom =  xmlDom.find(this.BO + "[" + this.BIND + "='" + bobind + "']");
		return boDom;
	},
	
	/**
	 * 获取bop的值
	 */
	getValue : function(bop) {
		var valueDom = bop.find(this.BOP_VALUE);
		if(valueDom.length != 0)
			return getRemoveCDATA(valueDom.text());
		else
			return getRemoveCDATA(bop.text());
	},
	
	/**
	 * 获取range中的枚举范围
	 */
	getEnumRange : function(range) {
		return range.children(DISLAND.BOP_RANGE_ENUM);
	},
	
	/**
	 * 获取operateDom的clone
	 */
	getCloneOptDom : function(operateDom) {
		var cloneOptDom = operateDom.clone();
		if(operateDom.attr(DISLAND.OPERATE_IDX)) {
			cloneOptDom.boDom = operateDom.parent().parent();
		}
		else {
			cloneOptDom.boDom = operateDom.parent();
		}
		
		return cloneOptDom;
	},
	
	/**
	 * 重置表格中bo的序号, 动态删除表格中的数据时使用
	 */
	resetIndex : function(boList) {
		boList.children(this.BO).each(function(index) {
			var oldIndex = $(this).attr(DISLAND.BO_INDEX);
			var newIndex = index + 1;
			//重置行级bo的序号
			$(this).attr(DISLAND.BO_INDEX, newIndex);
			//重置行级按钮的序号
			$(this).children(DISLAND.OPERATE).each(function() {
				var btnId = $(this).attr(DISLAND.OPERATE_ID) + DISLAND.SPLIT_LINE + oldIndex;
				$(this).attr(DISLAND.OPERATE_IDX, newIndex);
				
				//重置按钮组件的属性, also see CellRender.rowBtnRender
				var btn = $('#' + btnId);
				var newId = btnId.replace(/\d+$/, newIndex);
				
				btn.attr('id', newId);
				btn.attr('idx', newIndex)
			});
		});
	}
};
