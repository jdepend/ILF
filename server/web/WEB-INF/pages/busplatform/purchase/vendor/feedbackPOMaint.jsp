<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 反馈 -->
<qeweb:page bind="bp_FeedBackBO">
	<qeweb:commandButton name="feedback" operate="feedback"/>

	<qeweb:form id="bp_po_main" bind="bp_PurchaseOrderBO" param="required">
		<qeweb:label bind="purchaseNo" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.purchaseNo"/>
		<qeweb:label bind="buyer.orgCode" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgCode"/>
		<qeweb:label bind="buyer.orgName" text="com.qeweb.busplatform.pomanage.bo.BP_PurchaseOrderBO.buyer.orgName"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>

	<qeweb:form id="bp_feedback_form" bind="bp_FeedBackBO" layout="C3(feedbackContent)">
		<qeweb:textArea bind="feedbackContent"/>
	</qeweb:form>

	<!-- 反馈详情 -->
	<qeweb:table id="bp_feedback_table" bind="bp_FeedBackBO" param="required" sm="empty">
		<qeweb:label bind="feedUserBO.userName"/>
		<qeweb:label bind="feedbackContent" text="com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.feedbackContent"/>
		<qeweb:label bind="createTime" text="com.qeweb.busplatform.pomanage.bo.BP_FeedBackBO.createTime"/>
	</qeweb:table>
</qeweb:page>
