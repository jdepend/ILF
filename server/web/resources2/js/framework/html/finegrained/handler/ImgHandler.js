
/**
 * Img继承FCHandler(FCHandler.js), 处理图片
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function ImgHandler(bopDom) {
	ImgHandler.superclass.constructor.call(this, bopDom);
	
	this._updateValue = function(){
		var value = bopDom.children(DISLAND.BOP_VALUE).text();
		this.input.attr('src', value);
	}
}

extend(ImgHandler, FCHandler);