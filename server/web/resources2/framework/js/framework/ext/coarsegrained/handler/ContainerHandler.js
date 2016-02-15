/**
 * 粗粒度处理类
 * @param boDom
 * @returns {ContainerHandler}
 */
function ContainerHandler(boDom) {
	this.boDom = boDom;
	this.container = boDom ? Ext.getCmp(boDom.attr(DISLAND.BO_ID)) : null;
	//关联的粗粒度组件
	this.relations = boDom ? DISLAND.getRelationBo(this.boDom) : null;

	/**
	 * 根据boDom刷新粗粒度组件, 包括该粗粒度组件对应的数据岛, 子类需要覆写freshContainer
	 */
	this.fresh = function() {
		//将boDom更新至数据岛
		DISLAND.updateBO(this.boDom);
		if(this.boDom.attr(DISLAND.STATUS_HIDDEN) != 'true') {
			this.show();
			this.freshMyself();
		}
		else {
			this.hide();
		}
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
	
	/**
	 * 隐藏粗粒度组件
	 */
	this.hide = function() {
		if(this.container)
			this.container.hide();
	};
	
	/**
	 * 显示粗粒度组件
	 */
	this.show = function() {
		if(this.container)
			this.container.show();
	};
	
	/**
	 * 组件是否隐藏
	 */
	this.isHidden = function() {
		return this.container ? this.container.hidden : false;
	};
}

/**
 * 粗粒度初始化
 */
ContainerHandler.prototype.init = function() {
	this._addRelation();
}
