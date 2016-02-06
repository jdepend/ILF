<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="noticeDetail(this.id)" id="${element[0]}">
    <div class="col-xs-3">
        <button type="button" class="btn btn-default btn-lg">
            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
        </button>
    </div>
    <div class="col-xs-8">
        <div class="row">
            <div class="col-xs-12">${element[1]}</div>
        </div>
        <div class="row" style="margin-top: 10px;">
            <div class="col-xs-12"><fmt:formatDate value="${element[3]}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
        </div>
    </div>
    <div class="col-xs-1" style="padding-top: 20px;">
        <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
    </div>
</div>
