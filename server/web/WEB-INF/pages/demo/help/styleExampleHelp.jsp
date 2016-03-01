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
			<td align="left">页面Page标签设置style属性为：css2;form=font-style:italic,color:blue,background-color: green;</td>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">css2样式管理器中的相应属性将被附加样式重定义，页面中的字体变为斜体，字体颜色变为蓝色，背景颜色变为绿色</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">框架将style属性的值转换成CSS样式串，保存在页面中，从而替换掉默认样式。<br>
								在style=”css2;form=font-style:italic,color:blue,background-color: green;”中：<br>
								1. css2: 页面使用名为css2的样式管理器<br>
								2. form: 表示附加样式的有效范围，即form标签有效<br>
								3. “font-style:italic,color:blue,background-color: green;”: 表示附加样式的内容
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 