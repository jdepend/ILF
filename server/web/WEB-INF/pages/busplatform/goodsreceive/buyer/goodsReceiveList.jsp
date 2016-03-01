<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 收货记录 -->
<qeweb:page onLoad="bp_recForm.query">
	<qeweb:group relations="bp_recForm:bp_recTable">
		<qeweb:form id="bp_recForm" bind="bp_BuyerGoodsReceiveBO" queryRange="true" layout="C2(receiveTime)">
			<qeweb:textField bind="receiveNo" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveNo"/>
			<qeweb:textField bind="vendorGoodsDeliveryBO.deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
			<qeweb:expend>
				<qeweb:dateField bind="receiveTime" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveTime"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_recTable" bind="bp_BuyerGoodsReceiveBO" sm="empty">
			<qeweb:commandButton name="goImp" text="imp"/>
			<qeweb:commandButton name="download" operate="download"/>
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="bp_BuyerGoodsReceiveBO.viewDetial"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="receiveNo" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveNo"/>
			<qeweb:textField bind="vendorGoodsDeliveryBO.deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
			<qeweb:textField bind="receiveTime" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.receiveTime"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>