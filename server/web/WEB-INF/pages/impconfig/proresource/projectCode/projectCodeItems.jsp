<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="itemform.query">
	<qeweb:group relations="itemform:itemtalbe">
		<qeweb:form id="itemform" bind="projectModuleItemBO" param="required" layout="2" queryRange="true" text="项目模块信息">
			<qeweb:label bind="projectCode.moduleName" text="模块名称" />
			<qeweb:textField bind="fileName" text="文件名称" />
			<qeweb:hidden bind="rootPath"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="itemtalbe" bind="projectModuleItemBO" text="文件列表" sm="empty">
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="fileName" width="150"/>
			<qeweb:textField bind="filePath" width="700" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
