<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="DemoMobileShowCaseBOTable.sysFresh">
	
	<qeweb:group relations="ShopBOForm:DemoMobileShowCaseBOTable">
		<qeweb:form id="ShopBOForm" bind="ShopBO" queryRange="true" text="门店信息" param="required">
			<qeweb:label bind="shopCode" text="门店编码" />
			<qeweb:label bind="shopName" text="门店名称"/>
			<qeweb:label bind="shopType" text="门店类型" />
			<qeweb:hidden bind="province" />
			<qeweb:hidden bind="city"/>
			<qeweb:hidden bind="area"/>
			<qeweb:hidden bind="id" />
		</qeweb:form>
		
		<qeweb:table id="DemoMobileShowCaseBOTable" bind="DemoMobileShowCaseBO" text="巡检信息">
			<qeweb:expend>
				<qeweb:commandButton name="showDesc" text="view" 
				operate="DemoMobileShowCaseBO.showDesc,
				DemoMobileSalesclerkWorkplaceBO.showDesc,
				DemoMobileShopSalesBO.showDesc,
				DemoMobileShopStoreBO.showDesc,
				DemoMobileMaterialManagementBO.showDesc,
				DemoMobilePromotionManagementBO.showDesc"/>
			</qeweb:expend>
			
			<qeweb:hidden bind="id" />
			<qeweb:hidden bind="comparateId" />
			<qeweb:textField bind="comeInTime" text="进店时间" />
			<qeweb:textField bind="visitor.userName" text="巡检员" />
			<qeweb:anchor bind="locationStr" text="地址" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>