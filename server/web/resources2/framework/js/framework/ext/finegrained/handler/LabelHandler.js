/**
 * TextHandler继承FCHandler(FCHandler.js), 处理文本域textfield或多行文本域textarea
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function LabelHandler(bopDom, fc) {
	LabelHandler.superclass.constructor.call(this, bopDom, fc);
	
	this._addRelation = function(){};

	this._updateRange = function(){};

	//_setHidden
	this._setHidden = function(isHidden) {
		if(!this.fc.el)
			return;
		if(isHidden === "true"){
			this.fc.hide();
			//处理组合控件，及textFiled + sourceButton
			if(!this.fc.fieldLabel){
				this.fc.el.parent().parent().parent().parent().hide();
			}
			else{
				this.fc.el.parent().parent().hide(); 
			}
		}
		else {
			this.fc.show();
			if(!this.fc.fieldLabel){
				this.fc.el.parent().parent().parent().parent().show();
			}
			else{
				this.fc.el.parent().parent().show(); 
			}
		}
	};
	
	this._setReadonly = function(isReadOnly) {};
	
	//获取细粒度组件的值
	this._getValue = function(){
		return this.fc.realValue || this.fc.text;
	};

	//细粒度组件设值
	this._setValue = function(value) {
		var text = value;
		var realValue = "";
		
		this.bopDom.find(DISLAND.BOP_RANGE).children().each(function(){
			if($(this).context.nodeName != DISLAND.BOP_RANGE_ENUM)
				return true;
			
			if(value && $(this).is(DISLAND.BOP_RANGE_ENUM)) {
				$(this).children(DISLAND.BOP_RANGE_ITEM + "[" + DISLAND.BOP_RANGE_ITEM_VALUE + "='" + value + "']").each(function(){
					text = $(this).attr(DISLAND.BOP_RANGE_ITEM_LABEL);
					realValue = $(this).attr(DISLAND.BOP_RANGE_ITEM_VALUE);
					return false;
				});
			}
		});
		this.fc.setText(text);
		this.fc.realValue = realValue;
	};
}

extend(LabelHandler, FCHandler);