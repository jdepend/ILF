<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<span id="pageModel_title_glyphicon" class="glyphicon" aria-hidden="true"></span>
<span id="pageModel_title_name"></span>
<script>
    $(function(){
        $(window).bind(LoadDataEvent, function(event, data){
            $("#pageModel_title_name").html(data.title.name);
        });
    });
</script>
