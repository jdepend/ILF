<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<SCRIPT LANGUAGE="javascript">    
	var errorMessage = "<%=request.getAttribute("errorMessage")%>";
	if(errorMessage != "null"){
		alert(errorMessage);
		window.opener = null;
		window.close();
	}
 </SCRIPT> 
</body>
</html>