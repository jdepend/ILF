<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 信息反馈 --%>
<qeweb:page>
	<qeweb:form id="DemoMobileInfoFeedbackForm" bind="DemoMobileInfoFeedbackBO">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" />
		<qeweb:textArea bind="competitiveInfo" />
		<qeweb:textArea bind="marketInfo" />
		<qeweb:textArea bind="priceFeedback" />
		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>