/**
 * OptTrans处理双向选择的动态操作
 */
var OptTrans = {};

/**
 * 将ObjSource上选中的元素移至ObjTarget中, 并在ObjSource中删除选中的元素
 */
OptTrans.add = function(sourceId, targetId) {
	var ObjSource = $('#' + sourceId);
	var ObjTarget = $('#' + targetId);
	if (!!ObjSource.val()) {
		var html = ObjSource.find("option:selected");
		ObjTarget.append(html);
		OptTrans.aftermath(ObjSource, ObjTarget);
	}
}
/**
 * 将ObjSource的全部选元素移至ObjTarget中, 并删除ObjSource的所有选元素
 */
OptTrans.addAll = function(sourceId, targetId) {
	var ObjSource = $('#' + sourceId);
	var ObjTarget = $('#' + targetId);
	ObjTarget.append(ObjSource.html()); 
	ObjSource.empty(); 
	OptTrans.aftermath(ObjSource, ObjTarget);
}

/**
 * 为了解决双向选择改变时导致的样式问题而添加下面的善后处理
 */
OptTrans.aftermath = function(ObjSource, ObjTarget) {
	ObjSource.attr("style", "width:100%");
	ObjTarget.attr("style", "width:100%");
}

/**
 * 双向选择继承FCHandler(FCHandler.js), 处理双向选择
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function OptTransHandler(bopDom) {
	this.divDom = DISLANDTODOM.getFC(bopDom);
	this.fcDom = this.divDom.children();
	//fcDom包括下双向选择,双向选择的文字,如果有必填标识,还会有<font>标识.
	var optTransSelect;
	if(this.fcDom.length == 0) {
		optTransSelect = this.fcDom;
	}
	else {
		this.fcDom.each(function(){
			if(CommonDom.isOptTrans($(this))) {
				optTransSelect = $(this);
			}
		});
	}
	
	OptTransHandler.superclass.constructor.call(this, bopDom, optTransSelect);
	var targetId = this.divDom.attr('id') + "_target";
	var targetSelect = $('#' + targetId);
	var sourceId = this.divDom.attr('id') + "_source";
	var sourceSelect = $('#' + sourceId);
	var buttons = this.fcDom.find('input');
	
	//获取细粒度组件的值,optTransSelect返回Array
	this._getValue = function() {
		var result = [];
		targetSelect.children().each(function() {
			result.push($(this).val());
		});
		
		return result;
	};
	
	//_setDisabled
	this._setDisabled = function(isDisabled) {
		if (isDisabled === "true") {
			targetSelect.attr("disabled", "disabled");
			sourceSelect.attr("disabled", "disabled");
			buttons.each(function() {
				$(this).attr("disabled", "disabled");
			});
		}
		else { 
			this.targetSelect.removeAttr("disabled");
			this.sourceSelect.removeAttr("disabled");
			buttons.each(function() {
				$(this).removeAttr("disabled");
			});
		}
	};
	//_setReadonly
	this._setReadonly = function(isReadOnly) {
		if (isReadOnly === "true") {
			targetSelect.attr("readonly", "readonly");
			sourceSelect.attr("readonly", "readonly");
			buttons.each(function() {
				$(this).attr("disabled", "disabled");
			});
		}
		else {
			targetSelect.removeAttr("readonly");
			sourceSelect.attr("readonly", "readonly");
			buttons.each(function() {
				$(this).removeAttr("disabled");
			});
		}
	};
}

extend(OptTransHandler, FCHandler);