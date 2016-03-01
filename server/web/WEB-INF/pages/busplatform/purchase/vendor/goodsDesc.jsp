<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 收发货明细 --%>

<qeweb:page>

	<qeweb:table id="bp_rec_item" bind="bp_VendorGoodsDeliveryItemBO">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="itemNo" />
		<qeweb:textField bind="vendorGoodsDelivery.deliveryNo" />
		<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" />
		<qeweb:textField bind="purchaseOrderItemBO.material.materialName" />
		<qeweb:textField bind="deliveryQty" />
		<qeweb:textField bind="deliveryQty" />
	</qeweb:table>
	
	
</qeweb:page>