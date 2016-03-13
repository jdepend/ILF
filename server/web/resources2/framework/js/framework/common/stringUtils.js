/**
 * 判断字符串是否为空
 * @param {} str 待判断的字符串
 */
function isEmpty(str){
	return str == null || str == "" || str == 'undefined' || str == 'null';
}

/**
 * 判断字符串是否不为空
 * @param {} str 待判断的字符串
 */
function isNotEmpty(str){
	return !isEmpty(str);
}

/**
 * url是否是http://或https://
 * @param url 
 */
function isHttp(url) {
	return /^(https?:\/\/)/.test(url);
}

/**
 * 移除最后一个字符
 * @param str
 */
function removeEnd(str) {
	return str.substring(0, str.length - 1);
}

/**
 * 将array转换为str,以,分隔
 * @param array
 * @returns {String}
 */
function arrayToStr(array) {
	if(array == 'undefined' || array == null)
		return "";

	return "," + array.valueOf() + ",";
}

/**
 *
 * @param str
 * @param symbol
 * @returns
 */
function strToArray(str, symbol) {
	if(!!str)
		return str.split(symbol);
	else 
		return null;
}

/**
 * array中是否存在str
 * @param array
 * @param str
 */
function isInArray(array, str) {
	return array ? jQuery.inArray(str, array) != -1 : false;
}
/**
 * xml转换至String
 * <![CDATA[]]>的<>会被解析成&lt;和&gt;,需要转义回来
 * @param {} xmlObj xml对象
 * @return {}
 */
function xmlToString(xmlObj) {
	var result = "";

	if(typeof(xmlObj) == "object") {
		if(!xmlObj.get()[0])
			return "";

		//兼容IE浏览器
        //$.browser.msie
		if(window.ActiveXObject)
			result = xmlObj.get()[0].xml;
		else {
			result = new XMLSerializer().serializeToString(xmlObj.get()[0]);
        }
	}
	else if(typeof(xmlObj) == "string") {
		result = xmlObj;
	}
	return replaceAll(result, "&lt;,&gt;", "<,>");
}

/**
 * 批量替换字符，将str中的s1字符串替换为s2字符串
 * 示例：
 * var tempValue = "1 less than 2 ，3 more than 2";
 * tempValue = replaceAll(tempValue,"less than,more than","<,>");
 * tempValue "1 < 2 ，3 > 2"
 *
 * @param {} str 字符串
 * @param {} s1 一组需要替换的旧字符
 * @param {} s2 新字符，与旧字符一一对应
 * @return {} 替换过后的字符串
 */
function replaceAll(str, s1, s2){
    if(str instanceof Date){
    	str = Ext.util.Format.date(str, 'Y-m-d');
    }
    str = str + "";
	var olds = s1.split(","); // 旧数据
	var news = s2.split(","); // 新数据
	for(var i = 0 ; i < olds.length; i++){
		str = str.replace(new RegExp(olds[i], "gm"), news[i]);
	}
	return str;
}

/**
 * 将string格式的数据转换成json
 * @param {} obj 字符串
 * @return {} json对象
 */
function StringToJSON(obj){
	var patrn = /^{/; 
	if (!patrn.exec(obj)) 
		return null; 
	else
		return eval('(' + obj + ')');
}

/**
 * 1、若字符串包含字符‘\’ java 转义 ‘\\’ 返回页面 ext 对‘\’ 会再次转义，所以需将返回的字符串.replace("\\", "\\\\")<br>
 * 2、过滤XML特殊标记CDATA标签<br>
 * @param text
 * @return
 */
function getUnescapedText(text) {
	return !!text ? text.replace(/\\/g, '\\\\').replace('<![CDATA[', '').replace(']]>', '').replace(/\r/g,"\\r").replace(/\n/g,"\\n")  : "";
}

/**
 * 只过滤XML特殊标记CDATA标签<br>
 * @param {} text
 * @return {}
 */
function getRemoveCDATA(text) {
	if(!!text && text instanceof Date)
		return text;
	
	return !!text ? text.replace('<![CDATA[', '').replace(']]>', '').replace('<!--[CDATA[', '').replace(']]-->', '') : "";
}

/**
 * 将json格式的数据转换成string格式
 * @param {} o json对象
 * @return {} 字符串
 */
function JsonToStr(o) {
	var arr = [];
	var fmt =
		function(s) {
			if (typeof s == 'object' && s != null)
				return JsonToStr(s);
			else
				return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
		};
	for (var i in o)
		arr.push("'" + i + "':" + fmt(o[i]));

	return '{' + arr.join(',') + '}';
}

/**
 * 字符格式化
 * 示例：
 * var a = "I Love {0}, and You Love {1},Where are {0}!";
	alert(a.format( "You","Me"));
	结果：I Love You, and You Love Me,Where are You!
 */
String.prototype.format = function(){
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,
        function(m,i){
            return args[i];
        });
};

/**
 * $(xml).text() 在firefox或chorme中无法取到值.
 * getXmlNoteText(note)将过滤<!--[CDATA[]]-->
 */
function getXmlNoteText(note){
	var value = "";
	//TODO bopDom.text()<![CDATA[1122]]>一般情况取得的值是1122 ，有时取得的是<![CDATA[1122]]>带有CDATA，所以需要替换掉。根本原因没找到。
	//if($.browser.msie)
	if(window.ActiveXObject)
			value = note.text().replace("<![CDATA[", "").replace("]]>", "");
	//而执行table翻页bopDom的innerHTML不存在，所以也需判断innerHTML是否存在。
	else if(note[0] && note[0].innerHTML && note.html().indexOf("<!--[CDATA[") != -1)
		value = note.html().replace("<!--[CDATA[", "").replace("]]-->", "");
	else
		value = note.text();
	
	return decodeToDisplay(value);
}

/**
 * 删除左右两端的空格
 * @param {} str
 * @return {}
 */
function trim(str){
	if(str != null || typeof(str) != 'undefined'){
		var regExp = /^\s*(.*?)\s*$/;
  		return String(str).replace(regExp, "$1");
	}
	return str;
}

/**
 * 计算字符串长度(可同时字母和汉字，字母占一个字符，汉字占2个字符)
 * @param str
 * @returns {Number}
 */
function strlen(str){  
	str = str || "";
	var len = 0;
	for (var i = 0, strlen = str.length; i < strlen; i++) {   
		var c = str.charCodeAt(i);   
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) 
			len++;   
		else
			len += 2;   
	}   
	
	return len;  
}  

/**
 * 获取xml dom在的真实value
 * @param {} obj
 * @return {}
 */
function getRealValue(obj){
	if(typeof obj == 'string')
		return getRemoveCDATA(decodeToDisplay(obj));
	
	return getRemoveCDATA(getXmlNoteText(obj)) || "";
}

/**
 * 反向转意特殊符号（&、'），针对store中的值
 * @param {} str
 * @return {}
 */
function decodeToValue(str){	
	if(isUseExt() && Ext.isDate(str))
		return str;
	return str.replaceAll(":amp;", "\\&")
			.replaceAll(":apos;", "\\'")
			.replaceAll(":quot;", "\\\"")
			.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;");
}

/**
 * 反向转意特殊符号（&、'），针对显示值
 * @param {} str
 * @return {}
 */
function decodeToDisplay(str){
	if(!str)
		return "";
	else if(isUseExt() && Ext.isDate(str)) 
		return str.format('Y-m-d');
	else 
		return str.replaceAll(":amp;", "\&")
			.replaceAll(":apos;", "\'")
			.replaceAll(":quot;", "\"")
			.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replace(/\\n/g, "\n");
}
