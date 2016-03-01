<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 按钮示例：全局按钮 -->
<qeweb:page bind="demoTableBO">
	<qeweb:commandButton name="save" icon="save" text="form.save"/>
	<qeweb:commandButton name="back" text="form.back"/>
	
	<qeweb:form id="form" bind="demoTableBO" text="采购订单">
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
	</qeweb:form>
	<qeweb:table id="table" bind="demoTableBO" text="采购订单列表">
		<qeweb:commandButton name="btn1" text="btn1" icon="add"/>
		<qeweb:commandButton name="btn2" text="btn2" icon="downLoad"/>
		<qeweb:commandButton name="btn3" text="btn3" icon="expExl"/>
		<qeweb:expend>
			<qeweb:commandButton name="view" operate="view" icon="view"/>
			<qeweb:commandButton name="btn5" text="btn4" icon="hmenu-unlock"/>
			<qeweb:commandButton name="btn6" text="btn5" />
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
	</qeweb:table>
	
	<qeweb:commandButton name="syn" text="syn" icon="syn"/> 
</qeweb:page>