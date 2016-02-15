/**
 * 双向选择继承FCHandler(FCHandler.js), 处理双向选择
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function OptTransHandler(bopDom, fc) {
	OptTransHandler.superclass.constructor.call(this, bopDom, fc);
	
	this._updateItems = function(range) {
		var rangeList = range.children();
		if(rangeList == null || rangeList.length == 0)
			return;
		
		var newData = [];
		rangeList.each(function(){
			//下拉列表是枚举型组件,仅对应枚举型范围
			if($(this).is(DISLAND.BOP_RANGE_ENUM)) {
				$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){
					newData.push([$(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE), $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL)]);
				});
			}
		});
		
		fc.fromMultiselect.store.loadData(newData);
	};
	
	//获取细粒度组件的值,optTransSelect返回Array
	this._getValue = function() {		
		//fc.getValue()获取的值为显示的文本
		var result = [];
		var items = fc.toStore.data.items;
		for(var i=0;i<items.length;i++){
			result.push(items[i].json[0]);
		}
		return result;
	};
	
}

extend(OptTransHandler, FCHandler);