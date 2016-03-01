<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 导入采购订单 -->
<qeweb:page>
	<qeweb:form id="importForm" bind="importPurchaseOrderBO" layout="1" text="com.qeweb.busplatform.pomanage.imp.BP_ImpPurchaseOrderBO">
		<qeweb:fileField bind="exlFile" operate="upload" text="com.qeweb.busplatform.common.imp.excel.ImpExl.exlFile"/>
        <qeweb:commandButton name="imp" operate="imp" text="imp"/>
	</qeweb:form>
</qeweb:page>
