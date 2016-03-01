<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="bp_poForm.query">
	<qeweb:group relations="bp_poForm:bp_poTable">
		<qeweb:form id="bp_poForm" bind="bp_PurchaseOrderBO">
			<qeweb:textField bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:select bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.deliveryStatus"/>
			<qeweb:select bind="receiveStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.receiveStatus"/>
			<qeweb:textField bind="materialCode" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialCode"/>
			<qeweb:textField bind="materialName" text="com.qeweb.sysmanage.purview.bo.SysMaterialBO.materialName"/>
			<qeweb:select bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus"/>
			<qeweb:select bind="manlockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockStatus"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_poTable" bind="bp_PurchaseOrderBO" autoScroll="true">
			<qeweb:commandButton name="wholeConfirm" operate="confirm" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.wholeConfirm"/>
			<qeweb:commandButton name="wholeReject" operate="reject" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.wholeReject"/>
			<qeweb:expend>
				<qeweb:commandButton name="view" width="120" operate="bp_page_PurchaseOrderBO.setBtnStatus,bp_PurchaseOrderBO.viewDetial" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="publishStatus"/>
			<qeweb:textField bind="purchaseNo" width="150" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="buyer.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgCode"/>
			<qeweb:textField bind="buyer.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
			<qeweb:textField bind="publishTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.publishTime"/>
			<qeweb:select bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus" align="center"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.deliveryStatus" align="center"/>
			<qeweb:select bind="receiveStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.receiveStatus" align="center"/>
			<qeweb:select bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus" align="center"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus" align="center"/>
			<qeweb:select bind="manlockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockStatus" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>