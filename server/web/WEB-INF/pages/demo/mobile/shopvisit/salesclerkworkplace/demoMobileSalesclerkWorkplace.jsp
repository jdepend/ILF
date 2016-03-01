<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 驻店员在岗 -->
<qeweb:page>
	<qeweb:form id="DemoMobileWorkplaceForm" bind="DemoMobileSalesclerkWorkplaceBO">
		<qeweb:label bind="shopBO.shopName" text="门店名称"/>
		<qeweb:label bind="comeInTime" text="进店时间"/>
		<qeweb:textArea bind="serviceIdea" text="服务理念"/>
		<qeweb:textArea bind="corporateCulture" text="企业文化培训"/>
		<qeweb:textArea bind="promotionActivity" text="促销活动策划"/>

		<qeweb:hidden bind="picture" groupName="pic"/>
		<qeweb:hidden bind="locationStr" groupName="pic"/>
		<qeweb:commandButton name="camera" operate="camera" text="camera" groupName="pic"/>
		<qeweb:commandButton name="save" operate="save" text="form.save" />
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>