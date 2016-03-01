<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="materialForm.formQuery">
    <qeweb:group relations="materialForm:materialList">
        <qeweb:form id="materialForm" bind="materialBO" queryRange="true">
            <qeweb:textField bind="materialCode" />
            <qeweb:textField bind="materialName" />
            <qeweb:select bind="materialStatus" />
            <%--
		    <qeweb:textField bind="materialType.typeName" text="com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.typeName">
			  	<qeweb:source bindBop="materialType.typeName:typeName;materialType.id:id" bindBo="materialTypeBO" sm="radio"/>
		    </qeweb:textField>
            <qeweb:hidden bind="materialType.id"/>
            --%>
            <qeweb:commandButton name="formQuery" operate="query" text="form.query"/>
            <qeweb:commandButton name="sysReset" operate="sysReset" />
			<qeweb:commandButton name="exp" operate="export" text="exp" />
        </qeweb:form>

        <qeweb:table id="materialList" bind="materialBO"
        	display="materialCode=table,view;materialName=table,view;
        	stockUnit=table,view;purchaseUnit=table,view;materialDesc=table,view;
        	materialSpec=table,view;materialStatus=table,view">
        	<qeweb:commandButton name="enableMaterial" operate="enableMaterial"/>
        	<qeweb:commandButton name="disableMaterial" operate="disableMaterial"/>
        	<qeweb:commandButton name="goImp" text="imp"/>
        	<qeweb:expend>
                <qeweb:commandButton name="view" operate="view"/>
            </qeweb:expend>
        	<%--
            <qeweb:expend>
                <qeweb:commandButton name="modifyMaterialType"/>
            </qeweb:expend>
             --%>
            <qeweb:hidden bind="id"/>
            <qeweb:textField bind="materialCode" />
            <qeweb:textField bind="materialName" />
            <%--
            <qeweb:textField bind="materialType.typeName" text="com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.typeName"/>
             --%>
            <qeweb:textField bind="stockUnit" />
            <qeweb:textField bind="purchaseUnit" />
            <qeweb:textArea bind="materialDesc" />
            <qeweb:textArea bind="materialSpec" />
            <qeweb:select bind="materialStatus" />
        </qeweb:table>
    </qeweb:group>
</qeweb:page>
