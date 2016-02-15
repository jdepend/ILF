/**
 * 细粒度组件操作基类,FCHandler负责处理所有细粒度组件,为其添加校验,关联等操作.
 * 其子类仅能覆盖 this.*** 方法.
 * FCHandler中应当仅有init()能够被外界调用.
 *
 * @param bopDom	细粒度组件数据岛
 *
 * @param fc 细粒度组件Ext容器
 *
 * @returns
 *
 */
function FCHandler(bopDom, fc) {
	//触发细粒度组件关联的事件
	this._RELATION_EVENT = "change";
	this.bopDom = bopDom;
	if(!!bopDom)
		this.boDom = bopDom.parent();
	this.fc = fc;
	
	//添加细粒度组件关联:
	//通过_addRelation,_addClientValidate,_addClientValidate为组件注册事件
	//各子类可覆写该方法
	this._addRelation = function() {
		if(!this.fc)
			return;

		//与粗粒度组件关联
		if (this.bopDom.attr(DISLAND.BOP_ISCONRELATE) == 'true') {
			var fcHandler = this;
			this.fc.addListener(this._RELATION_EVENT, function() {BOPSubmit.conSubmit(fcHandler);});
		}
		//与细粒度组件关联
		if (this.bopDom.attr(DISLAND.BOP_ISRELATE) == 'true') {
			var fcHandler = this;
			this.fc.addListener(this._RELATION_EVENT, function() {BOPSubmit.submit(fcHandler);});
		}
		//BOP改变时是否能触发粗粒度组件关联(group标签控制的关联)
		var boDom = this.boDom; 
		if (this.bopDom.attr(DISLAND.BOP_ISTIGGER) == 'true' && !!boDom) {
			this.fc.addListener(this._RELATION_EVENT, 
				function() {
					var boId = boDom.attr(DISLAND.BO_ID);
					if(observableArr[boId]) {
						lockSrceen();
						BINDDATA.bindBO(boDom, BoFinalMethod.QUERY);
						BINDDATA.bindOperate(boDom, BoFinalMethod.QUERY);
						observableArr[boId].notify();
					}
				}
			);
		}
	};
	
	//添加校验
	this._addValidate = function() {};
	//添加自定义JS事件(扩展使用)
	this._addJS = function() {};

	//获取细粒度组件的值
	this._getValue = function(){};

	//细粒度组件设值
	this._setValue = function(value) {};
	
	//设置最小值, 附带expend标签时使用
	this._setMinValue = function(value) {};
	
	//设置最大值, 附带expend标签时使用
	this._setMaxValue = function(value) {};

	//设置组件的状态
	this._setStatus = function() {
		this._setHidden(this.getInitHidden());
	};
	
	//改变range
	this._updateRange = function(){
		var updateRange = this.bopDom.children(DISLAND.BOP_RANGE);
		if(updateRange != null && updateRange.length > 0){
			var bo = DISLAND.getBODom(this.boDom.attr(DISLAND.BO_ID));
			var bop = bo.find(DISLAND.BOP + "[" + DISLAND.BIND + "=" + this.bopDom.attr(DISLAND.BIND) + "]");
			var range = bop.children(DISLAND.BOP_RANGE);
			range.remove();
			bop.append(updateRange);
			this._updateRequired(updateRange, bop);
		}
		this._updateItems(updateRange);
		if(!!!this.fc || !this.fc.el)
			return;
		if(this.fc && typeof this.fc.validate  == "function")
			this.fc.validate();
	};
	
	/**
	 * 改变组件的必填状态
	 * @param {} range
	 * @param {} bop
	 */
	this._updateRequired = function(range, bop){
		//实际的bopDom对象实际是被包裹在jquery对象的对象数组中，因此只能通过判断对象数组的长度来判读对象是否存在
		if(bop.length == 0)
			return;
		var required = range.attr(DISLAND.BOP_RANGE_REQUIRED);
		//字段label
		var fieldLabel = Ext.DomQuery.selectNode('label[for=' + DISLANDTODOM.getFCId(bop) + ']');
		if(!fieldLabel)
			return;
		
		var labelArr = [];
		//兼容IE浏览器
		if($.browser.msie) 
			labelArr = fieldLabel.innerHTML.split("</FONT>");
		else 
			labelArr = fieldLabel.innerHTML.split("</font>");
			
		//当必填时，为控件添加必填属性并在label前添加"*"
		if(required == "true") {
			this.fc.allowBlank = false;
			fieldLabel.innerHTML = "<FONT color='red'>*</FONT>" + labelArr[labelArr.length - 1];
		}
		//当非必填时，为控件取消必填属性并在label前移除"*"
		else {
			this.fc.allowBlank = true;
			fieldLabel.innerHTML = labelArr[labelArr.length - 1];
		}
	};
	
	/**
	 * 改变组件的选项
	 * @param {} range
	 */
	this._updateItems = function(range){};

	/**
	 * 改变组件的value
	 */
	this._updateValue = function(){
		var value;
		var valueDom = this.bopDom.children(DISLAND.BOP_VALUE);
		//有范围值时, 即使用expend标签时
		if(valueDom.length == 2) {
			this._setMinValue(valueDom.slice(0)[0].text);
			this._setMaxValue(getXmlNoteText(valueDom.slice(1)));
		}
		else if(valueDom.length > 0) {
			value = getXmlNoteText(valueDom);
		}
		else {
			value = getXmlNoteText(this.bopDom);
		}

		this._setValue(decodeToDisplay(getUnescapedText(value)));
	};

	/**
	 * 改变组件的status
	 */
	this._updateStatus = function(){
		var status = this.bopDom.children(DISLAND.STATUS);
		
		//表格中的bop状态
		if(status.length == 0)
			status = this.bopDom;
		
		this._setReadonly(status.attr(DISLAND.STATUS_READONLY));
		this._setDisabled(status.attr(DISLAND.STATUS_DISABLE));
		this._setHidden(status.attr(DISLAND.STATUS_HIDDEN));
	};

	/**
	 * 设置组件的disable状态
	 */
	this._setDisabled = function(isDisabled) {
		if(!!!this.fc)
			return;
		this.fc.setDisabled(true);
		if (isDisabled === "true")
			this.fc.setDisabled(true);
		else
			this.fc.setDisabled(false);
	};

	/**
	 * 设置组件的hidden状态
	 */
	this._setHidden = function(isHidden) {
		if(!!!this.fc || !this.fc.el)
			return;
		
		if(isHidden === "true"){
			this.fc.hide();
			//隐藏组件的fieldLabel
			this.fc.el.parent().parent().parent().hide(); 
		}
		else {
			this.fc.show();
			this.fc.el.parent().parent().parent().show(); 
		}
	};

	/**
	 * 设置组件的readonly状态
	 */
	this._setReadonly = function(isReadOnly) {
		if(!!!this.fc)
			return;
		
		if (isReadOnly === "true")
			this.fc.setReadOnly(true);
		else
			this.fc.setReadOnly(false);
	};
	
	/**
	 * 追加历史修改对比
	 */
	this._setHistory = function() {
		if(!(this.fc && this.fc.historyText))
			return;
		
		this._createIconDiv();
		this._createTipDiv();
	};
	
	/**
	 * 创建历史修改记录图标
	 */
	this._createIconDiv = function() {
		var tipMsg = DISLAND.getBOPLabel(this.bopDom, this.fc.historyText);
		var bopText = DISLAND.getBOPLabel(this.bopDom);
		// 历史为空或无修改不创建图标
		if (isEmpty(tipMsg) || tipMsg == bopText)
			return;
		
		var fcDom = this._getFcRealDom();
		var fcParent = this._getFcParentDom(fcDom);
		var offset = fcDom.offset();
		var iconDiv = $('div[id="history-' + this.fc.id + '"]');
		if (iconDiv.length == 0) {
			iconDiv = $('<div id="history-' + this.fc.id + '"></div>');
		}

		var left = offset.left + fcDom.width() - 16;
		var top = offset.top - 32;
		var tipContent = this._getTipContent(tipMsg);
		iconDiv.attr('class', 'history-icon');
		iconDiv.css('left', left);
		iconDiv.css('top', top);
		iconDiv.hover(function() {
			var offset1 = $(this).offset();
			var top = offset1.top + 30;
			var tip = $('#his-tipDiv');
			tip.css('top', top);
			tip.html(tipContent);
			var left = offset1.left + 30;
			if (left + tip.width() > $('body').width())
				left = $('body').width() - tip.width() - 10;
			tip.css('left', left);
			tip.css('visibility', 'visible');
			tip.fadeIn();
		}, function() {
			var tip = $('#his-tipDiv');
			tip.fadeOut();
		});
		fcParent.append(iconDiv); 
		
	};
	
	/**
	 * 创建历史修改记录提示浮动DIV
	 */
	this._createTipDiv = function() {
		var tipDiv = $('#his-tipDiv');
		if(tipDiv.length == 0) 
			tipDiv = $('<div id="his-tipDiv"></div>');
		tipDiv.attr('class', 'history-tip');
		tipDiv.css('visibility', 'hidden'); 
		tipDiv.hover(function() {
			tipDiv.stop(true, true);
			tipDiv.fadeIn();
		}, function() {
			tipDiv.fadeOut();
		});
		$('body').append(tipDiv);
	};
	
	/**
	 * return $(this.fc.getEl().dom)
	 */
	this._getFcRealDom = function() {
		return $(this.fc.getEl().dom);
	};
	
	/**
	 * 历史对比信息
	 */
	this._getTipContent = function(tipMsg) {
		return I18N.HISTORY + tipMsg;
	};
	
	this._getFcParentDom = function(fcDom) {
		return fcDom.parents('td').children('div');
	};
}

/**
 * 获取组件的初始隐藏属性
 */
FCHandler.prototype.getInitHidden = function() {
	return DISLAND.getBOPStatus(this.bopDom).attr(DISLAND.STATUS_HIDDEN);
};

/**
 * 获取组件的值
 */
FCHandler.prototype.getValue = function() {
	return this._getValue() || "";
};

/**
 * 给组件的设值
 */
FCHandler.prototype.setValue = function(value) {
	this._setValue(value);
};

/**
 * 为细粒度组件添加关联和校验
 * template method
 */
FCHandler.prototype.init = function() {
	this._addRelation();
	this._addValidate();
	this._addJS();
	this._setStatus();
	this._setHistory();
};

/**
 * 改变细粒度组件的值/状态/范围
 * template method
 */
FCHandler.prototype.update = function(){
	this._updateRange();
	this._updateValue();
	this._updateStatus();
};

/**
 * 改变细粒度组件(数据岛和组件)的状态
 * template method
 */
FCHandler.prototype.updateStatus = function(disable, hidden, readonly){
	var status = this.bopDom.children(DISLAND.STATUS);
	if(disable != undefined)
		status.attr(DISLAND.STATUS_DISABLE, disable);
	if(hidden != undefined)
		status.attr(DISLAND.STATUS_HIDDEN, hidden);
	if(readonly != undefined)
		status.attr(DISLAND.STATUS_READONLY, readonly);
	
	this._updateStatus();
};

/**
 * 将细粒度组件强行设置为readonly
 */
FCHandler.prototype.setReadonly = function(){
	var status = this.bopDom.children(DISLAND.STATUS);
	status.attr(DISLAND.STATUS_READONLY, 'true');
	this._setReadonly('true');
};

/**
 * 当页面可见宽度变化时，改变ext的细粒度组件的宽度
 */
FCHandler.prototype.resize = function() {
	var iconDiv = $('div[id="history-' + this.fc.id + '"]');
	if (iconDiv.length == 0)
		return;

	var fcDom = this._getFcRealDom();
	var offset = $(fcDom).offset();
	var left = offset.left + fcDom.width() - 16;
	var top = offset.top - 32;
	iconDiv.css('left', left);
	iconDiv.css('top', top); 
};

/**
 * 获取触发细粒度组件关联的事件名称
 */
FCHandler.prototype.getRelationEventName = function() {
	return this._RELATION_EVENT;
};

