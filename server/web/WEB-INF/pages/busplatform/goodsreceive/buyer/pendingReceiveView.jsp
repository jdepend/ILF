<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<qeweb:page bind="bp_page_PendingReceiveBO" param="required">
	<qeweb:commandButton name="verifyPass" operate="verifyPass"/>
	<qeweb:commandButton name="verifyReject" operate="verifyReject"/>
	<qeweb:commandButton name="back" text="form.back"/>

	<qeweb:form id="bp_rec_main" bind="bp_PendingReceiveBO" param="required">
		<qeweb:label bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
		<qeweb:label bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
		<qeweb:label bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
		<qeweb:label bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
		<qeweb:label bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus"/>
		<qeweb:label bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		<qeweb:label bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus"/>
		<qeweb:hidden bind="id" />
	</qeweb:form>

	<qeweb:table id="bp_rec_item" bind="bp_VendorGoodsDeliveryItemBO" param="required" sm="empty" hasBbar="false">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO.itemNo"/>
		<qeweb:textField bind="purchaseOrderItemBO.purchaseOrderBO.purchaseNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.purchaseNo"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
		<qeweb:textField bind="purchaseOrderItemBO.orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
		<qeweb:textField bind="deliveryQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO.deliveryQty"/>
		<qeweb:textField bind="purchaseOrderItemBO.unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
		<qeweb:textField bind="purchaseOrderItemBO.orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
		<qeweb:anchor bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		<qeweb:anchor bind="lockMsg" text="com.qeweb.busplatform.bo.pomanage.BP_PurchaseOrderItemBO.lockMsg"/>
	</qeweb:table>
</qeweb:page>
