<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面流入口管理-新增入口页面 --%>
<qeweb:page bind="pfEntryBO">
	<qeweb:commandButton name="insert" operate="insert" text="form.save" icon="save"/>
	
	<qeweb:form id="pfEntryForm" bind="pfEntryBO" layout="2" text="变量执行过程-页面流入口管理">
		<qeweb:textField bind="boId" text="boId"/>
		<qeweb:textField bind="btnName" text="按钮名称"/>
		<qeweb:textField bind="sourcePage" text="源页面">
			<qeweb:source bindBo="analyzeBtnBO" bindBop="boId:boId;btnName:btnName;sourcePage:pageURL"/>
		</qeweb:textField>
		<qeweb:blank bind="temp"/>
	</qeweb:form>
	
	<qeweb:table id="pfEntryItemBO" bind="pfEntryItemBO" hasBbar="false"
		display="varLoadStrategy=table:edit;varBO.name=table:edit;defValue=table:edit" text="变量执行过程-页面流入口管理">
		<qeweb:commandButton name="addRow" operate="sysAddRow"/>
		<qeweb:commandButton name="delRow" operate="sysDelRow"/>
		<qeweb:hidden bind="id"/>
		<qeweb:select bind="varLoadStrategy" text="变量加载策略"/>
		<qeweb:select bind="varBO.name" text="变量名称"/>
		<qeweb:textField bind="defValue" text="默认值"/>
	</qeweb:table>
</qeweb:page>