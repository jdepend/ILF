function loadPageData() {

    var morePageRequestData=lt_pageCondition;
    morePageRequestData["pageNumber"]=lt_pageNumber + 1;
    morePageRequestData["pageSize"]=lt_pageSize;

    $.ajax({
        type: "GET",
        url: basePath + "/" + lt_morePageUrl,
        data: morePageRequestData,
        dataType: "json",
        success: function (data) {
            var length = data.dataModel.listData.length;
            if (length > 0) {
                appendListData(data.listData);
                $("body").scrollTop($("#lt_pageBtn").offset().top);
            }
            if (length < lt_pageSize) {
                $('#lt_pageBtn').html("已经没有数据了");
                lt_morePageData = false;
            } else {
                lt_pageNumber += 1;
                lt_morePageData = true;
            }
        }
    });
}