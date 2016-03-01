<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 粗粒度组件关联示例5 -->
<qeweb:page onLoad="form.query" layout="2;C2(form)">
	<qeweb:group relations="form:table,table2;table:table2">
		<qeweb:form id="form" bind="demoConRelationBO3" layout="3;C2(purchaseTime)" text="粗粒度组件关联">
			<qeweb:textField bind="purchaseNo" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:expend>
				<qeweb:dateField bind="purchaseTime" text="采购时间"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoConRelationBO3" text="订单行关联订单明细">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
			<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
			<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
			<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
		</qeweb:table>
		
		<qeweb:table id="table2" bind="demoConRealation_TargetBO_3" text="订单明细">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="itemNO" text="行号"/>
			<qeweb:textField bind="orderQty" text="订购数量"/>
			<qeweb:select bind="deliveryStatus" text="发货状态"/>
			<qeweb:select bind="confirmStatus" text="确认状态"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

