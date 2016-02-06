<%@ taglib prefix="m" uri="http://www.rofine.com/core/tags_mobile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div onclick="deptDetail(this.id)" id="${element[0]}">
    <div class="col-xs-3">
        <m:deptIcon name="${element[1]}"/>
    </div>
    <div class="col-xs-9" style="border-left: 1px solid darkgray;">
        <div class="row">
            <div class="col-xs-6" style="text-align: right;">科室人数：</div>
            <div class="col-xs-4">
                <div class="progress" style="margin-bottom: 0px;">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${element[2] * 0.8}%;">
                        <span class="sr-only">${element[2] * 0.8}% Complete</span>
                    </div>
                </div>
            </div>
            <div class="col-xs-2">${element[2]}</div>
        </div>
        <div class="row" style="margin-top: 10px;">
            <div class="col-xs-6" style="text-align: right;">工作纪实数量：</div>
            <div class="col-xs-4">
                <div class="progress" style="margin-bottom: 0px;">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${element[3] * 0.8}%;">
                        <span class="sr-only">${element[3] * 0.8}% Complete</span>
                    </div>
                </div>
            </div>
            <div class="col-xs-2">${element[3]}</div>
        </div>
    </div>
</div>

