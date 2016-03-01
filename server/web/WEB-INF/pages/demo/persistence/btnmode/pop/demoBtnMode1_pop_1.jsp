<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="form" bind="demoBtnMod1_Pop_1" param="required" text="批量设置订购数量">
		<qeweb:label bind="selectOrders" text="待设置的订单数" />
		<qeweb:textField bind="orders" text="订购数量"/>
		<qeweb:hidden bind="poIds"/>
		<qeweb:commandButton name="save" operate="saveOrders" text="form.save" />
	</qeweb:form>
</qeweb:page>
