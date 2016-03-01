<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="userBO" bind="userBO" layout="1" text="com.qeweb.sysmanage.purview.bo.UserBO.userInfo">
		<qeweb:label bind="userCode"/>
		<qeweb:label bind="userName"/>
		<qeweb:password bind="password"/>
		<qeweb:password bind="newPassword"/>
		<qeweb:password bind="newPasswordAgain"/>
		<qeweb:commandButton name="modifyPwd" operate="modifyPwd" text="form.save" icon="save"/>
	</qeweb:form>
</qeweb:page>