<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 物料需求管理 --%>
<qeweb:page>
	<qeweb:form id="DemoMobileMaterialManagementForm" bind="DemoMobileMaterialManagementBO">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" />
		<qeweb:textField bind="cabinetCount" />
		<qeweb:textField bind="g480Count" />
		<qeweb:textField bind="y470Count" />
		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>