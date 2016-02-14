/**
 * FCHandlerFactory
 */
var FCHandlerFactory = {
	/**
	 * createFCHandler
	 * @param bopDom	细粒度组件数据岛
	 */
	createFCHandler : function(bopDom) {
		var divDom = DISLANDTODOM.getFC(bopDom);
		var fcDom = divDom.children();

		if (CommonDom.isTextDom(fcDom))
			return new TextHandler(bopDom);
		else if (CommonDom.isLableDom(fcDom))
			return new LabelHandler(bopDom);
		else if (CommonDom.isSelect(fcDom))
			return new SelectHandler(bopDom);
		else if (CommonDom.isRadio(fcDom))
			return new RadioHandler(bopDom);
		else if (CommonDom.isCheckbox(fcDom))
			return new CheckboxHandler(bopDom);
		else if (CommonDom.isImg(fcDom))
			return new ImgHandler(bopDom);
		else if (CommonDom.isOptTrans(fcDom))
			return new OptTransHandler(bopDom);
		else
			return new FCHandler(bopDom);
	}	
};
