<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSysModulesBO" queryRange="true" >
			<qeweb:textField bind="moduleName" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSysModulesBO" >
			<qeweb:commandButton name="insert" operate="ddtSysModulesBO.toAdd" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtSysModulesBO.toEdit" text="update"/>
				<qeweb:commandButton name="validateDelete" operate="ddtSysModulesBO.validateDelete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="moduleName" />
			<qeweb:textField bind="parentModule.moduleName" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.parentModuleName" />
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="parentModule.id" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>