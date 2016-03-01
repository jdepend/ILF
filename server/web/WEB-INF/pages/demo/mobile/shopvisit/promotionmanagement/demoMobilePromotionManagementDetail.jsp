<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 促销执行管理 --%>
<qeweb:page>
	<qeweb:form id="form" bind="DemoMobilePromotionManagementBO" layout="3;C3(roadShowActivity,closeTableActivity,promotionActivity,picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:textArea bind="roadShowActivity" />
		<qeweb:textArea bind="closeTableActivity" />
		<qeweb:textArea bind="promotionActivity" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>