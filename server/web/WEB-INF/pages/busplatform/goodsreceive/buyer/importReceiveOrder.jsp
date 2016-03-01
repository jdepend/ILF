<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 收货单导入 --%>
<qeweb:page>
	<qeweb:form id="importForm" bind="bp_ImpReceiveOrder" layout="1" text="com.qeweb.busplatform.goodsreceive.imp.BP_ImpGoodsReceiveOrderBO">
		<qeweb:fileField bind="exlFile" operate="upload" text="com.qeweb.busplatform.common.imp.excel.ImpExl.exlFile"/>
        <qeweb:commandButton name="imp" operate="imp" text="imp"/>
	</qeweb:form>
</qeweb:page>
