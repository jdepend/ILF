<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 采购订单明细历史 -->
<qeweb:page>
	<qeweb:form id="bp_po_main" bind="bp_PurchaseOrderItemBO" param="required">
		<qeweb:label bind="material.materialCode"/>
		<qeweb:label bind="material.materialName"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>

	<qeweb:table id="bp_item_his" bind="bp_PurchaseOrderItemHisBO" param="required" hasBbar="false" sm="empty">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="itemNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
		<qeweb:textField bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
		<qeweb:textField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
		<qeweb:textField bind="lastModifyTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemHisBO.lastModifyTime"/>
	</qeweb:table>
</qeweb:page>
