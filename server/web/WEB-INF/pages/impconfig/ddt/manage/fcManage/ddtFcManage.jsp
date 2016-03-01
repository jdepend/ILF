<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSysFcBO" queryRange="true" >
			<qeweb:textField bind="container.page.name"/>
			<qeweb:select bind="container.containerType"/>
			<qeweb:textField bind="container.containerId"/>
			<qeweb:textField bind="container.boName"/>
			<qeweb:textField bind="bopname" />
			<qeweb:select bind="fcType" />
			<qeweb:textField bind="mdtFieldsId" />
			<qeweb:select bind="mdtFieldsType" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSysFcBO" autoScroll="true">
			<qeweb:commandButton name="insert" operate="ddtSysFcBO.toAdd" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtSysFcBO.toEdit" text="update" width="80"/>
				<qeweb:commandButton name="validateDelete" operate="ddtSysFcBO.validateDelete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="container.page.name" width="150"/>
			<qeweb:select bind="container.containerType" width="100"/>
			<qeweb:textField bind="container.containerId" width="100"/>
			<qeweb:textField bind="container.boName" width="150"/>
			<qeweb:textField bind="bopname"  width="250"/>
			<qeweb:textField bind="fcType" />
			<qeweb:textField bind="mdtFieldsId" />
			<qeweb:textField bind="mdtFieldsType" />
			<qeweb:radio bind="fcIsRequired" />
			<qeweb:select bind="fcStatus" />
			<qeweb:textField bind="fcMaxLength" />
			<qeweb:textField bind="fcMinValue" />
			<qeweb:textField bind="fcMaxValue" />
			<qeweb:textField bind="fcStepValue" />
			<qeweb:hidden bind="container.id" />
			<qeweb:hidden bind="container.containerTypeShow" />
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>