<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSysContainerBO" queryRange="true" >
			<qeweb:textField bind="page.name" />
			<qeweb:select bind="containerType" />
			<qeweb:textField bind="containerId" />
			<qeweb:textField bind="boName" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSysContainerBO" >
			<qeweb:commandButton name="insert" operate="ddtSysContainerBO.toAdd" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtSysContainerBO.toEdit" text="update" width="80"/>
				<qeweb:commandButton name="validateDelete" operate="ddtSysContainerBO.validateDelete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="page.name" width="200"/>
			<qeweb:select bind="containerType" width="100"/>
			<qeweb:textField bind="containerId" width="200"/>
			<qeweb:textField bind="boName" width="350"/>
			<qeweb:hidden bind="containerTypeShow"/>
			<qeweb:hidden bind="page.id"/>
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>