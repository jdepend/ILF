<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="projectPathBO" bind="projectPathBO" text="项目路径信息" layout="1" param="required">
		<qeweb:textField bind="projectPath" text="项目路径"/>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset" />
	</qeweb:form>
</qeweb:page>