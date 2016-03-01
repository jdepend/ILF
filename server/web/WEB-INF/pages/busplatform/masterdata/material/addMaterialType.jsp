<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page bind="materialTypeBO">
	<qeweb:form id="materialTypeBO" bind="materialTypeBO" text="" layout="1">
	    <qeweb:commandButton name="addMaterialType" operate="insert" text="save"/>
		<qeweb:textField bind="typeName"/>
		<qeweb:select bind="importantDegree"/>		
	    <qeweb:textField bind="parentTypeName">   
		  	<qeweb:source bindBop="parentTypeName:typeName;parentTypeId:id" bindBo="materialTypeBO" sm="radio"/>
	    </qeweb:textField>
		<qeweb:hidden bind="parentTypeId"/>
		<qeweb:textField bind="remarks"/>
	</qeweb:form>	
</qeweb:page>
