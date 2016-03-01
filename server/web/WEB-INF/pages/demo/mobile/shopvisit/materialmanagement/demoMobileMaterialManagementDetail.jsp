<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 物料需求管理 --%>
<qeweb:page>
	<qeweb:form id="DemoMobileMaterialManagementForm" bind="DemoMobileMaterialManagementBO" layout="3;C3(picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="cabinetCount" />
		<qeweb:label bind="g480Count" />
		<qeweb:label bind="y470Count" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>