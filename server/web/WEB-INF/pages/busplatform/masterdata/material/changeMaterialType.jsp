<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="materialBO">
    <qeweb:form id="materialBO" bind="materialBO" param="required" layout="1" text="">
	    <qeweb:hidden bind="id"/>
        <qeweb:label bind="materialCode" />
        <qeweb:label bind="materialName" />
	    <qeweb:textField bind="materialType.typeName" text="com.qeweb.busplatform.masterdata.bo.manage.BP_MaterialBO.typeName">   
		  	<qeweb:source bindBop="materialType.typeName:typeName;materialType.id:id" bindBo="materialTypeBO" sm="radio"/>
	    </qeweb:textField>
	    <qeweb:hidden bind="materialType.id"/>
	    <qeweb:commandButton name="update" operate="modifyMaterialType" text="update"/>
	</qeweb:form>	
</qeweb:page>
