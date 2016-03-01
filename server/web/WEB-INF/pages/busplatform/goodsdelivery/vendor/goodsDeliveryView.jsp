<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%--发货记录详情 --%>
<qeweb:page bind="bp_page_VendorGoodsDeliveryBO" param="required">
	<qeweb:commandButton name="delive" operate="delive" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.delive"/>
	<qeweb:commandButton name="cancelDelive" operate="cancelDelive" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.cancelDelive"/>
	<qeweb:commandButton name="back" text="form.back"/>

	<qeweb:form id="bp_rec_main" bind="bp_VendorGoodsDeliveryBO" layout="3;C3(remark)" param="required">
		<qeweb:label bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
		<qeweb:label bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
		<qeweb:label bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus"/>
		<qeweb:label bind="receiveStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.receiveStatus"/>
		<qeweb:label bind="estimatedDlvTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.estimatedDlvTime"/>
		<qeweb:label bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
		<qeweb:label bind="remark" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.remark"/>
		<qeweb:label bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		<qeweb:label bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>

	<qeweb:table id="bp_rec_item" bind="bp_VendorGoodsDeliveryItemBO" param="required" hasBbar="false" sm="empty">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseOrderItemBO.purchaseOrderBO.purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
		<qeweb:textField bind="purchaseOrderItemBO.material.materialSpec" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialSpec"/>
		<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO.itemNo"/>
		<qeweb:textField bind="purchaseOrderItemBO.unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
		<qeweb:textField bind="purchaseGoodsPlan.orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
		<qeweb:textField bind="deliveryQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO.deliveryQty"/>
		<qeweb:textField bind="purchaseGoodsPlan.orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
		<qeweb:anchor bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		<qeweb:anchor bind="lockMsg" text="com.qeweb.busplatform.bo.pomanage.BP_PurchaseOrderItemBO.lockMsg"/>
	</qeweb:table>
</qeweb:page>

