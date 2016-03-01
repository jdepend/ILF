<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出页面2 目标页面关闭后刷新源页面 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoPopPageBO3" text="关闭后刷新源页面">
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
			<qeweb:anchor bind="freshCount" text="刷新次数"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoPopPageBO3" sm="empty" text="结果列表">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
