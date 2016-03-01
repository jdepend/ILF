<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<qeweb:page>
	<qeweb:form id="bp_po_main" bind="bp_PurchaseOrderBO" param="required">
		<qeweb:label bind="purchaseNo"/>
	</qeweb:form>

	<qeweb:table id="bp_po_item" bind="bp_PurchaseOrderItemBO" param="required">
		<qeweb:textField bind="itemNo" />
		<qeweb:textField bind="orderQty" />
		<qeweb:textField bind="unitName" />
		<qeweb:dateField bind="orderTime"/>
	</qeweb:table>
</qeweb:page>
