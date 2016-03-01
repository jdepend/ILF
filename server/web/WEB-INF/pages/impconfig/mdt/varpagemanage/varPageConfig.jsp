<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 配置变量与组件的关联关系 -->
<qeweb:page bind="varPageBO" onLoad="table.sysFresh">
	<qeweb:commandButton name="goBack" text="form.back"/>
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="varPageBO" text="关联信息" layout="3;C2(pageURL)" param="required">
			<qeweb:label bind="relateName" text="关联名称"/>
			<qeweb:label bind="vars" text="变量名称"/>
			<qeweb:label bind="containerType" text="粗粒度组件类型"/>
			<qeweb:label bind="containerId" text="粗粒度组件ID"/>
			<qeweb:label bind="bind" text="绑定标识"/>
			<qeweb:label bind="pageURL" text="页面路径"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		
		<qeweb:table id="table" bind="varPageItemBO" text="变量值-组件关联管理" hasBbar="false">
			<qeweb:commandButton name="insert" operate="changeVar,varPageBO.show,varConfBO.findVarConfBos" text="新增" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="varPageItemBO.toShow,varPageBO.findVarPageBO" text="update"/>
				<qeweb:commandButton name="view" operate="varPageItemBO.toShow" text="view"/>
				<qeweb:commandButton name="updateTab" operate="varPageItemBO.toShow,varPageBO.findVarPageBO" text="update"/>
				<qeweb:commandButton name="viewTab" operate="varPageItemBO.toShow" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			
			<qeweb:textField bind="relateName" text="变量值-组件关联名称" width="200"/>
			<qeweb:textField bind="vcBinds" text="组件绑定标识" width="500"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
