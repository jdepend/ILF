<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoPopSelect_PO3" layout="2" text="弹出选择采购订单(翻页记忆)">
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:select bind="publishStatus" text="发布状态"/>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="table" bind="demoPopSelect_PO3" rememberChecked="true" header="false">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="confirmStatus" text="确认状态" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>