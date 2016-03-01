<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/pages/common/mapScript.jsp"%>

<%-- 信息反馈 --%>
<qeweb:page onLoad="DemoMobileInfoFeedbackForm.formQuery">
	<qeweb:group relations="DemoMobileInfoFeedbackForm:DemoMobileInfoFeedbackList">
		<qeweb:form id="DemoMobileInfoFeedbackForm" bind="DemoMobileInfoFeedbackBO" queryRange="true" layout="3;C3(comeInTime)">
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="visitor.userName" text="巡检员"/>
			<qeweb:expend>
				<qeweb:dateField bind="comeInTime"/>
			</qeweb:expend>
			<qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="DemoMobileInfoFeedbackList" bind="DemoMobileInfoFeedbackBO">
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="showDesc" operate="DemoMobileInfoFeedbackBO.showDesc" width="100" text="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="shopBO.shopCode" text="门店编码"/>
			<qeweb:textField bind="shopBO.shopName" text="门店名称"/>
			<qeweb:textField bind="competitiveInfo" />
			<qeweb:textField bind="marketInfo" />
			<qeweb:textField bind="priceFeedback" />
			<qeweb:dateField bind="comeInTime" width="150"/>
			<qeweb:textField bind="visitor.userName" text="巡检员"/>
			<qeweb:anchor bind="locationStr" text="地址" width="200"/>
		    <qeweb:select bind="shopBO.province" text="省"/>
			<qeweb:select bind="shopBO.city" text="市"/>
			<qeweb:select bind="shopBO.area" text="区"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>