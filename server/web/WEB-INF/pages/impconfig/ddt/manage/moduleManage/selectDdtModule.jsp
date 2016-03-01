<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSysModulesBO" queryRange="true" layout="1">
			<qeweb:textField bind="moduleName"></qeweb:textField>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSysModulesBO">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="moduleName"></qeweb:textField>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>