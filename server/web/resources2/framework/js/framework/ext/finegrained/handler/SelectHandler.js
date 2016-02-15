/**
 * SelectHandler继承FCHandler(FCHandler.js), 处理下拉列表
 *
 * @param bopDom  细粒度组件数据岛
 * @param fc 细粒度组件Ext容器
 * @returns
 */
function SelectHandler(bopDom, fc) {
	SelectHandler.superclass.constructor.call(this, bopDom, fc);

	this._RELATION_EVENT = "select";

	this._updateItems = function(range)  {
		var itmesStr = bopDom.attr(DISLAND.BOP_RANGE_ENUM);
		var newData = [];
		if(itmesStr){
			itmesStr = itmesStr.replaceAll("&quot;", "\"");
			var items = eval('(' + itmesStr + ')');
			for(var i = 0; i < items.length; i++) {
				newData.push([items[i].k, items[i].v]);
			}
			this._loadData(newData);
			return;
		}
		
		if(range != null && range.children().length > 0){	
			var temp = [];
			range.children(DISLAND.BOP_RANGE_ENUM).each(function(){
				//下拉列表是枚举型组件,仅对应枚举型范围
				$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){
					var key = $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE);
					if(temp[key])
						return true;
					
					temp[key] = 1;
					newData.push([key, $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL)]);
				});
			});
			
			this._loadData(newData);
		}
		else {
			return;
		}
	};
	
	//根据data重新加载select的store
	this._loadData = function(data) {
		this.fc.getStore().loadData(data);
	};
	
	//获取细粒度组件的值
	this._getValue = function(){
		return this.fc.getValue();
	};

	//细粒度组件设值
	this._setValue = function(value) {
		var items = this.fc.getStore().data.items;
		for(var i = 0, length = items.length; i < length; i++) {
			if(items[i].data.valueField == value) {
				this.fc.setValue(value);
				break;
			}
		}
	};
}

extend(SelectHandler, FCHandler);