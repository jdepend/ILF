<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 页面流映射管理首页 -->
<qeweb:page onLoad="pageflowMappingForm.formQuery">
	<qeweb:group relations="pageflowMappingForm:pageflowMappingTable">
		<qeweb:form id="pageflowMappingForm" bind="pfv_mainBO" text="页面流映射管理" queryRange="true" >
			<qeweb:textField bind="nodeName" text="节点名称"/>
			<qeweb:select bind="moduleId" width="150" text="模块名称"/>
			<qeweb:select bind="isConfig" text="是否已配置"/>
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="pageflowMappingTable" bind="pfv_mainBO" text="页面流映射管理"
			display="id=table,update;
				isConfig=table,view;
				nodeName=table,insert,update,view;
				vars=table,insert,update;
				moduleId=table,insert,update,view;
				sourcePage=table,insert,update,view;
				boId=table,insert,update,view;
				btnName=table,insert,update,view;
				targetPage=insert,update,view;
				remark=insert,update,view;">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="referenceTarget" operate="toConfig,pfv_mainBO.viewPFV" width="50" text="配置"/>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50" />
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="nodeName" width="150" text="节点名称"/>
			<qeweb:textField bind="moduleId" width="150" text="模块名称"/>
			<qeweb:select bind="isConfig" align="center" text="是否已配置" width="100"/>
			<qeweb:textField bind="boId" width="200" text="组件ID"/>
			<qeweb:textField bind="btnName" width="150" text="按钮名称">
				<qeweb:source bindBo="pageflowConfBO" bindBop="moduleId:moduleId;boId:boId;btnName:btnName;sourcePage:sourcePage;targetPage:targetPage"/>
			</qeweb:textField>
			<qeweb:textField bind="sourcePage" text="sourcePage" width="260"/>
			<qeweb:textField bind="targetPage" text="targetPage"/>
			<qeweb:textField bind="vars" text="变量" width="200">
				<qeweb:source bindBo="varBO" bindBop="vars:name" sm="checkbox"/>
			</qeweb:textField>
			<qeweb:textArea bind="remark" text="备注"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
