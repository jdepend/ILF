<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格示例:高亮显示 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:form id="form" bind="demoTableBO6" text="表单中的高亮显示">
		<qeweb:label bind="height_1" text="height_1"/>
		<qeweb:textField bind="vendor.orgCode" text="vendor.orgCode"/>
	</qeweb:form>
	
	<qeweb:table id="table" bind="demoTableBO6" text="表格中的高亮显示">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
		<qeweb:select bind="deliveryStatus" text="发货状态"/>
		<qeweb:select bind="receiveStatus" text="收货状态"/>
		<qeweb:select bind="closeStatus" text="关闭状态"/>
		<qeweb:checkBox bind="lockStatus" text="锁定状态"/>
	</qeweb:table>
</qeweb:page>
