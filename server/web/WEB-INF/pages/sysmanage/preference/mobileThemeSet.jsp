<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<%-- 终端个性化设置 --%>
<qeweb:page>
	<qeweb:form id="preferenceSetMobileForm" bind="preferenceSetMobileBO" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO">
		<qeweb:radio bind="themeType" text="com.qeweb.sysmanage.preference.bo.PreferenceSetBO.themeType"/>
		<qeweb:img bind="themeImg" text=""/>
		<qeweb:hidden bind="id"/>
		<qeweb:commandButton name="save" operate="save" text="form.save"/>
	</qeweb:form>
</qeweb:page>