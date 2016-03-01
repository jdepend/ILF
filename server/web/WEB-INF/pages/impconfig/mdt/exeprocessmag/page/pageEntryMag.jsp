<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面入口管理 --%>
<qeweb:page onLoad="pageEntryForm.query">
	<qeweb:group relations="pageEntryForm:pageEntryTable">
		<qeweb:form id="pageEntryForm" bind="pageEntryBO" queryRange="true" text="变量执行过程-页面入口管理">
			<qeweb:textField bind="varNames" text="变量名称"/>
			<qeweb:textField bind="pageURL"  text="页面路径"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="pageEntryTable" bind="pageEntryBO" text="变量执行过程-页面入口管理">
			<qeweb:commandButton name="insert" text="insert" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" text="update"/>
				<qeweb:commandButton name="view" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/> 
			<qeweb:textField bind="varNames" text="变量名称"/>
			<qeweb:textField bind="pageURL"  text="页面路径"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>