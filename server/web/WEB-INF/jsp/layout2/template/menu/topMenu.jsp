<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var enterMode = "<%=request.getParameter("enterMode")%>";
</script>
<div class="navbar-default navbar-fixed-top mlt-frame-border" style="position: absolute;">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="col-xs-1" style="position: absolute; z-index: 1;">
        <a class="navbar-brand" href="javascript:pageBack(enterMode);">
            <span class="glyphicon glyphicon-menu-left mlt-color" aria-hidden="true"></span>
        </a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="col-xs-12">
        <a class="navbar-brand" style="width:100%;text-align:center;" id="pageModel_menuName"></a>
    </div>
</div>
<script>
    $(function(){
        $(window).bind(LoadDataEvent, function(event, data){
            $("#pageModel_menuName").html(data.menuName);
        });
    });
</script>
