<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 用户-页面组件关联管理 -->
<qeweb:page onLoad="userPageBOForm.query">
	<qeweb:group relations="userPageBOForm:userPageBOTable">
		<qeweb:form id="userPageBOForm" bind="userPageBO" queryRange="true" text="用户-组件关联管理">
			<qeweb:textField bind="relateName" text="关联名称"/>
			<qeweb:textField bind="userInfo" text="用户信息"/>
			<qeweb:textField bind="vcId" text="粗粒度组件ID"/>
			<qeweb:textField bind="bind" text="绑定标识"/>
			<qeweb:select bind="configStatus" text="配置状态"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="userPageBOTable" bind="userPageBO" 
			display="id=update;relateName=table,insert,update,view;
				userInfo=table,insert,update,view;
				vcType=table,insert,update,view;
				configStatus=table;
				vcId=table,insert,update,view;
				bind=table,insert,update,view;
				pageURL=table,insert,update,view;"
			text="用户-组件关联管理">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="toUserPageItemMag" width="50" text="配置"/>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50" />
			</qeweb:expend>
			
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="relateName" text="关联名称"/>
			<qeweb:textField bind="userInfo" width="200" text="用户信息">
				<qeweb:source bindBo="selectUserBO" bindBop="userInfo:fullFieldName" sm="checkbox"/>
			</qeweb:textField>
			<qeweb:textField bind="vcType" width="100" align="center" text="粗粒度组件类型"/>
			<qeweb:select bind="configStatus" width="80" align="center" text="配置状态"/>
			<qeweb:textField bind="vcId" width="150" align="center" text="粗粒度组件ID"/>
			<qeweb:textField bind="bind" width="150" align="center" text="绑定标识"/>
			<qeweb:textField bind="pageURL" width="400" text="页面路径">
				<qeweb:source bindBo="vp_analyzeComponentBO" bindBop="pageURL:pageURL;vcId:componentId;bind:bind;vcType:componentType" sm="radio"/>
			</qeweb:textField>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>