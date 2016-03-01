<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="pfv_varBO" text="变量设置" layout="1" param="required">
		<qeweb:label bind="alias" text="变量"/>
		<qeweb:select bind="varValueSet" text="值集" />
		<qeweb:select bind="varValue" text="值"/>
		<qeweb:hidden bind="name"/>
		<qeweb:hidden bind="id"/>
		<qeweb:commandButton name="config" operate="config" text="保存"/>
	</qeweb:form>
</qeweb:page>
