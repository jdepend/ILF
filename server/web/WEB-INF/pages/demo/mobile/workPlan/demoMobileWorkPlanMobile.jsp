<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="DemoMobileWorkPlanBOTable.sysFresh">
	<qeweb:table id="DemoMobileWorkPlanBOTable" bind="DemoMobileWorkPlanBO" sm="empty">
		<qeweb:hidden bind="id" />
		<qeweb:select bind="store" />
		<qeweb:textField bind="planTime" />
		<qeweb:textField bind="checkingTime" />
	</qeweb:table>
</qeweb:page>