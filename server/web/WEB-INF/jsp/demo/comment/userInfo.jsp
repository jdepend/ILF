<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="userDetail(this.id)" id="${element[0]}">
    <div class="col-xs-7">
        <div class="row">
            <div class="col-xs-5">
                <m:avatar img="${element[1]}"/>
            </div>
            <div class="col-xs-7" style="padding-top: 20px;">
                ${element[2]}
            </div>
        </div>
    </div>
    <div class="col-xs-5">
        <div class="row">
            <span>${element[5]}</span>
        </div>
        <div class="row" style="padding-top: 10px;">
            <m:star count="${element[6]}" total="5"/>
        </div>
    </div>
</div>
