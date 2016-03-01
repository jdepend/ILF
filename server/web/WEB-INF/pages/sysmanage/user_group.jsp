<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="userGroupBO" bind="user_GroupBO" param="required" layout="3;C3(groupIds)">
		<qeweb:label bind="userBO.userName"/>
		<qeweb:optionTranserSelect bind="groupIds"/>
		<qeweb:hidden bind="userBO.id"/>
		<qeweb:commandButton name="save" operate="save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>