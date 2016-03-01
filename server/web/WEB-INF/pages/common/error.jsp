<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<html>
	<body>
		<p><h1>登录失败!可能是下列原因:</h1></p>
		<p><h2>1.用户名或密码错误</h2></p>
		<p><h2>2.该用户已经被禁用</h2></p>
		
		<form action="<%=ctx%>/system/login.action">
			<input type="submit" value="重新登录">
		</form>
	</body>
</html>
