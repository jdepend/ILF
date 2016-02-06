<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty pageModel.dataModel.title.glyphicon}">
    <span class="glyphicon ${pageModel.dataModel.title.glyphicon}" aria-hidden="true"></span>
</c:if>
<span>${pageModel.dataModel.title.name}</span>
