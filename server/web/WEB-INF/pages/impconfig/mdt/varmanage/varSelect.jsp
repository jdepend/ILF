<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量管理公用页面,供其它页面选择变量 -->
<qeweb:page onLoad="varForm.formQuery">
	<qeweb:group relations="varForm:varTable">
		<qeweb:form id="varForm" bind="varBO" text="变量查询" queryRange="true" layout="2">
			<qeweb:textField bind="name" />
			<qeweb:textField bind="alias" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="varTable" bind="varBO" text="变量列表">
			<qeweb:hidden bind="id" />
			<qeweb:select bind="moduleId" />
			<qeweb:textField bind="name" />
			<qeweb:textField bind="alias" />
			<qeweb:radio bind="scop"/>
			<qeweb:radio bind="canmodify"/>
			<qeweb:textArea bind="remark"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
