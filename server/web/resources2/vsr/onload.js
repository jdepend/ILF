/**
 * Created by Administrator on 2016/2/15.
 */
$(function(){

    if(isExitsFunction("pageInit")) {
        pageInit();
    }
})

//是否存在指定函数
function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}