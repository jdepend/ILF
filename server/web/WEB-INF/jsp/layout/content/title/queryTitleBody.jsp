<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="lt_queryTitle">
    <div id="lt_queryTitleClose">
        <div class="col-xs-10">
            <c:if test="${not empty pageModel.dataModel.title.glyphicon}">
                <span class="glyphicon ${pageModel.dataModel.title.glyphicon}" aria-hidden="true"></span>
            </c:if>
            <span>${pageModel.dataModel.title.name}</span>
        </div>
        <div id="lt_queryButton1" class="col-xs-2" style="text-align: right;">
            <span class="glyphicon glyphicon-menu-down mlt-color" style="cursor: hand;" aria-hidden="true"></span>
        </div>
    </div>
    <div id="lt_queryTitleOpen" style="display: none">
        <div style="text-align: right;">
            <span id="lt_queryButton2" class="glyphicon glyphicon-menu-up mlt-color" style="cursor: hand;" aria-hidden="true"></span>
        </div>
        <div id="lt_queryTitleForm"></div>
    </div>
</div>
<script>
    var lt_QueryTitleState = "close";
    $(function () {
        $("#lt_queryButton1").click(function () {
            if($("#lt_queryTitleForm").html().length == 0) {
                $.ajax({
                    type: "get",
                    url: basePath + "/" + "${pageModel.dataModel.content.urls[0]}",
                    dataType: "html",
                    data: lt_pageCondition,
                    success: function (data) {
                        $("#lt_queryTitleClose").hide();
                        $("#lt_queryTitleForm").html(data);
                        $("#lt_queryTitleOpen").show();
                        lt_QueryTitleState = "open";
                        $("#lt_queryTitle").trigger(QueryTitleFormOpenEvent, "220px");
                    }
                });
            }else{
                $("#lt_queryTitleClose").hide();
                $("#lt_queryTitleOpen").show();
                lt_QueryTitleState = "open";
                $("#lt_queryTitle").trigger(QueryTitleFormOpenEvent, "220px");
            }
        });

        $("#lt_queryButton2").click(function () {
            $("#lt_queryTitleOpen").hide();
            $("#lt_queryTitleClose").show();
            lt_QueryTitleState = "close";
            $("#lt_queryTitle").trigger(QueryTitleFormCloseEvent, "80px");
        });

        $("#lt_queryTitle").bind(QueryTitleFormNeedCloseEvent, function(){
            if(lt_QueryTitleState === "open") {
                $("#lt_queryTitleOpen").hide();
                $("#lt_queryTitleClose").show();
                lt_QueryTitleState = "close";
            }
        });
    });
</script>