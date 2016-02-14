
/**
 * TextHandler继承FCHandler(FCHandler.js), 处理文本域textfield或多行文本域textarea
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function TextHandler(bopDom) {
	TextHandler.superclass.constructor.call(this, bopDom);
	
	this._updateRange = function(){
		var rangeList = bopDom.children(DISLAND.BOP_RANGE).children();
		if(rangeList == null || rangeList.length == 0)
			return;

		//重新加载校验
		this.input.unbind(this._VALIDATE_EVENT);
		this._addValidate();
	};
}

extend(TextHandler, FCHandler);