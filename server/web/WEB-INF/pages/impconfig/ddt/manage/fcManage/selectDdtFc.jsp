<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="analyzeFcBO" layout="2;C2(container.boName)">
			<qeweb:select bind="module.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.moduleName"/>
			<qeweb:select bind="page.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.pageName"/>
			<qeweb:select bind="container.containerType"/>
			<qeweb:select bind="container.boName"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="analyzeFcBO" hasBbar="false">
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="container.id"/>
			<qeweb:hidden bind="page.name"/>
			<qeweb:hidden bind="container.containerTypeShow"/>
			<qeweb:hidden bind="container.containerId"/>
			<qeweb:textField bind="container.boName"/>
			<qeweb:textField bind="fc.bopname" text="bopName"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>