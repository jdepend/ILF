<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="DemoMobileMessageTable.sysFresh">
	<qeweb:table id="DemoMobileMessageTable" bind="DemoMobileMessageBO" sm="empty">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="messageTitle" />
		<qeweb:dateField bind="sendTime" />
	</qeweb:table>
</qeweb:page>