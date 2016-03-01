<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 反馈 -->
<qeweb:page bind="bp_GoodsPlanFeedBackBO">
	<qeweb:commandButton name="feedback" operate="feedback"/>
	<qeweb:form id="bp_po_main" bind="bp_PurchaseGoodsPlanBO" param="required">
		<qeweb:label bind="itemNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.itemNO"/>
		<qeweb:label bind="material.materialCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialCode"/>
		<qeweb:label bind="material.materialName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.materialName"/>
		<qeweb:label bind="orderQty" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderQty"/>
		<qeweb:label bind="unitName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.unitName"/>
		<qeweb:label bind="orderTime" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderItemBO.orderTime"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>

	<qeweb:form id="bp_feedback_form" bind="bp_FeedBackBO" layout="C3(feedbackContent)">
		<qeweb:textArea bind="feedbackContent"/>
	</qeweb:form>
	<!-- 反馈详情 -->
	<qeweb:table id="bp_feedback_table" bind="bp_FeedBackBO" param="required">
		<qeweb:label bind="feedUserBO.userName"/>
		<qeweb:label bind="feedbackContent" text="com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.feedbackContent"/>
		<qeweb:label bind="createTime" text="com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.createTime"/>
	</qeweb:table>

</qeweb:page>
