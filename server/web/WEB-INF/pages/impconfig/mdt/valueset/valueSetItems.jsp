<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="valueTable.sysFresh" bind="mdtValueSetBO">
	<qeweb:commandButton name="goback" text="form.back"/>
	
	<qeweb:group relations="valueSetForm:valueTable">
		<qeweb:form id="valueSetForm" bind="mdtValueSetBO" text="值集" param="required">
			<qeweb:label bind="code" />
			<qeweb:label bind="name" />
			<qeweb:hidden bind="id"/>
		</qeweb:form>
		<qeweb:table id="valueTable" bind="mdtValueBO" text="值">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update"/>
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:hidden bind="valueSet.id"/>
			<qeweb:textField bind="mdtValue" />
			<qeweb:textField bind="text"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
