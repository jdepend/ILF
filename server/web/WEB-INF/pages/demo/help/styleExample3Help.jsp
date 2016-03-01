<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=ctx %>/css/styleTemp.css">

<html>
<body>
	<br>
	<center>
	<table align='right' class="helpTable">
		<tr>
			<th rowspan="3" width="10%">帮助</th>
			<th width="15%">执行步骤</th>
			<td align="left">页面Page标签设置style属性为：form=font-weight:bold,color:red,background-color: white;</td>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">默认样式管理器中的相应属性将被附加样式重定义，页面中的字体变为粗体，字体颜色变为红色，背景颜色变为白色</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">框架将style属性的值转换成CSS样式串，保存在页面中，从而替换掉默认样式。<br>
							在style=” form=font-weight:bold,color:red,background-color: white;”中：<br>
							1. form: 表示附加样式的有效范围，即form标签有效<br>
							2. “font-weight:bold,color:red,background-color: white;”: 表示附加样式的内容<br>
</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 