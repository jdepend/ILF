<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 配置变量如何影响tab组件    新增页面 -->
<qeweb:page bind="varPageAllBO">		
	<qeweb:commandButton name="save" operate="saveTab,varPageBO.show" text="form.save"/>
	<qeweb:commandButton name="goBack" operate="varPageBO.show" text="form.back"/>
	
	<qeweb:form id="varPageForm" bind="varPageBO" text="关联信息" layout="3;C2(pageURL)" param="required">
		<qeweb:label bind="relateName" text="关联名称"/>
		<qeweb:label bind="vars" text="变量名称"/>
		<qeweb:label bind="containerType" text="粗粒度组件类型"/>
		<qeweb:label bind="containerId" text="粗粒度组件ID"/>
		<qeweb:label bind="bind" text="绑定标识"/>
		<qeweb:label bind="pageURL" text="页面路径"/>
		<qeweb:hidden bind="id"/>
	</qeweb:form>

	<qeweb:form id="varPageItemForm" bind="varPageItemBO" text="变量值-组件关联信息">
		<qeweb:textField bind="relateName" text="变量值-组件关联名称" width="150" />
	</qeweb:form>

	<qeweb:table id="varConfTable" bind="varConfBO" text="变量设置" hasBbar="false" sm="empty" param="required"
		display="varName=table;valueSetCode=table:edit;valueStr=table:edit">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="varName" text="变量" width="200" align="center" />
		<qeweb:select bind="valueSetCode" text="值集编码" width="200" align="center" />
		<qeweb:textField bind="valueStr" text="值" width="200" align="center" />
	</qeweb:table>

	<qeweb:table id="sheetBOTable" bind="sheetBO" text="sheet页信息" hasBbar="false"
		display="text=table:edit;path=table:edit">
		<qeweb:commandButton name="addRow" operate="sysAddRow" />
		<qeweb:commandButton name="delRow" operate="sysDelRow" />
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="text" text="sheet页名称" width="200" />
		<qeweb:textField bind="path" text="sheet页路径" width="800" />
	</qeweb:table>
</qeweb:page>