<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="materialTypeForm.formQuery">
	<qeweb:group relations="materialTypeForm:materialTypeTable">
        <qeweb:form id="materialTypeForm" bind="materialTypeBO" layout="2" queryRange="true" text="com.qeweb.busplatform.masterdata.bo.manage.selectMaterialType">
			<qeweb:textField bind="typeName"/>
			<qeweb:commandButton name="formQuery" operate="query" text="form.query" />
		</qeweb:form>
		<qeweb:table id="materialTypeTable" bind="materialTypeBO" text="com.qeweb.busplatform.masterdata.bo.manage.selectMaterialType">
			<qeweb:hidden bind="id"/>
            <qeweb:textField bind="typeCode" />
			<qeweb:textField bind="typeName" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
