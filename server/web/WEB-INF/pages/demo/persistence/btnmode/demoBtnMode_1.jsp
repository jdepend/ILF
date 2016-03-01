<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:表格级按钮的模式 -->
<qeweb:page onLoad="table1.sysFresh">
	<qeweb:form id="form" bind="demoBtnModeBO1" text="form">
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
	</qeweb:form>
	
	<qeweb:table id="table1" bind="demoBtnModeBO1" text="表格级按钮的模式" layout="8"
		display="purchaseNo=table:edit;vendor.orgCode=table:edit;
			purchaseTime=table:edit;publishStatus=table:edit;confirmStatus=table;orders=table:edit">
		<qeweb:commandButton name="saveBtn_0" operate="saveDef" text="default"/>			
		<qeweb:commandButton name="saveBtn_1" operate="saveSelect" text="selectMod"/>
		<qeweb:commandButton name="saveBtn_2" operate="saveModify" text="modify"/>
		<qeweb:commandButton name="saveBtn_3" operate="saveAll" text="all"/>
		<qeweb:commandButton name="saveBtn_4" operate="saveEmpty" text="empty"/>
		<qeweb:commandButton name="saveBtn_5" operate="saveAdaptive" text="adaptive"/>
		<qeweb:commandButton name="saveBtn_6" operate="saveAll,saveSelect" text="混合模式"/>
		<qeweb:commandButton name="saveBtn_7" operate="saveAllRecs,saveSelect,demoBtnMod1_Pop_1.acceptSelectOrders" text="批量设置" icon="syn"/>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
		<qeweb:textField bind="orders" text="订购数量" />
	</qeweb:table>
</qeweb:page>