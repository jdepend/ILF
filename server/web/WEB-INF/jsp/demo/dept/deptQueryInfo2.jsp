<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div class="container" style="padding-left: 15px; padding-right: 15px; padding-top: 20px;">
    <div class="form-horizontal">
        <input type="hidden" name="pageNumber" value="0">
        <div class="form-group">
            <label for="query_start_group" class="col-xs-4 control-label" style="text-align: right;">开始日期：</label>
            <div class="col-xs-8">
                <div id="query_start_group">
                    <input type="text" name="query_start" id="query_start" value="${pageModel.dataModel.page.condition['start']}" readonly/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="query_end_group" class="col-xs-4 control-label" style="text-align: right;">截止日期：</label>

            <div class="col-xs-8">
                <div id="query_end_group">
                    <input type="text" name="query_end" id="query_end" value="${pageModel.dataModel.page.condition['end']}" readonly/>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-xs-12">
                <button id="submit" class="btn btn-primary" style="margin:0 20%; width: 60%">查询</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#submit").click(function(){
            if($("#query_start").val().length == 0 || $("#query_end").val().length == 0){
                showErrorMsg("请录入开始日期和截止日期");
                return;
            }
            var data = {};
            data["pageNumber"] = 0;
            data["query_start"] = $("#query_start").val();
            data["query_end"] = $("#query_end").val();
            requestSelf("<%=basePath%>/dept/query/page/list", data);
        });

        showDate($("#query_start_group"), $('#query_start'));
        showDate($("#query_end_group"), $('#query_end'));
    });

</script>