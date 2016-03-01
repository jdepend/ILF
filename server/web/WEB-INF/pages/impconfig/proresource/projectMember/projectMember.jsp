<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page onLoad="memberForm.formQuery">
	<qeweb:group relations="memberForm:memberTable">
		<qeweb:form id="memberForm" bind="projectMemberBO" text="项目成员查询" queryRange="true" >
			<qeweb:textField bind="memberName" />
			<qeweb:select bind="memberRole" />
			<qeweb:commandButton name="formQuery" operate="query"/>
			<qeweb:commandButton name="sysReset" operate="sysReset"/>
		</qeweb:form>
		<qeweb:table id="memberTable" bind="projectMemberBO" text="项目成员列表" hasBbar="false" pageSize="10"
			display="id=table:update;
				memberCode=table:update,insert,view;
				memberName=table:update,insert,view;
				memberRole=table:update,insert,view;
				remark=table:update,insert,view">
			<qeweb:commandButton name="insert" operate="insert" />
			<qeweb:commandButton name="delete" operate="delete" />
			<qeweb:expend>
				<qeweb:commandButton name="update" operate="update" width="100"/>
				<qeweb:commandButton name="view" operate="view"/>
				<qeweb:commandButton name="resetPwd" operate="resetPwd" text="重置密码" />
			</qeweb:expend>
			<qeweb:hidden bind="id" />
			<qeweb:textField bind="memberCode" />
			<qeweb:textField bind="memberName" />
			<qeweb:checkBox bind="memberRole" width="200"/>
			<qeweb:textArea bind="remark" width="500"/>
		</qeweb:table>
	</qeweb:group>
</qeweb:page>
