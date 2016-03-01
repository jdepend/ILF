<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面流入口管理-查看入口页面 --%>
<qeweb:page onLoad="pfEntryItemBO.sysFresh">
	<qeweb:group relations="pfEntryForm:pfEntryItemBO">
		<qeweb:form id="pfEntryForm" bind="pfEntryBO" queryRange="true" layout="2;C2(sourcePage)" param="required" text="变量执行过程-页面流入口管理">
			<qeweb:label bind="sourcePage" text="源页面"/>
			<qeweb:label bind="boId" text="boId"/>
			<qeweb:label bind="targetPage" text="目标页面"/>
			<qeweb:label bind="btnName" text="按钮名称"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		
		<qeweb:table id="pfEntryItemBO" bind="pfEntryItemBO" sm="empty" hasBbar="false" text="变量执行过程-页面流入口管理">
			<qeweb:hidden bind="id"/>
			<qeweb:select bind="varLoadStrategy" text="变量加载策略"/>
			<qeweb:select bind="varBO.name" text="变量名称"/>
			<qeweb:textField bind="defValue" text="默认值"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>