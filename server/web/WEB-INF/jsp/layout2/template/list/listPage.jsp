<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<%=request.getContextPath()%>/resources/css/page.css" rel="stylesheet">
<div id="lt_list-wrapper">
    <div id="lt_list-scroller">
        <ul id="lt_list-group" class="list-group">
            <div class="mlt-record-background-color" style="height: 3px;"></div>
            <%@include file="list_elements.jsp" %>
            <div id="lt_rowend" class="mlt-record-background-color" style="height: 4px;"></div>

            <div id="lt_pageBtn_More" style="display: none">
                <div class="row mlt-page-background-color" id="lt_pageBtn" style="text-align: center;">
                    <a id="morePageData" style="cursor: hand;">加载更多</a>
                </div>
            </div>
            <div id="lt_pageBtn_NoData" style="display: none">
                <div class="row" id="lt_pageBtn" style="text-align: center; background-color: #dcdcdc;">已经没有数据了</div>
            </div>
        </ul>
        <div id="lt_pullUp">
            <span class="pullUpIcon"></span><span class="pullUpLabel">向上刷新。。。</span>
        </div>
    </div>
</div>
<script>
    var lt_pageNumber;
    var lt_pageSize;
    var lt_morePageData;
    var lt_morePageUrl;
    var lt_pageCondition;

    $(function () {
        $(window).bind(LoadDataEvent, function (event, data) {
            if(data.page != null && data.listData != null) {
                setPageInfo(data.page);
                appendListData(data.listData);
                appendPageBtn(data.page)
            }
        });
    });

    function setPageInfo(page) {
        lt_pageNumber = page.pageNumber;
        lt_pageSize = page.pageSize;
        lt_morePageData = page.more;
        lt_morePageUrl = page.morePageUrl;
        if (page.conditionInfo == null || page.conditionInfo.length == 0) {
            lt_pageCondition = {};
        } else {
            lt_pageCondition = page.conditionInfo;
        }
    }

    function appendPageBtn(page) {
        if (page.more) {
            $("#lt_pageBtn_More").css("display", "block");
            $("#lt_pageBtn_NoData").remove();
        } else {
            $("#lt_pageBtn_More").remove();
            $("#lt_pageBtn_NoData").css("display", "block");
        }
    }
</script>
<script src="<%=request.getContextPath()%>/resources/js/page_load_json.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/page.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/list.js"></script>