<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="com.qeweb.framework.impconfig.promember.bo.ProjectMemberBO"%>
<%@page import="com.qeweb.framework.common.UserContext"%>
<%@page import="com.qeweb.framework.impconfig.common.util.pathutil.ProjectInfoUtil"%>

<%
	ProjectMemberBO proMember = UserContext.getProjectMemberBO();
%>
<script type="text/javascript">
	Ext.onReady(function(){
		var bd = Ext.getBody();
		var date = new Ext.Button({
			text : ''
		});
		var logoPath = "/framework/images/preference/theme/logo/<%=DisplayType.getThemeType() %>.jpg";
		if(<%=DisplayType.isCustomLogo()%>)
			logoPath="<%=DisplayType.getLogoPath()%>";
			
		var p = new Ext.Panel({
			html : "<img src='" + appConfig.ctx + logoPath + "' width='100%' height='100%'>",
			width : bd.getWidth(),
			height : bd.getHeight(),
			bbar : [date, '->', '【开发版】&nbsp;&nbsp;'
				+ '当前SVN版本号:<%=ProjectInfoUtil.getSvnLatestRevision()%>&nbsp;&nbsp;&nbsp;&nbsp;' 
				+ I18N.SYSTEM_CURRENT_USER + ':<%=proMember.getMemberName()%>', '&nbsp;&nbsp;',
				'&nbsp;&nbsp;',
				{
					xtype : 'button',
					pressed : true,
					tooltip : I18N.SYSTEM_EXIT,
					text : ' &nbsp;' + I18N.SYSTEM_EXIT + '&nbsp;',
					handler : function() {
						window.parent.location.href = "<%=ctx %>/system/login!logout.action";
					}
				},
			'&nbsp;']
		});
		p.render(bd);
		
		Ext.TaskMgr.start({
			run : function() {
				date.setText("<span style='font-size: 12px;'>" + new Date().toLocaleString() + "</span>")
			},
			interval : 1000
		});
	});
</script>

