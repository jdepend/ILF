/**
 * 表单处理js，负责所有<qeweb:form>表单的操作
 * 
 * @param boDom
 * @returns {FormHandler}
 */
function FormHandler(boDom) {
	FormHandler.superclass.constructor.call(this, boDom);

	/**
	 * 根据boDom刷新form的数据
	 */
	this._freshMyself = function(boDom) {
		alert('_freshContainer');
	};

	/**
	 * 表单校验，循环调用每个细粒度组件的校验
	 */
	this.validate = function() {
		var result = true;
		var sourceBo = this.boDom.children(DISLAND.BOP);
		
		$.each(sourceBo, function(){
			var bop = $(this);
			var fcHandler = FCHandlerFactory.createFCHandler(bop);
			if (!$(fcHandler.input).validate(bop)) {
				result = false;
				return false;
			}
		});
		return result;
	};
	
	/**
	 * 添加粗粒度组件关联
	 */
	this._addRelation = function(){
		var formDomName = $(boDom).attr(DISLAND.BO_ID);
		var sourceFcId = DISLANDTODOM.getContainer(boDom).attr(DISLAND.CONTAINER_ID);
		$(this.Relations).each(function(){
	        observableArr[formDomName] = new ContainerObservable();
	        observableArr[formDomName].sourceFcId = sourceFcId;
	        observableArr[formDomName].addObserver(new ContainerObserver(), this);
		});
	};
}
extend(FormHandler, ContainerHandler);

var observableArr = new Array();
