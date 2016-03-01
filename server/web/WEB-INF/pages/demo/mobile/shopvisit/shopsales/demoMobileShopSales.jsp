<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 店面销售管理 --%>
<qeweb:page>
	<qeweb:form id="DemoMobileShopSalesForm" bind="DemoMobileShopSalesBO">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" text="进店时间"/>
		<qeweb:textField bind="y480Sale" />
		<qeweb:textField bind="y470Sale" />
		<qeweb:textField bind="g470Sale" />
		<qeweb:textField bind="y500Sale" />
		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="saveShowCaseMobile" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>