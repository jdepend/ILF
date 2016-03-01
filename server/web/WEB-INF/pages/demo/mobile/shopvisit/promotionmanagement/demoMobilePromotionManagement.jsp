<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 促销执行管理 --%>
<qeweb:page>
	<qeweb:form id="DemoMobilePromotionManagementForm" bind="DemoMobilePromotionManagementBO">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" />
		<qeweb:textArea bind="roadShowActivity" />
		<qeweb:textArea bind="closeTableActivity" />
		<qeweb:textArea bind="promotionActivity" />
		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>