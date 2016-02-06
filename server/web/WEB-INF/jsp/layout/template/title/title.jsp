<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row" id="lt_title">
    <div class="col-xs-12" style="height:30px; padding-top: 5px; padding-left: 5px;">
        <tiles:insertAttribute name="titleBody"/>
    </div>
</div>
<script>
    $("#lt_title").bind(TitleHiddenEvent, function(){
        $(this).fadeOut();
        $("#lt_title").trigger(TitleHiddenedEvent);
    });
    $("#lt_title").bind(TitleShowEvent, function(){
        $(this).fadeIn();
        $("#lt_title").trigger(TitleShowedEvent);
    });
</script>
