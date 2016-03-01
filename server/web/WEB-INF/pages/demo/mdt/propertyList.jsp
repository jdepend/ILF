<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="propertyBO.sysFresh">
	<qeweb:table bind="propertyBO" id="propertyBO">
		<qeweb:commandButton name="insert" text="新增"/>
		<qeweb:expend>
			<qeweb:commandButton name="showDesc" operate="propertyBO.showDesc" text="查看"/>
		</qeweb:expend>
		<qeweb:hidden bind="id"/>
		<qeweb:radio bind="type" />
		<qeweb:textField bind="amount" />
	</qeweb:table>
</qeweb:page>
