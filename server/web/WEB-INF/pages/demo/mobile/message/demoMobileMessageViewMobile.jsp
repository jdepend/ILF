<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="DemoMobileMessageBO">
	<qeweb:form id="DemoMobileMessageForm" bind="DemoMobileMessageBO" param="required" text="公告详情">
		<qeweb:label bind="messageTitle" />
		<qeweb:label bind="sendTime" />
		<qeweb:label bind="messageContent" />
	</qeweb:form>
</qeweb:page>