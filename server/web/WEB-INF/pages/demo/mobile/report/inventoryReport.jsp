<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 库存统计报表 --%>
<qeweb:page bind="DemoMobileInventoryBO" onLoad="form.formQuery">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="DemoMobileInventoryBO" layout="3;C3(arrivalTime)"
			queryRange="true" text="库存统计">
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="productType" text="产品型号"/>
			<qeweb:textField bind="productCategory" text="产品类别"/>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
			<qeweb:commandButton name="formQuery" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>

		<qeweb:table id="table" bind="DemoMobileInventoryBO" sm="empty" text="库存统计">
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="productType" text="产品型号"/>
			<qeweb:textField bind="productCategory" text="产品类别"/>
			<qeweb:textField bind="invQty" text="库存数量"/>
			<qeweb:textField bind="safeStock" text="安全库存"/>
			<qeweb:textField bind="replenishStatus" text="补货状态"/>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>