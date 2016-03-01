<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 细粒度组件布局示例 -->
<qeweb:page>
	<qeweb:form bind="demoFormBO" id="demoFormBO_1" layout="3;C2(textArea)" text="细粒度组件布局示例" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
	</qeweb:form>
	
	<qeweb:form bind="demoFormBO" id="demoFormBO_2" layout="3;R3(C2(textArea))" text="细粒度组件布局示例" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:password bind="password" text="password"/>
	</qeweb:form>
</qeweb:page>