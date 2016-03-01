<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSchemaBO" queryRange="true" >
			<qeweb:textField bind="schemaCode" />
			<qeweb:textField bind="schemaName" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSchemaBO" >
			<qeweb:commandButton name="insert" operate="insert" text="insert"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" text="update" width="80"/>
				<qeweb:commandButton name="validateDelete" operate="delete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="schemaCode" width="200"/>
			<qeweb:textField bind="schemaName" width="200"/>
			<qeweb:textField bind="schemaDesc" width="350"/>
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>