<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="com.qeweb.framework.common.appconfig.AppConfig"%>
<%@page import="com.qeweb.framework.common.constant.ConstantAppProp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mobile login</title>
<link rel="stylesheet" type="text/css" href="<%=ctx %>/css/android/theme.css">
<link rel="stylesheet" type="text/css" href="<%=ctx %>/css/android/custom.css">
<script type="text/javascript">
//提交
function doSubmit(){
     document.getElementById("loginForm").submit();
}
</script>
</head>
<body>
<div class="toolbar">
	<h1><%=AppConfig.getPropValue(ConstantAppProp.MOBILE_ABOUT) %></h1>
	<a onClick="doSubmit();" class="button" style="cursor:hand"><font size="4">登录</font></a>
</div>
<ul>
<form id="loginForm" action="<%=ctx %>/system/login!login.action"  method="post">
	<div align="center"><img src="<%=ctx %>/framework/images/demo/bosideng.jpg" height="150"></div>
	<ul class="rounded">
		<li>用&nbsp;户&nbsp;名：<input type="text" name="userCode"/></li>
		<li>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password"/></li>
	</ul><br/>
	<font color="gray" size="4"><center><b>快维供应链•苏州快维科技有限公司</b></center></font>
	<input type="hidden" name="displayType" value="<%=ConstantAppProp.DISPLAYTYPE_PAD%>"/>
</form>
</ul>
</body>
</html>