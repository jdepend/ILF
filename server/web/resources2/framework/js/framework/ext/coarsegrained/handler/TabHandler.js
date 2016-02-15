/**
 * tab处理js，负责所有<qeweb:tab>的操作
 * @param boDom
 * @returns {TabHandler}
 */
function TabHandler(boDom) {
	TabHandler.superclass.constructor.call(this, boDom);

	/**
	 * tab校验,找出tab中所有表单、检验、返回结果并激活检验出错的tab页。
	 */
	this.validate = function() {
		return this._validate(this.container, this.container.items, 0);
	};

	this._validate = function(container, items, activateIndex){
		var result = true;
		if(!items)
			return result;
		for ( var i = 0; i < items.length; i++) {
			var item = items.items[i];
			if(CommonDom.isForm(item) && !item.getForm().isValid()) {
				container.activate(activateIndex);
				//tab可能处于未激活状态，虽然执行了表单的检验方法，但是检验的结果并未渲染到页面上，所以在激活tab页后再执行一次检验。
				result = item.getForm().isValid();
			}
			else if(CommonDom.isForm(item) && item.getForm().isValid()) {
				continue;
			}
			else {
				result = this._validate(container, item.items, i);
			}
			if(!result)
				break;
		}
		
		return result;
	}

}
extend(TabHandler, ContainerHandler);
