<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 进店扫描 -->
<qeweb:page onLoad="ComeInShopBO.barcodeScan">
	<qeweb:form id="ComeInShopBO" bind="ComeInShopBO">
		<qeweb:textField bind="shopMsgBarcode" groupName="barcode" text="门店条码"/>
		<qeweb:commandButton name="barcodeScan" operate="barcodeScan" groupName="barcode" text="条码扫描"/>
		<qeweb:commandButton name="validateBarcode" operate="validateBarcode" text="开始巡店"/>
	</qeweb:form>
</qeweb:page>