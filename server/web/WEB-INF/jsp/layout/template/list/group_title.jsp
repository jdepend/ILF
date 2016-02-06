<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <%
        request.setAttribute("group", pageContext.getAttribute("groupData"));
    %>
    <tiles:insertAttribute name="groupTitleBody"/>
</div>
