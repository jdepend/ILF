<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="roleOrgBO">
	<qeweb:commandButton name="save" operate="saveVendor" text="form.save"/>
	<qeweb:commandButton name="goBack" text="form.back"/>
	
	<qeweb:form id="roleForm" bind="roleBO" param="required" queryRange="true" text="com.qeweb.sysmanage.purview.bo.Role_Module_BO">
		<qeweb:label bind="roleName"/>
		<qeweb:label bind="roleNotes"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>
	
	<qeweb:checkTree id="vendorPermissionBO" bind="vendorPermissionBO" param="required"/>
</qeweb:page>

