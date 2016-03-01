<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtContainerBO" queryRange="true" >
			<qeweb:textField bind="ddtSchemaBO.schemaCode" />
			<qeweb:textField bind="ddtSchemaBO.schemaName" />
			<qeweb:select bind="ddtSysContainerBO.containerType"/>
			<qeweb:textField bind="ddtSysContainerBO.boName"/>
			<qeweb:textField bind="ddtSysContainerBO.containerId"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtContainerBO" >
			<qeweb:commandButton name="insert" operate="ddtContainerBO.toAdd" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtContainerBO.toEdit" text="update" width="80"/>
				<qeweb:commandButton name="delete" operate="delete" text="delete"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="ddtSchemaBO.schemaCode" />
			<qeweb:textField bind="ddtSchemaBO.schemaName" />
			<qeweb:textField bind="ddtSysContainerBO.containerType" />
			<qeweb:textField bind="ddtSysContainerBO.boName" width="200"/>
			<qeweb:textField bind="ddtSysContainerBO.containerId" />
			<qeweb:textField bind="ddtSysContainerBO.page.name" width="150"/>
			<qeweb:textField bind="ddtSysContainerBO.page.url" width="600"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>