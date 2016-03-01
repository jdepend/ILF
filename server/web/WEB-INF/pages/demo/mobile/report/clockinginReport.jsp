<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 人员考勤报表 --%>
<qeweb:page bind="DemoMobileClockingInBO" onLoad="form.formQuery">
	<qeweb:group relations="form:table" >
		<qeweb:form id="form" bind="DemoMobileClockingInBO" layout="3;C2(arrivalTime)" queryRange="true">
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="userBO.userCode" text="巡检员编码"/>
			<qeweb:textField bind="userBO.userName" text="巡检员名称"/>
			<qeweb:expend>
				<qeweb:dateField bind="arrivalTime" text="进店时间"/>
			</qeweb:expend>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
			<qeweb:commandButton name="formQuery" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>

		<qeweb:table id="table" bind="DemoMobileClockingInBO" sm="empty">
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="userBO.userCode" text="巡检员编码"/>
			<qeweb:textField bind="userBO.userName" text="巡检员名称"/>
			<qeweb:textField bind="arrivalTime" width="130"/>
			<qeweb:textField bind="leaveTime" width="130"/>
			<qeweb:textField bind="sojournTime" width="130" align="center" text="巡店时间(分钟)"/>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>