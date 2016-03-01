<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.qeweb.framework.manager.DisplayType"%>

<% String bottomMsg = DisplayType.getBottomMsg(); 
	if(bottomMsg == null || bottomMsg.equals(""))
		bottomMsg = "版权归属：苏州快维科技有限公司/上海匡维信息技术有限公司 电话:400-880-1018 苏ICP备B2-20110079号";
%>

<html>
<head>
</head>
<body>
	<center>
		<font size="2" color="#555555"><%=bottomMsg %></font>
	</center>
</body>
</noframes>
</html>
