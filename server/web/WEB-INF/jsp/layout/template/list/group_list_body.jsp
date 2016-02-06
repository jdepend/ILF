<%@ page import="com.rofine.platform.web.model.PageModel" %>
<%@ page import="com.rofine.platform.web.model.data.ListDataModel" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mlt-page-background-color">
    <c:forEach items="${pageModel.dataModel.groupListData}" var="groupData">
        <div style="height: 20px;"></div>
        <div class="mlt-record-background-color">
            <!-- group title -->
            <%@include file="group_title.jsp" %>
            <c:set var="listData" value="${groupData[pageModel.dataModel.subListIndex]}"/>
            <%
                ((ListDataModel)((PageModel)request.getAttribute("pageModel")).getDataModel()).setListData((List) pageContext.getAttribute("listData"));
            %>
            <!-- group element list -->
            <%@include file="list.jsp" %>
            <!-- group footer -->
            <%@include file="group_footer.jsp" %>
        </div>
    </c:forEach>
</div>
