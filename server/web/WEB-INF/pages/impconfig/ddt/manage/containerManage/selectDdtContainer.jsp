<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="analyzeContainerBO" layout="2">
			<qeweb:select bind="module.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.moduleName"/>
			<qeweb:select bind="page.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.pageName"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="analyzeContainerBO" hasBbar="false">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="module.moduleName"/>
			<qeweb:textField bind="page.name"/>
			<qeweb:textField bind="page.url"/>
			<qeweb:textField bind="containerType"/>
			<qeweb:textField bind="boName"/>
			<qeweb:textField bind="containerId"/>
			<qeweb:hidden bind="containerTypeShow"/>
			<qeweb:hidden bind="page.id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>