<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 变量执行过程管理-页面入口管理-修改入口页面 --%>
<qeweb:page bind="pageEntryBO" onLoad="pageEntryItemBO.sysFresh">
	<qeweb:commandButton name="update" operate="update" text="form.save"/>
	
	<qeweb:group relations="pageEntryForm:pageEntryItemBO">
		<qeweb:form id="pageEntryForm" bind="pageEntryBO" layout="1" param="required" text="变量执行过程-页面入口管理">
			<qeweb:label bind="pageURL" text="页面路径" />
			<qeweb:hidden bind="id" />
		</qeweb:form>
		
		<qeweb:table id="pageEntryItemBO" bind="pageEntryItemBO" hasBbar="false"
			display="varLoadStrategy=table:edit;varBO.name=table:edit;defValue=table:edit" text="变量执行过程-页面入口管理">
			<qeweb:commandButton name="addRow" operate="sysAddRow"/>
			<qeweb:commandButton name="delRow" operate="sysDelRow"/>
			<qeweb:hidden bind="id"/>
			<qeweb:select bind="varLoadStrategy" text="变量加载策略"/>
			<qeweb:select bind="varBO.name" text="变量名称"/>
			<qeweb:textField bind="defValue" text="默认值"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>