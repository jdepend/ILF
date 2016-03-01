<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/pages/common/mapScript.jsp"%>

<qeweb:page>
	<qeweb:tab id="DemoMobileCalligateBO">
		<qeweb:sheet id="sheet1" text="门店美陈管理">
			<qeweb:form id="DemoMobileShowCaseBOForm" bind="DemoMobileShowCaseBO" layout="3;C2(comeInTime),C3(advertisment,picFile)" param="required">
				<qeweb:label bind="shopBO.shopName" text="门店名称" />
				<qeweb:label bind="comeInTime" text="进店时间" />
				<qeweb:label bind="numy" />
				<qeweb:label bind="numu" />
				<qeweb:label bind="nums" />
				<qeweb:label bind="comeInTime" text="进店时间"/>
				<qeweb:label bind="advertisment" text="灯箱广告情况"/>
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
		
		<qeweb:sheet id="sheet2" text="驻店员在岗">
			<qeweb:form id="DemoMobileWorkplaceForm" bind="DemoMobileSalesclerkWorkplaceBO" layout="1" param="required">
				<qeweb:textArea bind="serviceIdea" text="服务理念"/>
				<qeweb:textArea bind="corporateCulture" text="企业文化培训"/>
				<qeweb:textArea bind="promotionActivity" text="促销活动策划"/>
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
		
		<qeweb:sheet id="sheet3" text="店面销售管理">
			<qeweb:form id="DemoMobileShopSalesForm" bind="DemoMobileShopSalesBO" layout="3;C3(picFile)" param="required">
				<qeweb:label bind="y480Sale" />
				<qeweb:label bind="y470Sale" />
				<qeweb:label bind="g470Sale" />
				<qeweb:label bind="y500Sale" />
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
		
		<qeweb:sheet id="sheet4" text="店面库存管理">
			<qeweb:form id="DemoMobileShopStoreBO" bind="DemoMobileShopStoreBO" layout="3;C3(picFile)" param="required">
				<qeweb:label bind="y480Store" />
				<qeweb:label bind="y470Store" />
				<qeweb:label bind="g470Store" />
				<qeweb:label bind="y500Store" />
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
		
		<qeweb:sheet id="sheet5" text="物料需求管理">
			<qeweb:form id="DemoMobileMaterialManagementForm" bind="DemoMobileMaterialManagementBO" layout="3;C3(picFile)" param="required">
				<qeweb:label bind="shopBO.shopName" text="门店名称"/>
				<qeweb:label bind="cabinetCount" />
				<qeweb:label bind="g480Count" />
				<qeweb:label bind="y470Count" />
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
		
		
		<qeweb:sheet id="sheet6" text="促销执行管理">
			<qeweb:form id="DemoMobilePromotionManagementBO" bind="DemoMobilePromotionManagementBO" layout="3;C3(roadShowActivity,closeTableActivity,promotionActivity,picFile)" param="required">
				<qeweb:label bind="shopBO.shopName" text="门店名称"/>
				<qeweb:textArea bind="roadShowActivity" />
				<qeweb:textArea bind="closeTableActivity" />
				<qeweb:textArea bind="promotionActivity" />
				<qeweb:img bind="picFile" text="照片"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>