<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%--页面流配置 --%>
<qeweb:page onLoad="pageflowConfigForm.query">
	<qeweb:group relations="pageflowConfigForm:pageflowConfigTable">
		<qeweb:form id="pageflowConfigForm" bind="pageflowConfBO" queryRange="true" text="页面流配置查询"	>
			<qeweb:select bind="moduleId" text="模块名称"/>
			<qeweb:select bind="fileName" text="页面流文件名称"/>
			<qeweb:textField bind="boId" text="BOID"/>
			<qeweb:textField bind="bindBop" text="bindBop" />
			<qeweb:textField bind="btnName" text="btnName" />
			<qeweb:textField bind="sourcePage" text="sourcePage" />
			<qeweb:textField bind="targetPage" text="targetPage" />
			<qeweb:textField bind="targetVC" text="targetVC" />
			<qeweb:select bind="msgFlag" text="显示提示信息" />
			<qeweb:select bind="closePage" text="关闭弹出页面" />
			<qeweb:select bind="isPopup" text="弹出目标页面" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="pageflowConfigTable" bind="pageflowConfBO" text="页面流配置列表">
			<qeweb:commandButton name="insert" operate="pageflowConfBO.toInsert" text="insert" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="pageflowConfBO.toUpdate" width="45" text="update"/>
				<qeweb:commandButton name="view" operate="pageflowConfBO.toView" width="45" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="filePath"/>
			<qeweb:select bind="moduleId" width="220" text="模块名称"/>
			<qeweb:select bind="fileName" width="220" text="页面流文件名称"/>
			<qeweb:textField bind="boId" width="220" text="BOID"/>
			<qeweb:textField bind="bindBop" width="200" text="bindBop" />
			<qeweb:textField bind="btnName" width="200" text="btnName" />
			<qeweb:textField bind="sourcePage" width="400" text="sourcePage" />
			<qeweb:textField bind="targetPage" width="400" text="targetPage" />
			<qeweb:textField bind="targetVC" width="300" text="targetVC" />
			<qeweb:select bind="msgFlag" width="100" align="center" text="显示提示信息" />
			<qeweb:select bind="isPopup" width="100" align="center" text="弹出目标页面" />
			<qeweb:textField bind="dialogWidth" width="100" align="center" text="弹出页面宽度" />
			<qeweb:textField bind="dialogHeight" width="100" align="center" text="弹出页面高度" />
			<qeweb:textField bind="dialogTitle" width="100" text="弹出页面标题" />
			<qeweb:select bind="closePage" width="100" align="center" text="关闭弹出页面" />
			<qeweb:textArea bind="remark" width="200" text="备注" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
