
/**
 * SelectHandler继承FCHandler(FCHandler.js), 处理下拉列表
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function SelectHandler(bopDom) {
	SelectHandler.superclass.constructor.call(this, bopDom);

	this._updateRange = function(){
		var rangeList = bopDom.children(DISLAND.BOP_RANGE).children();
		if(rangeList == null || rangeList.length == 0)
			return;

		var select = this.input;
		rangeList.each(function(){
			if($(this).is(DISLAND.BOP_RANGE_ENUM)) {
				var html = "";
				$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){
						html += "<option value='" + $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE) + "'>";
						html += $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);
						html += "</option>";
				});
				select.empty();
				select.append(html);
			}
		});
	}
}

extend(SelectHandler, FCHandler);