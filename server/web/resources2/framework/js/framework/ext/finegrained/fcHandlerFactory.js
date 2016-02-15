/**
 * FCHandlerFactory
 */
var FCHandlerFactory = {
	/**
	 * createFCHandler
	 * @param bopDom	细粒度组件数据岛
	 * @param fc  细粒度组件
	 */
	createFCHandler : function(bopDom, fcObj) {
		var fc = fcObj || DISLANDTODOM.getFC(bopDom);
		
		if (CommonDom.isLabelDom(fc))
			return new LabelHandler(bopDom, fc);
		else if (CommonDom.isTextDom(fc))
			return new TextHandler(bopDom, fc);
		else if (CommonDom.isHidden(fc))
			return new HiddenHandler(bopDom, fc);
		else if (CommonDom.isSelect(fc))
			return new SelectHandler(bopDom, fc);
		else if (CommonDom.isRadio(fc))
			return new RadioHandler(bopDom, fc);
		else if (CommonDom.isCheckbox(fc))
			return new CheckboxHandler(bopDom, fc);
		else if (CommonDom.isImg(fc))
			return new ImgHandler(bopDom, fc);
		else if (CommonDom.isOptTrans(fc))
			return new OptTransHandler(bopDom, fc);
		else if (CommonDom.isDate(fc))
			return new DateHandler(bopDom, fc);
		else if (CommonDom.isFile(fc))
			return new FileHandler(bopDom, fc);
		else if (CommonDom.isAnchor(fc))
			return new AnchorHandler(bopDom, fc);
		else if (CommonDom.isEditorDom(fc))
			return new EditorHandler(bopDom, fc);
		else
			return new FCHandler(bopDom, fc);
	}	
};
