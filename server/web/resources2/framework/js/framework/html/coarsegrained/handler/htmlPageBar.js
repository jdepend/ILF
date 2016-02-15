function pagebar(tableHandler) {
	this.tableHandler = tableHandler;
	this.totalCount = 0;	// 总记录
	this.start = 0;			// 本页起始记录数
	this.end = 0;			// 本页结束记录数
	this.limit = 0;			// 每页显示记录数
}

pagebar.prototype.paint = function(pageBarDom, totalCount, start, limit, end){
	if(totalCount) {
		this.totalCount = totalCount;
		this.start = start;
		this.end = end;
		this.limit = limit;
	}

	if(this.getTotalPage() == 0) {
		pageBarDom.empty();
		pageBarDom.append(I18N.showPageBar(this.getTotalPage()));
	}
	else {
		pageBarDom.empty();
		pageBarDom.append(this.getFirstPageBtn());
		pageBarDom.append(this.getPrevPageBtn());
		pageBarDom.append(I18N.showPageBar(this.getTotalPage(), this.getCurPage(), this.start, this.end, this.totalCount));
		pageBarDom.append(this.getNextPageBtn());
		pageBarDom.append(this.getLastPageBtn());
	}
};

/**
 * 总页数
 */
pagebar.prototype.getTotalPage = function(){
	if(this.totalCount == 0)
		return 0;
	return Math.ceil(~~this.totalCount / ~~this.limit);
}

/**
 * 当前页
 */
pagebar.prototype.getCurPage = function(){
	if(this.totalCount == 0)
		return 1;
	return Math.ceil((~~this.start + ~~this.limit) / ~~this.limit);
}

/**
 * 第一页
 */
pagebar.prototype.getFirstPageBtn = function() {
	return this.getCurPage() > 1 ?
			this.createTurnImg('/framework/images/html/page-first.gif', 0) :
			this.createTurnImg('/framework/images/html/page-first-disabled.gif');
}

/**
 * 上一页
 */
pagebar.prototype.getPrevPageBtn = function() {
	return this.getCurPage() > 1 ?
			this.createTurnImg('/framework/images/html/page-prev.gif', (~~this.getCurPage() - 2)) :
			this.createTurnImg('/framework/images/html/page-prev-disabled.gif');
}

/**
 * 下一页
 */
pagebar.prototype.getNextPageBtn = function() {
	return this.getCurPage() < this.getTotalPage() ?
			this.createTurnImg('/framework/images/html/page-next.gif', this.getCurPage()) :
			this.createTurnImg('/framework/images/html/page-next-disabled.gif');
}

/**
 * 最后一页
 */
pagebar.prototype.getLastPageBtn = function() {
	return this.getCurPage() < this.getTotalPage() ?
		this.createTurnImg('/framework/images/html/page-last.gif', (~~this.getTotalPage() - 1)) :
		this.createTurnImg('/framework/images/html/page-last-disabled.gif');
}

pagebar.prototype.createTurnImg = function(src, pageNo) {
	var img = $("<img src='" + appConfig.ctx + src + "'>");
	if(pageNo >= 0) {
		img.attr('style', 'cursor:pointer');
		var temp = this;
		img.bind("click", function(){
			temp.turn(pageNo);
		});
	}

	return img;
}

pagebar.prototype.turn = function(pageNo){
	var tableName = this.tableHandler.attr('id');
	var loading = layer.load();
	$.ajax({
		type : "POST",
		url : appConfig.ctx + actionURL.getGaSearch(),
		data : {
			"tableName" : tableName,
			"start" : pageNo * this.limit,
			"limit" : 	this.limit,
			"sourceName" : 	this.sourceName,
			"dataIsland" :	paramValue(xmlToString(DISLAND.getDataIsland())),
			"sourceVcId" :	this.sourceVcId,
			"btnName"	 :	this.btnName,
			"sourcePage" :	this.sourcePage
		},
		success : function(data){
			var tableData = StringToJSON(data)['tableIsland'];
			var tableHandler = new TableHandler(XMLDomFactory.getInstance(tableData).find(DISLAND.BOLIST))
			tableHandler.fresh();
		},
		complete : function(){
			layer.close(loading);
		}
	});
}

