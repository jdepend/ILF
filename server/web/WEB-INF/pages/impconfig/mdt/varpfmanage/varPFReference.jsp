<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量-页面流关联   变量和目标页面的关联关系 -->
<qeweb:page bind="varPageFlowAllBO">
	<qeweb:commandButton name="insert" operate="insert" text="form.save" icon="save"/>
	
	<qeweb:form id="varPFForm" bind="varPageFlowBO" layout="2;C2(sourcePage)" param="required" text="关联信息">
		<qeweb:label bind="relateName" text="关联名称"/>
		<qeweb:label bind="vars" text="变量名称"/>
		<qeweb:label bind="boId" text="boId"/>
		<qeweb:label bind="btnName" text="btnName"/>
		<qeweb:label bind="sourcePage" text="源页面路径"/>
	</qeweb:form>
	
	<qeweb:form id="targetPageInfo" bind="varPageFlowItemBO" layout="3;C2(targetPage)" text="变量值-页面流关联信息">
		<qeweb:textField bind="relateName" text="关联名称"/>
		<qeweb:textField bind="targetPage" text="目标页面"/>
	</qeweb:form>
	
	<qeweb:table id="varPageFlowRefBO" bind="varPageFlowRefBO" param="required" hasBbar="false" sm="empty"
		display="varName=table;varValue=table:edit" text="">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="varName" text="变量名称" />
		<qeweb:textField bind="varValue" text="变量值" />
	</qeweb:table>
</qeweb:page>