<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="table.sysFresh" >
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
			<qeweb:hidden bind="id"/>
			<qeweb:label bind="name" text="变量" width="200" align="center"/>
			<qeweb:label bind="varValueSet" text="值集" width="200" align="center"/>
			<qeweb:label bind="varValue" text="值" width="200" align="center"/>
		</qeweb:table>
	</qeweb:group>	
	<qeweb:form id="itemForm" bind="pfv_itemBO" text="" layout="1" param="required">
		<qeweb:hidden bind="id"/>
		<qeweb:label bind="targetPage" text="targetPage"/>
	</qeweb:form>
</qeweb:page>
