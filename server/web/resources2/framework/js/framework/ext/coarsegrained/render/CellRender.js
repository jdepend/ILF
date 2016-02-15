/**
 * 表格单元格render 
 */
var CellRender = {
	/**
	 * 行级按钮
	 */
	rowBtnRender : function(btnId, btnName, btnText, btnIcon, index) {
		var id = btnId + DISLAND.SPLIT_LINE + index;
		var result = "";

		///(Icon)$/.test(btnIcon)为了向下兼容: 
		//在ExtCommandButton.java 的getIcon方法中, 将根据operate默认返回一些以Icon结尾的图标, 这些图标是在ext中内置的 
		if(isEmpty(btnIcon) || /(Icon)$/.test(btnIcon))
			result = "<span>&nbsp;&nbsp;<a href='#' id='" 
				+ id + "' name='" + btnName + "' " + "idx='" + index + "' onclick='BtnTableRowEvent.handle(this)' style='cursor:hand'>" + btnText + "</a></span>";
		//此处的icon图标是在projectStyle.css中定义的
		else {
			result = "<span><span id='" 
				+ id + "' name='" + btnName + "' " + "idx='" + index + "' style='cursor:hand' title='" + btnText + "'"
				+ "onclick='BtnTableRowEvent.handle(this)' class='" + btnIcon + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span>";
		}
		
		return result;
	},
	
	/**
	 * 超链接控件
	 */
	anchorRender : function(value, cell, record, rowIndex, tableId, bopBind) {
		var boListDom = TableHelper.getBoListDom(tableId);
		var boDom = boListDom.find(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + (rowIndex + 1) + "']");
		var bop = boDom.find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + bopBind + "']");
		
		var url = bop.attr(DISLAND.BOP_DATA_URL);
		var operateId = DISLANDTODOM.getTblOperateId(boListDom, bop);
		var operate = DISLAND.getRowOperate(bop.parent(), operateId);
		var display = bop.attr(DISLAND.BOP_VALUE_DISPLAY);
		// 当anchor标签绑定operate时，此时超链接表示执行一个操作
		if(operate.length > 0) {
			if(bop.attr(DISLAND.STATUS_DISABLE) != 'true' && bop.attr(DISLAND.STATUS_READONLY) != 'true')
				return "<a href='#' id='" + operateId + "' name='" + operateId + "' idx='" + (rowIndex + 1) + "' "
					+ "onclick='BtnTableRowEvent.handle(this)'>" + value + "</a>";
			else
				return value;
		}
		//当anchor标签绑定fileBop时，此时超链接标签表示下载文件
		else if(url) {
			var anchorStr = "";
			if (bop.attr(DISLAND.STATUS_DISABLE) == 'true' || bop.attr(DISLAND.STATUS_READONLY) == 'true') {
				anchorStr = value;
			} 
			//多附件
			else if (DISLAND.isMutiFileBOP(bop)) {
				var columnInfo = boListDom.children(DISLAND.COLUMNINFO);
				var files_url = url.split(DISLAND.SPLIT_TREBLE_BACKSLASH);
				var files_name = value.split(DISLAND.SPLIT_TREBLE_BACKSLASH);
				var target = DISLANDTODOM.getAnchorTarget(bop, columnInfo);
				for (var i = 0, len = files_url.length; i < len; i++) {
					if (!files_url[i] || !files_name[i])
						continue;
					anchorStr += "<a href='" + files_url[i] + "' " + target+ ">" + files_name[i] + "</a>&nbsp;&nbsp;";
				}
			}
			//单附件
			else {
				var columnInfo = boListDom.children(DISLAND.COLUMNINFO);
				anchorStr = "<a href='" + url + "' " + DISLANDTODOM.getAnchorTarget(bop, columnInfo) + ">" + value + "</a>";
			}

			return anchorStr;
		}
		// 当anchor标签绑定AnchorBOP时，此时表示一个普通的超链接，没有任何业务操作
		else if(isNotEmpty(display)) {
			if(bop.attr(DISLAND.STATUS_DISABLE) != 'true' && bop.attr(DISLAND.STATUS_READONLY) != 'true') {
				var columnInfo = boListDom.children(DISLAND.COLUMNINFO);
				return "<a href='" + getXmlNoteText(bop) + "' " + DISLANDTODOM.getAnchorTarget(bop, columnInfo) + ">" + display + "</a>";
			}
			else {
				return display;
			}
		}
		else {
			return "<a href='" + value + "'>" + value + "</a>"
		}
	},
		
	/**
	 * 图标类型(statusBOP)
	 * var imgStore = [];imgStore['0'] = 'path1/path2/icon1.png';imgStore['2'] = 'path1/path2/icon2.png';
 	 * var valueStore = [];valueStore['0'] = '未确认';valueStore['1']='已确认'; 
	 */
	iconRender : function(value, cell, imgStore, valueStore) {
		if(imgStore[value]) 
			return "<img src='" + appConfig.ctx + imgStore[value] + "' title='" + valueStore[value] + "'>";
		else 
			return valueStore[value];
	},
	
	/**
	 * 日期类型
	 */
	dateRender : function(value, format) {
		var result = "";
		if(format)
			result = Ext.util.Format.date(value, format);
		else
			result = Ext.util.Format.date(value, 'Y-m-d');
		
		return /^NaN/.test(result) ? value : result;
	},
	
	/**
	 * 日期控件, 优先级在日期类型之后, 当日期控件绑定非DateBop时使用 
	 */
	dateFCRender : function(value) {
		if(/\d+\s00:00:00$/.test(value) || /\d+[-]\d+[-]\d+/.test(value))
			return value.replace(/\s00:00:00$/, "");
		else if(!!value)
			return Ext.util.Format.date(value, 'Y-m-d');
		return '';
	},
	
	/**
	 * 图片类型
	 * @param value
	 * @returns
	 */
	imageRender : function(value, isAdaptive, height, width) {
		var img = '';
		if(value) {
			var imgArr = strToArray(value, ",");
			for(var i = 0, length = imgArr.length; i < length; i++) {
				var src = imgArr[i]
				if(!isHttp(imgArr[i]))
					src = appConfig.ctx + imgArr[i];
						
				if(isAdaptive) {
					img += "<a href='" + src + "' target='_blank' style='cursor:hand'><img src='" + src + "'></a>";
				}
				else {
					height = height || projectStyle.getColImgHeight();
					width = width || projectStyle.getColImgWidth();
					img += "<a href='" + src + "' target='_blank' style='cursor:hand'><img src='" + src + "' height='" + height + "' width='" + width + "'></a>";
				}
						
				if(i < length)
					img += projectStyle.getColImgMargin();
			}

			return img
		}
		else {
			return "";
		}
	},
	
	/**
	 * checkbox
	 * var bopStore = [];valueStore['0'] = '未确认';valueStore['1']='已确认'; 
	 */
	checkboxRender : function(value, bopStore) {
		var result = "";
		if(!value)
			return result;
		
		var arr = strToArray(value, ",")
		for(var i = 0; i < arr.length; i++) {
			if(!!bopStore[arr[i]])
				result += bopStore[arr[i]] + ",";
		}
		
		return !!result	? removeEnd(result) : result;
	},
	
	/**
	 * 枚举值
	 * var bopStore = [];valueStore['0'] = '未确认';valueStore['1']='已确认'; 
	 */
	enumRender : function(value, bopStore) {
		return bopStore[value];
	},
	
	/**
	 * 地址信息
	 */
	locationRender : function(value, tableId) {
		var boListDom = TableHelper.getBoListDom(tableId);
		var columnInfo = boListDom.children(DISLAND.COLUMNINFO);
		var VSR = DISLAND.getColumnVSR(bop, columnInfo);
		var display = decodeToDisplay(VSR.value);
		if (!!display && display != "--"){
			return String.format("<a href=\"#\" onclick=\"getMapWin('{0}', '{1}', '{2}');\">{2}</a>",
					display.split(",")[0], display.split(",")[1], display.split(",")[2]);
		}
		else if(display == "--"){
			return String.format("<a href=\"#\">{0}</a>", I18N.MOBILE_MAP_NOT_FIND);
		}
	},
	
	/**
	 * 可编辑单元格样式
	 */
	cellStyleRender : function(value, cell) {
		cell.style += ';border:1px #b5b8c8 solid;';
		return value;
	}
};