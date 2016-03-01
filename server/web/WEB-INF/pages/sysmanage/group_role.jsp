<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="groupRoleBO" bind="group_RoleBO" param="required" layout="3;C3(roleIds)">
		<qeweb:label bind="groupBO.groupName"/>
		<qeweb:optionTranserSelect bind="roleIds"/>
		<qeweb:hidden bind="groupBO.id"/>
		<qeweb:commandButton name="save" operate="save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>