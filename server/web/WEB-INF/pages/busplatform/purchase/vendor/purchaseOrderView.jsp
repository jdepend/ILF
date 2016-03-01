<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="bp_page_PurchaseOrderBO" param="required" onLoad="bp_po_item.sysFresh;bp_dlv_item.sysFresh;bp_rec_item.sysFresh">
	<qeweb:commandButton name="poConfirm" operate="confirm" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.poConfirm"/>
	<qeweb:commandButton name="poReject" operate="reject" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.poReject"/>
	<qeweb:commandButton name="back" text="form.back"/>

	<qeweb:group relations="bp_po_main:bp_po_item;bp_po_main:bp_dlv_item;bp_po_main:bp_rec_item">
		<qeweb:form id="bp_po_main" bind="bp_PurchaseOrderBO" param="required">
			<qeweb:label bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:label bind="buyer.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:label bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
			<qeweb:label bind="publishTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.publishTime"/>
			<qeweb:label bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus"/>
			<qeweb:label bind="changeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.changeStatus"/>
			<qeweb:label bind="deliveryStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.deliveryStatus"/>
			<qeweb:label bind="receiveStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.receiveStatus"/>
			<qeweb:label bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus"/>
			<qeweb:anchor bind="feedback" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.feedback"/>
			<qeweb:hidden bind="buyer.orgCode"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>

		<qeweb:table id="bp_po_item" bind="bp_PurchaseOrderItemBO" autoScroll="true" hasBbar="false" sm="empty"
			display="itemNO=table,view;material.materialCode=table,view;material.materialName=table,view;
			orderQty=table,view;unitName=table,view;orderTime=table,view;
			modifyCount=table,view;confirmStatus=table,view;closeStatus=table,view;feedback=table;
			lockStatus=table;lockMsg=table;">
			<qeweb:expend>
				<qeweb:commandButton name="confirm" operate="confirm" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.poConfirm"/>
				<qeweb:commandButton name="reject" operate="reject" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.poReject"/>
				<qeweb:commandButton name="view" operate="view" width="120"/>
				<qeweb:commandButton name="viewGoodsPlan" operate="bp_PurchaseOrderItemBO.showItemInfo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.viewGoodsPlan"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="purchaseOrderBO.id"/>
			<qeweb:hidden bind="purchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="itemNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:textField bind="material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:textField bind="material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:textField bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
			<qeweb:textField bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:dateField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
			<qeweb:select bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus" align="center"/>
			<qeweb:select bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus" align="center"/>
			<qeweb:anchor bind="feedback" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.feedback"/>
			<qeweb:anchor bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus" align="center"/>
			<qeweb:anchor bind="lockMsg" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockMsg" align="center"/>
		</qeweb:table>

		<!-- 发货单明细 -->
		<qeweb:table id="bp_dlv_item" bind="bp_VendorGoodsDeliveryItemBO" sm="empty" hasBbar="false">
			<qeweb:textField bind="vendorGoodsDelivery.deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:textField bind="deliveryQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.deliveryQty"/>
			<qeweb:textField bind="purchaseOrderItemBO.unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:textField bind="vendorGoodsDelivery.deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
		</qeweb:table>

		<!-- 收货单明细 -->
		<qeweb:table id="bp_rec_item" bind="bp_BuyerGoodsReceiveItemBO" sm="empty" hasBbar="false">
			<qeweb:textField bind="buyerGoodsReceiveBO.receiveNo" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveNo"/>
			<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:textField bind="receiveQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.receiveQty"/>
			<qeweb:textField bind="goodsRejectQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.goodsRejectQty"/>
			<qeweb:textField bind="buyerGoodsReceiveBO.receiveTime" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveTime"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
