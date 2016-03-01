<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="masterdataTemplateForm" bind="masterdataTemplateBO" layout='1' param="required">
		<qeweb:anchor bind="materialTemplate" />
		<qeweb:anchor bind="vendorTemplate"/>
		<qeweb:anchor bind="purchaseOrderTemplate" />
		<qeweb:anchor bind="goodsReciveTemplate" />
		<qeweb:commandButton name="createTemplate" operate="createTemplate"/>
	</qeweb:form>
</qeweb:page>

