<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div class="mlt-page-background-color" style="height: 5px;"></div>
<div class="mlt-record-background-color" style="padding-left: 15px; padding-bottom: 30px;">
    <div class="row" style="height: 60px; padding-top: 20px;padding-left: 10px;">
        <span style="font-weight:bold; color: gray;" id="dept"></span>
    </div>
    <div class="row">
        <div class="col-xs-3">
            <m:avatar img="" name=""/>
        </div>
        <div class="col-xs-9 mlt-frame-border-left">
            <div class="row">
                <div class="col-xs-12" style="background-color: #ffffff;">
                   总成绩：<span style="font-size: 20; color: green;">98</span>分
                </div>
            </div>
            <div class="row" >
                <div class="col-xs-12" style="background-color: #ffffff; margin-top: 15px;">
                    平均考核成绩：98分
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12" style="background-color: #ffffff;margin-top: 2px;">
                    民主测评成绩：98分
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12" style="background-color: #ffffff;margin-top: 2px;">
                    加、扣分：2分
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12" style="background-color: #ffffff;margin-top: 2px;">
                    嘉奖：一等功
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12" style="background-color: #ffffff;margin-top: 2px;">
                    定级：优秀
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        $(window).bind(LoadDataEvent, function(event, data){
            $("#dept").html(data.content.attributes[7]);
            $("img").attr("data-original", basePath + "/resources/images/" + data.content.attributes[1] + ".png");
            $("#avatarName").html(data.content.attributes[2]);
        });
    });
</script>