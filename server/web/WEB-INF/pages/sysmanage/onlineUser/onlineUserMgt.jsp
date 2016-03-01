<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="onlineUserBOForm.query">
	<qeweb:group relations="onlineUserBOForm:onlineUserBOTable">
		<qeweb:form id="onlineUserBOForm" bind="onlineUserBO" queryRange="true">
			<qeweb:textField bind="userCode" text="com.qeweb.sysmanage.purview.bo.UserBO.userCode"/>
			<qeweb:textField bind="userName" text="com.qeweb.sysmanage.purview.bo.UserBO.userName"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="onlineUserBOTable" bind="onlineUserBO" >
			<qeweb:expend>
				<qeweb:commandButton name="tickOut" operate="tickOut" />
			</qeweb:expend>
			<qeweb:textField bind="userCode" text="com.qeweb.sysmanage.purview.bo.UserBO.userCode"/>
			<qeweb:textField bind="userName" text="com.qeweb.sysmanage.purview.bo.UserBO.userName"/>
			<qeweb:dateField bind="loginTime"/>
			<qeweb:textField bind="ip" />
			<qeweb:hidden bind="sessionId"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>