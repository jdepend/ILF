<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtSysModulesBO" layout="1" param="required">
		<qeweb:textField bind="parentModule.moduleName" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.parentModuleName">
			<qeweb:source bindBo="ddtSysModulesBO"
				bindBop="parentModule.moduleName:moduleName;parentModule.id:id"
				sm="radio" />
		</qeweb:textField>
		<qeweb:textField bind="moduleName" />
		<qeweb:hidden bind="parentModule.id" />
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="update" operate="update" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>