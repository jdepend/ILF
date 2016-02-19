/**
 * Created by Administrator on 2016/2/19.
 */
$(function(){
    $("#buttonArea").find("button").click(function(){
        var form = $("#" + this.id.substring(0, this.id.indexOf("-")));
        //form.action = form.action + "?operate=" + this.operate;
        form.submit();
    });
})