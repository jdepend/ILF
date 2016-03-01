<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form bind="propertyBO" id="propertyBO" layout="1" param="required">
		<qeweb:label bind="type" />
		<qeweb:label bind="amount" />
		<qeweb:label bind="attr_1" />
		<qeweb:label bind="attr_2" />
		<qeweb:label bind="attr_3" />
		<qeweb:label bind="attr_4" />
	</qeweb:form>
</qeweb:page>
