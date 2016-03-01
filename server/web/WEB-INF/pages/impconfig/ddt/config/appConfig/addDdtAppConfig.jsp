<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtAppConfigBO" layout="1" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtAppConfigBO.addPage">
		<qeweb:textField bind="appName"/>
		<qeweb:textField bind="schemaCode" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO.schemaCode">
			<qeweb:source bindBo="ddtAppConfigBO" bindBop="schemaCode:schemaCode"/>
		</qeweb:textField>
		<qeweb:textField bind="userCode" text="com.qeweb.sysmanage.purview.bo.UserBO.userCode">
			<qeweb:source bindBop="userCode:userCode" bindBo="ddtAppConfigBO"/>
		</qeweb:textField>
		<qeweb:textField bind="roleName" text="com.qeweb.sysmanage.purview.bo.RoleBO.roleName"/>
		<qeweb:textField bind="attr1" />
		<qeweb:textField bind="attr2" />
		<qeweb:textField bind="attr3" />
		<qeweb:commandButton name="insert" operate="insert" text="form.save" icon="save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset" />
		<qeweb:commandButton name="goback" text="form.back" />
	</qeweb:form>
</qeweb:page>