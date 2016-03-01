<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="DemoMobileShopStoreBO" layout="3;C3(picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称" />
		<qeweb:label bind="comeInTime" text="进店时间" />
		<qeweb:label bind="y480Store" />
		<qeweb:label bind="y470Store" />
		<qeweb:label bind="g470Store" />
		<qeweb:label bind="y500Store" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>