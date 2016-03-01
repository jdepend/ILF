<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 店面库存管理 --%>
<qeweb:page>
	<qeweb:form id="DemoMobileShopStoreBOForm" bind="DemoMobileShopStoreBO" text="店面库存管理 ">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" text="进店时间"/>
		<qeweb:textField bind="y480Store" text="y480系列库存"/>
		<qeweb:textField bind="y470Store" text="y470系列库存"/>
		<qeweb:textField bind="g470Store" text="g470系列库存"/>
		<qeweb:textField bind="y500Store" text="y500系列库存"/>

		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>