<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 表格示例:翻页时记忆选中行 -->
<qeweb:page bind="demoTableBO3" onLoad="table_1.sysFresh">
	<qeweb:commandButton name="save2" icon="save" operate="save2" text="form.save"/>
	<qeweb:table id="table_1" bind="demoTableBO3" rememberChecked="true" text="翻页时记忆选中行">
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
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
