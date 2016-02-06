<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/22
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <form class="form-horizontal" style="padding: 10px 10px">
        <c:forEach items="${pageModel.pageMetaDataModel.container.elements}" var="element">
            <div class="form-group" id="${element.type}_group" <c:if test="${element.uiState == 'NODISPLAY'}">style="display: none"</c:if>>
                <label for="${element.type}" class="col-xs-4 control-label"
                       style="text-align: right;">${element.title}：</label>
                <c:if test="${element.uiType == 'TEXT'}">
                    <div class="col-xs-8">
                        <input class="form-control" name="${element.type}" id="${element.type}" type="${element.uiType}" required>
                    </div>
                </c:if>
                <c:if test="${element.uiType == 'SELECT'}">
                    <div class="col-xs-8">
                        <select class="form-control" name="${element.type}" id="${element.type}"  type="${element.uiType}" required>
                        </select>
                    </div>
                </c:if>
                <c:if test="${element.uiType == 'RADIO'}">
                    <div class="col-xs-8" id="${element.type}" type="${element.uiType}">
                    </div>
                </c:if>

            </div>
        </c:forEach>

        <div class="form-group">
            <div class="col-xs-12" style="text-align: center;">
                <button type="submit" class="btn btn-primary" style="padding: 5px 20%">提交</button>
            </div>
        </div>
    </form>
</div>
<script>
    $(function(){
        resetCycle();

        $(window).bind(LoadMetaDataEvent, function(event, param){
            for(var elementIndex in param.elements){
                var element = param.elements[elementIndex];
                var elementObj = $("#" + element.type);
                for(var candidateValue in element.candidateValues) {
                    var candidateName = element.candidateValues[candidateValue];
                    if(elementObj.attr("type") == "SELECT") {
                        elementObj.append("<option value='" + candidateValue + "'>" + candidateName + "</option>");
                    }else if(elementObj.attr("type") == "RADIO") {
                        elementObj.append("<label class='radio-inline'><input type='radio' name='"+ element.type + "' id='inlineRadio_" + candidateValue + "' class='"+ element.type + "' value='" + candidateValue + "'>" + candidateName + "</label>");
                    }
                }
            }
        });

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
