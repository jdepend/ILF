<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSchemaBO" queryRange="true" layout="2">
			<qeweb:textField bind="schemaCode" />
			<qeweb:textField bind="schemaName" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSchemaBO" >
			<qeweb:textField bind="schemaCode" width="100"/>
			<qeweb:textField bind="schemaName" width="150"/>
			<qeweb:textField bind="schemaDesc" width="350"/>
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>