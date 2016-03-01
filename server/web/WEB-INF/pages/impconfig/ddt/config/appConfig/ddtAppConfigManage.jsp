<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.formQuery">
	<qeweb:group relations="form:list">
		<qeweb:form id="form" bind="ddtAppConfigBO" queryRange="true" >
			<qeweb:textField bind="schemaCode" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO.schemaCode"/>
			<qeweb:textField bind="userCode" text="com.qeweb.sysmanage.purview.bo.UserBO.userCode"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
			<qeweb:commandButton name="formClear" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="list" bind="ddtAppConfigBO" >
			<qeweb:commandButton name="insert" text="insert" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" text="update" width="80"/>
				<qeweb:commandButton name="validateDelete" operate="delete" text="delete"/>
			</qeweb:expend>
			<qeweb:textField bind="schemaCode" text="com.qeweb.framework.impconfig.ddt.config.bo.DdtSchemaBO.schemaCode"/>
			<qeweb:textField bind="userCode" text="com.qeweb.sysmanage.purview.bo.UserBO.userCode"/>
			<qeweb:textField bind="roleName" text="com.qeweb.sysmanage.purview.bo.RoleBO.roleName"/>
			<qeweb:textField bind="attr1" />
			<qeweb:textField bind="attr2" />
			<qeweb:textField bind="attr3" />
			<qeweb:hidden bind="id"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>