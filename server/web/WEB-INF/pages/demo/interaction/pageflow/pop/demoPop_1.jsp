<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 页面流示例1 -->
<qeweb:page bind="demoPageflowBO1">
	<qeweb:commandButton name="back" text="form.back"/>
	<qeweb:form id="form" bind="demoPageflowBO1" param="required" text="">
		<qeweb:label bind="purchaseNo" text="采购单号"/>
		<qeweb:label bind="vendor.orgCode" text="供应商编码"/>
		<qeweb:label bind="publishStatus" text="发布状态" />
		<qeweb:label bind="country" text="国家" align="center"/>
		<qeweb:label bind="province" text="省份" align="center"/>
		<qeweb:label bind="city" text="城市" align="center"/>
		<qeweb:label bind="purchaseTime" text="采购时间"/>
		<qeweb:fileField bind="multiFile" text="多文件上传" />
		<qeweb:fileField bind="multiFile2" text="多文件上传2" operate="upload"/>
	</qeweb:form>
</qeweb:page>
