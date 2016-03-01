<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="DemoMobileShowCaseBOForm" bind="DemoMobileShowCaseBO" layout="3;C2(comeInTime),C3(advertisment,picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称" />
		<qeweb:label bind="comeInTime" text="进店时间" />
		<qeweb:label bind="numy" />
		<qeweb:label bind="numu" />
		<qeweb:label bind="nums" />
		<qeweb:label bind="comeInTime" text="进店时间"/>

		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>