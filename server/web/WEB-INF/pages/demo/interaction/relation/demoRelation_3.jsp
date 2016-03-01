<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 粗粒度组件关联示例1 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoConRelationBO" text="通过表单中的细粒度组件触发关联">
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="hidden" text="是否隐藏查询结果" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoConRelationBO"
			display="purchaseNo=table;vendor.orgCode=table;vendor.orgName=table;
				purchaseTime=table;publishStatus=table" text="">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

