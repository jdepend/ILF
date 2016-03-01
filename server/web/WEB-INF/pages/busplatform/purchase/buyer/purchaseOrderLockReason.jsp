<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="bp_poForm" bind="bp_PurchaseOrderBO" text="" layout="2;C2(manlockReason)" param="required">
		<qeweb:label bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
		<qeweb:label bind="purchaseTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseTime"/>
		<qeweb:label bind="vendor.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgCode" />
		<qeweb:label bind="vendor.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.vender.orgName"/>
		<qeweb:textArea bind="manlockReason" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.manlockReason"/>
		<qeweb:hidden bind="poManunlock"/>
		<qeweb:hidden bind="id"/>
		<qeweb:commandButton name="save" operate="manlock" text="save"/>
	</qeweb:form>
</qeweb:page>