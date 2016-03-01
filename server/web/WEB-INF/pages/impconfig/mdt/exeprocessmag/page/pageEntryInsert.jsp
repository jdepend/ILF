<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面入口管理-新增入口页面 --%>
<qeweb:page bind="pageEntryBO">
	<qeweb:commandButton name="insert" operate="insert" icon="save" text="form.save"/>
	<qeweb:commandButton name="back" text="form.back"/>
	
	<qeweb:form id="pageEntryForm" bind="pageEntryBO" layout="2" text="变量执行过程-页面入口管理">
		<qeweb:textField bind="pageURL" text="页面路径">
			<qeweb:source bindBo="varPageBO" bindBop="pageURL:pageURL" />
		</qeweb:textField>
	</qeweb:form>
	
	<qeweb:table id="pageEntryItemBO" bind="pageEntryItemBO" 
		editCell="varLoadStrategy,varBO.name,defValue"
		text="变量执行过程-页面入口管理">
		<qeweb:commandButton name="addRow" operate="sysAddRow"/>
		<qeweb:commandButton name="delRow" operate="sysDelRow"/>
		<qeweb:hidden bind="id"/>
		<qeweb:select bind="varLoadStrategy" text="变量加载策略"/>
		<qeweb:select bind="varBO.name" text="变量名称"/>
		<qeweb:textField bind="defValue" text="默认值"/>
	</qeweb:table>
</qeweb:page>