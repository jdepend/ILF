<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 用户信息值-页面组件关联管理 -->
<qeweb:page bind="userPageItemBO" onLoad="userPageItemBO.sysFresh">
	<qeweb:commandButton name="goBack" text="form.back"/>
	
	<qeweb:group relations="userPageBO:userPageItemBO">
		<qeweb:form id="userPageBO" bind="userPageBO" param="required" layout="3;C3(pageURL)" text="用户-组件关联">
			<qeweb:hidden bind="id"/>
			<qeweb:label bind="relateName" text="关联名称"/>
			<qeweb:label bind="userInfo" text="用户信息"/>
			<qeweb:label bind="vcId" text="粗粒度组件ID"/>
			<qeweb:label bind="bind" text="绑定标识"/>
			<qeweb:label bind="pageURL" text="页面路径"/>
		</qeweb:form>
		
		<qeweb:table id="userPageItemBO" bind="userPageItemBO" hasBbar="false" text="用户信息值-组件关联管理">
			<qeweb:commandButton name="insert" text="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" text="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="userPageId"/>
			<qeweb:textField bind="relateName" width="200" text="用户信息值-组件关联名称"/>
			<%--
			<qeweb:textField bind="relateName" width="500" text="组件绑定标识"/>
			--%>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>