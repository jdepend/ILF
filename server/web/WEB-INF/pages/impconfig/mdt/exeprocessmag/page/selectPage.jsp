<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="varPageForm.query">
	<qeweb:group relations="varPageForm:varPageTable">
		<qeweb:form id="varPageForm" bind="varPageBO" text="变量-组件关联管理" layout="2" queryRange="true">
			<qeweb:textField bind="relateName" text="关联名称" />
			<qeweb:textField bind="vars" text="变量名称" />
			<qeweb:textField bind="containerId" text="粗粒度组件ID"/>
			<qeweb:textField bind="bind" text="绑定标识"/>
			<qeweb:select bind="configStatus" text="配置状态" width="80" align="center"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="varPageTable" bind="varPageBO" text="变量-组件关联管理">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="relateName" text="关联名称" />
			<qeweb:textField bind="vars" text="变量名称" />
			<qeweb:select bind="configStatus" text="配置状态" align="center"/>
			<qeweb:select bind="containerType" text="粗粒度组件类型" align="center"/>
			<qeweb:textField bind="containerId" text="粗粒度组件ID" width="150" align="center"/>
			<qeweb:textField bind="bind" text="绑定标识" width="150" align="center"/>
			<qeweb:textField bind="pageURL" text="页面路径" width="400" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
