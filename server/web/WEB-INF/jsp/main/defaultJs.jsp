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
    $(function(){
        $("#pages").bind("back", function(event, pageScreen){
            if(pageScreen == 1) {
                parent.showBottomMenu();
            }
        });
        $("#pages").bind("enterBefore", function(event, pageScreen){
            if(pageScreen == 2) {
                parent.hideBottomMenu();
            }
        });
        $("#pages").bind("enterAfter", function(event, pageScreen){
            if(pageScreen == 2) {
                $("#myNavbar").click();
            }
        });
    });
</script>
