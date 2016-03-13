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
    this.fc = DISLANDTODOM.getFC(bopDom);//表单控件

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
	this.input = this.fc;
	
//	if(this.fcDom.length == 0) {
//		this.input = this.divDom;
//	}
//	else {
//		var tempInput;
//		$.each(this.fcDom, function(){
//			if(CommonDom.isSingleInput($(this))) {
//				tempInput = $(this);
//			}
//		})
//		this.input = tempInput;
//	}
	
	//添加关联
	this._addRelation = function(){
		//与粗粒度组件关联
		if (!!this.fc && this.bopDom.attr(DISLAND.BOP_ISCONRELATE) == 'true') {
			var fcHandler = this;
			this.fc.addListener(this._RELATION_EVENT, function() {BOPSubmit.conSubmit(fcHandler);});
		}
		//与细粒度组件关联
		if (!!this.fc && this.bopDom.attr(DISLAND.BOP_ISRELATE) == 'true') {
			var fcHandler = this;
			this.input.bind(this._RELATION_EVENT, function() {BOPSubmit.submit(fcHandler);});
		}
        //BOP改变时是否能触发粗粒度组件关联(group标签控制的关联)
//        var boDom = this.boDom;
//        if (this.bopDom.attr(DISLAND.BOP_ISTIGGER) == 'true' && !!boDom) {
//            this.fc.addListener(this._RELATION_EVENT,
//                function() {
//                    var boId = boDom.attr(DISLAND.BO_ID);
//                    if(observableArr[boId]) {
//                        //lockSrceen();
//                        BINDDATA.bindBO(boDom, BoFinalMethod.QUERY);
//                        BINDDATA.bindOperate(boDom, BoFinalMethod.QUERY);
//                        observableArr[boId].notify();
//                    }
//                }
//            );
//        }
	};
	
	//添加校验
	this._addValidate = function(){
		if(!!this.input){
			//this.input.addListen(bopDom);
		}
	};

    //设置组件的状态
    this._setStatus = function() {
        this._setHidden(this.getInitHidden());
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
        if(!!!this.fc)
            return;
        if(this.fc && typeof this.fc.validate  == "function")
            this.fc.validate();

    };

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

	this._updateStatus = function(){
		var status = this.bopDom.children(DISLAND.STATUS);
        //表格中的bop状态
        if(status.length == 0)
            status = this.bopDom;

		this._setReadonly(status.attr(DISLAND.STATUS_READONLY));
		this._setDisabled(status.attr(DISLAND.STATUS_DISABLE));
		this._setHidden(status.attr(DISLAND.STATUS_HIDDEN));
	};
	
	//_setDisabled
	this._setDisabled = function(isDisabled) {
        if(!!this.fc){
            this.fc.setDisabled(true);
            this.fc.setDisabled(true);
            if (isDisabled === "true")
                this.fc.setDisabled(true);
            else
                this.fc.setDisabled(false);
        }
	};
	
	//_setHidden
	this._setHidden = function(isHidden) {
        if(!!this.fc){
            if(isHidden === "true"){
                this.fc.hide();
                //隐藏组件的fieldLabel
                this.fc.parent().hide();
            }
            else {
                this.fc.show();
                this.fc.parent().show();
            }

        }
	};
	
	//_setReadonly
	this._setReadonly = function(isReadOnly) {
		 if(!!this.fc){
             if (isReadOnly === "true")
                 this.fc.setReadOnly(true);
             else
                 this.fc.setReadOnly(false);
         }
	};

    this._getFcRealDom = function() {
        return $(this.fc.getEl().dom);
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
 * 获取组件的初始隐藏属性
 */
FCHandler.prototype.getInitHidden = function() {
    return DISLAND.getBOPStatus(this.bopDom).attr(DISLAND.STATUS_HIDDEN);
};

/**
 * 为细粒度组件添加关联和校验
 * template method
 */
FCHandler.prototype.init = function() {
    this._addRelation();
    this._addValidate();
    //this._addJS();
    this._setStatus();
    //this._setHistory();
}

/**
 * 改变细粒度组件的值/状态/范围
 * template method
 */
FCHandler.prototype.update = function(){
	this._updateRange(); 
	this._updateValue(); 
	this._updateStatus(); 
}

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
 * 获取触发细粒度组件关联的事件名称
 */
FCHandler.prototype.getRelationEventName = function() {
    return this._RELATION_EVENT;
};
