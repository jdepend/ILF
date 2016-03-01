<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量如何影响tab组件    查看页面 -->
<qeweb:page>
	<qeweb:form id="varPageItemForm" bind="varPageItemBO" text="变量值-组件关联信息" param="required">
		<qeweb:hidden bind="id"/>
		<qeweb:label bind="relateName" text="变量值-组件关联名称" width="150" />
	</qeweb:form>
	
	<qeweb:table id="varConfTable" bind="varConfBO" text="变量设置" hasBbar="false" sm="empty" param="required">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="varName" text="变量" width="200" align="center" />
		<qeweb:select bind="valueSetCode" text="值集编码" width="200" align="center" />
		<qeweb:textField bind="valueStr" text="值" width="200" align="center" />
	</qeweb:table>
	
	<qeweb:table id="sheetBOTable" bind="sheetBO" text="sheet页信息" hasBbar="false" sm="empty" param="required">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="text" text="sheet页名称" width="200" />
		<qeweb:textField bind="path" text="sheet页路径" width="800" />
	</qeweb:table>
</qeweb:page>