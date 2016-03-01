<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量-页面流关联   配置目标页面 -->
<qeweb:page bind="varPageFlowBO" onLoad="targetPageInfo.sysFresh">
	<qeweb:commandButton name="goBack" text="form.back" />
	
	<qeweb:group relations="varPFForm:targetPageInfo">
		<qeweb:form id="varPFForm" bind="varPageFlowBO" layout="3;C2(sourcePage)" param="required" text="关联信息">
			<qeweb:label bind="relateName" text="关联名称"/>
			<qeweb:label bind="vars" text="变量名称"/>
			<qeweb:label bind="boId" text="boId"/>
			<qeweb:label bind="btnName" text="btnName"/>
			<qeweb:label bind="sourcePage" text="源页面路径"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		
		<qeweb:table id="targetPageInfo" bind="varPageFlowItemBO" text="变量值-页面流关联信息">
			<qeweb:commandButton name="insert" text="insert" icon="add" operate="varPageFlowBO.getCtxInfo,varPageFlowRefBO.getRefVars"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="relateName" text="关联名称"/>
			<qeweb:textField bind="targetPage" text="目标页面"/>
			<qeweb:textField bind="group" text="变量信息"/>
		</qeweb:table>
	</qeweb:group>	
</qeweb:page>