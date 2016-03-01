<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>
<%@page import="com.qeweb.framework.common.UserContext"%>
<%--shortcut
<link rel="shortcut icon" href="<%=ctx %>/framework/images/project/lightbulb.png" />
 --%>

<%-- main.js: 系统登出/锁定/首选项等功能 --%>
<script type="text/javascript" src="<%=ctx %>/js/project/system/borderlayout/main.js"></script>

<qeweb:page>
	<qeweb:mainMenu bind="sysMenuBO" id="sysMenuBO" icon="folder" text="菜单" />
</qeweb:page>

<body>
	<div id="north">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			height="60"
			background="<%=ctx%>/framework/images/background/<%=themeType %>.png">
			<tr>
				<td style="padding-left: 15px"><img class="IEPNG" src="<%=ctx %>/framework/images/logo.png" /></td>
				<td style="padding-right: 5px">
					<table width="100%" border="0" cellpadding="0" cellspacing="3"
						class="banner">
						<tr align="right">
							<td><%=UserContext.getOrgBO().getOrgName()%>, <%=UserContext.getUserBO().getUserName()%>
								&nbsp;&nbsp;<span id="rTime"><span></td>
						</tr>
						<tr align="right">
							<td>
								<table border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td><div id="configDiv"></td>
										<td>&nbsp;</td>
										<td><div id="logoutDiv"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	<% String bottomMsg = DisplayType.getBottomMsg(); 
		if(bottomMsg == null || bottomMsg.equals(""))
			bottomMsg = "版权归属：苏州快维科技有限公司/上海匡维信息技术有限公司 电话:400-880-1018 苏ICP备B2-20110079号";
	%>
	<div id="south" align="center">
		<font size="2" color="#555555"><%=bottomMsg %></font>
	</div>
	
	<input type="hidden" id="sessionTicket" value="${sessionTicket}">
</body>

<script type="text/javascript">
	//添加"我的工作台"
	mainTabs.add({
		id : 'tab_id_workbench',
		title : '我的工作台',
		border : false,
		layout : 'fit',
		listeners : {
			'render' : function() {
				Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>' + I18N.COMMON_LOADING + '</span>', 'x-mask-loading');
			},
			'activate' : function() {
				Ext.getCmp('centerPanel').setTitle(this.title)
			}
		},
		html : "<iframe src='" + appConfig.ctx 
			+ "/system/workbench.action' onload=Ext.getCmp('centerPanel').getEl().unmask() style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>"
	});
	mainTabs.setActiveTab('tab_id_workbench');
	
	//显示系统时钟
	showTime();
	setInterval("showTime()", 1000);
</script>
