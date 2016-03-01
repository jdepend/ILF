<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="moduleForm.formQuery">
	<qeweb:group relations="moduleForm:moduleTable">
		<qeweb:form id="moduleForm" bind="projectModuleBO" text="模块管理" queryRange="true" >
			<qeweb:textField bind="moduleName" />
			<qeweb:select bind="developers" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="moduleTable" bind="projectModuleBO" text="模块管理"
			display="id=update;moduleName=table,insert,update,view;
				developers=table,insert,update,view;
				srcPackage=table,insert,update,view;
				jspPackage=table,insert,update,view;
				hbmPackage=table,insert,update,view;
				springPackage=table,insert,update,view;
				pageflowPackage=table,insert,update,view;
				i18nPackage=table,insert,update,view;
				varPackage=table,insert,update,view"
			win="add:height=400;update:height=400;view:height=400">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width="100"/>
				<qeweb:commandButton name="view" operate="view" />
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="moduleName" />
			<qeweb:checkBox bind="developers" />
			<qeweb:textField bind="srcPackage" width="200"/>
			<qeweb:textField bind="jspPackage" width="200"/>
			<qeweb:textField bind="hbmPackage" width="200"/>
			<qeweb:textField bind="springPackage" width="200"/>
			<qeweb:textField bind="pageflowPackage" width="200"/>
			<qeweb:textField bind="i18nPackage" width="200"/>
			<qeweb:textField bind="varPackage" width="200"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
