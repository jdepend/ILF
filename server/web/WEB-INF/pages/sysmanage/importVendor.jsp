<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="importForm" bind="importVendorBO" layout="1">
		<qeweb:fileField bind="exlFile" operate="upload" text="com.qeweb.busplatform.common.imp.excel.ImpExl.exlFile"/>
        <qeweb:commandButton name="imp" operate="imp" text="imp"/>
	</qeweb:form>
</qeweb:page>
