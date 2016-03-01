<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 为用户组分配用户 --%>
<qeweb:page bind="userGroup_UserBO">
	<qeweb:commandButton name="save" operate="save" text="form.save" />
	<qeweb:commandButton name="goBack" text="form.back" />
	
	<qeweb:form id="userGroup_UserBO" bind="userGroup_UserBO" param="required" layout="3;C2(groupNotes)">
		<qeweb:label bind="userGroupBO.groupName"/>
		<qeweb:label bind="userGroupBO.groupNotes"/>
		<qeweb:hidden bind="userGroupBO.id"/>
	</qeweb:form>
	
	<qeweb:checkTree id="userTreeBO" bind="userTreeBO" param="required" />
</qeweb:page>