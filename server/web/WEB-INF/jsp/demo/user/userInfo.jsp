<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="userDetail(this.id)" id="${element[0]}">
    <div class="col-xs-3">
        <m:avatar img="${element[1]}"/>
    </div>
    <div class="col-xs-4" style="border-left: 1px solid darkgray;">
        <div>
            <span class="userId">${element[2]}</span>
        </div>
        <div style="padding-top: 10px;">
            <span style="font-size: 10;color: #ddd;">${element[3]}</span>
            <span style="font-size: 10;color: #ddd;">${element[4]}</span>
        </div>
    </div>
    <div class="col-xs-4" style="padding-top: 20px;">
        <span class="userId">纪实查看</span>
    </div>
    <div class="col-xs-1" style="padding-top: 20px;">
        <span class="glyphicon glyphicon-menu-right mlt-color" aria-hidden="true"></span>
    </div>
</div>
