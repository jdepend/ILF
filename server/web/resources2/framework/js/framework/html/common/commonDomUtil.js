/**
 * table复选框，全选
 */
function selectAll(obj,tableId){
	var allObj = $('[name=' + tableId + '_sm]');
	allObj.each(function(){
		 $(this).attr("checked", obj.checked);
	});
}

/**
 * 将table选中数据推送出去
 */
function doSelect(tableId){
	var boDomList = "";
	//获取选中集合
	var aArray = [];
	var allObj = $('[name=' + tableId + '_sm]');
	allObj.each(function(){
		if($(this).attr("checked")){
			aArray.push($(this).val());
		}
	});

	DISLAND.getDataIsland().children(DISLAND.BOLIST).each(
		function() {
			boDomList += "<" + DISLAND.BOLIST + " " + DISLAND.BIND + "='"
					+ $(this).attr(DISLAND.BIND) + "'>";
			$(this).children(DISLAND.BO).each(function() {
				if (isInArray(aArray, $(this).attr(DISLAND.BO_INDEX))) {
					boDomList += xmlToString($(this));
				}
			});
			boDomList += "</" + DISLAND.BOLIST + ">"
		});
		if(!opener){
			alert("error...");
			return;
	}
	opener.fillValue(boDomList);
	window.close();
}

/**
 * 父页面填值
 */
function fillValue(boDomList){

	return;
}

/**
 * 获取关联细粒度组件 弹出框返回值
 */
function getValue(boDoms,bindName){
	var bind;
	var value ="";

	 boDoms.children(DISLAND.BOLIST).each(function(){
		bind = $(this).attr(DISLAND.BIND);
		$(this).children(DISLAND.BO).children(DISLAND.BOP).each(function(){
			if ((bind + DISLAND.SPLIT_POINT + $(this).attr(DISLAND.BIND)) == bindName){
			    value += (value == "" ? "" : DISLAND.SPLIT_COMMA) + getXmlNoteText($(this));
			}
		});
	});
	return value;
}

/**
 * 返回的bodoms 填充对应的Container
 */
function fillContainerValue(boDoms,buttonEvent){
	var bind ;
	boDoms.children(DISLAND.BOLIST).each(function(){
		bind = $(this).attr(DISLAND.BIND);
		$(this).children(DISLAND.BO).children(DISLAND.BOP).each(function(){
			$(this).attr(DISLAND.BIND, bind + DISLAND.SPLIT_POINT + $(this).attr(DISLAND.BIND));
		});
	});
	if((buttonEvent.boDom).is(DISLAND.BOLIST)){
		boDoms.children(DISLAND.BOLIST).children(DISLAND.BO).each(function(){
			buttonEvent.boDom.append($(this));
		});
		var containerHandler = ContainerHandlerFactory.createContainerHandler(buttonEvent.boDom);
		containerHandler.fresh();
	}
}
