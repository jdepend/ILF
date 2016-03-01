<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%--为用户组分配角色组 --%>
<qeweb:page>
	<qeweb:form id="userGroup_GroupBO" bind="userGroup_GroupBO" param="required" layout="3;C2(userGroupBO.groupNotes),C3(groupIds)">
		<qeweb:label bind="userGroupBO.groupName"/>
		<qeweb:label bind="userGroupBO.groupNotes"/>
		<qeweb:optionTranserSelect bind="groupIds" text="com.qeweb.sysmanage.purview.bo.User_Group_BO.groupIds"/>
		<qeweb:hidden bind="userGroupBO.id"/>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:commandButton name="goBack" text="form.back"/>
	</qeweb:form>
</qeweb:page>