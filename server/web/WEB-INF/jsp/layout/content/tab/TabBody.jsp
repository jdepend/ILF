<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<ul class="nav nav-tabs">
    <c:forEach items="${pageModel.dataModel.tab.tabInfos}" var="tabInfo">
        <li role="presentation" <c:if test="${tabInfo.active}">class="active"</c:if>>
            <a id="tab_${tabInfo.id}" onclick="tabChange(this.id, '${tabInfo.url}')" style="padding-top: 2px;padding-bottom: 2px; cursor: hand;">${tabInfo.name}</a>
        </li>
    </c:forEach>
</ul>
