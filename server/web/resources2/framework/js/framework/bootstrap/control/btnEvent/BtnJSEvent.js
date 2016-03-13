/**
 * 按钮的JS事件处理者,extends ButtonEvent
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
		//按钮是否绑定了js方法
		if(jsMethods) {
			this.disable();
			var methodArr = DISLANDTODOM.getMethodArr(jsMethods);
			for(var i = 0, length = methodArr.length; i < length; i++) {
				//执行JS方法
				eval(methodArr[i]);
			}
			this.unDisable();
		}
		//职责链继续向下流转
		this.nextHandler.handleRequest(operateDom);
	}
}

extend(BtnJSEvent, ButtonEvent);
