<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格示例:隐藏操作列 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:table id="table" bind="demoTableBO9" text="使用全局配置table_autoHide_optCol隐藏操作列,id < 15时隐藏按钮">
		<qeweb:expend>
			<qeweb:commandButton name="btn1" text="btn1"/>
			<qeweb:commandButton name="btn2" text="btn2"/>
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
