<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<qeweb:page bind="bp_BuyerGoodsReceiveBO">
	<qeweb:commandButton name="back" text="form.back"/>
	<qeweb:form id="bp_rec_main" bind="bp_BuyerGoodsReceiveBO" param="required">
		<qeweb:label bind="receiveNo" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveNo"/>
		<qeweb:label bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
		<qeweb:label bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
		<qeweb:label bind="vendorGoodsDeliveryBO.deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
		<qeweb:label bind="receiveTime" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveTime"/>
	</qeweb:form>

	<qeweb:table id="bp_rec_item" bind="bp_BuyerGoodsReceiveItemBO" param="required" sm="empty" hasBbar="false">
		<qeweb:textField bind="purchaseOrderItemBO.purchaseOrderBO.purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
		<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveItemBO.itemNo"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
		<qeweb:textField bind="receiveQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.receiveQty"/>
		<qeweb:textField bind="goodsRejectQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.goodsRejectQty"/>
	</qeweb:table>
</qeweb:page>
