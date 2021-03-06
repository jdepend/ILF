<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoSelectOrgBO1" layout="2" param="required" text="表单接收参数">
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="table" bind="demoSelectOrgBO1" text="选择组织">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
