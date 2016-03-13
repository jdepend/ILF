/**
 * TextHandler继承FCHandler(FCHandler.js), 处理文本域textfield或多行文本域textarea
 * 
 * @param bopDom
 *            细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function LabelHandler(bopDom) {
	LabelHandler.superclass.constructor.call(this, bopDom);

	// 获取细粒度组件的值
	this._getValue = function() {
		return this.fc.innerHTML;
	};

	// 细粒度组件设值
	this._setValue = function(value) {
		this.fc.innerHTML = value;
	};
}
extend(LabelHandler, FCHandler);