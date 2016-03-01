<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="materialTypeForm.formQuery">
	<qeweb:group relations="materialTypeForm:materialTypeTable">
            <qeweb:form id="materialTypeForm" bind="materialTypeBO" layout="3" queryRange="true" >
			<qeweb:textField bind="typeCode"/>
			<qeweb:textField bind="typeName"/>
            <qeweb:select bind="importantDegree" />
            <qeweb:commandButton name="formQuery" operate="query" text="form.query"/>
            <qeweb:commandButton name="sysReset" operate="sysReset" />
		</qeweb:form>
		<qeweb:table id="materialTypeTable" bind="materialTypeBO">
			<qeweb:commandButton name="insert" text="insert"/>
			<qeweb:expend>
                <qeweb:commandButton name="update" text="update"/>
                <qeweb:commandButton name="delete" text="delete"/>
            </qeweb:expend>
			<qeweb:hidden bind="id"/>
            <qeweb:textField bind="typeCode" />
			<qeweb:textField bind="typeName" />
            <qeweb:select bind="importantDegree" />
            <qeweb:textField bind="remarks"/>
            <qeweb:hidden bind="parentTypeId"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
