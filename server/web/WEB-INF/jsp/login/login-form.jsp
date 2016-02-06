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
        .login-bg{
            background-image: url(<%=basePath%>/resources/images/login.jpg);
        }
        @media (min-width: 768px) {
            .login_width{
                padding-left: 15%;
                padding-right: 15%;
                width: 60%;
                padding-top: 100px;
            }
        }
        @media (max-width: 768px) {
            .login_width{
                padding-left: 5%;
                padding-right: 5%;
                width: 90%;
                padding-top: 100px;
            }
        }
    </style>
</head>

<body class="login-bg">

<div class="container login_width">

    <form class="form-signin" action="<%=basePath%>/login" method="POST">
        <h4 class="form-signin-heading" style="color: #ffffff">请登录</h4>

        <div class="form-group input-group">
            <label for="inputUserName" class="sr-only">用户名</label>
            <span class="input-group-addon" id="sizing-addon1">
                <span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
            </span>
            <input id="inputUserName" name="name" class="form-control"
                   placeholder="用户名" aria-describedby="sizing-addon1" required autofocus>
        </div>

        <div class="form-group input-group">
            <label for="inputPassword" class="sr-only">密码</label>
            <span class="input-group-addon" id="sizing-addon2">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
            </span>
            <input type="password" id="inputPassword" name="password" class="form-control"
                   placeholder="密码" aria-describedby="sizing-addon2" required>
        </div>

        <div class="checkbox">
            <label style="color: #ffffff">
                <input type="checkbox" value="remember-me" name="rememberMe">记住我
            </label>
        </div>
        <button id="submitBtn" class="btn btn-primary btn-block" type="submit">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
    </form>

</div>
<!-- /.container -->
</body>
<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
<script>
    var errorMsg = "${errorMsg}";
    $(function () {
       if(errorMsg != null && errorMsg.length > 0){
           showErrorMsg(errorMsg);
       }
    });
</script>
</html>
