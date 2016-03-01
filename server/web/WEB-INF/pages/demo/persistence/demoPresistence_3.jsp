<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:表格中自带的增/删/改 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoPresistenceBO3" layout="3;C2(purchaseTime)" text="" >
			<qeweb:textField bind="purchaseNo" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="confirmStatus" text="确认状态" />
			<qeweb:expend>
				<qeweb:dateField bind="purchaseTime" text="采购时间" />
			</qeweb:expend>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoPresistenceBO3" text="表格中自带的增/删/改">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="delete2" operate="delete" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50"/>
				<qeweb:commandButton name="view2" operate="demoPresistenceBO3.view2" width="50" text="自定义查看页面"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:dateField bind="purchaseTime" align="center" text="采购时间" width="100"/>
			<qeweb:select bind="publishStatus" text="发布状态" />
			<qeweb:select bind="confirmStatus" text="确认状态" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
