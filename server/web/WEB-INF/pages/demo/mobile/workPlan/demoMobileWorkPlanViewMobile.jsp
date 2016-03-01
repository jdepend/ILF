<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="DemoMobileWorkPlanBO">
	<qeweb:form id="DemoMobileWorkPlanBOForm" bind="DemoMobileWorkPlanBO" param="required" text="工作计划详情">
		<qeweb:label bind="store" />
		<qeweb:label bind="planTime" />
		<qeweb:label bind="checkingTime" />
	</qeweb:form>
</qeweb:page>