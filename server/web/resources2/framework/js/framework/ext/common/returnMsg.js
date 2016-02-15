/**
 * 展示返回提示信息
 */
function showReturnMsg(){
	var message = $('#returnMsg').val();
	//当提示信息为空时不显示
	if(message && message != 'null'){
		var pageDom = DISLAND.getDataIsland().children(DISLAND.PAGE);
		var tipsType = pageDom.attr(DISLAND.TIPS_TYPE);
		//普通
		if('simple' == tipsType){
			var delay = pageDom.attr(DISLAND.TIPS_DELAY);
			showTips.showOK(message, (isNaN(delay) ? 10 : delay));
		}
		//弹出式
		else if('popup' == tipsType){			
			var display = pageDom.attr(DISLAND.TIPS_DISPLAY);
			if("0" === display && this.success)
				return;
			showMSG.showOK(message);
		}
	}
}