<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="projectInfoBO" >
	<qeweb:commandButton name="save" operate="saveAll" text="form.save"/>
	<qeweb:tab id="myTab">
		<qeweb:sheet id="s1" text="基本信息">
			<qeweb:form id="projectInfoBOForm1" bind="projectInfoBO" layout="1" text="" param="required">
				<qeweb:textField bind="projectName" text="项目名称"/>
				<qeweb:textField bind="srcPath" text="java文件根目录"/>
				<qeweb:textField bind="jspPath" text="jsp文件根目录"/>
				<qeweb:textField bind="hbmPath" text="hbm文件根目录"/>
				<qeweb:textField bind="springPath" text="spring文件根目录"/>
				<qeweb:textField bind="pageFlowPath" text="页面流文件根目录"/>
				<qeweb:textField bind="i18nPath" text="国际化文件根目录"/>
				<qeweb:textField bind="varPath" text="变量文件根目录"/>
			</qeweb:form>
		</qeweb:sheet>
		<qeweb:sheet id="s2" text="SVN信息">
			<qeweb:form id="projectInfoBOForm2" bind="projectInfoBO" layout="1" text="" param="required">
				<qeweb:textField bind="svnUrl" text="SVN地址"/>
				<qeweb:textField bind="svnUsername" text="SVN用户名"/>
				<qeweb:password bind="svnPassword" text="SVN密码"/>
			</qeweb:form>
		</qeweb:sheet>
	</qeweb:tab>
</qeweb:page>