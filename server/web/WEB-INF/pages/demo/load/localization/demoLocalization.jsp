<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 国际化示例 -->
<qeweb:page>
	<qeweb:form bind="demoLocalizationBO" id="demoLocalizationBO" layout="3;C2(textArea)" icon="file">
		<qeweb:textField bind="textField"/>
		<qeweb:label bind="label"/>
		<qeweb:password bind="password"/>
		<qeweb:textArea bind="textArea"/>
		<qeweb:commandButton name="changeToEn" operate="changeToEn" icon="syn"/>
		<qeweb:commandButton name="changeToZH" operate="changeToZH" icon="syn"/>
	</qeweb:form>
</qeweb:page>