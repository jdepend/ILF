<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%-- 发货看板,供应商发货类型:按订单发货 --%>
<qeweb:page onLoad="bp_dlvForm.query">
	<qeweb:group relations="bp_dlvForm:bp_dlvTable">
		<qeweb:form id="bp_dlvForm" bind="bp_PendingDeliveryBO" layout="C2(orderTime)" queryRange="true">
			<qeweb:textField bind="purchaseNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
 			<qeweb:textField bind="materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
			<qeweb:expend>
				<qeweb:dateField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.orderTime"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_dlvTable" bind="bp_PendingDeliveryBO" rememberChecked="true" autoScroll="true">
			<qeweb:commandButton name="validatePlanItem" operate="validatePlanItem, bp_VendorGoodsDeliveryBO.gainDeliveryInfo, bp_PendingDeliveryBO.getDeliveryDesc"
				text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.validatePlanItem"/>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="itemId"/>
			<qeweb:hidden bind="vendorId"/>
			<qeweb:hidden bind="buyerId"/>
			<qeweb:textField bind="purchaseNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo" width="150"/>
			<qeweb:textField bind="buyer.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgCode"/>
			<qeweb:textField bind="buyer.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:textField bind="itemNO" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO.itemNo"/>
			<qeweb:textField bind="materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
 			<qeweb:textField bind="materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
 			<qeweb:textField bind="material.materialSpec" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialSpec"/>
			<qeweb:textField bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:textField bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.orderQty"/>
			<qeweb:textField bind="deliveryQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.deliveryQty"/>
 			<qeweb:textField bind="receiveQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.receiveQty" />
 			<qeweb:textField bind="goodsRejectQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.goodsRejectQty" />
 			<qeweb:textField bind="varianceQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.varianceQty"/>
			<qeweb:textField bind="shuldDlvQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.shuldDlvQty"/>
			<qeweb:textField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.orderTime"/>
			<qeweb:textField bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus"/>
			<qeweb:textField bind="manlockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockStatus"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>