<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul id="list-group" class="list-group mlt-record-background-color">
    <div style="height: 3px;"></div>
    <%@include file="list_elements.jsp"%>
    <div id="lt_rowend" style="height: 4px;"></div>
</ul>
<script>
    $(function(){
        $(window).bind(LoadDataEvent, function(event, data){
            appendListData(data.listData);
        });

        $(window).bind(LoadGroupDataEvent, function(event, grouprowsource, group, subListIndex){
            appendListData(group[subListIndex]);
        });
    });
</script>
<script src="<%=request.getContextPath()%>/resources/js/list.js"></script>