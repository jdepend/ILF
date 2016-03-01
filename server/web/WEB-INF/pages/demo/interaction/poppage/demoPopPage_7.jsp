<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出页面7 关闭后刷新全局按钮 -->
<qeweb:page bind="demoPopPageBO7">
	<qeweb:commandButton name="btn1" text="btn1"/>
	<qeweb:commandButton name="btn2" text="btn2"/>
	<qeweb:commandButton name="btn3" operate="demoPopPageBO7.toPage" text="弹出页面"/>
	
	<qeweb:form id="form" bind="demoPopPageBO7" text="关闭后刷新全局按钮">
		<qeweb:textField bind="orgCode" text="供应商编码"/>
		<qeweb:textField bind="orgName" text="供应商名称"/>
		<qeweb:select bind="orgType" text="组织类型" />
		<qeweb:anchor bind="freshCount" text="刷新次数"/>
		<qeweb:commandButton name="sysReset" operate="sysReset"/>
	</qeweb:form>
</qeweb:page>
