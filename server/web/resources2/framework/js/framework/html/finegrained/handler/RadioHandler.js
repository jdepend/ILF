
/**
 * RadioHandler继承FCHandler(FCHandler.js), 处理单选按钮组
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function RadioHandler(bopDom) {
	this._RELATION_EVENT = 'click';
	RadioHandler.superclass.constructor.call(this, bopDom);

	//添加关联
	this._addRelation = function(){
		if (this.bopDom.attr(DISLAND.BOP_ISRELATE) != 'true')
			return;

		//为每个radio都添加事件
		var fcHandle = this;
		var relationEvent = this._RELATION_EVENT;
		$.each(this.fcDom,function(){
			if(CommonDom.isRadio($(this))) {
				$(this).bind(relationEvent, function() {BOPSubmit.submit(fcHandle);});
			}
		});
	};

	
	//获取细粒度组件的值
	this._getValue = function() {
		var tempVal = '';
		$.each(this.fcDom,function(){
			if($(this).attr('checked')){
				tempVal = $(this).val();
			}
		});
		return tempVal;
	}
	
	//_setDisabled
	this._setDisabled = function(isDisabled) {
		
	};

	//_setReadonly
	this._setReadonly = function(isDisabled) {
		
	};
}

extend(RadioHandler, FCHandler);