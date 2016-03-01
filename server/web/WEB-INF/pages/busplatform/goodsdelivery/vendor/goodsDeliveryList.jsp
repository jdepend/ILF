<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 发货记录 -->
<qeweb:page onLoad="bp_dlvForm.query">
	<qeweb:group relations="bp_dlvForm:bp_dlvTable">
		<qeweb:form id="bp_dlvForm" bind="bp_VendorGoodsDeliveryBO" queryRange="true" layout="C3(deliveryTime)">
			<qeweb:textField bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus"/>
			<qeweb:select bind="receiveStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.receiveStatus"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
			<qeweb:expend>
				<qeweb:dateField bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
			</qeweb:expend>
			<qeweb:select bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_dlvTable" bind="bp_VendorGoodsDeliveryBO" sm="empty">
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="bp_page_VendorGoodsDeliveryBO.setBtnStatus,
						bp_VendorGoodsDeliveryBO.getDeliveryMaint"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="purchaseNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.purchaseNo"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:dateField bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
			<qeweb:radio bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus" align="center"/>
			<qeweb:radio bind="receiveStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.receiveStatus" align="center"/>
			<qeweb:radio bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus" align="center"/>
			<qeweb:radio bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>