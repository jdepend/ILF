<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<!-- 收货记录 -->
<qeweb:page onLoad="bp_poForm.query">
	<qeweb:group relations="bp_poForm:bp_poTable">
		<qeweb:form id="bp_poForm" bind="bp_PurchaseOrderBO">
			<qeweb:textField bind="purchaseNo" />
			<%--
			<qeweb:textField bind="vendor.code" />
			<qeweb:textField bind="vendor.name" />
			<qeweb:radio bind="sendStatus" />
			--%>
			<qeweb:radio bind="publishStatus" />
			<qeweb:radio bind="confirmStatus" />
			<qeweb:radio bind="closeStatus" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>

		<qeweb:table id="bp_poTable" bind="bp_PurchaseOrderBO">
			<qeweb:expend>
				<qeweb:commandButton name="view" operate="bp_PurchaseOrderBO.viewDetial" text="com.qeweb.busplatform.bo.BP_PurchaseOrderBO.view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="purchaseNo" />
			<%--
			<qeweb:textField bind="vendor.code" />
			<qeweb:textField bind="vendor.name" />
			<qeweb:radio bind="sendStatus" />
			 --%>
			<qeweb:radio bind="publishStatus" />
			<qeweb:radio bind="confirmStatus" />
			<qeweb:radio bind="closeStatus" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>