/**
 * Created by Administrator on 2016/2/11.
 */
function pageInit(){

    $("#id1").change(function(){
        if($(this).val() == "a"){
            setBopValueByBopCode("vendorBO.orgName","aaa");
        }else if($(this).val() == "b"){
            setBopValueByBopCode("vendorBO.orgName","bbb");
        }
    });
}