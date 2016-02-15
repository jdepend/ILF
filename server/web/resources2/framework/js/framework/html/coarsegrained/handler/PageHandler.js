/**
 * Page处理类
 * @param pageDom
 * @returns {ContainerHandler}
 */
function PageHandler(pageDom) {

	/**
	 * 善后工作, 在点击按钮执行行为成功后触发. 表格组件刷新表格, 并清除选中项.
	 */
	this.aftermath = function() {
		DISLAND.getDataIsland().children().each(function() {
			if(!$(this).is(DISLAND.BOLIST))
				return true;

			var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
			containerHandler.aftermath();
		});
	};
	
	/**
	 * 整体页面校验
	 */
	this.validate = function() {
		var result = true;
		//循环执行页面上每个粗粒度组件的validate
		DISLAND.getDataIsland().children().each(function() {
			var containerHandler = ContainerHandlerFactory.createContainerHandler($(this));
			if(containerHandler instanceof PageHandler)
				return true;
			
			if(!containerHandler.validate()){
				result = false;
				return false;
			}
		});
		
		return result;
	};
}

