<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page layout="2">
	<qeweb:form id="onlineUserMgt" bind="onlineUserMgt" param="required" queryRange="true" layout="2">
		<qeweb:textField bind="timeoutDisplay" />
		<qeweb:commandButton name="form.save" operate="updateTimeout" text="form.save"/>
	</qeweb:form>
</qeweb:page>