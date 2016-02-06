<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/10
  Time: 8:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<script>
   function userDetail(userId){
       $("#"+userId).css("background-color", "beige");
       request("<%=basePath%>/userinfo/" + userId + "/detail");
   }

   function submit(deptId){
       showMsg("提交本组[" + deptId + "]信息");
   }
</script>
