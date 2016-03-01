<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格示例:加载全部数据 -->
<qeweb:page onLoad="table_1.sysFresh">
	<qeweb:table id="table_1" bind="demoTableBO5" maxHeight="bodyHeight" hasBbar="false" text="加载全部数据"
		editCell="purchaseNo">
		<qeweb:commandButton name="btn0" text="btn1" operate="exe0"/>
		<qeweb:commandButton name="sysAddRow" operate="sysAddRow"/>
		<qeweb:commandButton name="sysDelRow" operate="sysDelRow"/>
		<qeweb:expend>
			<qeweb:commandButton name="btn1" text="btn1" operate="exe1"/>
			<qeweb:commandButton name="btn2" text="btn2" operate="exe2"/>
			<qeweb:commandButton name="view" operate="view"/>
			<qeweb:commandButton name="jsDelete" operate="jsDelete"></qeweb:commandButton>
		</qeweb:expend>
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
</qeweb:page>
