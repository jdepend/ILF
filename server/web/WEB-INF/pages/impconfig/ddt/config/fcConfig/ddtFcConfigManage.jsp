<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtFcBO" queryRange="true" >
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.name"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.url"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.boName"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.containerId" />
			<qeweb:select bind="ddtContainerBO.ddtSysContainerBO.containerType" />
			<qeweb:textField bind="ddtSysFcBO.bopname" />
			<qeweb:select bind="ddtSysFcBO.fcType" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtFcBO" >
			<qeweb:commandButton name="insert" operate="ddtFcBO.toAdd" text="insert" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete" text="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtFcBO.toEdit" text="update" width="80"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.name" width="130"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.page.url" width="400"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.boName" width="150"/>
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.containerId" />
			<qeweb:textField bind="ddtContainerBO.ddtSysContainerBO.containerTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.containerType"/>
			<qeweb:textField bind="ddtSysFcBO.bopname" />
			<qeweb:textField bind="ddtSysFcBO.fcTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.fcType"/>
			<qeweb:textField bind="contextName" text="上下文信息"/>
			<qeweb:textField bind="prevBopName"/>
			<qeweb:hidden bind="ddtContainerBO.ddtSchemaBO.schemaCode" />
			<qeweb:hidden bind="ddtContainerBO.ddtSchemaBO.schemaName" />
			<qeweb:hidden bind="ddtContainerBO.id" />
			<qeweb:hidden bind="ddtSysFcBO.id" />
			<qeweb:hidden bind="ddtContainerBO.ddtSysContainerBO.id" />
			<qeweb:hidden bind="ddtSysFcBO.container.id" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>