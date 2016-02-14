var ContainerHandlerFactory = {
	/**
	 * createContainerHandler
	 * @param boDom	粗粒度组件数据岛
	 */
	createContainerHandler : function(boDom) {
		var containerDom = DISLANDTODOM.getContainer(boDom);

		if (CommonDom.isForm(containerDom))
			return new FormHandler(boDom);
		else if (CommonDom.isTable(containerDom))
			return new TableHandler(boDom);
		else if(CommonDom.isPage(boDom)) 
			return new PageHandler(boDom);
		else
			return new ContainerHandler(boDom);
	}	
};
