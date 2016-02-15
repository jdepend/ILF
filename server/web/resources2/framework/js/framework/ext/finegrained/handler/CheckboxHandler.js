
/**
 * CheckboxHandler继承FCHandler(FCHandler.js), 处理多选按钮组
 * 
 * @param bopDom
 *            细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function CheckboxHandler(bopDom, fc) {
	CheckboxHandler.superclass.constructor.call(this, bopDom, fc);
	this._RELATION_EVENT = "check";	

	this._addRelation = function() {
		if (this.bopDom.attr(DISLAND.BOP_ISRELATE) != 'true')
			return;

		var fcHandler = this;
		// 为每个checkBox都添加事件
		for (var i = 0, length = fc.items.length; i < length; i++) {
			var item = Ext.getCmp(fc.getId() + DISLAND.SPLIT_LINE + i);
			item.addListener(this._RELATION_EVENT, function(checkbox, checked){
				BOPSubmit.submit(fcHandler);});
		}
	};

	/**
	 * 更新checkbox的选项
	 */
	this._updateItems = function(range) {
		//checkbox是枚举型组件,仅对应枚举型范围
		var rangeList = range.children(DISLAND.BOP_RANGE_ENUM);
		if(rangeList < 0)
			return;
		
		var items = this.fc.items;   
		for (var i = 0, length = items.getCount(); i < length; i++) {
			var delItems = this.fc.items.items[0];
			delItems.destroy();
			this.fc.items.remove(delItems);
		}
		var columns = this.fc.columns.length;
		var col = this.fc.panel.items.get(0);
		rangeList.each(function(){
			$(this).children(DISLAND.BOP_RANGE_ITEM).each(function(){
				//组装对象
				var item = new Ext.form.Checkbox({
					id : fc.getId() + DISLAND.SPLIT_LINE + i++,
					name : fc.name,
					value : $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE),
					boxLabel : $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL),
					inputValue : $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE),
					vtype : 'bopRange',
					itemCls : 'allow-float',
					clearCls : 'clear-float'
				});
						
				col = fc.panel.items.get(i % columns);   
				col.add(item);   
				fc.items.add(item);
			});
		});
		
		this.fc.panel.doLayout();
	};
	
	// 获取细粒度组件的值,checkbox返回Array
	this._getValue = function() {
		var mutiValue = "";
		for (var i = 0, length = fc.items.length; i < length; i++) {
			if (fc.items.get(i).checked){
				mutiValue += (mutiValue ? "," : "") + fc.items.get(i).inputValue;
			}
		}
		return mutiValue;
	};
	
	this._setValue = function(value) {
		//获取被选中的checkbox的值,按","分割
		var mutiValue = String(value).split(DISLAND.SPLIT_COMMA);
		if(mutiValue.length == 0)
			return;
		fc.items.each(function(item){			
			for(var i = 0, length = mutiValue.length; i < length; i++){
				if(item.inputValue == mutiValue[i]){
					if(fc.getXType() == 'checkboxgroup')
						fc.setValue(item.getId(), true);
					else
						item.setValue(true);
					return true;
				}
				else {
					if(fc.getXType() == 'checkboxgroup')
						fc.setValue(item.getId(), false);
					else
						item.setValue(false);
				}
			}
		});
	};

	// _setReadonly
	this._setReadonly = function(isReadOnly) {
		this._setDisabled(isReadOnly);
	};
	
	// _setDisabled
	this._setDisabled = function(isDisabled) {
		this.fc.items.each(function(item){
			item.setDisabled(isDisabled);
		});
	};
}

extend(CheckboxHandler, FCHandler);