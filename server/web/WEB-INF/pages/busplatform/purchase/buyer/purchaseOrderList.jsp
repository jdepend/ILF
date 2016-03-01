<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="bp_poForm.query">
	<qeweb:group relations="bp_poForm:bp_poTable">
		<qeweb:form id="bp_poForm" bind="bp_PurchaseOrderBO" queryRange="true">
			<qeweb:textField bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgCode" />
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
			<qeweb:select bind="publishStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.publishStatus"/>
			<qeweb:select bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.deliveryStatus"/>
			<qeweb:select bind="receiveStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.receiveStatus"/>
			<qeweb:select bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus"/>
			<qeweb:select bind="manlockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockStatus"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_poTable" bind="bp_PurchaseOrderBO" autoScroll="true">
			<qeweb:commandButton name="download" operate="download" text="down"/>
			<qeweb:commandButton name="importPOFromExcel" text="imp"/>
			<qeweb:commandButton name="importPOFromIA" operate="impPO" text="imp"/>
			<qeweb:commandButton name="publish" operate="publishBatch" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.publish"/>
			<qeweb:commandButton name="cancelPublish" operate="cancelPublishBatch" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.cancelPublish"/>
			<qeweb:commandButton name="cancelReject" operate="cancelRejectBatch" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.cancelReject"/>
			<qeweb:commandButton name="wholeClose" operate="closeBatch" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.close"/>
			<qeweb:expend>
				<qeweb:commandButton name="view" width="120" operate="bp_PurchaseOrderBO.viewDetial,bp_page_PurchaseOrderBO.setBtnStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
			<qeweb:textField bind="vendor.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgCode"/>
			<qeweb:textField bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
			<qeweb:textField bind="purchaseTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseTime" width="100"/>
			<qeweb:select bind="publishStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.publishStatus" align="center"/>
			<qeweb:select bind="confirmStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.confirmStatus" align="center"/>
			<qeweb:select bind="deliveryStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.deliveryStatus" align="center"/>
			<qeweb:select bind="receiveStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.receiveStatus" align="center"/>
			<qeweb:select bind="closeStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.closeStatus" align="center"/>
			<qeweb:select bind="lockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.lockStatus" align="center"/>
			<qeweb:select bind="manlockStatus" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockStatus" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>