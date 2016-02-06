<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .menuTitle{
        padding-top: 4px;
        font-size: 10;
        width: 30px;
        text-align:center;
    }
</style>
<div class="row mlt-frame-color mlt-frame-border"
     style="position: fixed;right: 0;left: 0; height:50px; ">
    <div class="row" style="margin-top: 8px;">
        <div class="col-xs-3">
            <div style="width: 30px; margin: 0 auto;" class="bottomMenuButton mlt-color">
                <span class="glyphicon glyphicon-home" aria-hidden="true" style="padding-left: 5px;"></span>
                <div class="menuTitle" url="main/default">首页</div>
            </div>
        </div>
        <div class="col-xs-3 mlt-frame-border-left">
            <div style="width: 30px; margin: 0 auto;"  class="bottomMenuButton">
                <span class="glyphicon glyphicon-record" aria-hidden="true" style="padding-left: 8px;"></span>
                <div class="menuTitle" url="main/tab$tab2/into">纪实</div>
            </div>
        </div>
        <div class="col-xs-3 mlt-frame-border-left">
            <div style="width: 30px; margin: 0 auto;"  class="bottomMenuButton">
                <span class="glyphicon glyphicon-modal-window" aria-hidden="true" style="padding-left: 8px;"></span>
                <div class="menuTitle" url="main/tab$tab3/into">项目</div>
            </div>
        </div>
        <div class="col-xs-3 mlt-frame-border-left">
            <div style="width: 30px; margin: 0 auto;"  class="bottomMenuButton">
                <span class="glyphicon glyphicon-user" aria-hidden="true" style="padding-left: 8px;"></span>
                <div class="menuTitle" url="userprofile/index">我的</div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        $(".bottomMenuButton").click(function (){
            $(".bottomMenuButton").attr("class", "bottomMenuButton");
            $(this).attr("class", "bottomMenuButton mlt-color");

            var url = $(this).find(".menuTitle").attr("url");
            $("#workspaceArea").attr("src", url);
        });
    })
</script>
