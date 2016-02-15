/**
 * RadioHandler继承FCHandler(FCHandler.js), 处理单选按钮组
 * 
 * @param bopDom
 *            细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function RadioHandler(bopDom, fc) {
	RadioHandler.superclass.constructor.call(this, bopDom, fc);
	this._RELATION_EVENT = "change";	
	// 添加关联
	this._addRelation = function() {
		if (this.bopDom.attr(DISLAND.BOP_ISRELATE) != 'true')
			return;

		var fcHandler = this;
		
		//垂直布局, 为radiogroup添加事件
		fc.addListener(this._RELATION_EVENT, function(){
			BOPSubmit.submit(fcHandler);
		});
	};	

	this._updateItems = function(range) {
		// 数据项
		if(range == null || range.children().length == 0)
			return;
	};

	// 获取细粒度组件的值
	this._getValue = function() {
		//获取被选中的radio的值
		for (var i = 0, length = fc.items.length; i < length; i++) {
			try {
				if(fc.items.get(i).checked)
					return fc.items.get(i).value;
			} catch(e) {
				if(fc.items[i].checked)
					return fc.items[i].value;
			}
		}
		return '';
	};

	this._setValue = function(value) {
		//获取被选中的radio的值
		fc.items.each(function(item){
			if(item.value == value)
				item.setValue(true);
			else
				item.setValue(false);
		});
	};
	
	// _setReadonly
	this._setReadonly = function(isReadOnly) {};
	
	// _setDisabled
	this._setDisabled = function(isDisabled) {
		var flag = false;
		if (isDisabled === "true")
			flag = true;
		this.fc.items.each(function(item){
			item.setDisabled(flag);
		});
	};
}

extend(RadioHandler, FCHandler);