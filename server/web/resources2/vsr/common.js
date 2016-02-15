/**
 * Created by Eric on 2016/2/12.
 */

var _island;
var splitstr=".";

$(document).ready(function(){
    //获得数据岛
//    console.log($('#dataIsland').html());
//    _island = $.parseJSON($('#dataIsland').html());
//    console.log(_island);
    console.log(getDataIslandStr());
});

function getDataIsland(){
    return _island;
}

function setBopValueByBopCode(bopCode,value){

    var bopData = getBopDataByBopCode(bopCode);
    //console.log(bopData);
    if(bopData){
        bopData.value.real =value;
        updateVC(bopCode,bopData);
    }
}

function updateVC(bopCode,bopData){

    var vcArray = $("input[bind='"+bopCode+"']");
    $.each(vcArray,function(idx,item){
        $(item).val(bopData.value.real);
    });
}

function getBopDataByBopCode(bopCode){
    var arr = bopCode.split(splitstr);
    var bopData;
    $.each(_island.container,function(idx,item){

        if(item.bind == arr[0]){
            $.each(item.bop,function(idx1,item1){
                if(item1.bind == arr[1]){

                    bopData = item1;
                    return false;
                }
            });
        }
    });
    return bopData;
}


function getDataIslandStr(dataIslandStr) {
    var xmlDom = "";
    if(!dataIslandStr)
        dataIslandStr = $('#dataIsland11').val();
    console.log(dataIslandStr);
    if(window.ActiveXObject && !!dataIslandStr) {
        xmlDom = new ActiveXObject("Microsoft.XMLDOM");
        xmlDom.loadXML(dataIslandStr);
    }
    else if(!!dataIslandStr){
        var domParser = new DOMParser();
        xmlDom = domParser.parseFromString(dataIslandStr, "text/xml");
        console.log("33");
    }

    return $(xmlDom);
}