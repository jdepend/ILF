<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 文件下载示例 -->
<qeweb:page onLoad="table.sysFresh">
	<qeweb:form id="form" bind="demoDownloadBO" layout="2" text="表单中的附件">
		<qeweb:fileField bind="downloadSFile_1" text="单文件下载1" />
		<qeweb:anchor bind="downloadSFile_2" text="单文件下载2" />
		<qeweb:anchor bind="downloadSFile_3" text="单文件下载3" />
		<qeweb:anchor bind="downloadSFile_4" text="单文件下载4" />
		<qeweb:fileField bind="downloadMFile" text="多文件下载" />
	</qeweb:form>
	<qeweb:table id="table" bind="demoDownloadBO" text="表格中的附件">
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:hidden bind="id"/>
		<qeweb:fileField bind="downloadSFile_1" text="单文件下载1" download="true" />
		<qeweb:anchor bind="downloadSFile_2" text="单文件下载2" />
		<qeweb:anchor bind="downloadSFile_3" text="单文件下载3" />
		<qeweb:anchor bind="downloadSFile_4" text="单文件下载4" />
		<qeweb:fileField bind="downloadMFile" text="多文件下载" />
	</qeweb:table>
</qeweb:page>

