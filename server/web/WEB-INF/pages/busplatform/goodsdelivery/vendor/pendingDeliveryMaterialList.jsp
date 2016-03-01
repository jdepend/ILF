<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 按物料发货看板 -->
<qeweb:page onLoad="bp_dlvForm.query">
	<qeweb:group relations="bp_dlvForm:bp_dlvTable">
		<qeweb:form id="bp_dlvForm" bind="bp_PendingDeliveryMaterialBO" >
			<qeweb:textField bind="material.materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
			<qeweb:textField bind="material.materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_dlvTable" bind="bp_PendingDeliveryMaterialBO"
			display="material.materialCode=table;
					material.materialName=table;
					buyer.orgCode=table;
					buyer.orgName=table;
					unDlvQty=table;
					waitQty=table:edit;
					unitName=table">
			<qeweb:commandButton name="createDelivery" operate="validatePlanItem, bp_VendorGoodsDeliveryBO.gainDeliveryInfo, bp_PendingDeliveryBO.getPendingDeliveryByMaterial"
				text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.createDelivery"/>
			<qeweb:commandButton name="export" operate="export" text="com.qeweb.busplatform.goodsdelivery.bo.BP_PendingDeliveryMaterialBO.export"/>
			<qeweb:hidden bind="materialId"/>
			<qeweb:hidden bind="buyerId"/>
			<qeweb:hidden bind="vendorId"/>
			<qeweb:textField bind="material.materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
			<qeweb:textField bind="material.materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
			<qeweb:textField bind="buyer.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgCode"/>
			<qeweb:textField bind="buyer.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:textField bind="unDlvQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.unsentQty"/>
			<qeweb:textField bind="waitQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseGoodsPlanBO.waitQty"/>
			<qeweb:textField bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>