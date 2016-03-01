<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 弹出页面5 目标页面关闭不执行操作 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form id="form" bind="demoPopPageBO5" text="弹出页面关闭后不刷新组件">
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
			<qeweb:anchor bind="freshCount" text="刷新次数"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="table" bind="demoPopPageBO5" text="弹出页面关闭后不刷新组件">
			<qeweb:commandButton name="insert" operate="demoPopPageBO5.toPage" text="新增" icon="add"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="demoPopPageBO5.toPage" text="修改" icon="edit"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode" text="供应商编码"/>
			<qeweb:textField bind="orgName" text="供应商名称"/>
			<qeweb:select bind="orgType" text="组织类型" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
