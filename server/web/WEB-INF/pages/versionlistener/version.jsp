<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="versionForm" bind="versionBO" layout="1" text="项目版本信息">
		<qeweb:label bind="projectVersion" text="项目版本"/>
		<qeweb:label bind="projectVersionTime" text="提交测试时间"/>
		<qeweb:label bind="qewebVersion" text="开发平台版本"/>
		<qeweb:label bind="busVersion" text="业务平台版本"/>
		<qeweb:label bind="person" text="填表人"/>
	</qeweb:form>
</qeweb:page>