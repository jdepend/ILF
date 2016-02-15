
/**
 * Img继承FCHandler(FCHandler.js), 处理图片
 * @param bopDom	细粒度组件数据岛
 * @param fc 细粒度组件Ext容器
 * @returns
 */
function ImgHandler(bopDom, fc) {
	ImgHandler.superclass.constructor.call(this, bopDom, fc);
	
	this._IMG_SUFFIX = "img";
	
	this.img = Ext.getCmp(fc.getId() + DISLAND.SPLIT_LINE + this._IMG_SUFFIX);
	
	this._updateValue = function(){
		var value = bopDom.children(DISLAND.BOP_VALUE).text();
		this.img.setSrc(value);
	};
	
	this._updateRange = function(){};
	
	this._setReadonly = function(isReadOnly) {
	
	};
	
	this._getValue = function() {
		return this.img.getSrc();
	};
	
	//细粒度组件设值
	this._setValue = function(value) {
		//系统内部图片，追加项目路径
		if(isHttp(value))
			this.img.setSrc(value);
		else
			this.img.setSrc(appConfig.ctx + value);
	};

	this._getFcRealDom = function() {
		return $(this.fc.getEl().dom).find('img');
	};

	this._getTipContent = function(tipMsg) {
		var src;
		if (isHttp(tipMsg))
			src = appConfig.ctx + tipMsg;
		else
			src = tipMsg;
		var fc = this._getFcRealDom();
		return I18N.HISTORY + '<br/><img src="' + src + '" width="' + fc.width()
				+ '" height="' + fc.height() + '"/>';
	};
}

extend(ImgHandler, FCHandler);