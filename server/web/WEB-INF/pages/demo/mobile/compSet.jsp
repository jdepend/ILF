<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 移动巡检设置, 设置是否全部巡检过后才能离店 -->
<qeweb:page layout="2">
	<qeweb:form id="LeaveOutShopBO" bind="LeaveOutShopBO" layout="1" text="离店设置" >
		<qeweb:radio bind="leaveOutRule" text="离店设置" />
		<qeweb:commandButton name="save" operate="save" text="form.save" />
	</qeweb:form>
</qeweb:page>