<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<nav class="navbar navbar-default navbar-fixed-top" style="padding-left: 10px; padding-right: 10px;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" id="myNavbar" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">演示系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">第一阶段</a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="group_list">组列表展现</a></li>
                        <li><a href="#" id="group_comment_list">组列表操作</a></li>
                        <li><a href="#" id="list">简单列表</a></li>
                        <li><a href="#" id="list_page">翻页列表</a></li>
                        <li><a href="#" id="query_page">查询列表</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">第二阶段</a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="group_list2">组列表展现</a></li>
                        <li><a href="#" id="list2">简单列表</a></li>
                        <li><a href="#" id="list_page2">翻页列表</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">事件模型</a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="sample_event">简单事件</a></li>
                        <li><a href="#" id="sample_object">对象模型</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">VSR</a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="html_create">Html生成</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">内部测试</a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="table">表格</a></li>
                        <li><a href="#" id="form">表单</a></li>
                        <li><a href="#" id="tab">Tab</a></li>
                        <li><a href="#" id="taglib">标签</a></li>
                        <li><a href="#" id="page">翻页</a></li>
                        <li><a href="#" id="silder_1">飞出飞入</a></li>
                        <li><a href="#" id="svg">K线图</a></li>
                        <li><a href="#" id="select">列表联动</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a  id="message" style="cursor: hand;">消息</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">个人菜单<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#" id="setting">设置</a></li>
                        <li><a href="#" id="exit">退出</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<script type="text/javascript">
    $(function () {
        $("#html_create").click(
                function(){
                    requestFirstPage("<%=basePath%>/htmlparse/getHtmlByPalTemplet/vendorManage");
                }
        );
        //例子
        $("#group_list").click(
                function(){
                    requestFirstPage("<%=basePath%>/user/list");
                }
        );
        $("#group_comment_list").click(
                function(){
                    requestFirstPage("<%=basePath%>/user/list/comment");
                }
        );
        $("#list").click(
                function(){
                    requestFirstPage("<%=basePath%>/dept/list");
                }
        );
        $("#list_page").click(
                function(){
                    requestFirstPage("<%=basePath%>/dept/page/list?pageNumber=0");
                }
        );
        $("#query_page").click(
                function(){
                    requestFirstPage("<%=basePath%>/dept/query/index");
                }
        );
        $("#message").click(
                function(){
                    requestFirstPage("<%=basePath%>/message/index?pageNumber=0");
                }
        );
        $("#group_list2").click(
                function(){
                    requestFirstPage("<%=basePath%>/user/list2");
                }
        );
        $("#list2").click(
                function(){
                    requestFirstPage("<%=basePath%>/dept/list2");
                }
        );
        $("#list_page2").click(
                function(){
                    requestFirstPage("<%=basePath%>/dept/page/list2");
                }
        );
        //事件模型
        $("#sample_event").click(
                function(){
                    requestFirstPage("<%=basePath%>/event/sample/index");
                }
        );
        $("#sample_object").click(
                function(){
                    requestFirstPage("<%=basePath%>/object/sample/index");
                }
        );
        //内部测试
        $("#table").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$table");
                }
        );
        $("#form").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$form");
                }
        );
        $("#tab").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$tab");
                }
        );
        $("#taglib").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$taglib");
                }
        );
        $("#page").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$page");
                }
        );
        $("#silder_1").click(
                function(){
                    location.href =  "<%=basePath%>/main/into/test$silder_1";
                }
        );
        $("#svg").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$svg");
                }
        );
        $("#select").click(
                function(){
                    requestFirstPage("<%=basePath%>/main/into/test$select");
                }
        );
        $("#setting").click(
                function(){
                    requestFirstPage("<%=basePath%>/setting/index");
                }
        );
        $("#exit").click(
                function(){
                    parent.exit();
                }
        );
    });
</script>

