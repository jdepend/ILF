<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="pageflowFileForm.formQuery">
	<qeweb:group relations="pageflowFileForm:pageflowFileTable">
		<qeweb:form id="pageflowFileForm" bind="pageflowFileBO" text="页面流文件查询" queryRange="true" >
			<qeweb:select bind="moduleId" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="pageflowFileTable" bind="pageflowFileBO" text="页面流文件列表" hasBbar="false"
			display="id=table;moduleId=table,insert;fileName=table,insert;filePath=table">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:hidden bind="id"/>
			<qeweb:select bind="moduleId" width="220"/>
			<qeweb:textField bind="fileName" width="300"/>
			<qeweb:textField bind="filePath" width="500"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
