<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/23
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function () {
        resetContent();
        $(window).resize(function(){
            resetContent();
        });

        if(loadDataUrl.length > 0){
            loadData(loadDataUrl);
        }
    });
    function resetContent(){
        var height = parent.parent.$("#workspaceArea").height();
        $("#contentArea").css("height",  height - 50 + "px");
    }


</script>
