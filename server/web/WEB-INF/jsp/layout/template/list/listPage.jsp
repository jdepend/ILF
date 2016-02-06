<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<%=request.getContextPath()%>/resources/css/page.css" rel="stylesheet">
<div id="lt_list-wrapper">
    <div id="lt_list-scroller">
        <ul id="lt_list-group" class="list-group">
            <div class="mlt-record-background-color" style="height: 3px;"></div>
            <%@include file="list_elements.jsp" %>
            <div class="mlt-record-background-color" style="height: 4px;"></div>

            <c:if test="${pageModel.dataModel.page.more}">
                <div class="row mlt-page-background-color" id="lt_pageBtn" style="text-align: center;">
                    <a id="morePageData" style="cursor: hand;">加载更多</a>
                </div>
            </c:if>
            <c:if test="${not pageModel.dataModel.page.more}">
                <div class="row" id="lt_pageBtn" style="text-align: center; background-color: #dcdcdc;">已经没有数据了</div>
            </c:if>
        </ul>
        <div id="lt_pullUp">
            <span class="pullUpIcon"></span><span class="pullUpLabel">向上刷新。。。</span>
        </div>
    </div>
</div>
<script>
    var lt_pageNumber = ${pageModel.dataModel.page.pageNumber};
    var lt_pageSize = ${pageModel.dataModel.page.pageSize};
    var lt_morePageData = ${pageModel.dataModel.page.more};
    var lt_morePageUrl = "${pageModel.dataModel.page.morePageUrl}";
    var lt_pageCondition = <c:choose><c:when test="${empty pageModel.dataModel.page.conditionInfo}">{}</c:when><c:otherwise>${pageModel.dataModel.page.conditionInfo}</c:otherwise></c:choose>;

    if(!lt_morePageData){
        $('#lt_pullUp').remove();
    }
</script>
<script src="<%=request.getContextPath()%>/resources/js/page_load_html.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/page.js"></script>

