<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 粗粒度组件选择 -->
<qeweb:page>
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="analyzeComponentBO" text="粗粒度组件选择" layout="1"> 
			<qeweb:textField bind="pageURL" text="页面路径"/>
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="table" bind="analyzeComponentBO" text="粗粒度组件选择" hasBbar="flase">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="vcType" text="粗粒度组件类型"/>
			<qeweb:textField bind="vcId" text="粗粒度组件ID"/>
			<qeweb:textField bind="bind" text="绑定标识"/>
			<qeweb:hidden bind="pageURL"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
