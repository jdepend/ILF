/**
 * EditorHandler继承TextHandler(TextHandler.js), 处理富文本编辑器
 *
 * @param bopDom	细粒度组件数据岛
 *
 * @param fc 细粒度组件Ext容器
 *
 * @returns
 *
 */
function EditorHandler(bopDom, fc) {
	EditorHandler.superclass.constructor.call(this, bopDom, fc);
	
	this._RELATION_EVENT = "change";

	/**
	 * 设置组件的disable状态
	 */
	this._setDisabled = function(isDisabled) {
		this._setReadonly(isDisabled);
	};
}

extend(EditorHandler, TextHandler);