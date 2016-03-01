<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 收货看板 -->
<qeweb:page onLoad="bp_recForm.query">
	<qeweb:group relations="bp_recForm:bp_recTable">
		<qeweb:form id="bp_recForm" bind="bp_PendingReceiveBO" queryRange="true" layout="3;C2(deliveryTime, estimatedDlvTime)">
			<qeweb:textField bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
			<qeweb:expend>
				<qeweb:dateField bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
			</qeweb:expend>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus"/>
			<qeweb:expend>
				<qeweb:dateField bind="estimatedDlvTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.estimatedDlvTime"/>
			</qeweb:expend>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
			<qeweb:select bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_recTable" bind="bp_PendingReceiveBO" sm="empty">
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="bp_page_PendingReceiveBO.setBtnStatus"
					text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.view"/>
				<%--
				<qeweb:commandButton name="goReceive"
					text="com.qeweb.busplatform.goodsreceive.bo.BP_PendingReceiveBO.receive"/>
				 --%>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="deliveryNo" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgCode"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.goodsreceive.bo.BP_BuyerGoodsReceiveBO.orgName"/>
			<qeweb:select bind="verifyStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.verifyStatus"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryStatus"/>
			<qeweb:textField bind="deliveryTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.deliveryTime"/>
			<qeweb:textField bind="estimatedDlvTime" text="com.qeweb.busplatform.goodsdelivery.bo.BP_VendorGoodsDeliveryBO.estimatedDlvTime"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.bo.goodsdelivery.BP_VendorGoodsDeliveryBO.lockStatus"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>