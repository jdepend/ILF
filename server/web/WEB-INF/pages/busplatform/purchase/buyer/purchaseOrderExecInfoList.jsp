<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 订单执行情况-->
<qeweb:page onLoad="bp_poForm.query">
	<qeweb:group relations="bp_poForm:bp_poTable">
		<qeweb:form id="bp_poForm" bind="bp_PurchaseOrderExecutionBO" queryRange="true" layout="3;C2(orderTime)">
			<qeweb:textField bind="purchaseOrderBO.purchaseNo"  text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="purchaseOrderBO.vendor.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgCode"/>
			<qeweb:textField bind="purchaseOrderBO.vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
			<qeweb:textField bind="purchaseOrderBO.buyerUser.userCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyerUser.userCode"/>
			<qeweb:textField bind="purchaseOrderBO.buyerUser.userName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyerUser.userName"/>
			<qeweb:expend>
				<qeweb:dateField bind="orderTime"  text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_poTable" bind="bp_PurchaseOrderExecutionBO" sm="empty">
			<qeweb:commandButton name="statistics" operate="statistics" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.statistics"/>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="purchaseOrderBO.purchaseNo" width="100" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="purchaseOrderItemBO.itemNO" width="50" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:textField bind="purchaseOrderItemBO.orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:textField bind="purchaseOrderItemBO.material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:textField bind="purchaseOrderItemBO.unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:textField bind="purchaseOrderBO.vendor.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgCode" />
			<qeweb:textField bind="purchaseOrderBO.vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
			<qeweb:textField bind="purchaseOrderItemBO.orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
			<qeweb:textField bind="receiveQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.receiveQty"/>
			<qeweb:textField bind="goodsRejectQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.goodsRejectQty"/>
			<qeweb:textField bind="onwayQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.onwayQty"/>
			<qeweb:textField bind="unsendQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderExecutionBO.unsendQty"/>
			<qeweb:textField bind="purchaseOrderBO.buyerUser.userCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyerUser.userCode"/>
			<qeweb:textField bind="purchaseOrderBO.buyerUser.userName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyerUser.userName"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>