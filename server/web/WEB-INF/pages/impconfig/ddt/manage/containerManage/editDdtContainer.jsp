<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtSysContainerBO" layout="1" param="required" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO.editPage">
		<qeweb:textField bind="page.name" />
		<qeweb:textField bind="containerTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.containerType"/>
		<qeweb:textField bind="boName" />
		<qeweb:textField bind="containerId">
			<qeweb:source bindBo="ddtSysContainerBO"
				bindBop="page.name:page.name;page.id:page.id;containerType:containerType;boName:boName;containerId:containerId;containerTypeShow:containerTypeShow"
				sm="radio" />
		</qeweb:textField>
		<qeweb:hidden bind="containerType" />
		<qeweb:hidden bind="id" />
		<qeweb:hidden bind="page.id"/>
		<qeweb:commandButton name="update" operate="update" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>