//扩展ext vtype
var _max = 0;
Ext.apply(Ext.form.VTypes,{
	//范围验证
  	bopRange: _bopRange,
  	bopRangeText: I18N.RANGE_INPUT_ERROR
});

function _bopRange(val,vc){
	var island = getDataIsland();
	var bopIdArray = vc.id.split("-");
	var vcParent = vc.findParentByType("form");
	if(vcParent != null && vcParent != 'undefined'){
		if(vcParent.getId().indexOf("-hiddenForm") > 0)
			bopIdArray[0] = bopIdArray[0] + "-hiddenForm";
	}
	
	var ddom = island.find("#" + bopIdArray[0]);
	var rangelist;
	if(island.find("#" + bopIdArray[0]).length > 0 && island.find("#" + bopIdArray[0])[0].tagName == DISLAND.BOLIST)
		rangelist = ddom.find(DISLAND.COLUMNINFO).children("column[bind='"+bopIdArray[2]+"']").slice(0).find(DISLAND.BOP_RANGE);
	else
		rangelist = ddom.children("bop[bind='"+bopIdArray[2]+"']").slice(0).find(DISLAND.BOP_RANGE);
	
	var isIn = true;
	var vtype = this;
	var i = -1;
	var vrt = false;
	//非空校验
	if(rangelist.attr(DISLAND.BOP_RANGE_REQUIRED) != "true" && vc.getValue() == "")
		return true;
	
	var minLength = rangelist.attr(DISLAND.BOP_RANGE_MINLENGTH);
	var maxLength = rangelist.attr(DISLAND.BOP_RANGE_MAXLENGTH);
	var valLength = strlen(vc.getValue());
	
	if(minLength && !maxLength){
		if(valLength < minLength){
			vtype.bopRangeText = I18N.RANGE_NOT_LESS + minLength + I18N.RANGE_LENGTH_LESS_2;
			return false;
		}
	}
	if(maxLength && !minLength){
		if(valLength > maxLength){
			vtype.bopRangeText = I18N.RANGE_NOT_LONG + maxLength + I18N.RANGE_LENGTH_LESS_2;
			return false;
		}
	}
	
	if(maxLength && minLength){
		if(valLength > maxLength || valLength < minLength){
			vtype.bopRangeText = I18N.RANGE_LENGTH_MUST_1 + minLength + DISLAND.SPLIT_EXPEND + maxLength + I18N.RANGE_LENGTH_MUST_2 + I18N.RANGE_LENGTH_REMARK;
			return false;
		}
	}
	rangelist.children().each(function(){
		var range = $(this);
		var type = range.context.nodeName;
		var logic = range.attr(DISLAND.BOP_RANGE_RULE);
		i++;	
		if(type == DISLAND.BOP_RANGE_ENUM || type == DISLAND.BOP_RANGE_SEQUENCE){
			//客户端校验
			vrt = cValidate(vc,range,vtype);
		}
		else if(type == DISLAND.BOP_RANGE_LOGIC){
			return true;
		}
		
		if(i == 0){
			isIn = vrt;
			if(!isIn && range.next().attr(DISLAND.BOP_RANGE_RULE) == DISLAND.BOP_RANGE_AND) {
				return false;
			}
		}
		else {
			switch (logic) {
				case DISLAND.BOP_RANGE_AND://AND
					isIn = isIn && vrt;
					break;
				case DISLAND.BOP_RANGE_OR://OR
					isIn = isIn || vrt;
					break;
				case DISLAND.BOP_RANGE_NOT://NOT
					isIn = isIn && !vrt;
					break;
			}
			if(!isIn && logic == DISLAND.BOP_RANGE_AND) {
				return false;
			}
		}
	});		
	return isIn;
}

//判断value是否在连续型范围内
function isINSequence(value, max, min, step, scale) {	
	if(Number(scale) > 0){
		var flag = String(value).match("^([/-]?)\\d+(\\.\\d{1,"+scale+"})?$");
		if(flag == null) 
			return false;
	}
	var lower = NumberUtil.sub(value,min);
	var upper = NumberUtil.sub(value,max);
	if(lower < 0 || 0 < upper)
		return false;
	if(Number(step) == 0)
		return true;
	return NumberUtil.mod(lower, step) == 0;
}

//判断value是否在枚举型范围内
function isINEnum(value, range){
	if(!!value){
		return range.find(DISLAND.BOP_RANGE_ITEM + "[" + DISLAND.BOP_VALUE + "='" + value + "']").length > 0;
	}
	else {
		return true;
	}
}

//客户端校验
function cValidate(vc, range, vtype){
	var rt = true;
	//连续型范围校验
	if(range.context.nodeName == DISLAND.BOP_RANGE_SEQUENCE){
		if(isNaN(vc.getValue())){
			vtype.bopRangeText = I18N.RANGE_NAN;
			return false;
		}
		var max = getRealValue(range.find(DISLAND.BOP_RANGE_MAX));
		var min = getRealValue(range.find(DISLAND.BOP_RANGE_MIN));
		var step = getRealValue(range.find(DISLAND.BOP_RANGE_STEP));
		var scale = getRealValue(range.find(DISLAND.BOP_RANGE_SCALE));
		rt = isINSequence(vc.getValue(),max,min,step,scale);
		if(!rt){
			var errMsg = I18N.RANGE_MUST_IN + min + I18N.RANGE_MUST_IN_TO + max +I18N.RANGE_MUST_IN_END;
			if(Number(scale) > 0){
				errMsg += ","+I18N.RANGE_SCALE+scale;
			}
			if(Number(step)!=0) {
				errMsg += ","+I18N.RANGE_MUST_IN_STRP+step;
			}
			vtype.bopRangeText = errMsg;
		}
	}
	else if(range.context.nodeName == DISLAND.BOP_RANGE_ENUM) {
		var value = vc.getValue();
		if(CommonDom.isSelect(vc)){
			value = vc.getRawValue();
		}
		rt = isINEnum(value, range);
		if(!rt){
			vtype.bopRangeText = I18N.RANGE_NOTIN_RANGE;
		}
	}
	return rt;
}

//服务器端校验
function serverValidate(fcHandler){
	var flag = true;
	var fc = fcHandler.fc;
	var data = {};
	data.vcId = fc.id;
	data.value = fcHandler.getValue();
	var rangelist = fcHandler.bopDom.find(DISLAND.BOP_RANGE);
	rangelist.children().each(function(){
		if(fcHandler instanceof LabelHandler)
			return true;
		
		var type = $(this).context.nodeName;
		if(type == DISLAND.BOP_RANGE_LOGIC){
			data.rangeClass = $(this).attr(DISLAND.BOP_RANGE_LOGIC_CLASS);
			//远程提交
			$.ajax({
				type: "POST",
				url: appConfig.ctx + actionURL.getFcServerValidate(),
				async: false,
				data: data,
				success: function(msg){
					if(msg != ""){
						fc.isValid(false);
						fc.markInvalid(msg);
						flag = false;
					}
				}
			});
			return false;
		}
	});
		
	return flag;
};

//增加EditorGridPanel的通用校验方法
Ext.apply(Ext.grid.EditorGridPanel.prototype,{
	isValidate : function(records){
		var cm = this.cm || this.colModel;
			var f = true;
			var column = {};
			for (var i = 0; i < cm.columns.length; i++) {
				var dataIndex = cm.getDataIndex(i);
				column[dataIndex] = i;
			}
			for (var i = 0; i < records.length; i++) {
				var o = records[i].data;
				var rowindex = i;// 行id
				for (var n in o) {
					var colindex = column[n];
					if (!!colindex && !!cm.columns[colindex].editor) {
						if (!cm.columns[colindex].editor.fieldLabel){
							cm.columns[colindex].editor.fieldLabel = cm.columns[colindex].header;
						}
						
						var flag = true;
						//给可编辑控件设值 
						if(typeof cm.columns[colindex].editor.setValue == 'function') {
							cm.columns[colindex].editor.setValue(o[n]);
							//设值后校验
							flag = cm.columns[colindex].editor.validate();
						}
							
						f = f && flag;
						if (!flag) {
							//给不通过校验的具体空格增加错误css样式（Ext中form的样式）
							Ext.get(this.getView().getCell(rowindex, colindex)).addClass('x-form-invalid');
						}
					} else {
						continue;
					}
				}
			}
			return f;
	}
});