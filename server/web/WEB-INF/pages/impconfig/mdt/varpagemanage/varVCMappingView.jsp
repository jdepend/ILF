<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量如何影响组件    查看页面 -->
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
		
	<qeweb:table id="varVCTable" bind="varVCBO" text="组件信息" hasBbar="false" sm="empty" param="required">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="vcType" text="组件类型" width="100" />
		<qeweb:textField bind="bind" text="绑定标识" width="150" />
		<qeweb:select bind="readonly" text="只读" width="100" align="center" />
		<qeweb:select bind="disable" text="禁用" width="100" align="center" />
		<qeweb:select bind="hidden" text="隐藏" width="100" align="center" />
		<qeweb:textField bind="valueStr" text="值" width="100" />
	</qeweb:table>
</qeweb:page>