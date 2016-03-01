<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 按钮示例：表格中的按钮 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoButtonBO2" text="采购订单">
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="table" bind="demoButtonBO2" text="采购订单列表">
			<qeweb:commandButton name="btn1" text="btn11111111111111111" icon="add"/>
			<qeweb:commandButton name="btn2" text="btn2111111111111111" icon="downLoad"/>
			<qeweb:commandButton name="btn30" text="btn3111111111113" icon="expExl"/>
			<qeweb:commandButton name="btn40" text="btn4111111111113" icon="expExl"/>
			<qeweb:commandButton name="btn50" text="btn5111111111113" icon="expExl"/>
			<qeweb:commandButton name="btn60" text="btn6111111111113" icon="expExl"/>
			<qeweb:commandButton name="btn70" text="btn7111111111113" icon="expExl"/>
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="view" icon="view"/>
				<qeweb:commandButton name="btn4" text="btn4" icon="hmenu-unlock"/>
				<qeweb:commandButton name="btn5" text="btn5" />
				<qeweb:commandButton name="using" text="启用" />
				<qeweb:commandButton name="unusing" text="禁用" />
				<qeweb:commandButton name="btnDisable" text="btn6" />
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>