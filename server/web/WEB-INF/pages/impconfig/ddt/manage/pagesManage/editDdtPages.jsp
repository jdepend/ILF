<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtSysPagesBO" layout="1" param="required" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.editPage">
		<qeweb:select bind="module.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.moduleName"/>
		<qeweb:textField bind="name"></qeweb:textField>
		<qeweb:textField bind="url"></qeweb:textField>
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="update" operate="update" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>