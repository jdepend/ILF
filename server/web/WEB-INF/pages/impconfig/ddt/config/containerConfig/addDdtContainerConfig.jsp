<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtContainerBO" layout="1" param="required" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtContainerBO.addPage">
		<qeweb:textField bind="ddtSchemaBO.schemaCode">
			<qeweb:source bindBo="ddtContainerBO" bindBop="ddtSchemaBO.schemaCode:schemaCode;ddtSchemaBO.schemaName:schemaName;ddtSchemaBO.id:id"/>
		</qeweb:textField>
		<qeweb:textField bind="ddtSchemaBO.schemaName"/>
		<qeweb:textField bind="ddtSysContainerBO.page.name" />
		<qeweb:textField bind="ddtSysContainerBO.containerTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.containerType"/>
		<qeweb:textField bind="ddtSysContainerBO.boName" />
		<qeweb:textField bind="ddtSysContainerBO.containerId">
			<qeweb:source bindBo="ddtContainerBO"
					bindBop="ddtSysContainerBO.page.name:page.name;ddtSysContainerBO.boName:boName;ddtSysContainerBO.containerId:containerId;ddtSysContainerBO.containerTypeShow:containerTypeShow;ddtSysContainerBO.id:id" />
		</qeweb:textField>
		<qeweb:hidden bind="ddtSchemaBO.id" />
		<qeweb:hidden bind="ddtSysContainerBO.id" />
		<qeweb:commandButton name="insert" operate="insert" text="form.save" icon="save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset" />
		<qeweb:commandButton name="goback" text="form.back" />
	</qeweb:form>
</qeweb:page>