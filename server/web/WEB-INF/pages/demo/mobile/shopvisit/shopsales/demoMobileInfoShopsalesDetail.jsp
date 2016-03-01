<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 店面销售管理 --%>
<qeweb:page>
	<qeweb:form id="form" bind="DemoMobileShopSalesBO" layout="3;C2(comeInTime),C3(picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称" />
		<qeweb:label bind="comeInTime" text="进店时间" />
		<qeweb:label bind="y480Sale" />
		<qeweb:label bind="y470Sale" />
		<qeweb:label bind="g470Sale" />
		<qeweb:label bind="y500Sale" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>