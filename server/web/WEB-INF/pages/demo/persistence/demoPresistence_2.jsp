<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:动态新增行 -->
<qeweb:page onLoad="table1.sysFresh">
	<qeweb:table id="table1" bind="demoPresistenceBO2" text="动态新增行示例1"
		editCell="purchaseNo,vendor.orgCode,purchaseTime,publishStatus">
		<qeweb:commandButton name="saveBtn_1" operate="save_1" icon="save" text="form.save"/>
		<qeweb:commandButton name="sysAddRow" operate="sysAddRow" />
		<qeweb:commandButton name="sysDelRow" operate="sysDelRow" />
		<qeweb:expend>
			<qeweb:commandButton name="exe" operate="exe" text="获取本行数据"/>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
	</qeweb:table>
	
	<qeweb:table id="table2" bind="demoPresistenceBO2" text="动态新增行示例2"
		display="purchaseNo=table:edit;vendor.orgCode=table:edit;
			purchaseTime=table:edit;publishStatus=table:edit;confirmStatus=table">
		<qeweb:commandButton name="saveBtn_1" operate="save_1" icon="save" text="form.save"/>
		<qeweb:commandButton name="selectBtn" icon="folder" text="选择">
			<qeweb:source bindBo="demoPurchaseOrderBO" sm="checkbox"
				bindBop="purchaseNo:purchaseNo;vendor.orgCode:vendor.orgCode;vendor.orgName:vendor.orgName;purchaseTime:purchaseTime;publishStatus:publishStatus;confirmStatus:confirmStatus"/>
		</qeweb:commandButton>
		<qeweb:commandButton name="sysAddRow" operate="sysAddRow" />
		<qeweb:commandButton name="sysDelRow" operate="sysDelRow" />
		<qeweb:expend>
			<qeweb:commandButton name="exe" operate="exe" text="获取本行数据"/>
			<qeweb:commandButton name="jsDelete" operate="jsDelete"/>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
	</qeweb:table>
</qeweb:page>
