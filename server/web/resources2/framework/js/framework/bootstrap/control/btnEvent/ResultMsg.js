/**
 * 执行按钮的持久化行为后，职责链将流至ResultMsg，
 * ResultMsg用于判断是否需要在执行按钮的行为后弹出提示。
 */
function ResultMsg(msg) {
	ResultMsg.superclass.constructor.call(this);
	var jsonMst = StringToJSON(msg) || new Array();
	this.success = jsonMst[ConstantJSON.MSG_SUCCESS];
	this.msg = jsonMst[ConstantJSON.MSG];
	this.path = jsonMst[ConstantJSON.MSG_PATH];
	this.closePage = jsonMst[ConstantJSON.CLOSE_PAGE];
	
	/**
	 * 处理后置提示，即操作结果提示
	 * @param operateDom 按钮数据岛
	 */
	this.handleRequest = function(operateDom) {
		this.init(operateDom);

		//按钮用途为单纯的上下文跳转,不弹出提示框
		if(!operateDom.attr(DISLAND.OPERATE_METHOD)) {
			this.nextHandler.handleRequest(operateDom);
		}
		//如果操作失败，无论是否设置弹出后置提示，系统都将弹出失败信息
		else if(!this.success) {
			if(!this.msg || this.msg == 'null')
				this.msg = I18N.MSG_OPERATION_FAILURE;
			
			ResultMessage.showMsg(this.success, this.msg, true, false);
		}
		//如果操作成功且不进行页面跳转
		else if(this.success && (!this.path || this.path == 'null')) {
			if(!this.msg || this.msg == 'null')
				this.msg = I18N.MSG_OPERATION_SUCCESS;
			//刷新父页面
			if(this.closePage){
				this.nextHandler.handleRequest(operateDom);
			}
			//弹出后置提示
			else {
				ResultMessage.showMsg(this.success, this.msg, operateDom.attr(DISLAND.OPERATE_HASMSG), false);
				
				if(operateDom.attr(DISLAND.OPERATE_NOTFRESH) != 'true'){
					//系统自带的修改得到的dom是表单的DOM，而需要更新的是表单对应的列表，因此需要做一下判断
					//系统自带的修改得到的dom是表单的DOM，而需要更新的是表单对应的列表，因此需要做一下判断
					var resultDom = DISLAND.getUpdateTableDom(this.boDom) || this.boDom;
					ContainerHandlerFactory.createContainerHandler(resultDom).aftermath();
				}
			}
		}
		//操作成功,继续向下流转
		else {
			this.nextHandler.handleRequest(operateDom);
		}
	};
}

extend(ResultMsg, ButtonEvent);