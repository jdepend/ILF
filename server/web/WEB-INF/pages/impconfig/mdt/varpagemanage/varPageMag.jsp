<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量-组件关联管理首页 -->
<qeweb:page onLoad="varPageForm.query">
	<qeweb:group relations="varPageForm:varPageTable">
		<qeweb:form id="varPageForm" bind="varPageBO" text="变量-组件关联管理" queryRange="true">
			<qeweb:textField bind="relateName" text="关联名称" />
			<qeweb:textField bind="vars" text="变量名称" />
			<qeweb:textField bind="containerId" text="粗粒度组件ID"/>
			<qeweb:textField bind="bind" text="绑定标识"/>
			<qeweb:select bind="configStatus" text="配置状态" width="80" align="center"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="varPageTable" bind="varPageBO" text="变量-组件关联管理"
			display="id=table,update;
				relateName=table,insert,update,view;
				vars=table,insert,update,view;
				configStatus=table;
				containerType=table,insert,update,view;
				containerId=table,insert,update,view;
				bind=table,insert,update,view;
				pageURL=table,insert,update,view" 
			win="add:width=600;update:width=600;view:width=600">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="toConfig" width="50" text="配置"/>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50" />
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="relateName" text="关联名称" />
			<qeweb:textField bind="vars" text="变量名称" width="200">
				<qeweb:source bindBo="varBO" bindBop="vars:name" sm="checkbox"/>
			</qeweb:textField>
			<qeweb:select bind="configStatus" text="配置状态" width="80" align="center"/>
			<qeweb:select bind="containerType" text="粗粒度组件类型" width="100" align="center"/>
			<qeweb:textField bind="containerId" text="粗粒度组件ID" width="150" align="center"/>
			<qeweb:textField bind="bind" text="绑定标识" width="150" align="center"/>
			<qeweb:textField bind="pageURL" text="页面路径" width="400">
				<qeweb:source bindBo="analyzeComponentBO" bindBop="pageURL:pageURL;containerId:vcId;bind:bind;containerType:vcType" sm="radio"/>
			</qeweb:textField>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
