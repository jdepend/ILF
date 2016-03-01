<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="pfv_mainBO" onLoad="table.sysFresh">
	<qeweb:commandButton name="goBack" text="form.back"/>
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="pfv_mainBO" text="" layout="2;C2(vars,sourcePage,targetPage)" param="required">
			<qeweb:label bind="nodeName" text="节点名称"/>
			<qeweb:label bind="moduleId" text="模块名称"/>
			<qeweb:label bind="vars" text="变量"/>
			<qeweb:label bind="sourcePage" text="sourcePage"/>
			<qeweb:label bind="boId" text="组件ID"/>
			<qeweb:label bind="btnName" text="按钮名称"/>
			<qeweb:label bind="targetPage" text="targetPage"/>
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		<qeweb:table id="table" bind="pfv_itemBO" text="" hasBbar="false">
			<qeweb:commandButton name="insert" operate="toAdd,pfv_mainBO.viewPFV" text="新增"/>
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="toHasData,vp_mainBO.viewVP,pfv_itemBO.getItem" width="50" text="修改"/>
				<qeweb:commandButton name="view" operate="toHasData,vp_mainBO.viewVP,pfv_itemBO.getItem" width="50" text="查看"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="vars" text="变量" width="250"/>
			<qeweb:textField bind="targetPage" text="targetPage" width="600"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
