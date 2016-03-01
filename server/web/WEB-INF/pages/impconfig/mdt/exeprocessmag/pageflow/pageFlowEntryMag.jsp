<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面流入口管理 --%>
<qeweb:page onLoad="pfEntryForm.query">
	<qeweb:group relations="pfEntryForm:pfEntryTable">
		<qeweb:form id="pfEntryForm" bind="pfEntryBO" queryRange="true" text="变量执行过程-页面流入口管理">
			<qeweb:textField bind="varNames" text="变量名称"/>
			<qeweb:textField bind="sourcePage" text="源页面"/>
			<qeweb:textField bind="boId" text="boId"/>
			<qeweb:textField bind="btnName" text="按钮名称"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="pfEntryTable" bind="pfEntryBO" text="变量执行过程-页面流入口管理">
			<qeweb:commandButton name="insert" text="insert" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" text="update"/>
				<qeweb:commandButton name="view" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="varNames" text="变量名称"/>
			<qeweb:textField bind="sourcePage" text="源页面"/>
			<qeweb:textField bind="boId" text="boId"/>
			<qeweb:textField bind="btnName" text="按钮名称"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>