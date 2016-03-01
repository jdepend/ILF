<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtFcBO" layout="1" param="required" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtFcBO.editPage">
		<qeweb:textField bind="ddtContainerBO.ddtSchemaBO.schemaCode" />
		<qeweb:textField bind="ddtContainerBO.ddtSchemaBO.schemaName" />
		<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.name"  />
		<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.url"  />
		<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.boName" />
		<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.containerTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.containerType"/>
		<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.containerId">
			<qeweb:source bindBo="ddtFcBO"
				bindBop="ddtContainerBO.id:id;ddtContainerBO.ddtSysContainerBO.boName:ddtSysContainerBO.boName;ddtContainerBO.ddtSchemaBO.schemaCode:ddtSchemaBO.schemaCode;ddtContainerBO.ddtSchemaBO.schemaName:ddtSchemaBO.schemaName;ddtContainerBO.ddtSysContainerBO.containerTypeShow:ddtSysContainerBO.containerTypeShow;ddtContainerBO.ddtSysContainerBO.containerId:ddtSysContainerBO.containerId;ddtContainerBO.ddtSysContainerBO.page.name:ddtSysContainerBO.page.name;ddtContainerBO.ddtSysContainerBO.page.url:ddtSysContainerBO.page.url;ddtContainerBO.ddtSysContainerBO.id:ddtSysContainerBO.id"/>
		</qeweb:textField>
		<qeweb:textField bind="ddtSysFcBO.fcTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.fcType"/>
		<qeweb:textField bind="ddtSysFcBO.bopname">
			<qeweb:source bindBo="ddtFcBO"
				bindBop="ddtSysFcBO.bopname:bopname;ddtSysFcBO.id:id;ddtSysFcBO.fcTypeShow:fcTypeShow;ddtSysFcBO.container.id:container.id" />
		</qeweb:textField>
		<qeweb:textField bind="contextName" text="上下文信息"/>
		<qeweb:textField bind="prevBopName"/>
		<qeweb:hidden bind="ddtContainerBO.id"/>
		<qeweb:hidden bind="ddtSysFcBO.id"/>
		<qeweb:hidden bind="ddtContainerBO.ddtSysContainerBO.id"/>
		<qeweb:hidden bind="ddtSysFcBO.container.id"/>
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="update" operate="update" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>