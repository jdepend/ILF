<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/11
  Time: 8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<div class="container">
    <form class="form-horizontal" style="padding: 10px 10px">

        <div class="form-group">
            <label for="inlineRadio1" class="col-xs-4 control-label" style="text-align: right;">界面风格：</label>

            <div class="col-xs-8" id="lt_color">
                <label class="radio-inline">
                    <input type="radio" name="lt_color" id="inlineRadio1" class="lt_color" value="#F52B00">橙色
                </label>
                <label class="radio-inline">
                    <input type="radio" name="lt_color" id="inlineRadio2" class="lt_color" value="#337ab7">蓝色
                </label>
                <label class="radio-inline">
                    <input type="radio" name="lt_color" id="inlineRadio3" class="lt_color" value="30">绿色
                </label>
                <label class="radio-inline">
                    <input type="radio" name="lt_color" id="inlineRadio4" class="lt_color" value="40">紫色
                </label>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        var sysColor = loadSysColor();
        if(sysColor != null){
            $(".lt_color[value='" + sysColor + "']").attr("checked", true);
        }

        $(".lt_color").bind("click", function () {
            saveSysColor($(this).val());
        });
    });

    function getCandidateSysColors(){
        var candidateColors = [];

    }

    function loadSysColor() {
        var defalutColor = "#F52B00";

        if (window.localStorage) {
            var storage = window.localStorage;
            var sysColor = storage.getItem("sysColor");
            if(sysColor != null){
                return sysColor;
            }else{
                return defalutColor;
            }
        }else {
           return defalutColor;
        }
    }

    function saveSysColor(sysColor) {
        if (window.localStorage) {
            var storage = window.localStorage;
            storage.setItem("sysColor", sysColor);
        }
    }
</script>
