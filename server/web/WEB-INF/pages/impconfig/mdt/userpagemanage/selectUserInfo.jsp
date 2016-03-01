<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 选择用户信息 -->

<qeweb:page onLoad="selectUserBOTable.sysFresh">
	<qeweb:table id="selectUserBOTable" bind="selectUserBO" hasBbar="fasle"
		text="选择用户信息">
		<qeweb:hidden bind="id" />
		<qeweb:select bind="subBOName" width="500" text="BO名称" />
		<qeweb:textField bind="fieldName" width="200" text="属性名" />
		<qeweb:hidden bind="fullFieldName" />
	</qeweb:table>
</qeweb:page>
