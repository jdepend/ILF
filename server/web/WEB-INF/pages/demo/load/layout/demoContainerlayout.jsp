<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 粗粒度组件布局示例 -->
<qeweb:page layout="3;C2(table_1),C3(demoFormBO_3)" onLoad="table_1.sysFresh" bind="demoFormBO">
	<qeweb:commandButton name="btn1" text="btn1"/>
	<qeweb:commandButton name="btn2" text="btn2"/>
	<qeweb:form bind="demoFormBO" id="demoFormBO_1" layout="1" text="" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
	<qeweb:form bind="demoFormBO" id="demoFormBO_2" layout="1" text="" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
	<qeweb:form bind="demoFormBO" id="demoFormBO_20" layout="1" text="" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
	<qeweb:form bind="demoFormBO" id="demoFormBO_4" layout="1" text="" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
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
	<qeweb:form bind="demoFormBO" id="demoFormBO_3" layout="3;C2(textArea)" text="" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
	<qeweb:commandButton name="btn3" text="btn3"/>
	<qeweb:commandButton name="btn4" text="btn4"/>
</qeweb:page>