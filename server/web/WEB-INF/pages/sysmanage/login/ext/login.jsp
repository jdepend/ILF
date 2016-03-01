<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.qeweb.framework.common.internation.AppLocalization"%>
<%
	String language = AppCookie.getLanguageType();
	language = StringUtils.isEmpty(language) ? "zh_CN" : language;
	AppCookie.setLanguageType(language);
%>

<%--shortcut
<link rel="shortcut icon" href="<%=ctx %>/framework/images/project/lightbulb.png" />
 --%>
 
<script type="text/javascript">
	function check() {
		$("#loginBtn").attr("disabled", true);
		$("#resetBtn").attr("disabled", true);
		if(!$("#userCode").val() || !$("#password").val()) {
			alert("用户名或密码不能为空！");
			$("#loginBtn").attr("disabled", false);
			$("#resetBtn").attr("disabled", false);
		}
		else {
			$("#form").submit();
		}
	}
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
	<head>
		<title>用户登录</title>
		<link href="<%=ctx %>/framework/css/login.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	    <div id="login">
		     <div id="top">
			      <div id="top_left"><img src="<%=ctx %>/framework/images/login_03.gif" /></div>
				  <div id="top_center"></div>
			 </div>
			 
			 <div id="center">
			      <div id="center_left"></div>
				  <div id="center_middle">
				  		<form id="form" action="<%=ctx%>/system/loginMain.action" method="post">
					       <div id="user"><%=AppLocalization.getLocalization("com.qeweb.system.bo.login.LoginBO.userCode")%>
					         <input type="text" id="userCode" name="userCode" />
					       </div>
						   <div id="pwd"><%=AppLocalization.getLocalization("com.qeweb.system.bo.login.LoginBO.userPassword")%>
						     <input type="password" id="password" name="password" onkeydown="if(event.keyCode==13){check();return false;}" />
						   </div>
						   <div id="btn">
						   	<a id="loginBtn" href="#" onclick="check()"><%=AppLocalization.getLocalization("com.qeweb.system.bo.login.LoginBO.login")%></a>
						   	<a id="resetBtn" href="#" onclick="reset()"><%=AppLocalization.getLocalization("com.qeweb.system.bo.login.LoginBO.reset")%></a>
						   </div>
				     	</form>
				  </div>
				  <div id="center_right"></div>		 
			 </div>
			 <div id="down">
			      <div id="down_left">
				      <div id="inf">
	                       <span class="inf_text">版本信息</span>
						   <span class="copyright">快维供应链  v1.4</span>
				      </div>
				  </div>
				  <div id="down_center"></div>		 
			 </div>
		</div>
	</body>
</html>
