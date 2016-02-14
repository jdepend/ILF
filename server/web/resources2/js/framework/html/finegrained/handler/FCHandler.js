/**
 * 细粒度组件操作基类,FCHandler负责处理所有细粒度组件,为其添加校验,关联等操作.
 * 其子类仅能覆盖 this.*** 方法.
 *
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function FCHandler(bopDom) {
	//触发细粒度组件关联的事件
	this._RELATION_EVENT = "change";
	//触发细粒度组件验证的事件
	this._VALIDATE_EVENT = "blur";
	this.bopDom = bopDom;
	if(!!this.bopDom)
		this.boDom = bopDom.parent();
	
	/*
	 * html展示的细粒度组件包括编辑组件,编辑组件的文字,如果有必填标识,还会有<font>标识, 这些信息共同组成divDom.					
	 * 例如：<div id='demo-DemoBO_BopValidate-clientBOP' >
				<div style='float:left;text-align:right;width:120.0px' > <font color=red>*</font>前台校验 : </div>
				<input type='text' name='demo-DemoBO_BopValidate-clientBOP' >
			 </div>
	 */
	this.divDom = DISLANDTODOM.getFC(this.bopDom);
	/*
	 * divDom中的信息是一个fcDom, 包括组件的文字和组件.
	 * 例：
	 * 		<div style='float:left;text-align:right;width:120.0px' > <font color=red>*</font>前台校验 : </div>
			<input type='text' name='demo-DemoBO_BopValidate-clientBOP' >
	 */
	this.fcDom = this.divDom.children();
	/*
	 * 每个细粒度组件中的编辑组件称为input.
	 * 例：
			<input type='text' name='demo-DemoBO_BopValidate-clientBOP' >
	 */
	this.input = null;
	
	if(this.fcDom.length == 0) {
		this.input = this.divDom;
	}
	else {
		var tempInput;
		$.each(this.fcDom, function(){
			if(CommonDom.isSingleInput($(this))) {
				tempInput = $(this);
			}
		})
		this.input = tempInput;
	}
	
	//添加关联
	this._addRelation = function(){
		//与粗粒度组件关联
		if (!!this.fcDom && this.bopDom.attr(DISLAND.BOP_ISCONRELATE) == 'true') {
//			var fcHandler = this;
//			this.fc.addListener(this._RELATION_EVENT, function() {BOPSubmit.conSubmit(fcHandler);});
		}
		//与细粒度组件关联
		if (!!this.fcDom && this.bopDom.attr(DISLAND.BOP_ISRELATE) == 'true') {
			var fcHandler = this;
			this.input.bind(this._RELATION_EVENT, function() {BOPSubmit.submit(fcHandler);});
		}
	};
	
	//添加校验
	this._addValidate = function(){
		if(!!this.input){
			this.input.addListen(bopDom);
		}
	};
	
	//细粒度组件设值
	this._setValue = function(value){
		this.input.val(value);
	};
	
	//获取细粒度组件的值
	this._getValue = function(){
		if(!!this.input)
			return this.input.val();
	}; 

	this._updateRange = function(){}; 
	this._updateValue = function(){};
	this._updateStatus = function(){
		var status = this.bopDom.children(DISLAND.STATUS);
		this._setReadonly(status.attr(DISLAND.STATUS_READONLY));
		this._setDisabled(status.attr(DISLAND.STATUS_DISABLE));
		this._setHidden(status.attr(DISLAND.STATUS_HIDDEN));
	};
	
	//_setDisabled
	this._setDisabled = function(isDisabled) {
		if (isDisabled === "true") 
			this.divDom.attr("disabled", "disabled");
		else 
			this.divDom.removeAttr("disabled");
	};
	
	//_setHidden
	this._setHidden = function(isHidden) {
		if (isHidden === "true") 
			this.divDom.hide();
		else
			this.divDom.show();
	};
	
	//_setReadonly
	this._setReadonly = function(isReadOnly) {
		if (isReadOnly === "true")
			this.divDom.attr("readonly", "readonly");
		else
			this.divDom.removeAttr("readonly");
	};
}

/**
 * 细粒度组件设值
 */
FCHandler.prototype.setValue = function(value) {
	this._setValue(value);
}

/**
 * 获取组件的值
 */
FCHandler.prototype.getValue = function() {
	return this._getValue() || "";
} 

/**
 * 为细粒度组件添加关联和校验
 * template method
 */
FCHandler.prototype.init = function() {
	this._addValidate();
}

/**
 * 改变细粒度组件的值/状态/范围
 * template method
 */
FCHandler.prototype.update = function(bopDom, divDom){
	this._updateRange(); 
	this._updateValue(); 
	this._updateStatus(); 
}