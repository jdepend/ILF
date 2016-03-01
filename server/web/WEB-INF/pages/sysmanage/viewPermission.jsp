<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="roleModuleBO">
	<qeweb:commandButton name="saveViewPermission" operate="saveViewPermission" text="form.save"/>
	<qeweb:commandButton name="goBack" text="form.back" />

	<qeweb:form id="roleModuleBO" bind="roleModuleBO" param="required" layout="3;C2(roleBO.roleNotes)">
		<qeweb:label bind="roleBO.roleName"/>
		<qeweb:label bind="roleBO.roleNotes"/>
		<qeweb:hidden bind="roleBO.id"/>
	</qeweb:form>
	
	<qeweb:checkTree id="moduleBO" bind="moduleBO" param="required"></qeweb:checkTree>
</qeweb:page>
