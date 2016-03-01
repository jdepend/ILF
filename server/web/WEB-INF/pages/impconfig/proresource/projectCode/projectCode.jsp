<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="codeForm.formQuery">
	<qeweb:group relations="codeForm:codeTable">
		<qeweb:form id="codeForm" bind="projectCodeBO" text="代码信息" queryRange="true" >
			<qeweb:textField bind="moduleName" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.moduleName" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="codeTable" bind="projectCodeBO" text="代码信息" sm="empty">
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="moduleName" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.moduleName"/>
			<qeweb:anchor bind="srcPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.srcPackage"/>
			<qeweb:anchor bind="jspPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.jspPackage"/>
			<qeweb:anchor bind="hbmPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.hbmPackage"/>
			<qeweb:anchor bind="springPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.springPackage"/>
			<qeweb:anchor bind="pageflowPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.pageflowPackage"/>
			<qeweb:anchor bind="i18nPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.i18nPackage"/>
			<qeweb:anchor bind="varPackage" width="200" align="left" text="com.qeweb.framework.impconfig.promodule.bo.ProjectModuleBO.varPackage"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
