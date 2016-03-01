<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%--查看页面流配置 --%>
<qeweb:page>
	<qeweb:form id="pageflowConfigForm" bind="pageflowConfBO" layout="2;C2(sourcePage,targetPage,remark)" param="required" text="页面流配置">
		<qeweb:select bind="moduleId" text="模块名称"/>
		<qeweb:select bind="fileName" text="页面流文件名称"/>
		<qeweb:textField bind="boId" text="BOID"/>
		<qeweb:textField bind="bindBop" text="bindBop" />
		<qeweb:textField bind="btnName" text="btnName" />
		<qeweb:textField bind="sourcePage" text="sourcePage" />
		<qeweb:textField bind="targetPage" text="targetPage" />
		<qeweb:textField bind="targetVC" text="targetVC" />
		<qeweb:select bind="msgFlag" text="目标页面提示信息" />
		<qeweb:select bind="isPopup" text="是否弹出目标页面" />
		<qeweb:textField bind="dialogWidth" text="弹出页面宽度" />
		<qeweb:textField bind="dialogHeight" text="弹出页面高度" />
		<qeweb:textField bind="dialogTitle" text="弹出页面标题" />
		<qeweb:select bind="closePage" text="是否关闭弹出页面" />
		<qeweb:textArea bind="remark" text="备注" />
	</qeweb:form>
</qeweb:page>
