<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form bind="propertyBO" id="propertyBO" layout="1">
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:radio bind="type" />
		<qeweb:textField bind="amount" />
		<qeweb:textField bind="attr_1" />
		<qeweb:textField bind="attr_2" />
	</qeweb:form>
</qeweb:page>
