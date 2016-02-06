/**
 * Created by Administrator on 2016/1/27.
 */
function appendListData(listData){
    for(var index in listData){
        var elementData = listData[index];
        var rowsource = $("#rowsource");
        $(window).trigger(LoadListElementEvent, [rowsource, elementData]);

        var newRecord = $("#rowsource").clone();
        //清除模板中行Id
        $("#rowsource").find(".rowId").attr("id", "");
        //删除#rowsource
        newRecord.removeAttr("id");
        //显示新的行
        newRecord.css('display','block');
        //追加到尾部
        $("#lt_rowend").before(newRecord);
    }
}