<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 离店 -->
<qeweb:page onLoad="LeaveOutShopBO.barcodeScan">
	<qeweb:form id="LeaveOutShopBO" bind="LeaveOutShopBO">
		<qeweb:textField bind="shopMsgBarcode" groupName="barcode" text="门店条码"/>
		<qeweb:label bind="shopCode" text="门店编码"/>
		<qeweb:label bind="shopName" text="com.qeweb.demo.mobile.bo.DemoMobileShopSalesBO.shopName"/>
		<qeweb:label bind="visitorName" text="巡店员名称"/>
		<qeweb:label bind="comeInTime" text="com.qeweb.demo.mobile.bo.DemoMobileMaterialManagementBO.comeInTime"/>
		<qeweb:label bind="leaveOutTime" text="com.qeweb.demo.mobile.bo.DemoMobileClockingInBO.leaveTime"/>
		<qeweb:hidden bind="shopId"/>
		<qeweb:commandButton name="barcodeScan" operate="barcodeScan" groupName="barcode" text="条码扫描"/>
		<qeweb:commandButton name="submit" operate="submit" text="提交"/>
	</qeweb:form>
</qeweb:page>