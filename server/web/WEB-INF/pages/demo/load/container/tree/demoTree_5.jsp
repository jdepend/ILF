<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 树示例:下拉树 -->
<qeweb:page onLoad="form.query">
	<qeweb:group relations="form:table">
		<qeweb:form bind="demoTreeBO5" id="form" text="下拉树">
			<qeweb:select bind="parentCode" text="上级部门"/>
			<qeweb:commandButton name="save" operate="save" text="form.save"/>
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="reset" operate="sysReset" />
		</qeweb:form>
		
		<qeweb:table bind="demoTreeBO5" id="table" text="弹出框中可使用下拉树">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" width="150" text="采购单号"/>
			<qeweb:textField bind="vendor.orgCode" text="供应商编码"/>
			<qeweb:textField bind="vendor.orgName" text="供应商名称"/>
			<qeweb:select bind="parentCode" text="上级部门"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>