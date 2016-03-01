<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 驻店员在岗 -->
<qeweb:page>
	<qeweb:form id="DemoMobileWorkplaceForm" bind="DemoMobileSalesclerkWorkplaceBO"
		layout="3;C2(comeInTime),C3(serviceIdea,corporateCulture,promotionActivity,picFile)" param="required">
		<qeweb:label bind="shopBO.shopName" text="门店名称" />
		<qeweb:label bind="comeInTime" text="进店时间" />
		<qeweb:textArea bind="serviceIdea" text="服务理念"/>
		<qeweb:textArea bind="corporateCulture" text="企业文化培训"/>
		<qeweb:textArea bind="promotionActivity" text="促销活动策划"/>
		<qeweb:img bind="picFile" text="照片"/>
	</qeweb:form>
</qeweb:page>