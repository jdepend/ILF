/**
 * 按钮的JS事件处理者,在执行按钮绑定的BO方法前执行extends ButtonEvent
 */
function BtnJSEvent() {
	BtnJSEvent.superclass.constructor.call(this);

	/**
	 * 处理请求，即执行按钮的行为
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		var jsMethods = operateDom.attr(DISLAND.OPERATE_JS_BEFORE);
		//JS函数的执行结果
		var result = true;
		
		//按钮是否绑定了js方法
		if(jsMethods) {
			this.disable();
			var methodArr = DISLANDTODOM.getMethodArr(jsMethods);
			if(methodArr.length > 0) {
				this.bindData();
				for(var i = 0, length = methodArr.length; i < length; i++) {
					//执行JS方法
					result = eval(methodArr[i]);
				}
			}
			this.unDisable();
		}

		//如果js函数返回true或没有返回值, 职责链继续向下流转
		//undefined != false is true, null != false is true
		if(result != false && this.nextHandler)
			this.nextHandler.handleRequest(operateDom);
	};
}

extend(BtnJSEvent, ButtonEvent);

/**
 * 将数据同步到数据岛
 */
BtnJSEvent.prototype.bindData = function() {
	//表单中的按钮
	if(this.boDom.is(DISLAND.BO))
		BINDDATA.bindBO(this.boDom, this.method);
	//表单中的按钮
	else if(this.boDom.is(DISLAND.BOLIST))
		BINDDATA.bindBOList(this.boDom, this.operateDom);
};