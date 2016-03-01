<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 选择组件 -->
<qeweb:page>
	<qeweb:table id="table" bind="analyzeComponentBO" text="组件选择" param="required" hasBbar="false" height="250">
		<qeweb:hidden bind="id" />
		<qeweb:textField bind="vcType" text="组件类型" width="200" align="center" />
		<qeweb:textField bind="bind" text="绑定标识" width="200" align="center" />
	</qeweb:table>
</qeweb:page>
