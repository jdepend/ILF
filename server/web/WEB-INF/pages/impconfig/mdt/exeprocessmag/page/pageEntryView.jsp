<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面入口管理-查看入口页面 --%>
<qeweb:page onLoad="pageEntryItemBO.sysFresh">
	<qeweb:group relations="pageEntryForm:pageEntryItemBO">
		<qeweb:form id="pageEntryForm" bind="pageEntryBO" layout="1" param="required" text="变量执行过程-页面入口管理">
			<qeweb:label bind="pageURL" text="页面路径" />
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		
		<qeweb:table id="pageEntryItemBO" bind="pageEntryItemBO" hasBbar="false" sm="empty" text="变量执行过程-页面入口管理">
			<qeweb:hidden bind="id"/>
			<qeweb:select bind="varLoadStrategy" text="变量加载策略"/>
			<qeweb:select bind="varBO.name" text="变量名称"/>
			<qeweb:textField bind="defValue" text="默认值"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>