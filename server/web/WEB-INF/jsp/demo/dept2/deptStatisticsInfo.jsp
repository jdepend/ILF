<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="deptDetail(this.id)" class="rowId" id="">
    <div class="col-xs-3">
        <m:deptIcon name="${element[1]}"/>
    </div>
    <div class="col-xs-9" style="border-left: 1px solid darkgray;">
        <div class="row">
            <div class="col-xs-6" style="text-align: right;">科室人数：</div>
            <div class="col-xs-4">
                <div class="progress" style="margin-bottom: 0px;">
                    <div id="userCount" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="">
                        <span id="userCount1" class="sr-only"></span>
                    </div>
                </div>
            </div>
            <div id="userCount2" class="col-xs-2"></div>
        </div>
        <div class="row" style="margin-top: 10px;">
            <div class="col-xs-6" style="text-align: right;">工作纪实数量：</div>
            <div class="col-xs-4">
                <div class="progress" style="margin-bottom: 0px;">
                    <div id="workCount" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: %;">
                        <span id="workCount1" class="sr-only">% Complete</span>
                    </div>
                </div>
            </div>
            <div id="workCount2" class="col-xs-2"></div>
        </div>
    </div>
</div>
<script>
    $(function(){
        $(window).bind(LoadListElementEvent, function(event, rowsource, element){
            rowsource.find(".rowId").attr("id", element[0]);
            rowsource.find(".thumbnail").html(element[1]);
            rowsource.find("#userCount").attr("style", "width:" + element[2] * 0.8 + "%;");
            rowsource.find("#userCount1").html(element[2] * 0.8 + " Complete");
            rowsource.find("#userCount2").html(element[2]);
            rowsource.find("#workCount").attr("style", "width:" + element[3] * 0.8 + "%;");
            rowsource.find("#workCount1").html(element[3] * 0.8 + " Complete");
            rowsource.find("#userCount2").html(element[3]);
        });
    });
</script>
