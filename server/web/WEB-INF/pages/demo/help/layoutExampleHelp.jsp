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
			<td align="left">查看JSP源码</th>
		</tr>
		<tr>
			<th>执行结果</th>
			<td align="left">看到qeweb:form有一个属性串：layout="3;C3(p10Client);C2(p6Client,p3Client);C1(p1Client,p5Client);"</td>
		</tr>
		<tr>
			<th>执行原理</th>
			<td align="left">1. layout属性：表示对该form中的细粒度组件进行布局定义<br>
        					 2. 3:表示自定义的布局管理器列数，如果不填写，则系统会取已经定义好的默认列数值<br>
        					 3. C3：表示定义占据3列的细粒度组件集合，即colspan的值，同理，C2表示占据2列，C1表示占据1列<br>
        					 4. p6Client,p3Client：表示细粒度组件的集合
			</td>
		</tr>
    </table>
    </center>
	<br>
	
	
</body>
</html>

 