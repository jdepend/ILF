<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<!-- 变量管理 -->
<qeweb:page onLoad="varForm.formQuery">
	<qeweb:group relations="varForm:varTable">
		<qeweb:form id="varForm" bind="varBO" text="变量查询" queryRange="true" >
			<qeweb:textField bind="name" />
			<qeweb:textField bind="alias" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="varTable" bind="varBO" 
			display="id=update;moduleId=table,insert,update,view;
				name=table,insert,update,view;
				alias=table,insert,update,view;
				scop=table,insert,update,view;
				enable=table,insert,update,view;
				canmodify=table,insert,update,view;
				candelete=table,insert,update,view;
				valueSetCode=table,insert,update,view;
				defValue=table,insert,update,view;
				remark=table,insert,update,view"
			text="变量列表" win="view:height=450;add:height=450;update:height=450;">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width="100"/>
				<qeweb:commandButton name="view" operate="view"/>
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:select bind="moduleId" />
			<qeweb:textField bind="name" />
			<qeweb:textField bind="alias" />
			<qeweb:select bind="scop"/>
			<qeweb:radio bind="enable"/>
			<qeweb:radio bind="canmodify"/>
			<qeweb:radio bind="candelete"/>
			<qeweb:textField bind="valueSetCode">
				<qeweb:source bindBo="mdtValueSetBO" bindBop="valueSetCode:code" sm="checkbox" />
			</qeweb:textField>
			<qeweb:textField bind="defValue" text="默认值"/>
			<qeweb:textArea bind="remark"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
