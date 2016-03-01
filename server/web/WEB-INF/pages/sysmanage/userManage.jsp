<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="userForm.query">
	<qeweb:group relations="userForm:userTable">
		<qeweb:form id="userForm" bind="userBO" queryRange="true">
			<qeweb:textField bind="userCode" />
			<qeweb:textField bind="userName" />
			<qeweb:textField bind="organizationBO.orgName" />
			<qeweb:radio bind="organizationBO.orgType"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="userTable" bind="userBO"
			display="id=update;
				userCode=table,insert,update,view;
				userName=table,insert,update,view;
				organizationBO.orgName=table,insert,update,view;
				organizationBO.id=insert,update;
				cellphone=table,insert,update,view;
				telephone=table,insert,update,view;
				email=table,insert,update,view;
				postCode=insert,update,view;
				address=insert,update,view;
				organizationBO.orgType=table,view;
				userStatus=table,insert,update,view">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
				<qeweb:commandButton name="view" operate="view"/>
				<qeweb:commandButton name="setGroups" />
				<qeweb:commandButton name="resetPassword" operate="resetPassword" />
				<qeweb:commandButton name="unusing" operate="unusing" />
				<qeweb:commandButton name="using" operate="using" />
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="userCode"/>
			<qeweb:textField bind="userName"/>
			<qeweb:textField bind="organizationBO.orgName" >
				<qeweb:source bindBo="organizationBO" bindBop="organizationBO.id:id;organizationBO.orgName:orgName" operate="organizationBO.toChoseOrg"/>
			</qeweb:textField>
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

