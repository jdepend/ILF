
/**
 * CheckboxHandler继承FCHandler(FCHandler.js), 处理多选按钮组
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function CheckboxHandler(bopDom) {
	CheckboxHandler.superclass.constructor.call(this, bopDom);

	this._updateRange = function(){
		var rangeList = bopDom.children(DISLAND.BOP_RANGE).children();
		if(!rangeList || rangeList.length == 0)
			return;

		var divDom = this.fc;
		rangeList.each(function(){
			var i = 0;
			var html = bopDom.attr(DISLAND.BOP_RANGE_ITEM_LABEL);
			var id = divDom.attr('id');
			var name = checkbox[checkbox.length - 1].attr('name');
			$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){
				html += "<input type='checkbox' value='" + $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE);
				html += "' id=" + id + "_" + i++;
				html += "' name='" + name + "'>";
				html += $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);
			});
			divDom.empty();
			divDom.append(html);
		});
	}

	//获取细粒度组件的值,checkbox返回Array
	this._getValue = function() {
		var mutiValue = [];
		var n = 0;

		$.each(this.fc,function(){
			if($(this).attr('checked')){
				mutiValue[n++] = $(this).val();
			}
		});
		
		return mutiValue;
	}

//	//_setDisabled
//	this._setDisabled = function(isDisabled) {
//
//	};
//
//	//_setReadonly
//	this._setReadonly = function(isReadOnly) {
//
//	};
}

extend(CheckboxHandler, FCHandler);