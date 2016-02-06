<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>

    <title>登录</title>
    <style>

        @media (min-width: 768px) {
            .login_width{
                padding-left: 15%;
                padding-right: 15%;
                width: 60%;
                padding-top: 100px;
            }
            .login-bg{
                background-image: url(<%=basePath%>/resources/images/login.jpg);
            }
        }
        @media (max-width: 768px) {
            .login_width{
                padding-left: 30px;
                padding-right: 30px;
                padding-top: 60px;
            }
            .login-bg{
                background-image: url(<%=basePath%>/resources/images/login-mobile.jpg);
            }
        }
    </style>
</head>

<body class="login-bg">
<div class="container login_width">
    <form class="form-signin">
        <h4 class="form-signin-heading" style="color: #ffffff">请登录</h4>

        <div class="form-group input-group">
            <label for="inputUserName" class="sr-only">用户名</label>
            <span class="input-group-addon" id="sizing-addon1">
                <span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
            </span>
            <input id="inputUserName" name="name" class="form-control" placeholder="用户名"
                   aria-describedby="sizing-addon1" autofocus>
        </div>

        <div class="form-group input-group">
            <label for="inputPassword" class="sr-only">密码</label>
            <span class="input-group-addon" id="sizing-addon2">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
            </span>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="密码"
                   aria-describedby="sizing-addon2">
        </div>

        <div class="checkbox">
            <label style="color: #ffffff">
                <input type="checkbox" id="rememberMe" value="remember-me" name="rememberMe">记住我
            </label>
        </div>

        <div id="submitBtn" class="btn btn-primary btn-block">登&nbsp;&nbsp;&nbsp;&nbsp;录</div>
    </form>

</div>
<!-- /.container -->
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<script type="text/javascript">

    function index() {
        window.location.href = "<%=basePath%>/main";
    }
    $(function () {
        $("#submitBtn").click(function () {
                    var name = $("#inputUserName").val();
                    var password = $("#inputPassword").val();

                    if (name == null || name.length == 0 || password == null || password.length == 0) {
                        showErrorMsg('请录入用户名密码');
                        return;
                    }
                    $.ajax({
                        type: "post",
                        async: true,
                        url: "<%=basePath%>/login/ajax",
                        timeout: 1000, //超时时间设置，单位毫秒
                        data: {
                            name: name,
                            password: password
                        },

                        success: function (data) {
                            if (data.result) {
                                saveLoginInfo();
                                index();
                            } else {
                                showErrorMsg('用户名密码错误');
                            }
                        },
                        error: function (request, status, err) {
                            showErrorMsg('请求超时');
                        }
                    });
                }
        );
        loadLoginInfo();
    });

    function loadLoginInfo() {
        if (window.localStorage) {
            var storage = window.localStorage;
            var userName = storage.getItem("userName");
            var password = storage.getItem("password");

            if (userName != null) {
                $("#inputUserName").val(userName);
            }

            if (password != null) {
                $("#inputPassword").val(password);
            }

            if ($("#inputUserName").val().length != 0 && $("#inputPassword").val().length != 0) {
                $('#rememberMe').attr("checked", true);
                $("#submitBtn").trigger("click");
            }
        }
    }

    function saveLoginInfo() {
        if (window.localStorage) {
            var rememberMe = $('#rememberMe').is(':checked');
            var storage = window.localStorage;
            if (rememberMe) {
                var userName = $("#inputUserName").val();
                var password = $("#inputPassword").val();
                storage.setItem("userName", userName);
                storage.setItem("password", password);
            } else {
                storage.removeItem("userName");
                storage.removeItem("password");
            }
        }
    }
</script>
</html>
