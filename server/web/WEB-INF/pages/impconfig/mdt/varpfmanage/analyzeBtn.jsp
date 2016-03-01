<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 根据jsp页面路径解析jsp中的按钮 -->
<qeweb:page>
	<qeweb:group relations="analyzeBtnForm:analyzeBtnTable">
		<qeweb:form id="analyzeBtnForm" bind="analyzeBtnBO" layout="1" text="选择控制组件">
			<qeweb:textField bind="pageURL" text="页面路径"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="analyzeBtnTable" bind="analyzeBtnBO" text="选择控制组件" hasBbar="false">
			<qeweb:hidden bind="id"/>
			<qeweb:hidden bind="pageURL"/>
			<qeweb:textField bind="vcId" text="容器ID"/>
			<qeweb:textField bind="vcType" align="center" text="容器类型"/>
			<qeweb:textField bind="boId" text="boId"/>
			<qeweb:textField bind="btnName" text="btnName"/>
			<qeweb:textField bind="operate" text="operate"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>