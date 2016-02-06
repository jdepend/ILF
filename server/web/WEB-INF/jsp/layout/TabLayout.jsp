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
        <%@include file="template/tab/tab.jsp" %>
        <!-- tab workspace -->
        <div id="tab_workspace"></div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<script type="text/javascript">
    function tabChange(tabId, taburl) {
        $("#" + tabId).parent().parent().children("[class='active']").removeClass("active");
        $("#" + tabId).parent().addClass("active");
        $("#tab_workspace").load("<%=basePath%>/" + taburl);
    }
    $("#tab_workspace").load("<%=basePath%>/" + "${pageModel.dataModel.tab.activeTab.url}");
</script>
<%@include file="template/main/contentscript.jsp" %>
<tiles:insertAttribute name="javascriptBody"/>
</html>