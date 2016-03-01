<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="demoPopPageBO4" layout="1" param="required" text="页面刷新次数">
		<qeweb:label bind="freshCount" text="刷新次数"/>
		<qeweb:commandButton name="fresh" operate="fresh" icon="refresh" text="刷新"/>
	</qeweb:form>
</qeweb:page>
