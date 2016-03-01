<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:全局按钮的模式 -->
<qeweb:page bind="demoBtnModeBO2" onLoad="table.sysFresh">
	<qeweb:commandButton name="saveBtn_0" operate="saveDef" text="default"/>			
	<qeweb:commandButton name="saveBtn_1" operate="saveSelect" text="selectMod"/>
	<qeweb:commandButton name="saveBtn_2" operate="saveModify" text="modify"/>
	<qeweb:commandButton name="saveBtn_3" operate="saveAll" text="all"/>
	<qeweb:commandButton name="saveBtn_4" operate="saveEmpty" text="empty"/>
	<qeweb:commandButton name="saveBtn_5" operate="saveAdaptive" text="adaptive"/>
	
	<qeweb:form id="form" bind="demoBtnModeBO2" text="form">
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
	</qeweb:form>
	
	<qeweb:table id="table" bind="demoBtnModeBO2" text="table"
		display="purchaseNo=table:edit;vendor.orgCode=table:edit;
			purchaseTime=table:edit;publishStatus=table:edit;confirmStatus=table;orders=table:edit">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
		<qeweb:textField bind="orders" text="订购数量" />
	</qeweb:table>
</qeweb:page>