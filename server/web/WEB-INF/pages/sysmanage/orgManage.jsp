<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="orgForm.query">
	<qeweb:group relations="orgForm:orgTable">
		<qeweb:form id="orgForm" bind="organizationBO" queryRange="true">
			<qeweb:textField bind="orgCode" />
			<qeweb:textField bind="orgName" />
			<qeweb:textField bind="parentOrg.orgCode" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgCode"/>
			<qeweb:textField bind="parentOrg.orgName" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgName"/>
			<qeweb:radio bind="orgType" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="orgTable" bind="organizationBO"
			display="id=update; 
				orgCode=table,insert,update,view; 
				orgName=table,insert,update,view;
				orgEngName=insert,update,view;
				orgType=table,insert,update,view;
				parentOrg.orgCode=table,insert,update,view;
				parentOrg.orgName=table,insert,update,view;
				orgDesc=table,insert,update,view;
				parentOrg.id=insert,update;
				parentOrg.orgType=insert,update">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
				<qeweb:commandButton name="view" operate="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode"/>
			<qeweb:textField bind="orgName"/>
			<qeweb:textField bind="orgEngName"/>
			<qeweb:radio bind="orgType" />
			<qeweb:textField bind="parentOrg.orgCode" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgCode" />
			<qeweb:textField bind="parentOrg.orgName" text="com.qeweb.sysmanage.purview.bo.OrganizationBO.parentOrg.orgName">
				<qeweb:source bindBo="organizationBO" bindBop="parentOrg.id:id;parentOrg.orgCode:orgCode;parentOrg.orgName:orgName;parentOrg.orgType:orgType" 
					operate="organizationBO.toChoseOrg"/>
			</qeweb:textField>
			<qeweb:textArea bind="orgDesc"/>
			<qeweb:hidden bind="parentOrg.id"/>
			<qeweb:hidden bind="parentOrg.orgType"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

