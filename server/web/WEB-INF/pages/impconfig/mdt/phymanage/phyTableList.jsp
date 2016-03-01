<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 物理表管理首页 --%>
<qeweb:page onLoad="phyFormBO.query">
	<qeweb:group relations="phyFormBO:phyTableBO">
		<qeweb:form id="phyFormBO" bind="phyTableBO" text="物理表管理" queryRange="true">
			<qeweb:textField bind="name" text="物理表名称"/>
			<qeweb:textField bind="alias" text="物理表别名"/>
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="phyTableBO" bind="phyTableBO" text="物理表管理" sort="sort:name">
			<qeweb:commandButton name="insert" text="insert" icon="add"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" text="update" width="50"/>
				<qeweb:commandButton name="keySetting" text="主外键设置" width="100"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="name" text="物理表名称" width="200"/>
			<qeweb:textField bind="alias" text="物理表别名" width="200"/>
			<qeweb:textArea bind="remark" text="备注" width="500"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>