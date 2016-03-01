<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="demoPresistenceBO3" layout="1" param="required" text="详细信息">
		<qeweb:textField bind="purchaseNo" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:dateField bind="purchaseTime" text="采购时间"/>
		<qeweb:select bind="publishStatus" text="发布状态" />
		<qeweb:select bind="confirmStatus" text="确认状态" />
	</qeweb:form>
</qeweb:page>
