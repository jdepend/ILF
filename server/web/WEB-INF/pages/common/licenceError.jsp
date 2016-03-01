<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="licenceBO" bind="licenceBO" layout="1" text="licence已过期，请与软件发行方联系！">
		<qeweb:fileField bind="licenceBOP" text="licence" operate="upload" />
		<qeweb:commandButton name="goback" text="form.back"/>
	</qeweb:form>
</qeweb:page>

