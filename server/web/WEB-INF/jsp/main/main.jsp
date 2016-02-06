<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<style>
    .workspace {
        width: 100%;
        scrolling: auto;
        border: 0;
        frameboder: 0;
    }
</style>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Cache-Control" content="max-age=60"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport"
          content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
    <title>ILF</title>
</head>
<body>
<div class="container" style="width: 100%;">
    <iframe id="workspaceArea" class="workspace" src="<%=basePath%>/main/default"></iframe>
    <div id="bottomMenu">
        <%@include file="/WEB-INF/jsp/main/bottomMenu.jsp" %>
    </div>

</div>
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<%--<script src="<%=basePath%>/resources/js/online.js"></script>--%>
<script type="text/javascript">

    var isBottomMenuHide = false;

    $(function () {
        resetWorkspace();
        $(window).resize(function () {
            resetWorkspace();
        })
    });

    function resetWorkspace() {
        var height = $(window).height();
        if(isBottomMenuHide){
            $("#workspaceArea").attr("height", height + "px");
        }else{
            $("#workspaceArea").attr("height", height - 50 + "px");
        }
    }

    function hideBottomMenu (){
        $("#bottomMenu").hide();
        isBottomMenuHide = true;
        resetWorkspace();
    }

    function showBottomMenu(){
        $("#bottomMenu").show();
        isBottomMenuHide = false;
        resetWorkspace();
    }

    function exit() {
        var width = $(window).width();
        if (width > 768) {
            location.href = "<%=basePath%>/login/into";
        } else {
            try {
                androidWebActivity.exit();
            } catch (e) {
                location.href = "<%=basePath%>/login/into";
            }
        }
    }
</script>
</html>

