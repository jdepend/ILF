<%@ page import="com.rofine.platform.web.model.PageModel" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mlt-page-background-color">
    <div id="grouprowsource" style="display: none">
        <div style="height: 20px;"></div>
        <div class="mlt-record-background-color">
            <!-- group title -->
            <%@include file="group_title.jsp" %>
            <!-- group element list -->
            <%@include file="list.jsp" %>
            <!-- group footer -->
            <%@include file="group_footer.jsp" %>
        </div>
    </div>
    <div id="grouprowend"></div>
</div>
<script>
    $(function(){
        $(window).bind(LoadDataEvent, function(event, data){

            for(var index in data.groupListData){
                var groupData = data.groupListData[index];
                var grouprowsource = $("#grouprowsource");
                $(window).trigger(LoadGroupDataEvent, [grouprowsource, groupData, data.subListIndex]);

                var newGroup = $("#grouprowsource").clone();
                //清除模板行记录
                grouprowsource.find(".list-group-item").not("#rowsource").remove();
                //删除#grouprowsource
                newGroup.removeAttr("id");
                //显示新的行
                newGroup.css('display','block');
                //追加到尾部
                $("#grouprowend").before(newGroup);
            }
        });
    });
</script>
