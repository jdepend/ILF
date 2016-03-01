<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出页面6 关闭按钮执行方法 -->
<qeweb:page>
	<qeweb:form id="form" bind="demoPopPageBO6" text="关闭按钮执行方法">
		<qeweb:textField bind="orgCode" text="供应商编码"/>
		<qeweb:textField bind="orgName" text="供应商名称"/>
		<qeweb:select bind="orgType" text="组织类型" />
		<qeweb:anchor bind="freshCount" text="刷新次数"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>
