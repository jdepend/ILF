<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 持久化示例:提交时不执行前台校验-->
<qeweb:page bind="demoBtnModeBO3">
	<qeweb:commandButton name="submit" operate="submit" text="submit"/>			
	<qeweb:commandButton name="noSubmit" operate="submit" text="noSubmit"/>
	
	<qeweb:form id="form" bind="demoBtnModeBO3" text="form">
		<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
		<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:anchor bind="taxesCategories" text="noSubmit"/>
		<qeweb:commandButton name="submit" operate="submit" text="submit"/>			
		<qeweb:commandButton name="noSubmit" operate="noSubmit" text="noSubmit"/>
	</qeweb:form>
</qeweb:page>