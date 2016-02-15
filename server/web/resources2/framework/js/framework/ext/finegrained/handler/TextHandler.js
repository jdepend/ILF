/**
 * TextHandler继承FCHandler(FCHandler.js), 处理文本域textfield或多行文本域textarea
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function TextHandler(bopDom, fc) {
	TextHandler.superclass.constructor.call(this, bopDom, fc);
	
	this._RELATION_EVENT = "blur";

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
	
	//获取细粒度组件的值
	this._getValue = function(){
		return this.fc.realValue || this.fc.getValue();
	};

	//细粒度组件设值
	this._setValue = function(value) {
		//当组件包含sourceBtn时
		if(this.fc.getXType() == "compositefield")
			this.fc.items.items[0].setValue(value);
		else
			this.fc.setValue(value);
	};
}

extend(TextHandler, FCHandler);