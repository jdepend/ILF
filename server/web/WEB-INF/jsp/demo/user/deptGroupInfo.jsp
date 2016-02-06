<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="padding-top: 5px;">
    <div class="col-xs-5 col-xs-offset-1">
        <span style="font-weight:bold; color: gray;">${group[1]}</span>
    </div>
    <div class="col-xs-4 col-xs-offset-2">
        <span style="font-weight:bold; color: gray;">科室人员：${fn:length(group[2])}</span>
    </div>
</div>