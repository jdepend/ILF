<%@page import="com.qeweb.framework.common.Envir"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
String ctx = Envir.getContextPath();
%>
<link href="<%=ctx %>/css/styleLab.css" type="text/css"  rel="stylesheet" />
<script type="text/javascript">
if (top.location !== self.location) {
	top.location=self.location;
	}
</script>
</head>
<body >
		<br>
    	<div class="space"></div>
    	<form name="SysUserLoginForm" method="post" action="<%=ctx %>/system/generalredirectAC.action">
        <input type="hidden" name="operation" value="login"> 
        <input type="hidden" name="redirectStr" value="/WEB-INF/pages/system/systemmgt/login.jsp"> 
        <table cellPadding="0" cellSpacing="0" height="50%">
		<tr style="height:20px">
			<td>&nbsp;</td>
			<td><h1 style="color:red;font-size:x-large;font-weight: 300">当前用户[${usercode }]已经登录,您想:</div></td>
		</tr>
		<tr style="height:20px">
			<td>&nbsp;</td>
			<td><input type="radio" name="loginPass" value="STOP" class="checkbox">结束当前用户的登录.</td>
		</tr>
		<tr style="height:20px">
			<td>&nbsp;</td>
			<td><input type="radio" name="loginPass" value="PASS" class="checkbox" checked="checked">终止已经登录的用户,用当前用户进行登录</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="button" name="login" class="button" value="确定"  onclick="javascript:document.forms[0].submit()">&nbsp;<input type="button" class="button" name="cancel" value="取消" onclick="doCancel()"></td>
		</tr>
        </table>
        </form>
    </body>
</html>