<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 信息反馈 --%>
<qeweb:page>
	<qeweb:form id="form" bind="DemoMobileInfoFeedbackBO" layout="3;C3(competitiveInfo,marketInfo,priceFeedback,picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:textArea bind="competitiveInfo" />
		<qeweb:textArea bind="marketInfo" />
		<qeweb:textArea bind="priceFeedback" />
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>