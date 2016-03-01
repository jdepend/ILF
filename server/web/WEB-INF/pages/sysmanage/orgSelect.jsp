<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="orgForm.query">
	<qeweb:group relations="orgForm:orgTable">
		<qeweb:form id="orgForm" bind="organizationBO" queryRange="true" layout="2">
			<qeweb:textField bind="orgCode" />
			<qeweb:textField bind="orgName" />
			<qeweb:textField bind="parentOrg.orgCode" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgCode"/>
			<qeweb:textField bind="parentOrg.orgName" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgName"/>
			<qeweb:radio bind="orgType" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="orgTable" bind="organizationBO">
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode"/>
			<qeweb:textField bind="orgName"/>
			<qeweb:radio bind="orgType" />
			<qeweb:textField bind="parentOrg.orgCode" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgCode"/>
			<qeweb:textField bind="parentOrg.orgName" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgName"/>
			<qeweb:textArea bind="orgDesc"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

