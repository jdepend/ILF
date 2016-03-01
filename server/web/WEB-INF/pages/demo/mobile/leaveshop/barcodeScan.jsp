<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="leaveShopBO.barcodeScan;leaveShopBO.validateBarcode">
	<qeweb:form id="leaveShopBO" bind="LeaveShopBO">
		<qeweb:hidden bind="shopMsgBarcode" groupName="barcode" />
		<qeweb:commandButton name="barcodeScan" operate="barcodeScan" groupName="barcode" />
		<qeweb:commandButton name="validateBarcode" operate="validateBarcode" />
	</qeweb:form>
</qeweb:page>