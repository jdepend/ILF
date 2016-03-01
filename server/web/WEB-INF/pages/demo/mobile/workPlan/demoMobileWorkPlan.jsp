<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="DemoMobileWorkPlanBOForm.formQuery">
	<qeweb:group
		relations="DemoMobileWorkPlanBOForm:DemoMobileWorkPlanBOTable">
		<qeweb:form id="DemoMobileWorkPlanBOForm" bind="DemoMobileWorkPlanBO"
			queryRange="true" layout="2">
			<qeweb:select bind="store" />
			<qeweb:expend>
				<qeweb:dateField bind="planTime" />
			</qeweb:expend>
			<qeweb:expend>
				<qeweb:dateField bind="checkingTime" />
			</qeweb:expend>
			<qeweb:commandButton name="formQuery" operate="query"
				text="form.query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="DemoMobileWorkPlanBOTable"
			bind="DemoMobileWorkPlanBO">
			<qeweb:hidden bind="id" />
			<qeweb:commandButton name="insert" operate="insert" text="新增" />
			<qeweb:commandButton name="tableDelete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="tableUpdate" operate="update" />
				<qeweb:commandButton name="tableView" operate="view" />
			</qeweb:expend>
			<qeweb:select bind="store" />
			<qeweb:dateField bind="planTime" />
			<qeweb:dateField bind="checkingTime" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>