<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格示例:展示普通表格 -->
<qeweb:page layout="2;C2(table_1,table_2)" onLoad="table_1.sysFresh;table_2.sysFresh;table_3.sysFresh;table_4.sysFresh">
	<qeweb:table id="table_1" bind="demoTableBO" pageSize="3" header="false" text="普通表格">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
		<qeweb:select bind="confirmStatus" text="确认状态" align="center"/>
		<qeweb:select bind="deliveryStatus" text="发货状态" align="center"/>
		<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
		<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
		<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
	</qeweb:table>
	
	<qeweb:table id="table_2" bind="demoTableBO" pageSize="2" hasBbar="false" text="无翻页,仅显示2条数据">
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
		<qeweb:select bind="lockStatus" text="锁定状态"/>
	</qeweb:table>
	
	<qeweb:table id="table_3" bind="demoTableBO" hideHeaders="true" sm="empty" text="无表头信息,无复选框">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
	</qeweb:table>
	
	<qeweb:table id="table_4" bind="demoTableBO" sm="radio" text="单选表格">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
	</qeweb:table>
</qeweb:page>
