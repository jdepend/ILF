/**
 * 全局自定义表格、表单、按钮的样式
 */
projectStyle = {
	/**
	 * 获取序号列宽度
	 * @param pagebar	表格分页栏
	 * @returns {Number}
	 */
	getNoWidth : function(pagebar) {
		//序号列默认宽度23px
		var width = 23; 
		/*
		 * 自定义表格中序号列宽度计算系数.
		 * 序号列的宽度 =  widthStep * 序号的位数, 比较基准是固定值23.
		 * 例1:
		 * 如果序号是14(位数是2), 列宽度计算系数是10, 则序号列的宽度 = 10 * 2 = 20.
		 * 20 < 23(比较基准), 则序号列宽是23.
		 * 例2:
		 * 如果序号是100(位数是3), 列宽度计算系数是10, 则序号列的宽度 = 10 * 3 = 30.
		 * 30 > 23(比较基准), 则序号列宽是30.
		 */
		var widthStep = 10;
		
		var maxNo = pagebar.cursor + pagebar.pageSize + 1;
		var result = widthStep * maxNo.toString().length;
		if(result < width)
			result = width;
		
		return result;
	},
	
	/**
	 * 如果表格单元格是图片, getColImgWidth返回图片的默认宽度 
	 */
	getColImgWidth : function() {
		return 40;
	},
	
	/**
	 * 如果表格单元格是图片, getColImgWidth返回图片的默认高度
	 */
	getColImgHeight : function() {
		return 40;
	},
	
	/**
	 * 如果表格单元格中展示图片, getImgMargin返回两个图片见的间隔.
	 * 注: getImgMargin应当返回有多个&nbsp;组成的字符串.
	 */
	getColImgMargin : function() {
		return "&nbsp;&nbsp;&nbsp;&nbsp;";
	},
	
	/**
	 * 自动计算表格的高度
	 * @param pageSize  	当前表格展示的记录数
	 * @param grid 			ExtGridPanel
	 */
	getTableAutoHeight : function(pageSize, grid) {
		var H_RECORD = 22;						//每行数据的高度
		//表格中存在展示状态图标的列
		if(grid.hasStatusCol) 
			H_RECORD = 24;
		//表格中存在可编辑列
		else if(grid.hasEditorsCol)
			H_RECORD = 23;
		
		var H_TBAR = grid.tbar ? grid.getToolbarHeight() : 0;		//tbar的高度;
		var H_TITLE = 24;						//列名高度
		var H_SCROLL = 40;						//滚动条高度
		var H_BBAR = 27;						//bbar高度
		var H_BASE = 18;						//补齐高度
		
		//当存在一行行级按钮时, grid.getToolbarHeight() 值为29;
		//当存在多行行级按钮时, grid.getToolbarHeight() 值为 tbar + bbar 的总高度
		if(!grid.bbar || H_TBAR > 29) {
			H_BBAR = 0;
		}
		
		var autoHeight = H_TBAR + H_TITLE + H_RECORD * pageSize + H_SCROLL + H_BBAR + H_BASE;
		
		return autoHeight > grid.getHeight() ? autoHeight : grid.getHeight();
	},

	/**
	 * ExtAnchor控件的样式
	 */
	getAnchorStyle : function() {
		return $.browser.msie ? 'padding-top: 5px;' : 'padding-top: 3px;';
	},
	
	/**
	 * ExtRadio或ExtCheckBox控件的样式
	 */	
	getRCStyle : function() {
		return $.browser.msie ? 'border:0px;padding-top:6px;margin-bottom:-16px;' : 'border:0px;padding-top:0px;margin-bottom:-22px;';
	},
	
	/**
	 * ExtRadio或ExtCheckBox控件的label样式
	 */	
	getRCLableStyle : function() {
		return $.browser.msie ? 'top:6px;' : '';
	},
	
	/**
	 * "保存查询条件"的弹出window的高度
	 */
	getSaveCaseWinHeight : function() {
		return 150;
	},
	
	/**
	 * "保存查询条件"的弹出window的宽度
	 */
	getSaveCaseWinWidth : function() {
		return 300;
	},
	
	/**
	 * "使用查询条件"的弹出window的高度
	 */
	getUseCaseWinHeight : function() {
		return 200;
	},
	
	/**
	 * "使用查询条件"的弹出window的宽度
	 */
	getUseCaseWinWidth : function() {
		return 300;
	}
}
