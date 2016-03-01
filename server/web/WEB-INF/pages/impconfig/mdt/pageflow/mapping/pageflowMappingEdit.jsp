<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="pfv_itemBO" onLoad="table.sysFresh" >
	<qeweb:commandButton name="saveItems" operate="saveConfig,pfv_mainBO.viewVP" text="保存映射"/>
	<qeweb:commandButton name="goBack" operate="pfv_mainBO.viewVP" text="form.back"/>
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="pfv_mainBO" text="" layout="2;C2(vars,sourcePage,targetPage)" param="required">
			<qeweb:label bind="nodeName" text="节点名称"/>
			<qeweb:label bind="moduleId" text="模块名称"/>
			<qeweb:label bind="vars" text="变量"/>
			<qeweb:label bind="sourcePage" text="sourcePage"/>
			<qeweb:label bind="boId" text="组件ID"/>
			<qeweb:label bind="btnName" text="按钮名称"/>
			<qeweb:label bind="targetPage" text="targetPage"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		<qeweb:table id="table" bind="pfv_varBO" text="变量" hasBbar="false" sm="empty">
			<qeweb:expend>
				<qeweb:commandButton name="config" operate="pfv_varBO.toConfig" width="50" text="配置"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="alias"/>
			<qeweb:textField bind="name" text="变量" width="200" align="center"/>
			<qeweb:select bind="varValueSet" text="值集" width="200" align="center"/>
			<qeweb:select bind="varValue" text="值" width="200" align="center"/>
		</qeweb:table>
	</qeweb:group>
	<qeweb:form id="itemForm" bind="pfv_itemBO" text="" layout="1" param="required">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="targetPage" text="targetPage"/>
	</qeweb:form>
</qeweb:page>
