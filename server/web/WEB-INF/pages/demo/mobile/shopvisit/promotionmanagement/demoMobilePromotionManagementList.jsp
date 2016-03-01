<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/pages/common/mapScript.jsp"%>

<%-- 促销执行管理 --%>
<qeweb:page bind="DemoMobilePromotionManagementBO" onLoad="queryForm.formQuery">
	<qeweb:group relations="queryForm:reulstList">
		<qeweb:form id="queryForm" bind="DemoMobilePromotionManagementBO" queryRange="true" layout="3;C3(comeInTime)">
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="visitor.userName" text="巡检员"/>
			<qeweb:expend>
				<qeweb:dateField bind="comeInTime" />
			</qeweb:expend>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
			<qeweb:commandButton name="formQuery" operate="query" />
			<qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="reulstList" bind="DemoMobilePromotionManagementBO">
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="showDesc" operate="DemoMobilePromotionManagementBO.showDesc" width="100" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
		    <qeweb:textField bind="roadShowActivity" />
		    <qeweb:textField bind="closeTableActivity" />
			<qeweb:textField bind="promotionActivity" />
			<qeweb:dateField bind="comeInTime" width="150"/>
			<qeweb:textField bind="visitor.userName" text="巡检员"/>
			<qeweb:anchor bind="locationStr" text="地址" width="200"/>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>