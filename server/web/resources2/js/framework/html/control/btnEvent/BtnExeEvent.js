//临时处理方法，手机端操作用于记录操作按钮dom
var _operateDom;
/**
 * 执行按钮的事件, extends ButtonEvent
 * BtnExeEvent的handleRequest负责处理按钮的行为.
 * 这里是职责链的终点。
 */
function BtnExeEvent() {
	BtnExeEvent.superclass.constructor.call(this);

	/**
	 * 处理请求，执行按钮绑定的操作
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		var btnEvent;
		if(CommonDom.isForm(this.containerDom)) {
			if(BoFinalMethod.isQuery(this.method))
				btnEvent = new BtnFormQueryEvent();
			else if(BoFinalMethod.isClear(this.method) || BoFinalMethod.isReset(this.method))
				btnEvent = new BtnFormClearEvent();
			else
				btnEvent = new BtnFormExeEvent();

			btnEvent.handleRequest(operateDom);
		}
		else if(CommonDom.isTable(this.containerDom)) {
			//table中通常不设置query按钮
			if(BoFinalMethod.isQuery(this.method)) {
				;
			}
			else if(BoFinalMethod.isReset(this.method)) {
				;
			}
			else {
				btnEvent = new BtnTableExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		}
		else {
			//page中通常不设置query按钮
			if(BoFinalMethod.isQuery(this.method)) {
				;
			}
			else {
				btnEvent = new BtnPageExeEvent();
				btnEvent.handleRequest(operateDom);
			}
		}
	};

	/**
	 * 校验
	 */
	this.validate = function() {
		var containerHandler = ContainerHandlerFactory.createContainerHandler(this.boDom);
		return containerHandler.validate(this.containerDom);
	};
}

extend(BtnExeEvent, ButtonEvent);

/**
 * 将页面数据值同步到数据岛中, 各子类需要覆写该方法
 * @param operateDom 	按钮数据岛
 */
BtnExeEvent.prototype.bindData = function(operateDom){}

/**
 * 执行按钮的行为，即通过Ga执行BO的方法
 * @param operateDom 	按钮数据岛
 */
BtnExeEvent.prototype.execute = function(operateDom) {
	this.bindData(operateDom);
	var data = this.getDataStr();
//	_operateDom = operateDom;
//	if(ANDROID_ENGINE.isTerminalEvnir()) {
//		ANDROID_ENGINE.mobileSubmit(data)
//		return;
//	}

	//执行form的持久化操作， form持久化操作完毕后不再更新数据岛
	$.ajax({
		type : "POST",
		url : appConfig.ctx + actionURL.getGaExe(),
		data : data,
		success : function(msg) {
			//窜session，跳转到登录后的第一个页面
			validateSession(msg);
			//后续操作
			var resultMsg = new ResultMsg(msg);
			var pageFlow = new PageFlow(msg);
			resultMsg.setNextHandler(pageFlow);
			
			resultMsg.handleRequest(operateDom);
		}
	});
};

/**
 * 手机端submit回调函数
 * @param {} data
 */
function submitCallback(data) {
	//后续操作
	var resultMsg = new ResultMsg(data);
	var pageFlow = new PageFlow(data);
	resultMsg.setNextHandler(pageFlow);
	resultMsg.handleRequest(_operateDom);
}
