<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/11
  Time: 8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div class="col-xs-12 text-center" style="padding-top: 10px;">
    <img class="img-circle" src="<%=basePath%>/resources/images/1.png">
    <h3><strong>${pageModel.dataModel.object[0]}</strong></h3>
    <p style="padding-left:40px;padding-right:40px;">
        <a class="btn btn-primary btn-lg" id="clearLoginInfo">清除自动登录</a>
    </p>
</div>
<script>
    $("#clearLoginInfo").click(
            function () {
                if (window.localStorage) {
                    var storage = window.localStorage;
                    storage.removeItem("userName");
                    storage.removeItem("password");
                }
                showMsg("清除成功");
            }
    );
</script>