<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出选择示例2 表格中的普通弹出 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:table id="table" bind="demoPopSelectBO2" text="表格中的弹出">
		<qeweb:commandButton name="selectBtn" icon="folder" text="选择">
			<qeweb:source bindBo="demoPurchaseOrderBO" sm="checkbox"
				bindBop="purchaseNo:purchaseNo;vendor.orgCode:vendor.orgCode;
				vendor.orgName:vendor.orgName;purchaseTime:purchaseTime;
				publishStatus:publishStatus;confirmStatus:confirmStatus"/>
		</qeweb:commandButton>
		<qeweb:commandButton name="sysDelRow" operate="sysDelRow" />
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="insert" operate="insert"/>
		<qeweb:expend>
			<qeweb:commandButton name="update" operate="update" width="40"/>
			<qeweb:commandButton name="view" operate="view" width="40"/>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="purchaseNo" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:textField bind="vendor.orgName" text="供应商名称">
			<qeweb:source bindBo="orgBO" bindBop="vendor.orgCode:orgCode;vendor.orgName:orgName" text="选择组织"/>
		</qeweb:textField>
		<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
		<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
		<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
	</qeweb:table>
</qeweb:page>

