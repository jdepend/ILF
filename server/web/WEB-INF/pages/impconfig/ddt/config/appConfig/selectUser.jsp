<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="userForm.query">
	<qeweb:group relations="userForm:userTable">
		<qeweb:form id="userForm" bind="userBO" layout="2" queryRange="true">
			<qeweb:textField bind="userCode" />
			<qeweb:textField bind="userName" />
			<qeweb:textField bind="organizationBO.orgName" />
			<qeweb:radio bind="organizationBO.orgType"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="userTable" bind="userBO">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="userCode"/>
			<qeweb:textField bind="userName"/>
			<qeweb:textField bind="organizationBO.orgName" />
			<qeweb:hidden bind="organizationBO.id" />
			<qeweb:textField bind="cellphone"/>
			<qeweb:textField bind="telephone"/>
			<qeweb:textField bind="email"/>
			<qeweb:textField bind="postCode"/>
			<qeweb:textArea bind="address"/>
			<qeweb:radio bind="organizationBO.orgType" align="center"/>
			<qeweb:radio bind="userStatus" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

