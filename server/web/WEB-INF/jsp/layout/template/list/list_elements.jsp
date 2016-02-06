<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:forEach items="${pageModel.dataModel.listData}" var="element">
    <li class="list-group-item mlt-record-background-color"  style="height: 80px; padding-top: 4px; padding-bottom: 4px;">
        <div class="row rowcontent mlt-record-middle-background-color"  style="height:72px; padding-top: 8px;padding-bottom: 8px; ">
            <c:set var="element" scope="request" value="${element}"/>
            <tiles:insertAttribute name="elementBody"/>
        </div>
    </li>
</c:forEach>
