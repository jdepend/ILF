<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="DemoMobileClockingInBO">
    <qeweb:commandButton name="goBack" text="form.back"/>
	<qeweb:form id="form" bind="DemoMobileClockingInBO" layout="3;C3(picFile)" param="required">
		<qeweb:label bind="shopCode" />
		<qeweb:label bind="shopName" />
		<qeweb:label bind="visitDate" text="拜访时间"/>
		<qeweb:label bind="userBO.userCode" text="督导员编码" />
		<qeweb:label bind="userBO.userName" text="督导员名称" />
		<qeweb:label bind="arrivalTime" />
		<qeweb:label bind="leaveTime" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>