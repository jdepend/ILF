/**
 * 嗅探浏览器的类型, 根据浏览器获取xmlDom.
 * XMLDomFactory使用了惰性加载, 并不会自动执行getInstance.
 * 使用XMLDomFactory.getInstance()可获取经jquery封装的xmlDom
 */
var XMLDomFactory = (function() {
	return {
		getInstance : function(dataIslandStr) {
			var xmlDom = "";
			if(!dataIslandStr)
				dataIslandStr = $('#' + DISLAND.hiddenId).val();
			
			if($.browser.msie && !!dataIslandStr) {
				xmlDom = new ActiveXObject("Microsoft.XMLDOM");
				xmlDom.loadXML(dataIslandStr);
			}
			else if(!!dataIslandStr){
				var domParser = new DOMParser();
				xmlDom = domParser.parseFromString(dataIslandStr, "text/xml");
			}
			
			return $(xmlDom);
		}
	};
})();