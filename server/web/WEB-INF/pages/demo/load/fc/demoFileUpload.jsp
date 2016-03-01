<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 文件上示例 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:form id="form" bind="demoUploadBO" layout="2" text="表单中的附件">
		<qeweb:fileField bind="singleFile" text="单文件上传" operate="upload"  download="true" showRange="true"/>
		<qeweb:fileField bind="multiFile" text="多文件上传" operate="upload"/>
		<qeweb:fileField bind="multiFile2" text="多文件上传2" operate="upload"/>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
	</qeweb:form>
	<qeweb:table id="table" bind="demoUploadBO" display="singleFile=table:edit;singleFile2=table:edit;bop1=table:edit;bop2=table" text="表格中的附件">
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:hidden bind="id"/>
		<qeweb:fileField bind="singleFile" text="单文件上传" operate="upload" showRange="true"/>
		<qeweb:fileField bind="singleFile2" text="单文件上传2" operate="upload" showRange="true"/>
		<qeweb:select bind="bop1" text="bop1"/>
		<qeweb:textField bind="bop2" text="bop2"/>
	</qeweb:table>
</qeweb:page>

