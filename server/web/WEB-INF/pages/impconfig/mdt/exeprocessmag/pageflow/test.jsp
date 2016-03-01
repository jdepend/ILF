<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="pageEntryForm" bind="pageEntryBO" text="test" queryRange="true">
		<qeweb:textField bind="pageURL" text="页面路径" />
		<qeweb:commandButton name="btn1" text="btn1"/>
		<qeweb:commandButton name="btn2" text="btn2"/>
	</qeweb:form>
	
	<qeweb:form id="pageEntryForm2" bind="pageEntryBO" text="test" queryRange="true">
		<qeweb:textField bind="pageURL" text="页面路径" />
		<qeweb:commandButton name="btn3" operate="jump" text="btn3"/>
	</qeweb:form>
	<qeweb:tab id="myTab">
	</qeweb:tab>
</qeweb:page>