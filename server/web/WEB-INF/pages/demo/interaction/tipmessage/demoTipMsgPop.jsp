<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="demoTipMsgPopBO.sysFresh">
	<qeweb:table id="demoTipMsgPopBO" bind="demoTipMsgPopBO" text="">
		<qeweb:commandButton name="btn" text="处理"/>
		<qeweb:textField bind="bop1" text="bop1"/>
		<qeweb:textField bind="bop2" text="bop2"/>
		<qeweb:textField bind="bop3" text="bop3"/>
	</qeweb:table>
</qeweb:page>