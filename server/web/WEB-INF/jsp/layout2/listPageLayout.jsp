<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="template/main/header.jsp" %>
</head>
<body>
<div class="container">
    <!-- menu -->
    <div id="topMenu">
        <%@include file="template/menu/topMenu.jsp" %>
    </div>
    <div style="height: 50px;"></div>
    <div class="container" id="contentArea" style="overflow-y: auto;">
        <!-- title -->
        <%@include file="template/title/title.jsp" %>
        <!-- list -->
        <div>
            <%@include file="template/list/listPage.jsp" %>
        </div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<%@include file="template/main/contentscript.jsp" %>
<tiles:insertAttribute name="javascriptBody"/>
</html>