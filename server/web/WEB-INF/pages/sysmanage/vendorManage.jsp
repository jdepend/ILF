<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="orgForm.query">
	<qeweb:group relations="orgForm:orgTable">
		<qeweb:form id="orgForm" bind="vendorBO" queryRange="true" text="com.qeweb.sysmanage.purview.bo.org.VendorBO">
			<qeweb:textField bind="orgCode" text="com.qeweb.sysmanage.purview.bo.org.VendorBO.orgCode"/>
			<qeweb:textField bind="orgName" text="com.qeweb.sysmanage.purview.bo.org.VendorBO.orgName"/>
			<qeweb:radio bind="orgType" />
			<qeweb:commandButton name="query" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
			<qeweb:commandButton name="exp" operate="export" text="exp" />
		</qeweb:form>
		<qeweb:table id="orgTable" bind="vendorBO"
			display="id=update; 
				orgCode=table,insert,update,view; 
				orgName=table,insert,update,view;
				orgEngName=insert,update,view;
				orgDesc=table,insert,update,view"
			text="com.qeweb.sysmanage.purview.bo.org.VendorBO">
			<qeweb:commandButton name="insert" operate="insert"/>
			<qeweb:commandButton name="delete" operate="delete"/>
        	<qeweb:commandButton name="goImp" text="imp"/>
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
				<qeweb:commandButton name="view" operate="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id"/>
			<qeweb:textField bind="orgCode" text="com.qeweb.sysmanage.purview.bo.org.VendorBO.orgCode"/>
			<qeweb:textField bind="orgName" text="com.qeweb.sysmanage.purview.bo.org.VendorBO.orgName"/>
			<qeweb:textField bind="orgEngName"/>
			<qeweb:textArea bind="orgDesc"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>

