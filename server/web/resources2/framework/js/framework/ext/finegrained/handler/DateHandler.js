/**
 * DateHandler继承FCHandler(FCHandler.js), 处理日期控件
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function DateHandler(bopDom, fc) {
	DateHandler.superclass.constructor.call(this, bopDom, fc);
	this._addValidate = function() {
		if(fc.expend) {
			var min = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MIN);
			var max = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MAX);
			//min,无值后要重新设定限制
			min.addListener(this._RELATION_EVENT, function(){max.setMinValue(min.getValue() || '1900-1-1');});
			//max,无值后要重新设定限制
			max.addListener(this._RELATION_EVENT, function(){min.setMaxValue(max.getValue() || '2999-12-31');});
		}
	};
	
	this._updateRange = function(){
		if(fc.expend) {
			
		}
		else {
			var updateRange = this.bopDom.children(DISLAND.BOP_RANGE);
			var bo = DISLAND.getBODom(this.boDom.attr(DISLAND.BO_ID));
			var bop = bo.find(DISLAND.BOP + "[" + DISLAND.BIND + "=" + this.bopDom.attr(DISLAND.BIND) + "]");
			this._updateRequired(updateRange, bop);
		}
	};
	
	this._updateStatus = function() {
		if(fc.expend) {
			
		}
		else {
			var $super = new FCHandler();
			$super._updateStatus.call(this);
		}
	};
	
	//获取细粒度组件的值
	this._getValue = function() {
		if(fc.expend) {
			var min = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MIN);
			var max = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MAX);
			return (min.value || '') + DISLAND.SPLIT_COMMA + (max.value || '') ;	
		}
		else {
			return fc.value;		
		}
	};
	
	//细粒度组件设值
	this._setValue = function(value) {
		var date = this.getFormatDate(value);
		this.fc.format = 'Y-m-d';
		this.fc.setValue(date);
	};
	
	this._setMinValue = function(value) {
		var min = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MIN);
		min.format = 'Y-m-d';
		var date = this.getFormatDate(value);
		min.setValue(date);
	};
	
	this._setMaxValue = function(value) {
		var max = Ext.getCmp(fc.getId() + DISLAND.SPLIT_EXPEND + DISLAND.BOP_MAX);
		max.format = 'Y-m-d';
		var date = this.getFormatDate(value);
		max.setValue(date);
	}
}

extend(DateHandler, FCHandler);

DateHandler.prototype.getFormatDate = function(value) {
	var date = '';
	if(value && typeof value == 'object'){
		date = new Date(value.time);
		date.setHours(value.hours);
		date.setMinutes(value.minutes);	
		date.setSeconds(value.seconds);
	}
	else if(value && typeof value == 'string'){
		date = new Date();
		date.setDate(parseInt(value.split("-")[2]));
		date.setMonth(parseInt(value.split("-")[1]) - 1);	
		date.setFullYear(value.split("-")[0]);
	}
	
	return date;
};