/**
 * FileHandler继承FCHandler(FCHandler.js), 处理文件上传控件
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function FileHandler(bopDom, fc) {
	FileHandler.superclass.constructor.call(this, bopDom, fc);
	
	//获取细粒度组件的值
	this._getValue = function() {
		var check = Ext.getCmp(DISLANDTODOM.getFileCheckID(fc.getId()));
		return check ? check.value : fc.value;
	};
	
	//细粒度组件设值
	this._setValue = function(value) {
		var check = Ext.getCmp(DISLANDTODOM.getFileCheckID(fc.getId()));
		if(check)
			check.setValue(value);	
	};

	//改变value
	this._updateValue = function() {
		this._setValue(bopDom.children(DISLAND.BOP_DATA).slice(0).attr(DISLAND.BOP_DATA_URL));
	};
	
	/**
	 * 历史对比信息
	 */
	this._getTipContent = function(tipMsg) {
		var href;
		if (isHttp(tipMsg))
			href = appConfig.ctx + tipMsg;
		else
			href = tipMsg;
		var fc = this._getFcRealDom();
		return I18N.HISTORY + '<br/><a href="' + href + '" target="_blank">' + I18N.FILE_DOWNLOAD + '</a>';
	};
	
	/**
	 * 设置组件的hidden状态
	 */
	this._setHidden = function(isHidden) {
		if(!this.fc || !this.fc.el)
			return;
		
		if(isHidden === "true") {
			this.fc.el.parent().parent().parent().parent().parent().hide();
			//FileHandler第一次被初始化的标识, 为了解决附件隐藏后"上传"按钮窜位的bug而做此判断
			if(!this.loadSign) {
				this.fc.el.parent().parent().parent().hide(); 
				this.fc.hide();
			}
		}
		else {
			this.fc.show();
			this.fc.el.parent().parent().parent().parent().parent().show(); 
			this.fc.el.parent().parent().parent().show(); 
		}
	};
	
	/**
	 * 设置组件的readonly状态
	 */
	this._setReadonly = function(isReadOnly) {
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
		//附件的校验区域
		var blank = Ext.getCmp(DISLANDTODOM.getFileCheckID(this.fc.getId()));
		blank.allowBlank = required != 'true';
		
		//修改控件的必填项符号
		//附件的label, 多附件时需要使用DISLANDTODOM.getFileCheckID(fc.getId())
		//IE7/8时, 需要使用Ext.DomQuery.selectNode('LABEL[FOR=' + fc.getId() + ']')
		var fieldLabel = Ext.DomQuery.selectNode('label[for=' + fc.getId() + ']')
				|| Ext.DomQuery.selectNode('LABEL[FOR=' + fc.getId() + ']')
				|| Ext.DomQuery.selectNode('label[for=' + DISLANDTODOM.getFileCheckID(fc.getId()) + ']')
				|| Ext.DomQuery.selectNode('LABEL[FOR=' + DISLANDTODOM.getFileCheckID(fc.getId()) + ']');
				
		if(!fieldLabel)
			return;
			
		var labelArr = [];
		// 兼容IE7/8浏览器
		if ($.browser.msie)
			labelArr = fieldLabel.innerHTML.split("</FONT>");
		else
			labelArr = fieldLabel.innerHTML.split("</font>");

		// 当必填时，为控件添加必填属性并在label前添加"*"
		if (required == "true") {
			fieldLabel.innerHTML = "<FONT color='red'>*</FONT>"+ labelArr[labelArr.length - 1];
		}
		// 当非必填时，为控件取消必填属性并在label前移除"*"
		else {
			fieldLabel.innerHTML = labelArr[labelArr.length - 1];
		}
	};
}

extend(FileHandler, FCHandler);

/**
 * FileHandler第一次被初始化的标识, 为了解决附件隐藏后"上传"按钮窜位的bug而设置该变量
 */
FileHandler.prototype.loadSign = false;