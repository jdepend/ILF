/**
 * Created by Administrator on 2016/2/11.
 */
$(function(){
    $("#id1").change(function(){
        if($(this).val() == "1"){
            alert("a1");
        }else if($(this).val() == "2"){
            alert("a2");
        }

        //changeRange("id5", "range:" + $(this).val());
    })
})