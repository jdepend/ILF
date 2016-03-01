<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="bp_po_plan.sysFresh">
	<qeweb:group relations="bp_po_main:bp_po_plan">
		<qeweb:form id="bp_po_main" bind="bp_PurchaseOrderItemBO" param="required">
			<qeweb:label bind="itemNO" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:label bind="material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:label bind="material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:label bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
			<qeweb:label bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:label bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
	
		<!-- 供货计划 -->
		<qeweb:table id="bp_po_plan" bind="bp_PurchaseGoodsPlanBO" autoScroll="true"
			text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO"
			display="itemNo=table,view;
			material.materialCode=table,view;
			material.materialName=table,view;
			orderQty=table,view;
			unitName=table,view;
			orderTime=table,view;
			modifyCount=table,view;
			confirmStatus=table,view;
			feedback=table">
			<qeweb:commandButton name="cancelGoodsPlanReject" operate="cancelReject" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.cancelReject"/>
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="view" width="100"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="itemNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
			<qeweb:textField bind="material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
			<qeweb:textField bind="material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
			<qeweb:textField bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
			<qeweb:textField bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
			<qeweb:textField bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime" width="200"/>
			<qeweb:anchor bind="modifyCount" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.modifyCount"/>
			<qeweb:label bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus" align="center"/>
			<qeweb:anchor bind="feedback" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.feedback"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
