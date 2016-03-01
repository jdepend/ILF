<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 树示例: 菜单 -->
<qeweb:page layout="2">
	<qeweb:menu bind="demoMenuBO" id="demoMenuBO" menuType="level" text="层级菜单"/>
</qeweb:page>