<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 查看SQL --%>
<qeweb:page>
	<qeweb:form id="phyAllBO" bind="phyAllBO" text="查看SQL" layout="1" param="required">
		<qeweb:textArea bind="sql" height="290" text="sql"/>
	</qeweb:form>
</qeweb:page>