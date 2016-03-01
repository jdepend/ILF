<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 样式管理器示例 -->
<qeweb:page style="form=background-color:white">
	<qeweb:form bind="demoFormBO" id="demoFormBO_1" layout="3;C2(textArea,label,label2),C3(editor)" text="text型控件" icon="file">
		<qeweb:textField bind="textField" text="textField"/>
		<qeweb:password bind="password" text="password"/>
		<qeweb:textArea bind="textArea" text="textArea"/>
		<qeweb:label bind="label" text="label"/>
		<qeweb:label bind="label2" text="label2"/>
		<qeweb:editor bind="editor" text="editor"/>
		<qeweb:label bind="editor2" text="editor2" translate="true"/>
		<qeweb:commandButton name="save" operate="save" text="save"/>
	</qeweb:form>
</qeweb:page>