<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格布局示例 -->
<qeweb:page>
	<qeweb:table id="table" bind="demoTableBO" sm="empty" height="200"
		layout="[t1](no, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus),
			[t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus)" 
		text="[t1](no, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus),[t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus)">
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
	
	<qeweb:table id="table2" bind="demoTableBO" sm="empty" height="200"
		layout="[top]([t1](no, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus),
			[t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus))" 
		text="[top]([t1](no, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus),[t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus))">
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
	
	<qeweb:table id="table3" bind="demoTableBO" sm="empty" height="400"
		layout="[标题1]([t1](no, btn, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus)),
			[标题2]([t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus))" 
		text="[标题1]([t1](no, btn, purchaseNo,vendor.orgCode,vendor.orgName),[t2](purchaseTime,publishStatus,confirmStatus)),[标题2]([t3](deliveryStatus,receiveStatus),[t4](closeStatus,lockStatus))">
		<qeweb:expend>
			<qeweb:commandButton name="btn1" text="btn1" />
			<qeweb:commandButton name="btn2" text="btn2" />
			<qeweb:commandButton name="btn3" text="btn3" />
		</qeweb:expend>
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
</qeweb:page>
