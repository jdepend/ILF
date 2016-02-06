<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="padding-top: 5px;">
    <div class="col-xs-5 col-xs-offset-1">
        <span style="font-weight:bold; color: gray;" id="pageModel_group_dept"></span>
    </div>
    <div class="col-xs-4 col-xs-offset-2">
        <span style="font-weight:bold; color: gray;" id="pageModel_group_userCount"></span>
    </div>
</div>
<script>
    $(function(){
        $(window).bind(LoadGroupDataEvent, function(event, grouprowsource, group){
            grouprowsource.find("#pageModel_group_dept").html(group[1]);
            grouprowsource.find("#pageModel_group_userCount").html("科室人员：" + group[2].length);
        });
    });
</script>