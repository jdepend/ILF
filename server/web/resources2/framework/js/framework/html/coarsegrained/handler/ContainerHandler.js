/**
 * 粗粒度处理类
 * @param boDom
 * @returns {ContainerHandler}
 */
function ContainerHandler(boDom) {
	this.boDom = boDom;
	this.container = boDom ? DISLANDTODOM.getContainer(boDom) : null;
	//关联粗粒度组件
	this.Relations = boDom ? DISLAND.getRelationBo(this.boDom) : null;

	/**
	 * 根据boDom刷新粗粒度组件, 包括该粗粒度组件对应的数据岛, 子类需要覆写freshContainer
	 */
	this.fresh = function() {
		//将boDom更新至数据岛
		DISLAND.updateBO(this.boDom);
		this.freshMyself();
	};

	/**
	 *  根据boDom刷新粗粒度组件的展示内容
	 */
	this.freshMyself = function() {};

	/**
	 * 重新装载组件的数据，和freshMyself不同，reload不和数据岛发生交互
	 */
	this.reload = function(method, sourceContainerId, scopeDataIsland) {};

	/**
	 * 粗粒度组件校验
	 */
	this.validate = function() {
		return true;
	};

	/**
	 * 善后工作, 在点击按钮执行行为成功后触发.
	 * 如: 表格组件刷新表格, 并清除选中项.
	 */
	this.aftermath = function() {};
	
	/**
	 * 添加粗粒度组件关联
	 */
	this._addRelation = function() {};
}

/**
 * 粗粒度初始化
 */
ContainerHandler.prototype.init = function() {
	this._addRelation();
}