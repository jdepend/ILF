<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="valueSetForm.formQuery">
	<qeweb:group relations="valueSetForm:valueSetTable">
		<qeweb:form id="valueSetForm" bind="mdtValueSetBO" text="值集查询" queryRange="true" >
			<qeweb:textField bind="code" />
			<qeweb:textField bind="name" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="valueSetTable" bind="mdtValueSetBO" text="值集列表">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="itemManage" operate="mdtValueSetBO.view" text="值管理"/>
				<qeweb:commandButton name="update" operate="update"/>	
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="code" />
			<qeweb:textField bind="name" />
			<qeweb:textArea bind="remark" />
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
