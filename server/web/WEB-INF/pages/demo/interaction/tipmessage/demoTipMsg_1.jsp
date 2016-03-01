<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 消息提醒示例 -->
<qeweb:page bind="demoTipMessageBO1">
	<qeweb:form bind="demoTipMessageBO1" id="demoTipMessageBO1" layout="2;C2(textArea)" text="" icon="file">
		<qeweb:textField bind="bop1" text="bop1"/>
		<qeweb:password bind="bop2" text="bop2"/>
		<qeweb:textArea bind="bop3" text="bop3"/>
	</qeweb:form>
</qeweb:page>