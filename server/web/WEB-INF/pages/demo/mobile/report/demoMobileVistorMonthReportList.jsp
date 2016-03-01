<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="bp_admitForm.query">
	<qeweb:group relations="bp_admitForm:bp_appDefineTable">
		<qeweb:form id="bp_admitForm" bind="DemoMobileVistorMonthReportBO" queryRange="true" text="时间周期巡店率">
			<qeweb:textField bind="supervisorCode" text="督导编号"/>
			<qeweb:textField bind="supervisorName" text="督导名称"/>
			<qeweb:textField bind="period" text="时间周期"/>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="bp_appDefineTable" bind="DemoMobileVistorMonthReportBO" sm="empty" text="列表">
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="period" text="时间周期" />
			<qeweb:textField bind="supervisorCode" text="督导编号" />
			<qeweb:textField bind="supervisorName" text="督导名称" />
			<qeweb:textField bind="realVistorCount" text="实际巡店次数" />
			<qeweb:textField bind="planVistorCount" text="计划巡店次数"/>
			<qeweb:textField bind="percnet" text="巡店率"/>
			<qeweb:textField bind="remark" text="备注"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>