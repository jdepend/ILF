<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="ddtSysModulesBO" layout="1" param="required">
		<qeweb:label bind="validateResult" text=""/>
		<qeweb:hidden bind="id" />
		<qeweb:commandButton name="deleteModule" operate="deleteModule" text="confirm"/>
		<qeweb:commandButton name="cancel" text="取消"/>
	</qeweb:form>
</qeweb:page>