<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
    String _currentUser = (String) session.getAttribute("user");
%>
<link href="<%=basePath%>/resources/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>/resources/css/slider_1.css" rel="stylesheet">
<link href="<%=basePath%>/resources/css/mlt.css" rel="stylesheet">

<link href="<%=basePath%>/resources/css/ns-default.css" rel="stylesheet">
<link href="<%=basePath%>/resources/css/ns-style-bar.css" rel="stylesheet">
<link href="<%=basePath%>/resources/css/ns-style-growl.css" rel="stylesheet">

<link href="<%=basePath%>/resources/css/mobiscroll.custom-2.6.2.min.css" rel="stylesheet">

<script src="<%=basePath%>/resources/js/jquery.js"></script>
<script src="<%=basePath%>/resources/js/jquery.lazyload.js"></script>
<script src="<%=basePath%>/resources/js/bootstrap.js"></script>
<script src="<%=basePath%>/resources/js/mobiscroll.custom-2.6.2.min.js"></script>
<script src="<%=basePath%>/resources/js/iscroll.js"></script>
<script src="<%=basePath%>/resources/js/modernizr.custom.js"></script>
<script src="<%=basePath%>/resources/js/jquery.cxselect.js"></script>
<script src="<%=basePath%>/resources/js/ilf-metadata.js"></script>
<script src="<%=basePath%>/resources/js/ilf-event.js"></script>
<script src="<%=basePath%>/resources/js/mlt.js"></script>
<script>
    var basePath = "<%=basePath%>";
    var loadDataUrl = "${pageModel.loadDataUrl}";
</script>


