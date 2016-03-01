<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="OnlineUserBO.sysFresh" >
	<qeweb:form id="form1" bind="OnlineUserBO" text="设置在线用户超时时间">
		<qeweb:textField bind="outTime" text="超时时间（分钟）"/>
		<qeweb:commandButton name="setOutTime" operate="setOutTime" text="设置"/>
	</qeweb:form>
	<qeweb:table id="OnlineUserBOTable" bind="OnlineUserBO"  text="在线用户列表" param="required">
		<qeweb:textField bind="userIP" text="IP"/>
		<qeweb:textField bind="userCode" text="用户编码"/>
		<qeweb:textField bind="userName" text="用户名称"/>
	</qeweb:table>
</qeweb:page>
