<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="DemoMobileMessageForm.formQuery">
	<qeweb:group relations="DemoMobileMessageForm:DemoMobileMessageTable">
		<qeweb:form id="DemoMobileMessageForm" bind="DemoMobileMessageBO" queryRange="true" layout="3;C2(sendTime)">
			<qeweb:textField bind="messageTitle" />
			<qeweb:expend>
				<qeweb:dateField bind="sendTime" />
			</qeweb:expend>
			<qeweb:commandButton name="formQuery" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="DemoMobileMessageTable" bind="DemoMobileMessageBO"
			display="id=update;
				 messageTitle=table,update,insert,view;
				 messageContent=table,update,insert,view;
				 sendTime=table,view;">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="tableDelete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="tableUpdate" operate="update" />
				<qeweb:commandButton name="tableView" operate="view" />
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="messageTitle" />
			<qeweb:textArea bind="messageContent" />
			<qeweb:textField bind="sendTime" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>