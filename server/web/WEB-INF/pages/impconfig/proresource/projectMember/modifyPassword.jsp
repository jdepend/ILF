<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="memberForm" bind="projectMemberBO" text="项目成员信息" layout="1" param="required">
		<qeweb:label bind="memberCode"/>
		<qeweb:label bind="memberName"/>
		<qeweb:password bind="password" />
		<qeweb:password bind="newPassword"/>
		<qeweb:password bind="newPasswordAgain" />
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="modifyPassword" operate="modifyPassword" icon="save" text="form.save"/>
	</qeweb:form>
</qeweb:page>
