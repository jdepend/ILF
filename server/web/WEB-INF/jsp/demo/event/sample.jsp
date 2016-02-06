<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/22
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container" style="padding-top: 10px;">
    <div id="number1" style="background-color: gray; width:100%; height:100px;">
        <span style="padding: 30 25%">点击这里将改变其他区块的颜色</span>
    </div>
    <div id="number2" style="background-color: green; width:100%; height:100px;"></div>
    <div id="number3" style="background-color: goldenrod; width:100%; height:100px;"></div>
</div>
<script>
    $(function(){
        $("#number2").bind("hello2", function(){
            $(this).css("background-color", "darkmagenta");
        });
        $("#number3").bind("hello3", function(){
            $(this).css("background-color", "darkseagreen");
        });
    })
</script>