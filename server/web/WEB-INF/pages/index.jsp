<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.qeweb.framework.common.Envir"%>
<%@page import="com.qeweb.framework.manager.DisplayType"%>
<%
	String ctx = Envir.getContextPath();
%>
<link rel="shortcut icon" href="<%=ctx %>/framework/images/project/lightbulb.png" />
<link rel="stylesheet" type="text/css" href="<%=ctx %>/framework/css/styleTemp.css">
<html>

<html>

<body>
	<form id='name1'>
		<table align="center" class="resultTable">
			<tr>
				<th colspan="4"><center>Qeweb新产品框架功能介绍</center></th>
			</tr>
			<tr>
				<th>功能名称</th>
				<th>功能描述</th>
				<th>示例</th>
			</tr>
			<tr>
				<td>Ext & Html</td>
				<td align="left">
					1. Ext样式Demo<br>
					2. desktop样式Demo<br>
					3. Html样式Demo<br>
					4. Android样式Demo<br>
					5. iphone样式Demo<br>
				</td>
				<td>
					<a href='<%=ctx %>/demo/main.action?displayType=ext' target="_self">查看-Ext</a><br>
					<a href='<%=ctx %>/demo/desktop.action?displayType=desktop' target="_self">查看-desktop</a><br>
					<a href='<%=ctx %>/demo/main.action?displayType=html' target="_self">查看-Html</a><br>
					<a href='<%=ctx %>/demo/changeDisplayType.action?displayType=Android' target="_self">查看-Android</a><br>
					<a href='<%=ctx %>/demo/changeDisplayType.action?displayType=iphone' target="_self">查看-iphone</a>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td align="center">登录</td>
				<td align="left">
					登录功能<br>
					Android手机端登录功能<br>
					iphone手机端登录功能<br>
					PAD登录功能
				</td>
				<td>
					<a href='<%=ctx %>/system/login.action?displayType=ext' target="_self">查看</a><br/>
					<a href='<%=ctx %>/system/login.action?displayType=Android' target="_self">查看</a><br/>
					<a href='<%=ctx %>/system/login.action?displayType=iphone' target="_self">查看</a><br/>
					<a href='<%=ctx %>/system/login.action?displayType=Android_PAD' target="_self">查看</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
