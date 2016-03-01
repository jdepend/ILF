<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="poForm.query">
	<qeweb:group relations="poForm:poTable">
		<qeweb:form id="poForm" bind="MDT_PurchaseOrder" queryRange="true">
			<qeweb:textField bind="poNO" />
			<qeweb:textField bind="vendor.code" />
			<qeweb:textField bind="vendor.name" />
			<qeweb:radio bind="publishStatus" />
			<qeweb:radio bind="confirmStatus" />
			<qeweb:radio bind="sendStatus" />
			<qeweb:radio bind="receiveStatus" />
			<qeweb:radio bind="closeStatus" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		
		<qeweb:table id="poTable" bind="MDT_PurchaseOrder">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="poNO" />
			<qeweb:textField bind="vendor.code" />
			<qeweb:textField bind="vendor.name" />
			<qeweb:radio bind="publishStatus" />
			<qeweb:radio bind="confirmStatus" />
			<qeweb:radio bind="sendStatus" />
			<qeweb:radio bind="receiveStatus" />
			<qeweb:radio bind="closeStatus" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>