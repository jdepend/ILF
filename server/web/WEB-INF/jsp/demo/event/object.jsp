<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/22
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function(){
        resetCycle();

        $("#cycle").bind("filter", function(event, param, targetParam){
            resetCycle();
            for(var valueIndex in targetParam){
                var selectValue = targetParam[valueIndex];
                $(".cycle[value='" + selectValue + "']").attr("disabled", false);
            }
        });

        $("#cycle_current").bind("change", function(event, param, targetParam){
            if(targetParam == "open"){
                $("#cycle_current_group").fadeIn();
            }else{
                $("#cycle_current_group").fadeOut();
            }
        });
    })

    function resetCycle(){
        $(".cycle").attr("disabled", true);
        $(".cycle").attr("checked", false);
    }
</script>
