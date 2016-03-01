<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 用户组管理 --%>
<qeweb:page onLoad="groupForm.query">
	<qeweb:group relations="groupForm:groupTable">
		<qeweb:form id="groupForm" bind="userGroupBO" queryRange="true">
			<qeweb:textField bind="groupName"/>
			<qeweb:textField bind="groupNotes"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="groupTable" bind="userGroupBO"
			display="id=update;groupName=table,insert,update,view;groupNotes=table,insert,update,view">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
				<qeweb:commandButton name="view" operate="view"/>
				<qeweb:commandButton name="distUsers" operate="userTreeBO.createUserTree" />
				<qeweb:commandButton name="distGroups" />
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="groupName"/>
			<qeweb:textArea bind="groupNotes"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>