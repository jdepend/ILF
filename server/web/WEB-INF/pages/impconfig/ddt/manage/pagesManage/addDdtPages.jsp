<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtSysPagesBO" layout="1" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysPagesBO.addPage">
		<qeweb:select bind="module.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.moduleName"/>
		<qeweb:textField bind="name"/>
		<qeweb:textField bind="url"/>
		<qeweb:commandButton name="insert" operate="insert" text="form.save" icon="save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>