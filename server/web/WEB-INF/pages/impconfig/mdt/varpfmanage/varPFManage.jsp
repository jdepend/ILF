<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量-页面流关联管理 -->
<qeweb:page onLoad="varPFForm.query">
	<qeweb:group relations="varPFForm:varPFTable">
		<qeweb:form id="varPFForm" bind="varPageFlowBO" queryRange="true" text="变量-页面流关联管理">
			<qeweb:textField bind="relateName" text="关联名称"/>
			<qeweb:textField bind="sourcePage" text="源页面路径"/>
			<qeweb:textField bind="boId" text="boId"/>
			<qeweb:textField bind="btnName" text="btnName"/>
			<qeweb:select bind="configStatus" text="配置状态"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="varPFTable" bind="varPageFlowBO" 
			display="id=update;relateName=table,insert,update,view;
				vars=table,insert,update,view;
				configStatus=table,view;
				boId=table,insert,update,view;
				btnName=table,insert,update,view;
				sourcePage=table,insert,update,view"
			text="变量-页面流关联管理">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="conf" operate="toConf" text="配置" width="50"/>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="relateName" text="关联名称"/>
			<qeweb:textField bind="vars" text="变量名称" width="200" >
				<qeweb:source bindBo="varBO" bindBop="vars:name" sm="checkbox"/>
			</qeweb:textField>
			<qeweb:select bind="configStatus" align="center" text="配置状态" width="80"/>
			<qeweb:textField bind="boId" text="boId" width="150"/>
			<qeweb:textField bind="btnName" text="btnName" width="150"/>
			<qeweb:textField bind="sourcePage" text="源页面路径" width="400">
				<qeweb:source bindBo="analyzeBtnBO" bindBop="boId:boId;btnName:btnName;sourcePage:pageURL" /> 
			</qeweb:textField>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>