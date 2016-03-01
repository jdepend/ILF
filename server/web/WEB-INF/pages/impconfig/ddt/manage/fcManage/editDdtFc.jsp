<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="form" bind="ddtSysFcBO" layout="1" param="required" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.editPage">
		<qeweb:textField bind="container.page.name" />
		<qeweb:textField bind="container.containerTypeShow" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeContainerBO.containerType"/>
		<qeweb:textField bind="container.containerId" />
		<qeweb:textField bind="container.boName" />
		<qeweb:textField bind="bopname">
			<qeweb:source bindBo="ddtSysFcBO"
				bindBop="container.id:container.id;container.page.name:page.name;container.containerTypeShow:container.containerTypeShow;container.containerId:container.containerId;container.boName:container.boName;bopname:fc.bopname"
				sm="radio" />
		</qeweb:textField>
		<qeweb:select bind="fcType" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.fcType"/>
		<qeweb:textField bind="mdtFieldsId" />
		<qeweb:select bind="mdtFieldsType" />
		<qeweb:radio bind="fcIsRequired" />
		<qeweb:select bind="fcStatus" />
		<qeweb:textField bind="fcMaxLength" />
		<qeweb:textField bind="fcMinValue" />
		<qeweb:textField bind="fcMaxValue" />
		<qeweb:textField bind="fcStepValue" />
		<qeweb:hidden bind="container.id" />
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="update" operate="update" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>