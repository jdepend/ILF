/**
 * 将数据转换为数据岛dom
 */
var TRANSDISLANDDOM = {

	transMaxValue : function(maxValue) {
		var xmlStr = "<value expend=\"max\">" + maxRawValue +"</value>"
		return XMLDomFactory.getInstance(xmlStr);
	}
};