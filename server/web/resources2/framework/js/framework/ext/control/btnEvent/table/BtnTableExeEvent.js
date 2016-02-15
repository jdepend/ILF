/**
 * 执行table按钮的事件, extends BtnExeEvent
 * BtnTableExeEvent的handleRequest负责处理表格级按钮的除JS,query外的其它方法
 *
 */
function BtnTableExeEvent() {
	BtnTableExeEvent.superclass.constructor.call(this);

	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		
		//按钮未绑定操作时无需校验
		if(!operateDom.attr(DISLAND.OPERATE_METHOD))
			this.execute(operateDom);
		//行级按钮无需校验 
		else if(operateDom.attr(DISLAND.OPERATE_EXPEND))
			this.execute(operateDom);
		else if(this.validate())
			this.execute(operateDom);
	};
}

extend(BtnTableExeEvent, BtnExeEvent);


/**
 * 将table的值同步到数据岛中
 */
BtnTableExeEvent.prototype.bindData = function(operateDom){
	BINDDATA.bindBOList(this.boDom, operateDom);
};

/**
 * 该方法重新生成BtnTableExeEvent实例, 并用this和operateDom初始化BtnExeEvent.
 */
BtnTableExeEvent.prototype.getNewEvent = function(operateDom) {
	var cloneObj = new BtnTableExeEvent();
	
	cloneObj.operateDom = operateDom;						//按钮operate Dom
	cloneObj.method = DISLAND.operate_BOMethod(operateDom);	//按钮绑定的方法名
	
	//以下属性全部从this中获取.
	cloneObj.buttonDom = this.buttonDom;				//按钮的dom, 即button控件
	cloneObj.containerDom = this.containerDom;			//按钮所在的粗粒度组件
	cloneObj.btnName = this.btnName;					//btnName
	cloneObj.boDom = this.boDom;						//按钮所属的BO或BOList
	cloneObj.groupFCHandlers = this.groupFCHandlers;	//与按钮同一组的细粒度组件, FCHandler
	cloneObj.optDomArr = this.optDomArr;				//operateDom缓存, 使ButtonEvent.init()仅执行一次
	cloneObj.nextHandler = this.nextHandler;			//下一个处理者
	
	return cloneObj;
};