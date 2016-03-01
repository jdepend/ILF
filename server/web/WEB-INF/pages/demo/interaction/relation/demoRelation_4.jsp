<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 粗粒度组件关联示例2 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoConRelationBO2" layout="3;C2(purchaseTime)" text="通过按钮触发关联">
			<qeweb:textField bind="purchaseNo" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:expend>
				<qeweb:dateField bind="purchaseTime" text="采购时间"/>
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoConRelationBO2" text="查询结果">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" align="center"/>
			<qeweb:select bind="receiveStatus" text="收货状态" align="center"/>
			<qeweb:select bind="closeStatus" text="关闭状态" align="center"/>
			<qeweb:select bind="lockStatus" text="锁定状态" align="center"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

