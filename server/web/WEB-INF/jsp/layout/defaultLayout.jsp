<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<script src="<%=basePath%>/resources/js/mlt-page.js"></script>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@include file="template/main/header.jsp" %>
</head>
<body>
<div id="pages" class="container view-container">
    <div id="page1" class="page-container" style="left:0%;display:block;">
        <tiles:insertAttribute name="menuBody"/>
        <div style="padding-top: 60px;">
                <tiles:insertAttribute name="workspaceBody"/>
        </div>
    </div>
    <div id="pageMaster" class="page-container" style="display:none;">
        <iframe id="iframeMaster" class="pageIframe" style="width:100%; border: 0;"></iframe>
    </div>
</div>
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<script type="text/javascript">
    $(function () {
        resetWorkspace();
        $(window).resize(function () {
            resetWorkspace();
        })
    });

    function resetWorkspace() {
        var height = $(window).height();
        $(".pageIframe").attr("height", height + "px");
    }
</script>
<tiles:insertAttribute name="javascriptBody"/>
</html>