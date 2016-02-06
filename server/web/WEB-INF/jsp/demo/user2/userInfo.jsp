<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="userDetail(this.id)" class="rowId" id="">
    <div class="col-xs-3">
        <m:avatar img=""/>
    </div>
    <div class="col-xs-4" style="border-left: 1px solid darkgray;">
        <div>
            <span class="userId" id="userName"></span>
        </div>
        <div style="padding-top: 10px;">
            <span style="font-size: 10;color: #ddd;" id="sex"></span>
            <span style="font-size: 10;color: #ddd;" id="duty"></span>
        </div>
    </div>
    <div class="col-xs-4" style="padding-top: 20px;">
        <span class="userId">纪实查看</span>
    </div>
    <div class="col-xs-1" style="padding-top: 20px;">
        <span class="glyphicon glyphicon-menu-right mlt-color" aria-hidden="true"></span>
    </div>
</div>
<script>
    $(function(){
        $(window).bind(LoadListElementEvent, function(event, rowsource, element){
            rowsource.find(".rowId").attr("id", element[0]);
            rowsource.find("img").attr("data-original", basePath + "/resources/images/" + element[1] + ".png");
            rowsource.find("#userName").html(element[2]);
            rowsource.find("#sex").html(element[3]);
            rowsource.find("#duty").html(element[4]);
        });
    });
</script>
