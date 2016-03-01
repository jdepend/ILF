<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 发货看板 -->
<qeweb:page bind="bp_VendorGoodsDeliveryBO">
	<qeweb:commandButton name="createDelivery" operate="createDelivery" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.createDelivery"/>
	<qeweb:commandButton name="back" operate="back" text="form.back"/>
	<qeweb:form id="bp_dlvForm" bind="bp_VendorGoodsDeliveryBO" param="required" layout="C3(remark)">
		<qeweb:label bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
		<qeweb:label bind="createUser.userName" />
		<qeweb:dateField bind="estimatedDlvTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.estimatedDlvTime"/>
		<qeweb:textArea bind="remark" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.remark"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
		<qeweb:hidden bind="createUser.id"/>
	</qeweb:form>
    <!-- 自动匹配的待发货记录 -->
 	<qeweb:table id="bp_dlvTable" bind="bp_PendingDeliveryBO" param="required" hasBbar="false" sm="empty"
		display="purchaseNO=table;itemNO=talbe;materialCode=table;materialName=table;
				unitName=table;orderQty=table;shuldDlvQty=table;waitQty=table;orderTime=table;"
		text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryItemBO">
		<qeweb:expend>
			<qeweb:commandButton name="jsDelete" operate="jsDelete" text="delete"/>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:hidden bind="itemId"/>
		<qeweb:hidden bind="vendorId"/>
		<qeweb:hidden bind="buyerId"/>
		<qeweb:textField bind="purchaseNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
		<qeweb:textField bind="itemNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.itemNo"/>
		<qeweb:textField bind="materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
 		<qeweb:textField bind="materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
		<qeweb:textField bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
		<qeweb:textField bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.orderQty"/>
		<qeweb:textField bind="shuldDlvQty" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryBO.shuldDlvQty"/>
		<qeweb:textField bind="waitQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.waitQty"/>
		<qeweb:textField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.orderTime"/>
	</qeweb:table>
</qeweb:page>