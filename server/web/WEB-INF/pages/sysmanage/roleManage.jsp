<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="roleForm.query">
	<qeweb:group relations="roleForm:roleTable">
		<qeweb:form id="roleForm" bind="roleBO" queryRange="true">
			<qeweb:textField bind="roleName"/>
			<qeweb:textField bind="roleNotes"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="roleTable" bind="roleBO"
			display="id=update;
					roleName=table,insert,update,view;
					roleCode=table,insert,update,view;
					roleNotes=table,insert,update,view">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width='70'/>
				<qeweb:commandButton name="view" operate="view"/>
				<qeweb:commandButton name="viewPermission" operate="moduleBO.createModuleTree"/>
				<qeweb:commandButton name="optPermission" operate="operateBO.createOptTree"/>
				<qeweb:commandButton name="vendorPermission" operate="vendorPermissionBO.cerateVendorTree"/>
				<qeweb:commandButton name="buyerPermission" operate="buyerPermissionBO.cerateBuyerTree"/>
				<qeweb:commandButton name="userDataPermission" operate="userDataPermissionBO.createUserTree"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="roleName"/>
			<qeweb:textField bind="roleCode"/>
			<qeweb:textArea bind="roleNotes"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

