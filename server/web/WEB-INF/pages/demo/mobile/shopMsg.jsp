<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="ShopBOForm.query">
	<qeweb:group relations="ShopBOForm:ShopBOTable">
		<qeweb:form id="ShopBOForm" bind="ShopBO" queryRange="true" text="门店信息" >
			<qeweb:textField bind="shopCode" text="门店编码" />
			<qeweb:textField bind="shopName" text="门店名称" />
			<qeweb:select bind="shopType" text="门店类型" />
			<qeweb:textField bind="shopMaster" text="店长" />
			<qeweb:select bind="province" text="省" />
			<qeweb:select bind="city" text="市" />
			<qeweb:select bind="area" text="区" />
			<qeweb:commandButton name="query" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="ShopBOTable" bind="ShopBO" text="门店信息">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width="50"/>
				<qeweb:commandButton name="view" operate="view" width="50"/>
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="shopCode" text="门店编码" />
			<qeweb:textField bind="shopName" text="门店名称" width="150"/>
			<qeweb:select bind="shopType" text="门店类型" />
			<qeweb:textField bind="shopMaster" text="店长" />
			<qeweb:textArea bind="address" text="门店地址" />
			<qeweb:textArea bind="remark" text="备注" />
			<qeweb:select bind="province" text="省" />
			<qeweb:select bind="city" text="市" />
			<qeweb:select bind="area" text="区" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>