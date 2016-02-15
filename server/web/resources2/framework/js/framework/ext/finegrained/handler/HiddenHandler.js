/**
 * HiddenHandler继承TextHandler(TextHandler.js), 处理隐藏域hidden
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function HiddenHandler(bopDom, fc) {
	HiddenHandler.superclass.constructor.call(this, bopDom, fc);
	
	/**
	 * 隐藏域无需设置hidden
	 */
	this._setHidden = function(isHidden) {
		
	};
	
	this._setReadonly = function(isReadOnly) {
		
	};
	
	this._updateRange = function(){
	
	};
}

extend(HiddenHandler, TextHandler);

