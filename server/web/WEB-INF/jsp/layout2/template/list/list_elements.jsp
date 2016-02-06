<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li id="rowsource" class="list-group-item mlt-record-background-color" style="height: 80px; padding-top: 4px; padding-bottom: 4px; display: none">
    <div class="row rowcontent mlt-record-middle-background-color"
         style="height:72px; padding-top: 8px;padding-bottom: 8px; ">
        <tiles:insertAttribute name="elementBody"/>
    </div>
</li>

