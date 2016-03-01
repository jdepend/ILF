<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<qeweb:page>
	<qeweb:form id="userBO" bind="userBO" layout="1" header="false">
		<qeweb:label bind="userCode"/>
		<qeweb:label bind="userName"/>
		<qeweb:password bind="password"/>
		<qeweb:password bind="newPassword"/>
		<qeweb:password bind="newPasswordAgain"/>
		<qeweb:commandButton name="modifyPwd" operate="modifyPwd,relogin" text="form.save" icon="save"/>
	</qeweb:form>
</qeweb:page>

<script type="text/javascript">
	//密码修改后重新登录
	function relogin() {
		Ext.Msg.alert(I18N.CONFIRM_TIPS, I18N.ALERT_LOGOUT, function() {
			parent.window.location.href = appConfig.ctx + "/system/login!logout.action";
		});
		
		return true;
	}
</script>