<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head style="padding-top: 60px;">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height" />

    <title>Dept</title>
</head>
<body>
<div class="container">
    <div id="topMenu">
        <%@include file="/WEB-INF/jsp/layout/template/menu/topMenu.jsp"%>
    </div>
    <div style="height: 50px;"></div>
    <div class="container" id="contentArea" style="overflow-y: auto;">
        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 text-center">
            <img class="img-circle" src="<%=basePath%>/resources/images/1.png">
            <h3><strong> JHON DEO ALEX</strong></h3>
            <h4>Go Live Blogging</h4>
            <p style="padding-left:40px;padding-right:40px;">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec enim sapien.
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec enim sapien.
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec enim sapien.
            </p>
        </div>
    </div>
</div>
</body>
<%@include file="/WEB-INF/jsp/layout/template/main/contentscript.jsp" %>
</html>