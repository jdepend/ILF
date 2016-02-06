/**
 * Created by Administrator on 2015/12/18.
 */
function obtainUrl(urlPattern, param){
    var url = urlPattern.replace(/{.*}/g,param);
    return url;
}

function parseParam(param, key) {
    var paramStr = "";
    if (param instanceof String || param instanceof Number || param instanceof Boolean
        ) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    }
    else {
        $.each(param, function (i) {
            var k = key == null ? i : key + (param instanceof
                    Array ? "[" + i + "]" : "." + i
                    )
                ;
            paramStr += '&' + parseParam(this, k);
        });
    }
    return paramStr.substr(1);
};

function createNewUrl(url, data){
    var newUrl = url;
    if(newUrl.indexOf("?") > 0){
        newUrl += "&enterMode=page";
    }else{
        newUrl += "?enterMode=page";
    }

    if (data != null) {
        var paramStr = parseParam(data);
        newUrl += "&" + paramStr;
    }

    return newUrl;
}

function request(url, data){
    var newUrl = createNewUrl(url, data);
    parent.requestPage(newUrl);
}

function requestFirstPage(url, data){
    var newUrl = createNewUrl(url, data);
    requestPage(newUrl);
}

function requestSelf(url, data){
    var newUrl = createNewUrl(url, data);
    location.href = newUrl;
}

function pageBack(enterMode) {
    if(enterMode == 'page') {
        parent.pagePageBack();
    }else{
        history.back();
    }
}

$(function() {
    setTimeout(loadImg, 200);
});

function loadImg(){
    $("img").lazyload({
        effect: "fadeIn",
        container : "#contentArea"
    });
}

function loadData(url) {
    setTimeout(function () {
        $.ajax({
            type: "get",
            url: basePath + "/" + url,
            dataType: "json",
            success: function (pageModel) {
                if(pageModel.eventModel != null) {
                    $(window).trigger(LoadEventDataEvent, pageModel.eventModel);
                }
                if(pageModel.eventModel != null && pageModel.eventModel.objectModel != null) {
                    $(window).trigger(LoadMetaDataEvent, pageModel.eventModel.objectModel);
                }
                if(pageModel.dataModel != null) {
                    $(window).trigger(LoadDataEvent, pageModel.dataModel);
                }
                loadImg();
                //如果返回数据指定了新的loadDataUrl将再次发出请求
                if(pageModel.loadDataUrl != null){
                    loadData(pageModel.loadDataUrl);
                }
            }
        });
    }, 1000);
}
