<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtSysPagesBO" queryRange="true" >
			<qeweb:select bind="module.id" text="com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysModulesBO.moduleName"/>
			<qeweb:textField bind="name" />
			<qeweb:textField bind="url" />
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtSysPagesBO" >
			<qeweb:commandButton name="insert" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="ddtSysPagesBO.toEdit" text="update" width="80"/>
				<qeweb:commandButton name="validateDelete" operate="ddtSysPagesBO.validateDelete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="module.moduleName" width="130"/>
			<qeweb:textField bind="name" width="140"/>
			<qeweb:textField bind="url" width="600"/>
			<qeweb:hidden bind="module.id"/>
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>