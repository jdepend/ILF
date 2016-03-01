<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="demoPopPageBO5" layout="1" param="required" text="组织信息">
		<qeweb:hidden bind="id"/>
		<qeweb:textField bind="orgCode" text="供应商编码"/>
		<qeweb:textField bind="orgName" text="供应商名称"/>
		<qeweb:select bind="orgType" text="组织类型" />
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>
